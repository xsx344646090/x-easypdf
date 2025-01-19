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


import java.util.HashMap;


/**
 * [!CLASS_DESCRIPTION!]
 *
 * @author Jonathan Rahn
 * @version $Id$
 */
class BidirectionalMap<T1, T2> {
    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------
    
    private final HashMap<T1, T2> mapT1ToT2 = new HashMap<>();
    private final HashMap<T2, T1> mapT2ToT1 = new HashMap<>();
    
    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param value1 [!PARA_DESCRIPTION!]
     * @param value2 [!PARA_DESCRIPTION!]
     */
    void putValuePair(final T1 value1, final T2 value2) {
        this.mapT1ToT2.put(value1, value2);
        this.mapT2ToT1.put(value2, value1);
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param key [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    T2 getValueForT1(final T1 key) {
        return this.mapT1ToT2.get(key);
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param key [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    T1 getValueForT2(final T2 key) {
        return this.mapT2ToT1.get(key);
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param key [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    boolean containsT1Key(final T1 key) {
        return this.mapT1ToT2.containsKey(key);
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param key [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    boolean containsT2Key(final T2 key) {
        return this.mapT2ToT1.containsKey(key);
    }
}
