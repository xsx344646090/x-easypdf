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


/**
 * [!CLASS_DESCRIPTION!]
 *
 * @author Marian Gavalier
 * @version $Id$
 */
class IO {
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param val [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    static int nbits(final int val) {
        return 32 - Integer.numberOfLeadingZeros(val);
    }
    
    static int nbits(final long val) {
        return 64 - Long.numberOfLeadingZeros(val);
    }
    
}
