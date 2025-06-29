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


import java.util.ArrayList;
import java.util.List;


/**
 * [!CLASS_DESCRIPTION!]
 *
 * @author Marian Gavalier
 * @version $Id$
 */
class CHPageOffset {
    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------

    /**
     * vector size is npages
     */
    final List<CHPageOffsetEntry> entries;

    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!CONSTR_DESCIRPTION_FOR_CHPageOffset!]
     *
     * @param pages [!PARA_DESCRIPTION!]
     */
    CHPageOffset(final int pages) {
        entries = new ArrayList<>(pages);
        for (int i = 0; i < pages; i++) {
            entries.add(new CHPageOffsetEntry());
        }
    }

    //~ Inner Classes ---------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!CLASS_DESCRIPTION!]
     *
     * @author Jonnathan Rahn
     * @version $Id$
     */
    class CHPageOffsetEntry {
        //~ Instance members ---------------------------------------------------------------------------------------------------------------------------

        /**
         * vectors' sizes = nshared_objects
         */
        final List<Integer> shared_identifiers = new ArrayList<>();
        /**
         * [!FIELD_DESCRIPTION!]
         */
        int nobjects;
        /**
         * [!FIELD_DESCRIPTION!]
         */
        int nshared_objects;

        //~ Constructors -------------------------------------------------------------------------------------------------------------------------------

        /**
         * [!CONSTR_DESCIRPTION_FOR_CHPageOffsetEntry!]
         */
        CHPageOffsetEntry() {
            nobjects = 0;
            nshared_objects = 0;
        }
    }
}
