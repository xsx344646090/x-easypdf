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
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.cos.COSObjectKey;

import java.util.*;


/**
 * Class enqueues PDF-objects and associates them with object metadata,
 * including the object number and length. Also supports dummy objects that are
 * to be replaced later, but can alreay be assigned an object number.
 *
 * @author Jonathan Rahn
 * @version $Id$
 */
class PDFObjectQueue {
    //~ Enums -----------------------------------------------------------------------------------------------------------------------------------------

    private final LinkedHashMap<COSBase, ObjectMetaData> object_queue = new LinkedHashMap<>();

    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------
    private final HashMap<PDFDummyObjects, ObjectMetaData> dummies = new HashMap<>();
    private int object_id = 0;

    /**
     * Enqueues a PDF-object and assigns the next object number.
     *
     * @param object object
     */
    void enqueueObject(final COSBase object) {
        if (!object_queue.containsKey(object)) {
            object.setDirect(true);
            object_queue.put(object, new ObjectMetaData(object_id++));
            if (object instanceof COSObject) {
                ((COSObject) object).getObject().setDirect(false);
                object.setKey(new COSObjectKey(object_id - 1, 0));
            }
        }
    }

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Returns count of all objects scheduled, dummies excluded.
     *
     * @return size
     */
    int size() {
        return object_queue.size();
    }

    /**
     * Returns all non-dummy objects scheduled and their associated metadata.
     *
     * @return entryset.
     */
    Set<Map.Entry<COSBase, ObjectMetaData>> entrySet() {
        return object_queue.entrySet();
    }

    /**
     * Returns all non-dummy objects scheduled.
     *
     * @return objects
     */
    Set<COSBase> keySet() {
        return object_queue.keySet();
    }

    /**
     * Assigns the current object number, objects enqueued subsequently will be
     * assigned a sequenced number starting with the value specified.
     *
     * @param id object number
     */
    void setObjectID(final int id) {
        this.object_id = id;
    }

    /**
     * Resets the oject number to 1.
     */
    void resetObjectID() {
        this.object_id = 1;
    }

    /**
     * Enqueus a list of objects.
     *
     * @param part list of objects to be enqueued.
     */
    void enqueuePart(final List<COSBase> part) {
        for (final COSBase iter : part) {
            enqueueObject(iter);
        }
    }

    /**
     * Enqueus a dummy object and assigns it an object number
     *
     * @param key The kind of object the dummy represents.
     */
    void enqueueDummy(final PDFDummyObjects key) {
        dummies.put(key, new ObjectMetaData(object_id++));
    }

    /**
     * Removes a dummy object and places the real object along with its metadata
     * to the real queue.
     *
     * @param key    key to replace
     * @param object real object to replace the dummy
     */
    void replaceDummy(final PDFDummyObjects key, final COSBase object) {
        final ObjectMetaData data = this.get(key);

        dummies.remove(key);

        object_queue.put(object, data);
    }

    /**
     * Retrieves the metadata associated with the object specified.
     *
     * @param base object
     * @return metadata
     */
    ObjectMetaData get(final COSBase base) {
        return object_queue.get(base);
    }

    /**
     * Retrieves the metadata associated with the dummy specified.
     *
     * @param dummy dummy
     * @return metadata
     */
    ObjectMetaData get(final PDFDummyObjects dummy) {
        return dummies.get(dummy);
    }

    /**
     * Retrieves the object number that is to be assigned next
     *
     * @return next object number
     */
    int getNextID() {
        return object_id;
    }


    /**
     * Types of objects which depend on the other objects being written.
     *
     * @author Jonathan Rahn
     * @version $Id$
     */
    enum PDFDummyObjects {
        //~ Enum constants -----------------------------------------------------------------------------------------------------------------------------

        LINDICT, FIRSTXREF, SECONDXREF, HINTSTREAM
    }

    //~ Inner Classes ---------------------------------------------------------------------------------------------------------------------------------

    /**
     * Encapsulates metadata associated with a pdf object
     *
     * @author Jonathan Rahn
     * @version $Id$
     */
    class ObjectMetaData {
        //~ Instance members ---------------------------------------------------------------------------------------------------------------------------

        /**
         * object number
         */
        final int objNumber;

        /**
         * length in bytes of written object. -1 until assigned
         */
        long objLength = -1;

        //~ Constructors -------------------------------------------------------------------------------------------------------------------------------

        /**
         * Default constructor
         *
         * @param objNumber object number
         */
        ObjectMetaData(final int objNumber) {
            this.objNumber = objNumber;
        }
    }
}
