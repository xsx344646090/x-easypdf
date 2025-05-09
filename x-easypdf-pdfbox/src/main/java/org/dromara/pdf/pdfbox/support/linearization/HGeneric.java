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


/**
 * PDF 1.4: Table F.9
 *
 * @author Marian Gavalier
 * @version $Id$
 */
class HGeneric {
    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!FIELD_DESCRIPTION!]
     */
    int first_object_nr;  // 3

    /**
     * [!FIELD_DESCRIPTION!]
     */
    COSBase first_object;  // 1

    /**
     * [!FIELD_DESCRIPTION!]
     */
    long first_object_offset;  // 2

    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nobjects;  // 3

    /**
     * [!FIELD_DESCRIPTION!]
     */
    long group_length;  // 4

    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!CONSTR_DESCIRPTION_FOR_HGeneric!]
     */
    HGeneric() {
        first_object_nr = 0;
        first_object_offset = 0;
        nobjects = 0;
        group_length = 0;
    }

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param info    [!PARA_DESCRIPTION!]
     * @param queue   [!PARA_DESCRIPTION!]
     * @param offsets [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    static HGeneric filledOutlineHints(final StructuredPDFInfo info, final PDFObjectQueue queue, final Linearizer.LinearizationInformation offsets) {
        final HGeneric outlineHints = new HGeneric();

        if (info.outlineData.nobjects > 0) {
            outlineHints.first_object_nr = queue.get(info.outlineData.first_object).objNumber;
            outlineHints.first_object_offset = offsets.firstObjectOffset;
            outlineHints.nobjects = info.outlineData.nobjects;
            outlineHints.group_length = offsets.outlineLength;
        }
        return outlineHints;
    }


    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param w [!PARA_DESCRIPTION!]
     * @throws IOException [!EXC_DESCRIPTION!]
     */
    void writeHGeneric(final BitWriter w) throws IOException {
        w.writeBits(this.first_object_nr, 32);  // 1
        w.writeBits(this.first_object_offset, 32);  // 2
        w.writeBits(this.nobjects, 32);  // 3
        w.writeBits(this.group_length, 32);  // 4
    }
}
