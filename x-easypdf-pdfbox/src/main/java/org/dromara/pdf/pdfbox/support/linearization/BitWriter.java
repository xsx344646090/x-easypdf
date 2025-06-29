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


import java.io.IOException;
import java.io.OutputStream;


/**
 * [!CLASS_DESCRIPTION!]
 *
 * @author Marian Gavalier
 * @version $Id$
 */
class BitWriter {
    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------

    // byref übergeben
    private final char[] ch = new char[1];
    private final int[] bitOffset = new int[1];
    private final OutputStream str;
    private final int[] length = new int[1];

    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!CONSTR_DESCIRPTION_FOR_BitWriter!]
     *
     * @param str [!PARA_DESCRIPTION!]
     */
    BitWriter(final OutputStream str) {
        this.str = str;
        ch[0] = 0;
        length[0] = 0;
        bitOffset[0] = 7;
    }

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    private static void write_bits(final char[] ch, final int[] bitOffset, final long val, int bits, final OutputStream str, final int[] length) throws IOException {
        if (bits > 32) {
            throw new ArithmeticException("write_bits: too many bits requested");
        }

        // bit_offset + 1 is the number of bits left in ch
        while (bits > 0) {
            final int bitsToWrite = Math.min(bits, bitOffset[0] + 1);
            char newval = (char) ((val >> (bits - bitsToWrite)) & ((1 << bitsToWrite) - 1));
            final int bits_left_in_ch = bitOffset[0] + 1 - bitsToWrite;

            newval <<= bits_left_in_ch;
            ch[0] |= newval;
            if (bits_left_in_ch == 0) {
                str.write((byte) ch[0]);
                bitOffset[0] = 7;
                ch[0] = 0;
                length[0]++;
            } else {
                bitOffset[0] -= bitsToWrite;
            }
            bits -= bitsToWrite;
        }
    }

    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param val  [!PARA_DESCRIPTION!]
     * @param bits [!PARA_DESCRIPTION!]
     * @throws IOException [!EXC_DESCRIPTION!]
     */
    void writeBits(final long val, final int bits) throws IOException {
        write_bits(ch, bitOffset, val, bits, str, length);
    }

    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @return [!RETURN_DESCRIPTION!]
     */
    int getCount() {
        return length[0];
    }

    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @param val  [!PARA_DESCRIPTION!]
     * @param bits [!PARA_DESCRIPTION!]
     * @throws IOException [!EXC_DESCRIPTION!]
     */
    void writeBitsSigned(final long val, final int bits) throws IOException {
        long uval;

        if (val < 0) {
            uval = ((1 << bits) + val);
        } else {
            uval = val;
        }
        writeBits(uval, bits);
    }

    /**
     * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
     *
     * @throws IOException [!EXC_DESCRIPTION!]
     */
    void flush() throws IOException {
        if (bitOffset[0] < 7) {
            final int bitsToWrite = bitOffset[0] + 1;

            write_bits(ch, bitOffset, 0, bitsToWrite, str, length);
        }
    }
}
