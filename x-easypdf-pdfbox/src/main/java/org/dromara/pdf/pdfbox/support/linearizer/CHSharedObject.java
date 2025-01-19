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


import org.apache.pdfbox.cos.COSBase;

import java.util.ArrayList;
import java.util.List;


/**
 * PDF 1.4: Table F.5
 *
 * @author Marian Gavalier
 * @version $Id$
 */
class CHSharedObject {
    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------
    
    /**
     * vector size is nshared_total
     */
    final List<COSBase> entries;
    /**
     * [!FIELD_DESCRIPTION!]
     */
    COSBase first_shared_obj;
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nshared_first_page;
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int nshared_total;
    
    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------
    
    /**
     * [!CONSTR_DESCIRPTION_FOR_CHSharedObject!]
     */
    CHSharedObject() {
        nshared_first_page = 0;
        nshared_total = 0;
        entries = new ArrayList<>();
    }
}
