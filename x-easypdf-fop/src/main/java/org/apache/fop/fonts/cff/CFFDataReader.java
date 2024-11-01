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

/* $Id$ */

package org.apache.fop.fonts.cff;

import org.apache.fontbox.cff.CFFOperator;
import org.apache.fontbox.cff.DataInputByteArray;
import org.apache.fop.fonts.truetype.FontFileReader;
import org.apache.fop.fonts.truetype.OTFFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to read the CFF data from an OTF CFF font file.
 */
public class CFFDataReader {
    private static final int DOUBLE_BYTE_OPERATOR = 12;
    private static final int NUM_STANDARD_STRINGS = 391;
    private DataInputByteArray cffData;
    private byte[] header;
    private CFFIndexData nameIndex;
    private CFFIndexData topDICTIndex;
    private CFFIndexData stringIndex;
    private CFFIndexData charStringIndex;
    private CFFIndexData globalIndexSubr;
    private CFFIndexData localIndexSubr;
    private CustomEncoding encoding;
    private FDSelect fdSelect;
    private List<FontDict> fdFonts;
    /**
     * Commonly used parsed dictionaries
     */
    private LinkedHashMap<String, DICTEntry> topDict;
    
    public CFFDataReader() {
    
    }
    
    /**
     * Constructor for the CFF data reader which accepts the CFF byte data
     * as an argument.
     *
     * @param cffDataArray A byte array which holds the CFF data
     */
    public CFFDataReader(byte[] cffDataArray) throws IOException {
        cffData = new DataInputByteArray(cffDataArray);
        readCFFData();
    }
    
    /**
     * Constructor for the CFF data reader which accepts a FontFileReader object
     * which points to the original font file as an argument.
     *
     * @param fontFile The font file as represented by a FontFileReader object
     */
    public CFFDataReader(FontFileReader fontFile) throws IOException {
        cffData = new DataInputByteArray(OTFFile.getCFFData(fontFile));
        readCFFData();
    }
    
    private void readCFFData() throws IOException {
        header = readHeader();
        nameIndex = readIndex();
        topDICTIndex = readIndex();
        topDict = parseDictData(topDICTIndex.getData());
        stringIndex = readIndex();
        globalIndexSubr = readIndex();
        charStringIndex = readCharStringIndex();
        encoding = readEncoding();
        fdSelect = readFDSelect();
        localIndexSubr = readLocalIndexSubrs();
        fdFonts = parseCIDData();
    }
    
    public Map<String, DICTEntry> getPrivateDict(DICTEntry privateEntry) throws IOException {
        return parseDictData(getPrivateDictBytes(privateEntry));
    }
    
    public byte[] getPrivateDictBytes(DICTEntry privateEntry) throws IOException {
        int privateLength = privateEntry.getOperands().get(0).intValue();
        int privateOffset = privateEntry.getOperands().get(1).intValue();
        return getCFFOffsetBytes(privateOffset, privateLength);
    }
    
    /**
     * Retrieves a number of bytes from the CFF data stream
     *
     * @param offset The offset of the bytes to retrieve
     * @param length The number of bytes to retrieve
     * @return Returns a byte array of requested bytes
     * @throws IOException Throws an IO Exception if an error occurs
     */
    private byte[] getCFFOffsetBytes(int offset, int length) throws IOException {
        cffData.setPosition(offset);
        return cffData.readBytes(length);
    }
    
    /**
     * Parses the dictionary data and returns a map of objects for each entry
     *
     * @param dictData The data for the dictionary data
     * @return Returns a map of type DICTEntry identified by the operand name
     * @throws IOException Throws an IO Exception if an error occurs
     */
    public LinkedHashMap<String, DICTEntry> parseDictData(byte[] dictData) throws IOException {
        LinkedHashMap<String, DICTEntry> dictEntries = new LinkedHashMap<String, DICTEntry>();
        List<Number> operands = new ArrayList<Number>();
        List<Integer> operandLengths = new ArrayList<Integer>();
        int lastOperandLength = 0;
        for (int i = 0; i < dictData.length; i++) {
            int readByte = dictData[i] & 0xFF;
            if (readByte < 28) {
                int[] operator = new int[(readByte == DOUBLE_BYTE_OPERATOR) ? 2 : 1];
                if (readByte == DOUBLE_BYTE_OPERATOR) {
                    operator[0] = dictData[i];
                    operator[1] = dictData[i + 1];
                    i++;
                } else {
                    operator[0] = dictData[i];
                }
                String operatorName = "";
                if (operator.length > 1) {
                    operatorName = CFFOperator.getOperator(operator[0], operator[1]);
                } else {
                    operatorName = CFFOperator.getOperator(operator[0]);
                }
                DICTEntry newEntry = new DICTEntry();
                newEntry.setOperator(operator);
                newEntry.setOperands(new ArrayList<Number>(operands));
                newEntry.setOperatorName(operatorName);
                newEntry.setOffset(i - lastOperandLength);
                newEntry.setOperandLength(lastOperandLength);
                newEntry.setOperandLengths(new ArrayList<Integer>(operandLengths));
                byte[] byteData = new byte[lastOperandLength + operator.length];
                System.arraycopy(dictData, i - operator.length - (lastOperandLength - 1),
                        byteData, 0, operator.length + lastOperandLength);
                newEntry.setByteData(byteData);
                dictEntries.put(operatorName, newEntry);
                operands.clear();
                operandLengths.clear();
                lastOperandLength = 0;
            } else {
                if (readByte >= 32 && readByte <= 246) {
                    operands.add(readByte - 139);
                    lastOperandLength += 1;
                    operandLengths.add(1);
                } else if (readByte >= 247 && readByte <= 250) {
                    operands.add((readByte - 247) * 256 + (dictData[i + 1] & 0xFF) + 108);
                    lastOperandLength += 2;
                    operandLengths.add(2);
                    i++;
                } else if (readByte >= 251 && readByte <= 254) {
                    operands.add(-(readByte - 251) * 256 - (dictData[i + 1] & 0xFF) - 108);
                    lastOperandLength += 2;
                    operandLengths.add(2);
                    i++;
                } else if (readByte == 28) {
                    operands.add((dictData[i + 1] & 0xFF) << 8 | (dictData[i + 2] & 0xFF));
                    lastOperandLength += 3;
                    operandLengths.add(3);
                    i += 2;
                } else if (readByte == 29) {
                    operands.add((dictData[i + 1] & 0xFF) << 24 | (dictData[i + 2] & 0xFF) << 16
                                         | (dictData[i + 3] & 0xFF) << 8 | (dictData[i + 4] & 0xFF));
                    lastOperandLength += 5;
                    operandLengths.add(5);
                    i += 4;
                } else if (readByte == 30) {
                    boolean terminatorFound = false;
                    StringBuilder realNumber = new StringBuilder();
                    int byteCount = 1;
                    do {
                        byte nibblesByte = dictData[++i];
                        byteCount++;
                        terminatorFound = readNibble(realNumber, (nibblesByte >> 4) & 0x0F);
                        if (!terminatorFound) {
                            terminatorFound = readNibble(realNumber, nibblesByte & 0x0F);
                        }
                    } while (!terminatorFound);
                    operands.add(Double.valueOf(realNumber.toString()));
                    lastOperandLength += byteCount;
                    operandLengths.add(byteCount);
                }
            }
        }
        return dictEntries;
    }
    
    private boolean readNibble(StringBuilder realNumber, int nibble) {
        if (nibble <= 0x9) {
            realNumber.append(nibble);
        } else {
            switch (nibble) {
                case 0xa:
                    realNumber.append(".");
                    break;
                case 0xb:
                    realNumber.append("E");
                    break;
                case 0xc:
                    realNumber.append("E-");
                    break;
                case 0xd:
                    break;
                case 0xe:
                    realNumber.append("-");
                    break;
                case 0xf:
                    return true;
                default:
                    throw new AssertionError("Unexpected nibble value");
            }
        }
        return false;
    }
    
    private byte[] readHeader() throws IOException {
        // Read known header
        byte[] fixedHeader = cffData.readBytes(4);
        int hdrSize = (fixedHeader[2] & 0xFF);
        byte[] extra = cffData.readBytes(hdrSize - 4);
        byte[] header = new byte[hdrSize];
        for (int i = 0; i < fixedHeader.length; i++) {
            header[i] = fixedHeader[i];
        }
        for (int i = 4; i < extra.length; i++) {
            header[i] = extra[i - 4];
        }
        return header;
    }
    
    /**
     * Reads a CFF index object are the specified offset position
     *
     * @param offset The position of the index object to read
     * @return Returns an object representing the index
     * @throws IOException Throws an IO Exception if an error occurs
     */
    public CFFIndexData readIndex(int offset) throws IOException {
        cffData.setPosition(offset);
        return readIndex();
    }
    
    private CFFIndexData readIndex() throws IOException {
        return readIndex(cffData);
    }
    
    /**
     * Reads an index from the current position of the DataInputByteArray object
     *
     * @param input The object holding the CFF byte data
     * @return Returns an object representing the index
     * @throws IOException Throws an IO Exception if an error occurs
     */
    public CFFIndexData readIndex(DataInputByteArray input) throws IOException {
        CFFIndexData nameIndex = new CFFIndexData();
        if (input != null) {
            int origPos = input.getPosition();
            nameIndex.parseIndexHeader(input);
            int tableSize = input.getPosition() - origPos;
            nameIndex.setByteData(input.getPosition() - tableSize, tableSize);
        }
        return nameIndex;
    }
    
    /**
     * Retrieves the SID for the given GID object
     *
     * @param charsetOffset The offset of the charset data
     * @param gid           The GID for which to retrieve the SID
     * @return Returns the SID as an integer
     */
    public int getSIDFromGID(int charsetOffset, int gid) throws IOException {
        if (gid == 0) {
            return 0;
        }
        cffData.setPosition(charsetOffset);
        int charsetFormat = cffData.readUnsignedByte();
        switch (charsetFormat) {
            case 0: // Adjust for .notdef character
                cffData.setPosition(cffData.getPosition() + (--gid * 2));
                return cffData.readUnsignedShort();
            case 1:
                return getSIDFromGIDFormat(gid, 1);
            case 2:
                return getSIDFromGIDFormat(gid, 2);
            default:
                return 0;
        }
    }
    
    private int getSIDFromGIDFormat(int gid, int format) throws IOException {
        int glyphCount = 0;
        while (true) {
            int oldGlyphCount = glyphCount;
            int start = cffData.readUnsignedShort();
            glyphCount += ((format == 1) ? cffData.readUnsignedByte() : cffData.readUnsignedShort()) + 1;
            if (gid <= glyphCount) {
                return start + (gid - oldGlyphCount) - 1;
            }
        }
    }
    
    public byte[] getHeader() {
        return header.clone();
    }
    
    public CFFIndexData getNameIndex() {
        return nameIndex;
    }
    
    public CFFIndexData getTopDictIndex() {
        return topDICTIndex;
    }
    
    public LinkedHashMap<String, DICTEntry> getTopDictEntries() {
        return topDict;
    }
    
    public CFFIndexData getStringIndex() {
        return stringIndex;
    }
    
    public CFFIndexData getGlobalIndexSubr() {
        return globalIndexSubr;
    }
    
    public CFFIndexData getLocalIndexSubr() {
        return localIndexSubr;
    }
    
    public CFFIndexData getCharStringIndex() {
        return charStringIndex;
    }
    
    public DataInputByteArray getCFFData() {
        return cffData;
    }
    
    public CustomEncoding getEncoding() {
        return encoding;
    }
    
    public FDSelect getFDSelect() {
        return fdSelect;
    }
    
    public List<FontDict> getFDFonts() {
        return fdFonts;
    }
    
    public DataInputByteArray getLocalSubrsForGlyph(int glyph) throws IOException {
        // Subsets are currently written using a Format0 FDSelect
        FDSelect fontDictionary = getFDSelect();
        if (fontDictionary instanceof Format0FDSelect) {
            Format0FDSelect fdSelect = (Format0FDSelect) fontDictionary;
            int found = fdSelect.getFDIndexes()[glyph];
            FontDict font = getFDFonts().get(found);
            byte[] localSubrData = font.getLocalSubrData().getByteData();
            if (localSubrData != null) {
                return new DataInputByteArray(localSubrData);
            } else {
                return null;
            }
        } else if (fontDictionary instanceof Format3FDSelect) {
            Format3FDSelect fdSelect = (Format3FDSelect) fontDictionary;
            int index = 0;
            for (int first : fdSelect.getRanges().keySet()) {
                if (first > glyph) {
                    break;
                }
                index++;
            }
            FontDict font = getFDFonts().get(index);
            byte[] localSubrsData = font.getLocalSubrData().getByteData();
            if (localSubrsData != null) {
                return new DataInputByteArray(localSubrsData);
            } else {
                return null;
            }
        }
        return null;
    }
    
    /**
     * Parses the char string index from the CFF byte data
     *
     * @return Returns the char string index object
     * @throws IOException Throws an IO Exception if an error occurs
     */
    public CFFIndexData readCharStringIndex() throws IOException {
        int offset = topDict.get("CharStrings").getOperands().get(0).intValue();
        cffData.setPosition(offset);
        return readIndex();
    }
    
    private CustomEncoding readEncoding() throws IOException {
        CustomEncoding foundEncoding = null;
        if (topDict.get("Encoding") != null) {
            int offset = topDict.get("Encoding").getOperands().get(0).intValue();
            if (offset != 0 && offset != 1) {
                // No need to set the offset as we are reading the data sequentially.
                int format = cffData.readUnsignedByte();
                int numEntries = cffData.readUnsignedByte();
                switch (format) {
                    case 0:
                        foundEncoding = readFormat0Encoding(format, numEntries);
                        break;
                    case 1:
                        foundEncoding = readFormat1Encoding(format, numEntries);
                        break;
                    default:
                        break;
                }
            }
        }
        return foundEncoding;
    }
    
    private Format0Encoding readFormat0Encoding(int format, int numEntries)
            throws IOException {
        Format0Encoding newEncoding = new Format0Encoding();
        newEncoding.setFormat(format);
        newEncoding.setNumEntries(numEntries);
        int[] codes = new int[numEntries];
        for (int i = 0; i < numEntries; i++) {
            codes[i] = cffData.readUnsignedByte();
        }
        newEncoding.setCodes(codes);
        return newEncoding;
    }
    
    private Format1Encoding readFormat1Encoding(int format, int numEntries)
            throws IOException {
        Format1Encoding newEncoding = new Format1Encoding();
        newEncoding.setFormat(format);
        newEncoding.setNumEntries(numEntries);
        Map<Integer, Integer> ranges = new LinkedHashMap<Integer, Integer>();
        for (int i = 0; i < numEntries; i++) {
            int first = cffData.readUnsignedByte();
            int left = cffData.readUnsignedByte();
            ranges.put(first, left);
        }
        newEncoding.setRanges(ranges);
        return newEncoding;
    }
    
    private FDSelect readFDSelect() throws IOException {
        FDSelect fdSelect = null;
        DICTEntry fdSelectEntry = topDict.get("FDSelect");
        if (fdSelectEntry != null) {
            int fdOffset = fdSelectEntry.getOperands().get(0).intValue();
            cffData.setPosition(fdOffset);
            int format = cffData.readUnsignedByte();
            switch (format) {
                case 0:
                    fdSelect = readFormat0FDSelect();
                    break;
                case 3:
                    fdSelect = readFormat3FDSelect();
                    break;
                default:
            }
        }
        return fdSelect;
    }
    
    private Format0FDSelect readFormat0FDSelect() throws IOException {
        Format0FDSelect newFDs = new Format0FDSelect();
        newFDs.setFormat(0);
        int glyphCount = charStringIndex.getNumObjects();
        int[] fds = new int[glyphCount];
        for (int i = 0; i < glyphCount; i++) {
            fds[i] = cffData.readUnsignedByte();
        }
        newFDs.setFDIndexes(fds);
        return newFDs;
    }
    
    private Format3FDSelect readFormat3FDSelect() throws IOException {
        Format3FDSelect newFDs = new Format3FDSelect();
        newFDs.setFormat(3);
        int rangeCount = cffData.readUnsignedShort();
        newFDs.setRangeCount(rangeCount);
        Map<Integer, Integer> ranges = new LinkedHashMap<Integer, Integer>();
        for (int i = 0; i < rangeCount; i++) {
            int first = cffData.readUnsignedShort();
            int fd = cffData.readUnsignedByte();
            ranges.put(first, fd);
        }
        newFDs.setRanges(ranges);
        newFDs.setSentinelGID(cffData.readUnsignedShort());
        return newFDs;
    }
    
    private List<FontDict> parseCIDData() throws IOException {
        List<FontDict> fdFonts = new ArrayList<FontDict>();
        if (topDict.get("ROS") != null) {
            DICTEntry fdArray = topDict.get("FDArray");
            if (fdArray != null) {
                int fdIndex = fdArray.getOperands().get(0).intValue();
                CFFIndexData fontDicts = readIndex(fdIndex);
                for (int i = 0; i < fontDicts.getNumObjects(); i++) {
                    FontDict newFontDict = new FontDict();
                    
                    byte[] fdData = fontDicts.getValue(i);
                    Map<String, DICTEntry> fdEntries = parseDictData(fdData);
                    newFontDict.setByteData(fontDicts.getValuePosition(i), fontDicts.getValueLength(i));
                    DICTEntry fontFDEntry = fdEntries.get("FontName");
                    if (fontFDEntry != null) {
                        newFontDict.setFontName(getString(fontFDEntry.getOperands().get(0).intValue()));
                    }
                    DICTEntry privateFDEntry = fdEntries.get("Private");
                    if (privateFDEntry != null) {
                        newFontDict = setFDData(privateFDEntry, newFontDict);
                    }
                    
                    fdFonts.add(newFontDict);
                }
            }
        }
        return fdFonts;
    }
    
    private FontDict setFDData(DICTEntry privateFDEntry, FontDict newFontDict) throws IOException {
        int privateFDLength = privateFDEntry.getOperands().get(0).intValue();
        int privateFDOffset = privateFDEntry.getOperands().get(1).intValue();
        cffData.setPosition(privateFDOffset);
        byte[] privateDict = cffData.readBytes(privateFDLength);
        newFontDict.setPrivateDictData(privateFDOffset, privateFDLength);
        Map<String, DICTEntry> privateEntries = parseDictData(privateDict);
        DICTEntry subroutines = privateEntries.get("Subrs");
        if (subroutines != null) {
            CFFIndexData localSubrs = readIndex(privateFDOffset
                                                        + subroutines.getOperands().get(0).intValue());
            newFontDict.setLocalSubrData(localSubrs);
        } else {
            newFontDict.setLocalSubrData(new CFFIndexData());
        }
        return newFontDict;
    }
    
    private String getString(int sid) throws IOException {
        return new String(stringIndex.getValue(sid - NUM_STANDARD_STRINGS));
    }
    
    private CFFIndexData readLocalIndexSubrs() throws IOException {
        CFFIndexData localSubrs = null;
        DICTEntry privateEntry = topDict.get("Private");
        if (privateEntry != null) {
            int length = privateEntry.getOperands().get(0).intValue();
            int offset = privateEntry.getOperands().get(1).intValue();
            cffData.setPosition(offset);
            byte[] privateData = cffData.readBytes(length);
            Map<String, DICTEntry> privateDict = parseDictData(privateData);
            DICTEntry localSubrsEntry = privateDict.get("Subrs");
            if (localSubrsEntry != null) {
                int localOffset = offset + localSubrsEntry.getOperands().get(0).intValue();
                cffData.setPosition(localOffset);
                localSubrs = readIndex();
            }
        }
        return localSubrs;
    }
    
    /**
     * A class containing data for a dictionary entry
     */
    public static class DICTEntry {
        private int[] operator;
        private List<Number> operands;
        private List<Integer> operandLengths;
        private String operatorName;
        private int offset;
        private int operandLength;
        private byte[] data = new byte[0];
        
        public int[] getOperator() {
            return this.operator;
        }
        
        public void setOperator(int[] operator) {
            this.operator = operator;
        }
        
        public List<Number> getOperands() {
            return this.operands;
        }
        
        public void setOperands(List<Number> operands) {
            this.operands = operands;
        }
        
        public String getOperatorName() {
            return this.operatorName;
        }
        
        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }
        
        public int getOffset() {
            return this.offset;
        }
        
        public void setOffset(int offset) {
            this.offset = offset;
        }
        
        public int getOperandLength() {
            return this.operandLength;
        }
        
        public void setOperandLength(int operandLength) {
            this.operandLength = operandLength;
        }
        
        public byte[] getByteData() {
            return data.clone();
        }
        
        public void setByteData(byte[] data) {
            this.data = data.clone();
        }
        
        public List<Integer> getOperandLengths() {
            return operandLengths;
        }
        
        public void setOperandLengths(List<Integer> operandLengths) {
            this.operandLengths = operandLengths;
        }
    }
    
    private static class DataLocation {
        private int dataPosition;
        private int dataLength;
        
        public DataLocation() {
            dataPosition = 0;
            dataLength = 0;
        }
        
        public DataLocation(int position, int length) {
            this.dataPosition = position;
            this.dataLength = length;
        }
        
        public int getDataPosition() {
            return dataPosition;
        }
        
        public int getDataLength() {
            return dataLength;
        }
    }
    
    /**
     * Parent class which provides the ability to retrieve byte data from
     * a sub-table.
     */
    public class CFFSubTable {
        private DataLocation dataLocation = new DataLocation();
        
        public void setByteData(int position, int length) {
            dataLocation = new DataLocation(position, length);
        }
        
        public byte[] getByteData() throws IOException {
            int oldPos = cffData.getPosition();
            try {
                cffData.setPosition(dataLocation.getDataPosition());
                return cffData.readBytes(dataLocation.getDataLength());
            } finally {
                cffData.setPosition(oldPos);
            }
        }
    }
    
    /**
     * An object used to hold index data from the CFF data
     */
    public class CFFIndexData extends CFFSubTable {
        private int numObjects;
        private int offSize;
        private int[] offsets = new int[0];
        private DataLocation dataLocation = new DataLocation();
        
        public int getNumObjects() {
            return this.numObjects;
        }
        
        public void setNumObjects(int numObjects) {
            this.numObjects = numObjects;
        }
        
        public int getOffSize() {
            return this.offSize;
        }
        
        public void setOffSize(int offSize) {
            this.offSize = offSize;
        }
        
        public int[] getOffsets() {
            return offsets.clone();
        }
        
        public void setOffsets(int[] offsets) {
            this.offsets = offsets.clone();
        }
        
        public void setData(int position, int length) {
            dataLocation = new DataLocation(position, length);
        }
        
        public byte[] getData() throws IOException {
            int origPos = cffData.getPosition();
            try {
                cffData.setPosition(dataLocation.getDataPosition());
                return cffData.readBytes(dataLocation.getDataLength());
            } finally {
                cffData.setPosition(origPos);
            }
        }
        
        /**
         * Parses index data from an index object found within the CFF byte data
         *
         * @param cffData A byte array containing the CFF data
         * @throws IOException Throws an IO Exception if an error occurs
         */
        public void parseIndexHeader(DataInputByteArray cffData) throws IOException {
            setNumObjects(cffData.readUnsignedShort());
            setOffSize(cffData.readUnsignedByte());
            int[] offsets = new int[getNumObjects() + 1];
            byte[] bytes;
            // Fills the offsets array
            for (int i = 0; i <= getNumObjects(); i++) {
                switch (getOffSize()) {
                    case 1:
                        offsets[i] = cffData.readUnsignedByte();
                        break;
                    case 2:
                        offsets[i] = cffData.readUnsignedShort();
                        break;
                    case 3:
                        bytes = cffData.readBytes(3);
                        offsets[i] = ((bytes[0] & 0xFF) << 16) + ((bytes[1] & 0xFF) << 8) + (bytes[2] & 0xFF);
                        break;
                    case 4:
                        bytes = cffData.readBytes(4);
                        offsets[i] = ((bytes[0] & 0xFF) << 24) + ((bytes[1] & 0xFF) << 16)
                                             + ((bytes[2] & 0xFF) << 8) + (bytes[3] & 0xFF);
                        break;
                    default:
                        continue;
                }
            }
            setOffsets(offsets);
            int position = cffData.getPosition();
            int dataSize = offsets[offsets.length - 1] - offsets[0];
            int resetPosition = cffData.getPosition() + dataSize;
            if (resetPosition == cffData.length()) {
                resetPosition = resetPosition - 1;
            }
            cffData.setPosition(resetPosition);
            setData(position, dataSize);
        }
        
        /**
         * Retrieves data from the index data
         *
         * @param index The index position of the data to retrieve
         * @return Returns the byte data for the given index
         * @throws IOException Throws an IO Exception if an error occurs
         */
        public byte[] getValue(int index) throws IOException {
            int oldPos = cffData.getPosition();
            try {
                cffData.setPosition(dataLocation.getDataPosition() + (offsets[index] - 1));
                return cffData.readBytes(offsets[index + 1] - offsets[index]);
            } finally {
                cffData.setPosition(oldPos);
            }
        }
        
        public int getValuePosition(int index) {
            return dataLocation.getDataPosition() + (offsets[index] - 1);
        }
        
        public int getValueLength(int index) {
            return offsets[index + 1] - offsets[index];
        }
    }
    
    public abstract class CustomEncoding {
        private int format;
        private int numEntries;
        
        public int getFormat() {
            return format;
        }
        
        public void setFormat(int format) {
            this.format = format;
        }
        
        public int getNumEntries() {
            return numEntries;
        }
        
        public void setNumEntries(int numEntries) {
            this.numEntries = numEntries;
        }
    }
    
    public class Format0Encoding extends CustomEncoding {
        private int[] codes = new int[0];
        
        public int[] getCodes() {
            return codes.clone();
        }
        
        public void setCodes(int[] codes) {
            this.codes = codes.clone();
        }
    }
    
    public class Format1Encoding extends CustomEncoding {
        private Map<Integer, Integer> ranges;
        
        public Map<Integer, Integer> getRanges() {
            return ranges;
        }
        
        public void setRanges(Map<Integer, Integer> ranges) {
            this.ranges = ranges;
        }
    }
    
    public abstract class FDSelect {
        private int format;
        
        public int getFormat() {
            return format;
        }
        
        public void setFormat(int format) {
            this.format = format;
        }
    }
    
    public class Format0FDSelect extends FDSelect {
        private int[] fds = new int[0];
        
        public int[] getFDIndexes() {
            return fds.clone();
        }
        
        public void setFDIndexes(int[] fds) {
            this.fds = fds.clone();
        }
    }
    
    public class Format3FDSelect extends FDSelect {
        private int rangeCount;
        private Map<Integer, Integer> ranges;
        private int sentinelGID;
        
        public int getRangeCount() {
            return rangeCount;
        }
        
        public void setRangeCount(int rangeCount) {
            this.rangeCount = rangeCount;
        }
        
        public Map<Integer, Integer> getRanges() {
            return ranges;
        }
        
        public void setRanges(Map<Integer, Integer> ranges) {
            this.ranges = ranges;
        }
        
        public int getSentinelGID() {
            return sentinelGID;
        }
        
        public void setSentinelGID(int sentinelGID) {
            this.sentinelGID = sentinelGID;
        }
    }
    
    public class FontDict extends CFFSubTable {
        private String fontName;
        private DataLocation dataLocation = new DataLocation();
        private CFFIndexData localSubrData;
        
        public String getFontName() {
            return fontName;
        }
        
        public void setFontName(String groupName) {
            this.fontName = groupName;
        }
        
        public void setPrivateDictData(int position, int length) {
            dataLocation = new DataLocation(position, length);
        }
        
        public byte[] getPrivateDictData() throws IOException {
            int origPos = cffData.getPosition();
            try {
                cffData.setPosition(dataLocation.getDataPosition());
                return cffData.readBytes(dataLocation.getDataLength());
            } finally {
                cffData.setPosition(origPos);
            }
        }
        
        public CFFIndexData getLocalSubrData() {
            return localSubrData;
        }
        
        public void setLocalSubrData(CFFIndexData localSubrData) {
            this.localSubrData = localSubrData;
        }
    }
}
