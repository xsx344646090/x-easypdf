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


import org.apache.pdfbox.cos.COSName;

import java.util.Objects;


/**
 * [!CLASS_DESCRIPTION!]
 *
 * @author Marian Gavalier
 * @version $Id$
 */
class ObjUser {
    //~ Enums -----------------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!FIELD_DESCRIPTION!]
     */
    user_e ou_type;

    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------
    /**
     * [!FIELD_DESCRIPTION!]
     */
    int pageno;  // if ou_page;
    /**
     * [!FIELD_DESCRIPTION!]
     */
    COSName key;  // if ou_trailer_key or ou_root_key

    /**
     * type is set to ou_bad
     */
    ObjUser() {
        ou_type = user_e.ou_bad;
        pageno = 0;
    }

    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------

    /**
     * type must be ou_root
     *
     * @param type [!PARA_DESCRIPTION!]
     */
    ObjUser(final user_e type) {
        this();
        ou_type = type;
    }


    /**
     * type must be one of ou_page or ou_thumb
     *
     * @param type   [!PARA_DESCRIPTION!]
     * @param pageno [!PARA_DESCRIPTION!]
     */
    ObjUser(final user_e type,
            final int pageno) {
        this(type);
        this.pageno = pageno;
    }


    /**
     * type must be one of ou_trailer_key or ou_root_key
     *
     * @param type [!PARA_DESCRIPTION!]
     * @param key  [!PARA_DESCRIPTION!]
     */
    ObjUser(final user_e type,
            final COSName key) {
        this(type);
        this.key = key;
    }

    @Override
    public boolean equals(final Object rhs) {
        if (!(rhs instanceof ObjUser)) {
            return false;
        }

        if (this.ou_type != ((ObjUser) rhs).ou_type) {
            return false;
        } else if (this.ou_type == ((ObjUser) rhs).ou_type) {
            if (this.pageno < ((ObjUser) rhs).pageno) {
                return false;
            } else if (this.pageno == ((ObjUser) rhs).pageno) {
                return (this.key == ((ObjUser) rhs).key);
            }
        }
        return false;
    }

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "" + ou_type + ", " + pageno + ", " + key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ou_type, pageno, key);
    }


    /**
     * [!CLASS_DESCRIPTION!]
     *
     * @author Jonathan Rahn
     * @version $Id$
     */
    public enum user_e {
        //~ Enum constants -----------------------------------------------------------------------------------------------------------------------------

        ou_bad, ou_page, ou_thumb, ou_trailer_key, ou_root_key, ou_root
    }
}
