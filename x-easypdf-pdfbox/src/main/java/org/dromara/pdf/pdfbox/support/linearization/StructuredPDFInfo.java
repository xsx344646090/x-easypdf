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

import java.io.IOException;
import java.util.*;


/**
 * [!CLASS_DESCRIPTION!]
 *
 * @author Jonathan Rahn
 * @version $Id$
 */
class StructuredPDFInfo {
    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!FIELD_DESCRIPTION!]
     */
    final List<COSBase> part4 = new ArrayList<>();
    /**
     * [!FIELD_DESCRIPTION!]
     */
    final List<COSBase> part6 = new ArrayList<>();
    /**
     * [!FIELD_DESCRIPTION!]
     */
    final List<COSBase> part7 = new ArrayList<>();
    /**
     * [!FIELD_DESCRIPTION!]
     */
    final List<COSBase> part8 = new ArrayList<>();
    /**
     * [!FIELD_DESCRIPTION!]
     */
    final List<COSBase> part9 = new ArrayList<>();
    /**
     * [!FIELD_DESCRIPTION!]
     */
    final CHSharedObject sharedObjectData = new CHSharedObject();
    /**
     * [!FIELD_DESCRIPTION!]
     */
    final HGeneric outlineData = new HGeneric();
    /**
     * [!FIELD_DESCRIPTION!]
     */
    final BidirectionalMap<Integer, COSBase> indexObjectMap = new BidirectionalMap<>();
    private final COSDocument document;
    private final List<COSObject> pages;
    /**
     * [!FIELD_DESCRIPTION!]
     */
    CHPageOffset pageOffsetData;
    /**
     * [!FIELD_DESCRIPTION!]
     */
    BidirectionalMultiMap<ObjUser, COSBase> userObjectMap;

    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!CONSTR_DESCIRPTION_FOR_StructuredPDFInfo!]
     *
     * @param doc [!PARA_DESCRIPTION!]
     */
    StructuredPDFInfo(final COSDocument doc) {
        this.document = doc;
        this.pages = new ArrayList<>();
    }

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @return [!RETURN_DESCRIPTION!]
     */
    int sizeSecondPart() {
        return this.part7.size() + this.part8.size() + this.part9.size() + 2;
    }


    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @return [!RETURN_DESCRIPTION!]
     */
    COSBase getPart4EndMarker() {
        return this.part4.get(this.part4.size() - 1);
    }


    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @return [!RETURN_DESCRIPTION!]
     */
    COSBase getPart6EndMarker() {
        return this.part6.get(this.part6.size() - 1);
    }

    List<COSObject> getPages() {
        return this.pages;
    }


    /**
     * * This function calculates the ordering of objects, divides them into the
     * appropriate parts, and computes some values for the linearization
     * parameter dictionary and hint tables.
     *
     * @throws IOException [!EXC_DESCRIPTION!]
     */
    void getLinearizedParts() throws IOException {
        // Separate objects into the categories sufficient for us to
        // determine which part of the linearized file should contain the
        // object.  This categorization is useful for other purposes as
        // well.  Part numbers refer to version 1.4 of the PDF spec.

        // Parts 1, 3, 5, 10, and 11 don't contain any objects from the
        // original file (except the trailer dictionary in part 11).
        // Part 4 is the document catalog (root) and the following root
        // keys: /ViewerPreferences, /PageMode, /Threads, /OpenAction,
        // /AcroForm, /Encrypt.  Note that Thread information dictionaries
        // are supposed to appear in part 9, but we are disregarding that
        // recommendation for now.
        // Part 6 is the first page section.  It includes all remaining
        // objects referenced by the first page including shared objects
        // but not including thumbnails.  Additionally, if /PageMode is
        // /Outlines, then information from /Outlines also appears here.
        // Part 7 contains remaining objects private to pages other than
        // the first page.
        // Part 8 contains all remaining shared objects except those that
        // are shared only within thumbnails.
        // Part 9 contains all remaining objects.
        // We sort objects into the following categories:
        // * open_document: part 4
        // * first_page_private: part 6
        // * first_page_shared: part 6
        // * other_page_private: part 7
        // * other_page_shared: part 8
        // * thumbnail_private: part 9
        // * thumbnail_shared: part 9
        // * other: part 9
        // * outlines: part 6 or 9
        final COSObject root = (COSObject) document.getTrailer().getItem(COSName.ROOT);
        boolean outlines_in_first_page = false;
        final COSBase pagemode = ((COSDictionary) root.getObject()).getDictionaryObject(COSName.PAGE_MODE);

        if (pagemode instanceof COSName) {
            if (((COSName) pagemode).getName().equalsIgnoreCase("/UseOutlines")) {
                if (((COSDictionary) root.getObject()).getDictionaryObject(COSName.OUTLINES) != null) {
                    outlines_in_first_page = true;
                } else {
                    // UseOutlines aber keine Outlines!!!
                }
            }
        }

        // hauptdokument elemente
        final List<COSName> open_document_keys = new ArrayList<>();

        open_document_keys.add(COSName.VIEWER_PREFERENCES);
        open_document_keys.add(COSName.PAGE_MODE);
        open_document_keys.add(COSName.THREADS);
        open_document_keys.add(COSName.OPEN_ACTION);
        open_document_keys.add(COSName.ACRO_FORM);

        final PreLinearizationOptimizer optimizer = new PreLinearizationOptimizer(this.document);

        this.userObjectMap = optimizer.optimize(this.pages);

        final List<COSBase> lc_open_document = new ArrayList<>();
        final List<COSBase> lc_first_page_private = new ArrayList<>();
        final List<COSBase> lc_first_page_shared = new ArrayList<>();
        final Set<COSBase> lc_other_page_private = new HashSet<>();
        final List<COSBase> lc_other_page_shared = new ArrayList<>();
        final List<COSBase> lc_thumbnail_private = new ArrayList<>();
        final List<COSBase> lc_thumbnail_shared = new ArrayList<>();
        final List<COSBase> lc_other = new ArrayList<>();
        final List<COSBase> lc_outlines = new ArrayList<>();
        final ArrayList<COSBase> lc_root = new ArrayList<>();

        traverseElements(open_document_keys, lc_root, lc_outlines, lc_open_document, lc_first_page_private, lc_first_page_shared, lc_other_page_private, lc_other_page_shared, lc_thumbnail_private, lc_thumbnail_shared, lc_other);

        initData(lc_root, lc_open_document);

        processPart6(lc_first_page_private, lc_first_page_shared, outlines_in_first_page, lc_outlines);
        processPart7(lc_other_page_private);

        processPart8(lc_other_page_shared);

        processPart9(lc_other, lc_thumbnail_private, lc_thumbnail_shared, outlines_in_first_page, lc_outlines);

        gatherSharedHintTable();
    }


    private void initData(final ArrayList<COSBase> lc_root, final List<COSBase> lc_open_document) throws ArithmeticException {
        // Generate ordering for objects in the output file.  Sometimes we
        // just dump right from a set into a vector.  Rather than
        // optimizing this by going straight into the vector, we'll leave
        // these phases separate for now.  That way, this section can be
        // concerned only with ordering, and the above section can be
        // considered only with categorization.  Note that sets of
        // COSBases are sorted by COSBase.  In a linearized file,
        // objects appear in sequence with the possible exception of hints
        // tables which we won't see here anyway.  That means that running
        // calculateLinearizationData() on a linearized file should give
        // results identical to the original file ordering.
        // We seem to traverse the page tree a lot in this code, but we
        // can address this for a future code optimization if necessary.
        // Premature optimization is the root of all evil.
      /*
      Vector<COSBase> pages;
      { // local scope
      // Map all page objects to the containing object stream.  This
      // should be a no-op in a properly linearized file.
      std::vector<QPDFObjectHandle> t = getAllPages();
      for (std::vector<QPDFObjectHandle>::iterator iter = t.begin();
      iter != t.end(); ++iter)
      {
      pages.push_back(getUncompressedObject(*iter, object_stream_data));
      }
      }
      int npages = pages.size();
       */
        // We will be initializing some values of the computed hint
        // tables.  Specifically, we can initialize any items that deal
        // with object numbers or counts but not any items that deal with
        // lengths or offsets.  The code that writes linearized files will
        // have to fill in these values during the first pass.  The
        // validation code can compute them relatively easily given the
        // rest of the information.
        // npages is the size of the existing pages vector, which has been
        // created by traversing the pages tree, and as such is a
        // reasonable size.
        // c_linp.npages = npages;
        pageOffsetData = new CHPageOffset(pages.size());

        // Part 4: open document objects.  We don't care about the order.
        if (lc_root.size() != 1) {
            throw new ArithmeticException("calculateLinearizationData: too many roots");
        }
        part4.add(lc_root.get(0));
        lc_open_document.forEach((obj) -> {
            part4.add(obj);
        });
    }


    private void gatherSharedHintTable() throws ArithmeticException {
        // Calculate shared object hint table information including
        // references to shared objects from page offset hint data.
        // The shared object hint table consists of all part 6 (whether
        // shared or not) in order followed by all part 8 objects in
        // order.  Add the objects to shared object data keeping a map of
        // object number to index.  Then populate the shared object
        // information for the pages.
        // Note that two objects never have the same object number, so we
        // can map from object number only without regards to generation.
        sharedObjectData.nshared_first_page = part6.size();
        sharedObjectData.nshared_total = sharedObjectData.nshared_first_page + part8.size();

        part6.forEach((iterpart6) -> {
            this.indexObjectMap.putValuePair(sharedObjectData.entries.size(), iterpart6);
            sharedObjectData.entries.add(iterpart6);
        });

        if (!part8.isEmpty()) {
            sharedObjectData.first_shared_obj = part8.get(0);
            part8.forEach((iterpart8) -> {
                this.indexObjectMap.putValuePair(sharedObjectData.entries.size(), iterpart8);
                sharedObjectData.entries.add(iterpart8);
            });
        }

        if (sharedObjectData.nshared_total != sharedObjectData.entries.size()) {
            throw new ArithmeticException("shared object hint table has wrong number of entries");
        }

        // Now compute the list of shared objects for each page after the
        // first page.
        for (int i = 1; i < pages.size(); ++i) {
            final CHPageOffset.CHPageOffsetEntry pe = pageOffsetData.entries.get(i);
            final ObjUser ou = new ObjUser(ObjUser.user_e.ou_page, i);

            if (!this.userObjectMap.containsT1Key(ou)) {
                throw new ArithmeticException("keine page Object!");
            }

            final List<COSBase> ogs = this.userObjectMap.getT1ValuesForKey(ou);

            for (final COSBase iterogs : ogs) {
                if ((this.userObjectMap.getT2ValuesForKey(iterogs).size() > 1) && (this.indexObjectMap.containsT2Key(iterogs))) {
                    final int idx = this.indexObjectMap.getValueForT2(iterogs);

                    ++pe.nshared_objects;
                    pe.shared_identifiers.add(idx);
                }
            }
        }
    }


    private void processPart9(final List<COSBase> lc_other, final List<COSBase> lc_thumbnail_private, final List<COSBase> lc_thumbnail_shared, final boolean outlines_in_first_page, final List<COSBase> lc_outlines) throws ArithmeticException {
        // Part 9: other objects
        // The PDF specification makes recommendations on ordering here.
        // We follow them only to a limited extent.  Specifically, we put
        // the pages tree first, then private thumbnail objects in page
        // order, then shared thumbnail objects, and then outlines (unless
        // in part 6).  After that, we throw all remaining objects in
        // arbitrary order.
        final List<COSBase> pages_ogs = this.userObjectMap.getT1ValuesForKey(new ObjUser(ObjUser.user_e.ou_root_key, COSName.PAGES));

        if (pages_ogs.isEmpty()) {
            throw new ArithmeticException("calculateLinearizationData: pages_ogs is empty");
        }
        pages_ogs.forEach((pages_ogsiter) -> {
            if (lc_other.contains(pages_ogsiter)) {
                lc_other.remove(pages_ogsiter);
                part9.add(pages_ogsiter);
            }
        });

        // Place private thumbnail images in page order.  Slightly more
        // information would be required if we were going to bother with
        // thumbnail hint tables.
        for (int i = 0; i < pages.size(); ++i) {
            final COSBase thumb = ((COSDictionary) pages.get(i).getObject()).getDictionaryObject(COSName.THUMB);

            // thumb = getUncompressedObject(thumb, object_stream_data);
            if (thumb != null) {
                // Output the thumbnail itself
                // COSBase thumb_og(thumb.getObjGen());
                final COSBase thumb_og = thumb;

                if (lc_thumbnail_private.contains(thumb_og)) {
                    lc_thumbnail_private.remove(thumb_og);
                    part9.add(thumb);
                } else {
                    // No internal error this time...there's nothing to
                    // stop this object from having been referred to
                    // somewhere else outside of a page's /Thumb, and if
                    // it had been, there's nothing to prevent it from
                    // having been in some set other than
                    // lc_thumbnail_private.
                }

                final List<COSBase> ogs = this.userObjectMap.getT1ValuesForKey(new ObjUser(ObjUser.user_e.ou_thumb, i));

                ogs.forEach((iterogs) -> {
                    if (lc_thumbnail_private.contains(iterogs)) {
                        lc_thumbnail_private.remove(iterogs);
                        part9.add(iterogs);
                    }
                });
            }
        }
        if (!lc_thumbnail_private.isEmpty()) {
            throw new ArithmeticException("calculateLinearizationData: lc_thumbnail_private - not empty after placing thumbnails");
        }

        // Place shared thumbnail objects
        lc_thumbnail_shared.forEach((iterthumshar) -> {
            part9.add(iterthumshar);
        });

        // Place outlines unless in first page
        if (!outlines_in_first_page) {
            pushOutlinesToPart(part9, lc_outlines);
        }

        // Place all remaining objects
        lc_other.forEach((iterother) -> {
            part9.add(iterother);
        });

        final int num_placed = part4.size() + part6.size() + part7.size() + part8.size() + part9.size();
        final int num_wanted = this.userObjectMap.getT2Size();

        if (num_placed != num_wanted) {
            throw new ArithmeticException("calculateLinearizationData: wrong number of objects placed (num_placed = " + num_placed + "; number of objects: " + num_wanted);
        }
    }


    private void processPart8(final List<COSBase> lc_other_page_shared) {
        // Part 8: other pages' shared objects
        // Order is unimportant.
        lc_other_page_shared.forEach((itershared) -> {
            part8.add(itershared);
        });
    }


    private void processPart7(final Set<COSBase> lc_other_page_private) throws ArithmeticException {
        // Part 7: other pages' private objects
        // For each page in order:
        for (int i = 1; i < pages.size(); ++i) {
            // Place this page's page object

            final COSBase page_og = pages.get(i);

            if (!lc_other_page_private.contains(page_og)) {
                throw new ArithmeticException("calculateLinearizationData: page object for page " + i + " - object not in lc_first_page_private");
            }
            lc_other_page_private.remove(page_og);
            part7.add(page_og);

            // Place all non-shared objects referenced by this page,
            // updating the page object count for the hint table.
            pageOffsetData.entries.get(i).nobjects = 1;

            final ObjUser ou = new ObjUser(ObjUser.user_e.ou_page, i);

            if (!this.userObjectMap.containsT1Key(ou)) {
                throw new ArithmeticException("calculateLinearizationData: no user data for page " + i);
            }

            final List<COSBase> ogs = this.userObjectMap.getT1ValuesForKey(ou);

            for (final COSBase ogpage : ogs) {
                if (lc_other_page_private.remove(ogpage)) {
                    // lc_other_page_private.remove(ogpage);
                    part7.add(ogpage);
                    ++pageOffsetData.entries.get(i).nobjects;
                }
            }
        }

        // That should have covered all part7 objects.
        if (!lc_other_page_private.isEmpty()) {
            throw new ArithmeticException("calculateLinearizationData: lc_other_page_private is not empty after generation of part7");
        }
    }


    private void processPart6(final List<COSBase> lc_first_page_private, final List<COSBase> lc_first_page_shared, final boolean outlines_in_first_page, final List<COSBase> lc_outlines) throws ArithmeticException {
        // Part 6: first page objects.  Note: implementation note 124
        // states that Acrobat always treats page 0 as the first page for
        // linearization regardless of /OpenAction.  pdlin doesn't provide
        // any option to set this and also disregards /OpenAction.  We
        // will do the same.
        // First, place the actual first page object itself.
        final COSBase first_page_og = pages.get(0);

        if (!lc_first_page_private.contains(first_page_og)) {
            throw new ArithmeticException("calculateLinearizationData: first page - object not in lc_first_page_private");
        }
        lc_first_page_private.remove(first_page_og);

        // this.m.c_linp.first_page_object = pages.at(0).getObjectID();
        part6.add(first_page_og);

        // The PDF spec "recommends" an order for the rest of the objects,
        // but we are going to disregard it except to the extent that it
        // groups private and shared objects contiguously for the sake of
        // hint tables.
        lc_first_page_private.forEach((page) -> {
            part6.add(page);
        });

        lc_first_page_shared.forEach((page) -> {
            part6.add(page);
        });

        // Place the outline dictionary if it goes in the first page section.
        if (outlines_in_first_page) {
            pushOutlinesToPart(part6, lc_outlines);
        }

        // Fill in page offset hint table information for the first page.
        // The PDF spec says that nshared_objects should be zero for the
        // first page.  pdlin does not appear to obey this, but it fills
        // in garbage values for all the shared object identifiers on the
        // first page.
        pageOffsetData.entries.get(0).nobjects = part6.size();
    }


    private void traverseElements(final List<COSName> open_document_keys, final ArrayList<COSBase> lc_root, final List<COSBase> lc_outlines, final List<COSBase> lc_open_document, final List<COSBase> lc_first_page_private, final List<COSBase> lc_first_page_shared, final Set<COSBase> lc_other_page_private, final List<COSBase> lc_other_page_shared, final List<COSBase> lc_thumbnail_private, final List<COSBase> lc_thumbnail_shared, final List<COSBase> lc_other) throws ArithmeticException {
        for (final Map.Entry<COSBase, List<ObjUser>> entry : this.userObjectMap.getT2ToT1Map().entrySet()) {
            final COSBase og = entry.getKey();
            final List<ObjUser> ous = entry.getValue();

            boolean in_open_document = false;
            boolean in_first_page = false;
            int other_pages = 0;
            int thumbs = 0;
            int others = 0;
            boolean in_outlines = false;
            boolean is_root = false;

            for (final ObjUser ou : ous) {
                switch (ou.ou_type) {
                    case ou_trailer_key:
                        break;
                    case ou_thumb:
                        ++thumbs;
                        break;
                    case ou_root_key:
                        if (open_document_keys.contains(ou.key)) {
                            in_open_document = true;
                        } else if (ou.key == COSName.OUTLINES) {
                            in_outlines = true;
                        } else {
                            ++others;
                        }
                        break;
                    case ou_page:
                        if (ou.pageno == 0) {
                            in_first_page = true;
                        } else {
                            ++other_pages;
                        }
                        break;
                    case ou_root:
                        is_root = true;
                        break;

                    case ou_bad:
                        throw new ArithmeticException("INTERNAL ERROR: calculateLinearizationData: - invalid user type");
                    default:
                        break;
                }
            }

            if (is_root) {
                lc_root.add(og);
            } else if (in_outlines) {
                lc_outlines.add(og);
            } else if (in_open_document) {
                lc_open_document.add(og);
            } else if ((in_first_page) && (others == 0) && (other_pages == 0) && (thumbs == 0)) {
                lc_first_page_private.add(og);
            } else if (in_first_page) {
                lc_first_page_shared.add(og);
            } else if ((other_pages == 1) && (others == 0) && (thumbs == 0)) {
                lc_other_page_private.add(og);
            } else if (other_pages > 1) {
                lc_other_page_shared.add(og);
            } else if ((thumbs == 1) && (others == 0)) {
                lc_thumbnail_private.add(og);
            } else if (thumbs > 1) {
                lc_thumbnail_shared.add(og);
            } else {
                lc_other.add(og);
            }
        }
    }


    private void pushOutlinesToPart(final List<COSBase> part, final List<COSBase> lc_outlines) {
        final COSObject root = (COSObject) document.getTrailer().getItem(COSName.ROOT);
        final COSObject outlines = (COSObject) ((COSDictionary) root.getObject()).getItem(COSName.OUTLINES);

        if (outlines == null) {
            return;
        }
        outlineData.first_object = outlines;
        outlineData.nobjects = 1;
        lc_outlines.remove(outlines);
        part.add(outlines);
        for (final COSBase iter : lc_outlines) {
            part.add(iter);
            ++outlineData.nobjects;
        }
    }
}
