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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * [!CLASS_DESCRIPTION!]
 *
 * @author Jonathan Rahn
 * @version $Id$
 */
class BidirectionalMultiMap<T1, T2> {
    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------
    
    private final HashMap<T1, List<T2>> mapT1ToT2s = new LinkedHashMap<>();
    private final HashMap<T2, List<T1>> mapT2ToT1s = new LinkedHashMap<>();
    
    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param ou [!PARA_DESCRIPTION!]
     * @param og [!PARA_DESCRIPTION!]
     */
    void addValuePair(final T1 ou, final T2 og) {
        if (!mapT1ToT2s.containsKey(ou)) {
            mapT1ToT2s.put(ou, new ArrayList<>());
        }
        mapT1ToT2s.get(ou).add(og);
        
        if (!mapT2ToT1s.containsKey(og)) {
            mapT2ToT1s.put(og, new ArrayList<>());
        }
        mapT2ToT1s.get(og).add(ou);
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param key [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    List<T2> getT1ValuesForKey(final T1 key) {
        return this.mapT1ToT2s.get(key);
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param key [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    List<T1> getT2ValuesForKey(final T2 key) {
        return this.mapT2ToT1s.get(key);
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @return [!RETURN_DESCRIPTION!]
     */
    HashMap<T1, List<T2>> getT1ToT2Map() {
        return this.mapT1ToT2s;
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @return [!RETURN_DESCRIPTION!]
     */
    HashMap<T2, List<T1>> getT2ToT1Map() {
        return this.mapT2ToT1s;
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param key [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    boolean containsT1Key(final T1 key) {
        return this.mapT1ToT2s.containsKey(key);
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param key [!PARA_DESCRIPTION!]
     * @return [!RETURN_DESCRIPTION!]
     */
    boolean containsT2Key(final T2 key) {
        return this.mapT2ToT1s.containsKey(key);
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @return [!RETURN_DESCRIPTION!]
     */
    int getT1Size() {
        return this.mapT1ToT2s.size();
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @return [!RETURN_DESCRIPTION!]
     */
    int getT2Size() {
        return this.mapT2ToT1s.size();
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @return [!RETURN_DESCRIPTION!]
     */
    boolean isT1Empty() {
        return this.mapT1ToT2s.isEmpty();
    }
    
    
    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @return [!RETURN_DESCRIPTION!]
     */
    boolean isT2Empty() {
        return this.mapT2ToT1s.isEmpty();
    }
}
