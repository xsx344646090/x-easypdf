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


import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * Class representing a part of a virtual routing file. Either contains a
 * reference to an external file or a byte array with hard content. Can be
 * serialized with a VirtualFileWriter
 *
 * @author Marian Gavalier
 * @version $Id$
 */
public class VirtualPart implements Serializable {
    //~ Static fields/initializers --------------------------------------------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------

    /**
     * The path to the referenced file. null if not a reference
     */
    final String reference;

    /**
     * The offset the this part's content in the referenced file. -1 if not a
     * reference
     */
    final long refBeginPos;
    /**
     * The length of the referenced content. -1 if not a reference
     */
    final int referenceLength;
    /**
     * The contained bytes of this part is not a reference
     */
    final byte[] hardPart;
    /**
     * Gives the offset of this part in the output PDF file. -1 if not given in
     * constructor
     */
    long outBeginPos;
    private byte[] _referenceBytes = null;

    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------

    /**
     * Initializes a VirtualPart which represents a hard byte array.
     *
     * @param hardPart The contained bytes
     */
    public VirtualPart(final byte[] hardPart) {
        this.reference = null;
        this.refBeginPos = -1;
        this.outBeginPos = -1;
        this.referenceLength = -1;
        this.hardPart = hardPart;
    }


    /**
     * Initializes a VirtualPart which represents a hard byte array, including
     * infomation about its relation to the entire output file.
     *
     * @param hardPart    The contained bytes
     * @param outBeginPos The offset position of this part in the deflated PDF
     */
    VirtualPart(final byte[] hardPart, final long outBeginPos) {
        this.reference = null;
        this.outBeginPos = outBeginPos;
        this.refBeginPos = -1;
        this.referenceLength = -1;
        this.hardPart = hardPart;
    }


    /**
     * Initializes a VirtualPart which represents a reference.
     *
     * @param reference              The path to the referenced file
     * @param referenceBeginPosition The offset of the referenced content in the
     *                               referenced file
     * @param referenceLength        The length of the referenced content
     */
    public VirtualPart(final String reference, final long referenceBeginPosition, final int referenceLength) {
        this.reference = reference;
        this.refBeginPos = referenceBeginPosition;
        this.referenceLength = referenceLength;
        this.hardPart = null;
        this.outBeginPos = -1;
    }


    /**
     * Initializes a VirtualPart which represents a reference, including
     * infomation about its relation to the entire output file.
     *
     * @param reference              The path to the referenced file
     * @param referenceBeginPosition The offset of the referenced content in the
     *                               referenced file
     * @param referenceLength        The length of the referenced content
     * @param outBeginPos            The offset position of this part in the
     *                               deflated PDF
     */
    VirtualPart(final String reference, final long referenceBeginPosition, final int referenceLength, final long outBeginPos) {
        this.reference = reference;
        this.refBeginPos = referenceBeginPosition;
        this.referenceLength = referenceLength;
        this.hardPart = null;
        this.outBeginPos = outBeginPos;
    }

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Calculates the length of the given part in the expanded PDF file
     *
     * @param parts the parts to consider
     * @return length
     */
    public static long calculateInflatedLength(final List<VirtualPart> parts) {
        long length = 0;

        for (final VirtualPart entry : parts) {
            length += entry.getInflatedLength();
        }
        return length;
    }

    /**
     * Calculates the length of the given parts in the deflated routing file
     *
     * @param parts the parts to consider
     * @return length
     */
    static long calculateDeflatedLength(final List<VirtualPart> parts) {
        long length = 0;

        for (final VirtualPart entry : parts) {
            length += entry.getDeflatedLength();
        }
        return length;
    }

    boolean isReference() {
        return this.reference != null;
    }

    byte[] getReferenceBytes() {
        if (this._referenceBytes == null) {
            this._referenceBytes = this.reference.getBytes(StandardCharsets.UTF_8);
        }
        return this._referenceBytes;
    }

    /**
     * Calculates the length of this part in the inflated PDF file
     *
     * @return length
     */
    public int getInflatedLength() {
        if (this.isReference()) {
            return referenceLength;
        } else {
            return hardPart.length;
        }
    }

    public void setOutBeginPos(final long offset) {
        this.outBeginPos = offset;
    }

    /**
     * Calculates the length of this part in the deflated routing file
     *
     * @return length
     */
    int getDeflatedLength() {
        if (this.isReference()) {
            return Byte.BYTES + Long.BYTES + Integer.BYTES + Integer.BYTES + this.getReferenceBytes().length;
        } else {
            return hardPart.length + Byte.BYTES + Integer.BYTES;
        }
    }

    /**
     * Returns the end position of this part in the referenced file.
     *
     * @return position
     */
    long getRefEndPos() {
        assert this.refBeginPos >= 0 : "RefBeginPos of VirtualPart < 0";
        return this.refBeginPos + this.getInflatedLength();
    }


    /**
     * Returns the end position of this part in the virtualized PDF
     *
     * @return position
     */
    long getOutEndPos() {
        assert this.outBeginPos >= 0 : "OutBeginPos of VirtualPart < 0";
        return this.outBeginPos + this.getInflatedLength();
    }
}
