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


import org.apache.pdfbox.cos.COSBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * PDF 1.4: Table F.3
 *
 * @author Marian Gavalier
 * @version $Id$
 */
class HPageOffset {
    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------

    /**
     * vector size is npages
     */
    final List<HPageOffsetEntry> entries;
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int min_nobjects;  // 1
    /**
     * [!FIELD_DESCRIPTION!]
     */
    long first_page_offset;  // 2
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nbits_delta_nobjects;  // 3
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int min_page_length;  // 4
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nbits_delta_page_length;  // 5
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int min_content_offset;  // 6
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nbits_delta_content_offset;  // 7
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int min_content_length;  // 8
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nbits_delta_content_length;  // 9
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nbits_nshared_objects;  // 10
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nbits_shared_identifier;  // 11
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nbits_shared_numerator;  // 12
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int shared_denominator;  // 13

    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!CONSTR_DESCIRPTION_FOR_HPageOffset!]
     *
     * @param pages [!PARA_DESCRIPTION!]
     */
    private HPageOffset(final int pages) {
        entries = new ArrayList<>();

        min_nobjects = 0;
        first_page_offset = 0;
        nbits_delta_nobjects = 0;
        min_page_length = 0;
        nbits_delta_page_length = 0;
        min_content_offset = 0;
        nbits_delta_content_offset = 0;
        min_content_length = 0;
        nbits_delta_content_length = 0;
        nbits_nshared_objects = 0;
        nbits_shared_identifier = 0;
        nbits_shared_numerator = 0;
        shared_denominator = 0;
        for (int i = 0; i < pages; i++) {
            entries.add(new HPageOffsetEntry());
        }
    }

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param info            [!PARA_DESCRIPTION!]
     * @param queue           [!PARA_DESCRIPTION!]
     * @param lin_dict_offset [!PARA_DESCRIPTION!]
     * @param pageCount       [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    static HPageOffset filledPageOffsetHints(final StructuredPDFInfo info, final PDFObjectQueue queue, final long lin_dict_offset, final int pageCount) {
        int min_nobjects = Integer.MAX_VALUE;
        int max_nobjects = Integer.MIN_VALUE;
        long min_length = Integer.MAX_VALUE;
        long max_length = Integer.MIN_VALUE;
        int max_shared = Integer.MIN_VALUE;

        final HPageOffset pageOffsetHints;

        pageOffsetHints = new HPageOffset(pageCount);

        // calculateHPageOffset + writeparts already
        for (int i = 0; i < pageCount; ++i) {
            // set corect parent for every page
            final ObjUser ou = new ObjUser(ObjUser.user_e.ou_page, i);
            long length = 0;
            final List<COSBase> ogs = info.userObjectMap.getT1ValuesForKey(ou);

            for (final COSBase ogpage : ogs) {
                length += queue.get(ogpage).objLength;
            }

            // substract shared object length from page length
            final CHPageOffset.CHPageOffsetEntry entry = info.pageOffsetData.entries.get(i);

            if (entry.nshared_objects > 0) {
                for (final int sharedObjId : entry.shared_identifiers) {
                    length -= queue.get(info.indexObjectMap.getValueForT1(sharedObjId)).objLength;
                }
            }

            // Calculate values for each page, assigning full values to
            // the delta items.  They will be adjusted later.
            final int nobjects = entry.nobjects;
            final int nshared = entry.nshared_objects;

            min_nobjects = Math.min(min_nobjects, nobjects);
            max_nobjects = Math.max(max_nobjects, nobjects);
            min_length = Math.min(min_length, length);
            max_length = Math.max(max_length, length);
            max_shared = Math.max(max_shared, nshared);

            pageOffsetHints.entries.get(i).delta_nobjects = nobjects;
            pageOffsetHints.entries.get(i).delta_page_length = length;
            pageOffsetHints.entries.get(i).nshared_objects = nshared;
        }
        pageOffsetHints.min_nobjects = min_nobjects;

        // TODO:: !!!!!!!!!!!!!!!!!
        pageOffsetHints.first_page_offset = lin_dict_offset;
        pageOffsetHints.nbits_delta_nobjects = IO.nbits(max_nobjects - min_nobjects);
        pageOffsetHints.min_page_length = (int) min_length;
        pageOffsetHints.nbits_delta_page_length = IO.nbits((int) max_length - (int) min_length);
        pageOffsetHints.nbits_nshared_objects = IO.nbits(max_shared);
        pageOffsetHints.nbits_shared_identifier = IO.nbits(info.sharedObjectData.nshared_total);
        pageOffsetHints.shared_denominator = 4;  // doesn't matter

        // It isn't clear how to compute content offset and content
        // length.  Since we are not interleaving page objects with the
        // content stream, we'll use the same values for content length as
        // page length.  We will use 0 as content offset because this is
        // what Adobe does (implementation note 127) and pdlin as well.
        pageOffsetHints.nbits_delta_content_length = pageOffsetHints.nbits_delta_page_length;
        pageOffsetHints.min_content_length = pageOffsetHints.min_page_length;
        for (int i = 0; i < pageCount; i++) {
            // Adjust delta entries
            // assert(phe.at(i).delta_nobjects >= min_nobjects);
            // assert(phe.at(i).delta_page_length >= min_length);
            pageOffsetHints.entries.get(i).delta_nobjects -= min_nobjects;
            pageOffsetHints.entries.get(i).delta_page_length -= min_length;
            pageOffsetHints.entries.get(i).delta_content_length = pageOffsetHints.entries.get(i).delta_page_length;

            final CHPageOffset.CHPageOffsetEntry entry = info.pageOffsetData.entries.get(i);

            for (int j = 0; j < entry.nshared_objects; ++j) {
                pageOffsetHints.entries.get(i).shared_identifiers.add(entry.shared_identifiers.get(j));
                pageOffsetHints.entries.get(i).shared_numerators.add(0);
            }
        }
        return pageOffsetHints;
    }


    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param w [!PARA_DESCRIPTION!]
     * @throws IOException [!EXC_DESCRIPTION!]
     */
    void writeHPageOffset(final BitWriter w) throws IOException {
        w.writeBits(this.min_nobjects, 32);  // 1
        w.writeBits(this.first_page_offset, 32);  // 2
        w.writeBits(this.nbits_delta_nobjects, 16);  // 3
        w.writeBits(this.min_page_length, 32);  // 4
        w.writeBits(this.nbits_delta_page_length, 16);  // 5
        w.writeBits(this.min_content_offset, 32);  // 6
        w.writeBits(this.nbits_delta_content_offset, 16);  // 7
        w.writeBits(this.min_content_length, 32);  // 8
        w.writeBits(this.nbits_delta_content_length, 16);  // 9
        w.writeBits(this.nbits_nshared_objects, 16);  // 10
        w.writeBits(this.nbits_shared_identifier, 16);  // 11
        w.writeBits(this.nbits_shared_numerator, 16);  // 12
        w.writeBits(this.shared_denominator, 16);  // 13

        for (final HPageOffsetEntry en : entries) {
            w.writeBits(en.delta_nobjects, this.nbits_delta_nobjects);
        }
        w.flush();
        for (final HPageOffsetEntry en : entries) {
            w.writeBits(en.delta_page_length, this.nbits_delta_page_length);
        }
        w.flush();
        for (final HPageOffsetEntry en : entries) {
            w.writeBits(en.nshared_objects, this.nbits_nshared_objects);
        }
        w.flush();
        for (final HPageOffsetEntry en : entries) {
            for (final Integer ele : en.shared_identifiers) {
                w.writeBits(ele, this.nbits_shared_identifier);
            }
        }
        w.flush();
        for (final HPageOffsetEntry en : entries) {
            for (final Integer ele : en.shared_numerators) {
                w.writeBits(ele, this.nbits_shared_numerator);
            }
        }
        w.flush();
        for (final HPageOffsetEntry en : entries) {
            w.writeBits(en.delta_content_offset, this.nbits_delta_content_offset);
        }
        w.flush();
        for (final HPageOffsetEntry en : entries) {
            w.writeBits(en.delta_content_length, this.nbits_delta_content_length);
        }
        w.flush();
    }

    //~ Inner Classes ---------------------------------------------------------------------------------------------------------------------------------

    /**
     * PDF 1.4: Table F.4
     *
     * @author Jonathan Rahn
     * @version $Id$
     */
    class HPageOffsetEntry {
        //~ Instance members ---------------------------------------------------------------------------------------------------------------------------

        /**
         * [!FIELD_DESCRIPTION!]
         */
        int delta_nobjects;  // 1

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long delta_page_length;  // 2

        /**
         * [!FIELD_DESCRIPTION!]
         */
        int nshared_objects;  // 3

        /**
         * vectors' sizes = nshared_objects
         */
        List<Integer> shared_identifiers = new ArrayList<>();  // 4

        /**
         * [!FIELD_DESCRIPTION!]
         */
        List<Integer> shared_numerators = new ArrayList<>();  // 5

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long delta_content_offset;  // 6

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long delta_content_length;  // 7

        //~ Constructors -------------------------------------------------------------------------------------------------------------------------------

        /**
         * [!CONSTR_DESCIRPTION_FOR_HPageOffsetEntry!]
         */
        private HPageOffsetEntry() {
            delta_nobjects = 0;
            delta_page_length = 0;
            nshared_objects = 0;
            delta_content_offset = 0;
            delta_content_length = 0;
        }
    }
}
