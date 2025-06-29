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


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * PDF 1.4: Table F.5
 *
 * @author Marian Gavalier
 * @version $Id$
 */
public class HSharedObject {
    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!FIELD_DESCRIPTION!]
     */
    int first_shared_obj;  // 1

    /**
     * [!FIELD_DESCRIPTION!]
     */
    long first_shared_offset;  // 2

    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nshared_first_page;  // 3

    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nshared_total;  // 4

    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nbits_nobjects;  // 5

    /**
     * [!FIELD_DESCRIPTION!]
     */
    int min_group_length;  // 6

    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nbits_delta_group_length;  // 7

    /**
     * vector size is nshared_total
     */
    List<HSharedObjectEntry> entries = new ArrayList<>();

    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!CONSTR_DESCIRPTION_FOR_HSharedObject!]
     *
     * @param objects [!PARA_DESCRIPTION!]
     */
    private HSharedObject(final int objects) {
        first_shared_obj = 0;
        first_shared_offset = 0;
        nshared_first_page = 0;
        nshared_total = 0;
        nbits_nobjects = 0;
        min_group_length = 0;
        nbits_delta_group_length = 0;
        for (int i = 0; i < objects; i++) {
            entries.add(new HSharedObjectEntry());
        }
    }

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param info                [!PARA_DESCRIPTION!]
     * @param queue               [!PARA_DESCRIPTION!]
     * @param first_shared_offset [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    static HSharedObject filledSharedObjectHints(final StructuredPDFInfo info, final PDFObjectQueue queue, final long first_shared_offset) {
        long min_length = Integer.MAX_VALUE;
        long max_length = Integer.MIN_VALUE;

        final HSharedObject sharedObjectHints = new HSharedObject(info.sharedObjectData.nshared_total);

        for (int i = 0; i < info.sharedObjectData.nshared_total; ++i) {
            // Assign absolute numbers to deltas; adjust later
            final long length = queue.get(info.sharedObjectData.entries.get(i)).objLength;

            min_length = Math.min(min_length, length);
            max_length = Math.max(max_length, length);
            sharedObjectHints.entries.get(i).delta_group_length = (int) length;
        }
        sharedObjectHints.nshared_total = info.sharedObjectData.nshared_total;
        sharedObjectHints.nshared_first_page = info.sharedObjectData.nshared_first_page;
        if (sharedObjectHints.nshared_total > sharedObjectHints.nshared_first_page) {
            sharedObjectHints.first_shared_obj = queue.get(info.sharedObjectData.first_shared_obj).objNumber;
            sharedObjectHints.first_shared_offset = first_shared_offset;
        }
        sharedObjectHints.min_group_length = (int) min_length;
        sharedObjectHints.nbits_delta_group_length = IO.nbits((int) max_length - (int) min_length);
        for (int i = 0; i < info.sharedObjectData.nshared_total; ++i) {
            // Adjust deltas
            assert (sharedObjectHints.entries.get(i).delta_group_length >= min_length);
            sharedObjectHints.entries.get(i).delta_group_length -= min_length;
        }
        return sharedObjectHints;
    }


    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param w [!PARA_DESCRIPTION!]
     * @throws IOException [!EXC_DESCRIPTION!]
     */
    void writeHSharedObject(final BitWriter w) throws IOException {
        w.writeBits(this.first_shared_obj, 32);  // 1
        w.writeBits(this.first_shared_offset, 32);  // 2
        w.writeBits(this.nshared_first_page, 32);  // 3
        w.writeBits(this.nshared_total, 32);  // 4
        w.writeBits(this.nbits_nobjects, 16);  // 5
        w.writeBits(this.min_group_length, 32);  // 6
        w.writeBits(this.nbits_delta_group_length, 16);  // 7

        final int nitems = this.nshared_total;

        for (final HSharedObjectEntry en : entries) {
            w.writeBits(en.delta_group_length, this.nbits_delta_group_length);
        }
        w.flush();
        for (final HSharedObjectEntry en : entries) {
            w.writeBits(en.signature_present, 1);
        }
        w.flush();
        for (int i = 0; i < nitems; ++i) {
            // If signature were present, we'd have to write a 128-bit hash.
            if (entries.get(i).signature_present != 0) {
                // ???
            }
        }
        for (final HSharedObjectEntry en : entries) {
            w.writeBits(en.nobjects_minus_one, this.nbits_nobjects);
        }
        w.flush();
    }

    //~ Inner Classes ---------------------------------------------------------------------------------------------------------------------------------

    /**
     * PDF 1.4: Table F.6
     *
     * @author Jonathan Rahn
     * @version $Id$
     */
    class HSharedObjectEntry {
        //~ Instance members ---------------------------------------------------------------------------------------------------------------------------

        /**
         * Item 3 is a 128-bit signature (unsupported by Acrobat)
         */
        int delta_group_length;  // 1

        /**
         * [!FIELD_DESCRIPTION!]
         */
        int signature_present;  // 2 -- always 0

        /**
         * [!FIELD_DESCRIPTION!]
         */
        int nobjects_minus_one;  // 4 -- always 0

        //~ Constructors -------------------------------------------------------------------------------------------------------------------------------

        /**
         * [!CONSTR_DESCIRPTION_FOR_HSharedObjectEntry!]
         */
        private HSharedObjectEntry() {
            delta_group_length = 0;
            signature_present = 0;
            nobjects_minus_one = 0;
        }
    }
}
