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


import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.List;


/**
 * Class represeting a place to dump written VirtualParts and associate them with
 * certain parts of a linearized PDF-Document. These dumped parts can later be
 * reassambled and written to a VirtualFile.
 *
 * @author Jonathan Rahn
 * @version $Id$
 */
public class WrittenObjectStore {
    //~ Enums -----------------------------------------------------------------------------------------------------------------------------------------

    public static final int EOF = -1;

    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------
    private final EnumMap<SpecialParts, List<VirtualPart>> specialParts = new EnumMap<>(SpecialParts.class);

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    public static void writePartsToOutputStream(List<VirtualPart> objParts, OutputStream fos) throws IOException {
        for (VirtualPart part : objParts) {
            if (part.isReference()) {
                try (FileInputStream fis = new FileInputStream(part.reference)) {
                    copyLarge(fis, fos, part.refBeginPos, part.referenceLength);
                }
            } else {
                fos.write(part.hardPart);
            }
        }
    }

    public static long copyLarge(final InputStream input, final OutputStream output, final long inputOffset, final long length) throws IOException {
        if (inputOffset > 0) {
            input.skip(inputOffset);
        }
        if (length == 0) {
            return 0;
        }
        byte[] buffer = new byte[16384];
        final int bufferLength = buffer.length;
        int bytesToRead = bufferLength;
        if (length > 0 && length < bufferLength) {
            bytesToRead = (int) length;
        }
        int read;
        long totalRead = 0;
        while (bytesToRead > 0 && EOF != (read = input.read(buffer, 0, bytesToRead))) {
            output.write(buffer, 0, read);
            totalRead += read;
            if (length > 0) { // only adjust length if not reading to the end
                // Note the cast must work because buffer.length is an integer
                bytesToRead = (int) Math.min(length - totalRead, bufferLength);
            }
        }
        return totalRead;
    }

    /**
     * Writes dumped content to a VirtualFile at the destination specified.
     *
     * @param dest
     */
    @SneakyThrows
    public void write(final OutputStream dest) {

        writePartsToOutputStream(this.get(SpecialParts.PARTS_BEFORE_FIRST_XREF), dest);
        writePartsToOutputStream(this.get(SpecialParts.FIRST_PART_OBJECTS), dest);
        writePartsToOutputStream(this.get(SpecialParts.HINTSTREAM), dest);
        writePartsToOutputStream(this.get(SpecialParts.SECOND_PART_OBJECTS), dest);
        writePartsToOutputStream(this.get(SpecialParts.SECOND_XREF_PART), dest);
    }

    /**
     * Dumps a list of VirtualParts and associates them with a PDF part. If there
     * are already parts associated with the key specified, they will be
     * overwritten.
     *
     * @param key     Key specifying location in PDF
     * @param objects List of VirtualParts
     * @return Total inflated length of all added parts
     */
    long add(final SpecialParts key, final List<VirtualPart> objects) {
        specialParts.put(key, objects);
        return VirtualPart.calculateInflatedLength(objects);
    }

    /**
     * Retrieves length of VirtualParts associated with the key specified.
     *
     * @param key Key specifying location in PDF
     * @return Total inflated length of all parts associated with the key
     */
    long getLength(final SpecialParts key) {
        return VirtualPart.calculateInflatedLength(specialParts.get(key));
    }

    /**
     * Retrieves all VirtualParts associated with the given key.
     *
     * @param key Key specifying location in PDF
     * @return All parts associated with the key given
     */
    List<VirtualPart> get(final SpecialParts key) {
        return specialParts.get(key);
    }

    /**
     * Enum describing parts of linearized PDF.
     *
     * @author JRA
     * @version $Id$
     */
    public static enum SpecialParts {
        //~ Enum constants -----------------------------------------------------------------------------------------------------------------------------

        PARTS_BEFORE_FIRST_XREF, HINTSTREAM, SECOND_XREF_PART, FIRST_PART_OBJECTS, SECOND_PART_OBJECTS, PART_4_END_MARKER
    }

}
