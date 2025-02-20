/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dromara.pdf.pdfbox.support.linearizer;


import org.apache.pdfbox.cos.*;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


/**
 * Represents a blank PDF document to which parsed COSDocuments may be appended.
 *
 * @author Marian Gavalier
 * @version $Id$
 */
public class SimplifiedPDFDocument extends COSDocument implements Closeable {
    //~ Static fields/initializers --------------------------------------------------------------------------------------------------------------------

    private static final long DUMMY_MAX_VALUE_LONG = 1111111111111111111L;
    private static final int DUMMY_MAX_VALUE_INT = 1111111111;

    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------
    // This is required to retain the added documents
    private final COSDocument wrappedDocument;
    private int pageCount = 0;

    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------´

    /**
     * Default constructor
     *
     * @param doc
     * @throws IOException
     */
    public SimplifiedPDFDocument(COSDocument doc) throws IOException {
        super();
        this.wrappedDocument = doc;
        this.integrateDocument(doc);
        this.setVersion(Math.max(doc.getVersion(), 1.6F));
    }

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    private static COSObject createPagesObjekt() throws IOException {
        final COSDictionary pagesDict = new COSDictionary();

        pagesDict.setItem(COSName.TYPE, COSName.PAGES);
        pagesDict.setInt(COSName.COUNT, DUMMY_MAX_VALUE_INT);  // correct later!
        pagesDict.setItem(COSName.KIDS, new COSArray());

        return new COSObject(pagesDict, new COSObjectKey(DUMMY_MAX_VALUE_LONG + 1, 0));
    }

    /**
     * Helper to get kids from malformed PDFs.
     *
     * @param node page tree node
     * @return list of kids
     */
    private static List<COSObject> getKids(final COSObject node) {
        final List<COSObject> result = new ArrayList<>();
        final COSArray kids;

        COSBase base = node.getObject();
        if (base instanceof COSDictionary) {
            kids = ((COSDictionary) base).getCOSArray(COSName.KIDS);
        } else {
            kids = null;
        }

        if (kids == null) {
            // probably a malformed PDF
            return result;
        }

        for (int i = 0, size = kids.size(); i < size; i++) {
            result.add((COSObject) kids.get(i));
        }

        return result;
    }

    /**
     * Returns true if the node is a page tree node (i.e. and intermediate).
     *
     * @param node [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    private static boolean isPageTreeNode(final COSObject node) {
        final COSDictionary dict = (COSDictionary) node.getObject();

        // some files such as PDFBOX-2250-229205.pdf don't have Pages set as the Type, so we have
        // to check for the presence of Kids too
        return ((dict.getCOSName(COSName.TYPE) == COSName.PAGES) || dict.containsKey(COSName.KIDS));
    }

    public int getPageCount() {
        return this.pageCount;
    }

    @Override
    public void close() throws IOException {
        super.close();
        wrappedDocument.close();
    }

    /**
     * Appends the COSDocument specified
     *
     * @param doc doc
     * @return number of pages appended
     * @throws IOException IO error
     */
    private int integrateDocument(final COSDocument doc) throws IOException {
        if (this.getTrailer() == null) {
            this.setTrailer(createDocumentTrailer());
        }

        final COSObject root = (COSObject) this.getTrailer().getItem(COSName.ROOT);
        final COSDictionary pages = ((COSDictionary) root.getObject()).getCOSDictionary(COSName.PAGES);
        final COSArray pagesKids = (COSArray) pages.getItem(COSName.KIDS);

        final COSObject docRoot = (COSObject) doc.getTrailer().getItem(COSName.ROOT);
        final PageIterator iter = new PageIterator(((COSDictionary) docRoot.getObject()).getCOSObject(COSName.PAGES));
        int pagesCount = 0;

        for (final COSObject page : iter) {
            ((COSDictionary) page.getObject()).setItem(COSName.PARENT, pages);
            pagesKids.add(page);
            pagesCount++;
        }
        pages.setInt(COSName.COUNT, pagesKids.size());
        this.pageCount += pagesCount;
        return pagesCount;
    }

    private COSObject createDocumentRoot() throws IOException {
        final COSDictionary rootDict = new COSDictionary();

        rootDict.setDirect(true);
        rootDict.setItem(COSName.TYPE, COSName.CATALOG);
        rootDict.setItem(COSName.PAGES, createPagesObjekt());

        return new COSObject(rootDict, new COSObjectKey(DUMMY_MAX_VALUE_LONG, 0));
    }

    private COSDictionary createDocumentTrailer() throws IOException {
        final COSDictionary trailerDict = new COSDictionary();

        trailerDict.setDirect(true);
        trailerDict.setInt(COSName.SIZE, DUMMY_MAX_VALUE_INT);  // correct later
        trailerDict.setItem(COSName.ROOT, createDocumentRoot());
        this.addIDToTrailer(trailerDict);
        return trailerDict;
    }

    /**
     * Updates the passed trailer with a new ID. The ID generated does not take
     * all seed data recommended by Adobe as it does not necessarily exist yet,
     * but it uses the current time, a Salt and this objet's java hashCode.
     *
     * @param trailer The trailer to which the ID should be added
     */
    void addIDToTrailer(final COSDictionary trailer) {
        final MessageDigest md5;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // should never happen
            throw new RuntimeException(e);
        }

        // algorithm says to use time/path/size/values in doc to generate the id.
        // we don't have path or size, so do the best we can
        md5.update((Long.toString(System.currentTimeMillis()) + "PDF_BOX_SALT_STRING" + this.hashCode()).getBytes(StandardCharsets.ISO_8859_1));

        final COSDictionary info = (COSDictionary) trailer.getDictionaryObject(COSName.INFO);

        if (info != null) {
            info.getValues().forEach((cosBase) -> md5.update(cosBase.toString().getBytes(StandardCharsets.ISO_8859_1)));
        }

        final COSString firstID = new COSString(md5.digest());
        final COSArray idArray = new COSArray();

        idArray.add(firstID);
        idArray.add(firstID);
        trailer.setItem(COSName.ID, idArray);
    }

    //~ Inner Classes ---------------------------------------------------------------------------------------------------------------------------------

    /**
     * Iterator which walks all pages in the tree, in order.
     *
     * @author JRA
     * @version $Id$
     */
    private static final class PageIterator implements Iterator<COSObject>, Iterable<COSObject> {
        //~ Instance members ---------------------------------------------------------------------------------------------------------------------------

        private final Queue<COSObject> queue = new ArrayDeque<>();

        //~ Constructors -------------------------------------------------------------------------------------------------------------------------------

        /**
         * [!CONSTR_DESCIRPTION_FOR_PageIterator!]
         *
         * @param node [!PARA_DESCRIPTION!]
         */
        private PageIterator(final COSObject node) {
            enqueueKids(node);
        }

        //~ Methods ------------------------------------------------------------------------------------------------------------------------------------

        private void enqueueKids(final COSObject node) {
            if (isPageTreeNode(node)) {
                final List<COSObject> kids = getKids(node);

                kids.forEach(kid -> enqueueKids(kid));
            } else {
                queue.add(node);
            }
        }


        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }


        @Override
        public COSObject next() {
            final COSObject next = queue.poll();

            return next;
        }


        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }


        @Override
        public Iterator<COSObject> iterator() {
            return this;
        }
    }

    /**
     * * end pages Enumeration ***************************
     */
}
