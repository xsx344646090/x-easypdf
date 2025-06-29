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
package org.dromara.pdf.pdfbox.support.linearization;


import org.apache.pdfbox.cos.*;
import org.dromara.pdf.pdfbox.util.IdUtil;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

        final COSDictionary root = this.getTrailer().getCOSDictionary(COSName.ROOT);
        final COSDictionary pages = root.getCOSDictionary(COSName.PAGES);
        final COSArray pagesKids = pages.getCOSArray(COSName.KIDS);

        int pagesCount = 0;
        final COSDictionary docRoot = doc.getTrailer().getCOSDictionary(COSName.ROOT);
        final COSDictionary docPages = docRoot.getCOSDictionary(COSName.PAGES);
        COSArray docPageArray = docPages.getCOSArray(COSName.KIDS);
        for (COSBase page : docPageArray) {
            ((COSDictionary) ((COSObject) page).getObject()).setItem(COSName.PARENT, pages);
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
        final COSString firstID = new COSString(IdUtil.get());
        final COSArray idArray = new COSArray();

        idArray.add(firstID);
        trailer.setItem(COSName.ID, idArray);
    }
}
