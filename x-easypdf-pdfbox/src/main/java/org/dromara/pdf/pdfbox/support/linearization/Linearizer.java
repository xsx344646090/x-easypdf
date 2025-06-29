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
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdfparser.xref.NormalXReference;
import org.apache.pdfbox.pdfparser.xref.XReferenceEntry;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class that is able to create a linearized version of a CompoundPDFDocument.
 * No outlines or other advanced PDF-features will be generated.
 *
 * @author Jonathan Rahn
 * @version $Id$
 */
public class Linearizer {
    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------

    private final PDDocument document;

    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------

    /**
     * Default constructor
     *
     * @param doc document to linearize
     */
    @SneakyThrows
    public Linearizer(final PDDocument doc) {
        SimplifiedPDFDocument simplified = new SimplifiedPDFDocument(doc.getDocument());
        this.document = new PDDocument(simplified);
    }

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Starts the linearization
     *
     * @return Parts of the linearized PDF dumped in a WrittenObjectStore
     */
    @SneakyThrows
    public WrittenObjectStore linearize() {
        // optimiere objekte und ordne diese in richtige Reihenfolge
        final StructuredPDFInfo info = new StructuredPDFInfo(this.document.getDocument());

        info.getLinearizedParts();

        // Object number sequence:
        //
        // second half
        // second half uncompressed objects
        // second half xref stream, if any
        // second half compressed objects
        // first half
        // linearization dictionary
        // first half xref stream, if any
        // part 4 uncompressed objects
        // encryption dictionary, if any
        // hint stream
        // part 6 uncompressed objects
        // first half compressed objects
        // Second half objects
        final LinearizedPDFWriter writer = new LinearizedPDFWriter(this.document);
        final LinearizationInformation offsets = new LinearizationInformation();
        final WrittenObjectStore writtenStore = new WrittenObjectStore();
        final COSDictionary firstTrailer = document.getDocument().getTrailer();
        final COSDictionary secTrailer = new COSDictionary();
        final PDFObjectQueue queue = new PDFObjectQueue();

        queue.setObjectID(info.sizeSecondPart());

        // First half objects
        queue.enqueueDummy(PDFObjectQueue.PDFDummyObjects.LINDICT);
        queue.enqueueDummy(PDFObjectQueue.PDFDummyObjects.FIRSTXREF);
        queue.enqueuePart(info.part4);
        queue.enqueueDummy(PDFObjectQueue.PDFDummyObjects.HINTSTREAM);
        queue.enqueuePart(info.part6);
        offsets.firstTrailerSize = queue.getNextID();

        queue.resetObjectID();
        queue.enqueuePart(info.part7);
        queue.enqueuePart(info.part8);
        queue.enqueuePart(info.part9);
        queue.enqueueDummy(PDFObjectQueue.PDFDummyObjects.SECONDXREF);
        offsets.secondTrailerSize = queue.getNextID();

        try {
            final COSObject linearizedDictObject = this.createLinearizedDictionary(queue.get(PDFObjectQueue.PDFDummyObjects.LINDICT).objNumber, writer);

            this.setObjectNumbers(queue, writer);

            // adjust trailers
            secTrailer.setInt(COSName.SIZE, offsets.secondTrailerSize);
            // this.document.addIDToTrailer(firstTrailer);
            firstTrailer.setInt(COSName.SIZE, offsets.firstTrailerSize);
            firstTrailer.setLong(COSName.PREV, Long.MAX_VALUE);

            writer.writeHeader();
            writer.doWriteObject(linearizedDictObject);
            offsets.firstXrefOffset = writer.getPos();
            writer.doWriteXrefRangeDummy(this.document.getDocument(), offsets.getFirstXrefStart(), offsets.getFirstXrefLength(), firstTrailer, queue.get(PDFObjectQueue.PDFDummyObjects.FIRSTXREF).objNumber, 0);
            offsets.firstXrefEnd = writtenStore.add(WrittenObjectStore.SpecialParts.PARTS_BEFORE_FIRST_XREF, writer.getAndResetParts());

            this.writeObjects(queue, writer, writtenStore, offsets, info);

            final COSObject hintStreamObject = this.fillHintStream(info, offsets, queue, writer);

            writer.doWriteObject(hintStreamObject);
            offsets.hintLength = writtenStore.add(WrittenObjectStore.SpecialParts.HINTSTREAM, writer.getAndResetParts());

            // we have all mandatory sizes, compute missed values
            COSArray cosArray = ((COSDictionary) linearizedDictObject.getObject()).getCOSArray(COSName.H);
            cosArray.set(0, COSInteger.get(offsets.linDictOffset));
            cosArray.set(1, COSInteger.get(offsets.hintLength));

            final List<XReferenceEntry> xrefentries = writer.retrieveXRefEntries();
            final List<XReferenceEntry> xrefentries_first = new ArrayList<>();
            final ArrayList<XReferenceEntry> xrefentries_sec = new ArrayList<>(queue.size());
            long cumulateLength = this.fillXRefEntries(xrefentries, xrefentries_first, xrefentries_sec, writtenStore, queue, info.getPart4EndMarker(), info.getPart6EndMarker());

            firstTrailer.setLong(COSName.PREV, cumulateLength);
            offsets.mainXrefOffset = cumulateLength;

            // write end file again - this time with corect values
            writer.doWriteXrefRange(this.document.getDocument(), offsets.getSecondXrefLength(), xrefentries_sec, secTrailer, queue.get(PDFObjectQueue.PDFDummyObjects.SECONDXREF).objNumber, offsets.firstXrefOffset, cumulateLength);
            cumulateLength += writtenStore.add(WrittenObjectStore.SpecialParts.SECOND_XREF_PART, writer.getAndResetParts());

            // Now that we now the correct size of the main cross-reference stream, we can fill the linearization dictionary
            final COSDictionary linearizedDict = (COSDictionary) linearizedDictObject.getObject();

            linearizedDict.setLong(COSName.L, cumulateLength);
            linearizedDict.setLong(COSName.O, queue.get(info.getPages().get(0)).objNumber);
            linearizedDict.setInt(COSName.N, info.getPages().size());
            linearizedDict.setLong(COSName.E, offsets.getEndFirstPage());
            linearizedDict.setLong(COSName.T, offsets.mainXrefOffset);

            // Now we've got all elements we needed, write everything remaining. Ordinary objects and main xref stream are already written.
            writer.writeHeader();
            writer.removeWrittenObject(linearizedDictObject);
            writer.doWriteObject(linearizedDictObject);
            writer.fillUntilPos(offsets.firstXrefOffset);
            writer.doWriteXrefRange(this.document.getDocument(), offsets.getFirstXrefLength(), xrefentries_first, firstTrailer, queue.get(PDFObjectQueue.PDFDummyObjects.FIRSTXREF).objNumber, 0, offsets.firstXrefOffset);
            writer.fillUntilPos(offsets.firstXrefEnd);
            writtenStore.add(WrittenObjectStore.SpecialParts.PARTS_BEFORE_FIRST_XREF, writer.getAndResetParts());
            return writtenStore;
        } catch (IOException ex) {
            throw new ArithmeticException(ex.toString());
        }
    }

    @SneakyThrows
    public void close() {
        if (this.document != null) {
            this.document.close();
        }
    }


    private COSObject fillHintStream(final StructuredPDFInfo info, final LinearizationInformation offsets, final PDFObjectQueue queue, final LinearizedPDFWriter writer) throws IOException {
        // Calculate minimum and maximum values for number of objects per
        // page and page length.
        final HPageOffset pageOffsetHints = HPageOffset.filledPageOffsetHints(info, queue, offsets.linDictOffset, info.getPages().size());
        final HGeneric outlineHints = HGeneric.filledOutlineHints(info, queue, offsets);
        final HSharedObject sharedObjectHints = HSharedObject.filledSharedObjectHints(info, queue, offsets.firstSharedOffset);
        final COSObject hintStreamObject = this.createHintStream(queue.get(PDFObjectQueue.PDFDummyObjects.HINTSTREAM).objNumber, writer);
        final COSStream cosstr = (COSStream) hintStreamObject.getObject();

        try (final OutputStream output = cosstr.createOutputStream()) {
            final BitWriter w = new BitWriter(output);

            pageOffsetHints.writeHPageOffset(w);
            cosstr.setInt(COSName.S, w.getCount());
            sharedObjectHints.writeHSharedObject(w);
            if (outlineHints.nobjects > 0) {
                cosstr.setInt(COSName.O, w.getCount());
                outlineHints.writeHGeneric(w);
            }
        }
        return hintStreamObject;
    }


    private void setObjectNumbers(final PDFObjectQueue queue, final LinearizedPDFWriter writer) {
        for (final Map.Entry<COSBase, PDFObjectQueue.ObjectMetaData> entry : queue.entrySet()) {
            writer.getObjectKeys().put(entry.getKey(), new COSObjectKey(entry.getValue().objNumber, 0));  // always gen 0
            if (entry.getKey() instanceof COSObject) {
                writer.getObjectKeys().put(((COSObject) entry.getKey()).getObject(), new COSObjectKey(entry.getValue().objNumber, 0));  // always gen 0
            }
        }
    }


    private long fillXRefEntries(final List<XReferenceEntry> xrefentries, final List<XReferenceEntry> xrefentries_first, final List<XReferenceEntry> xrefentries_sec, final WrittenObjectStore objParts, final PDFObjectQueue queue, final COSBase part4_end_marker, final COSBase part6_end_marker) {
        xrefentries.sort(LinearizedPDFWriter.XREFCOMP);

        List<XReferenceEntry> xrefentries_write = xrefentries_first;

        xrefentries_first.add(getObjectXrefEntryBinary(xrefentries, queue.get(PDFObjectQueue.PDFDummyObjects.LINDICT).objNumber));

        long cumulateLength = objParts.getLength(WrittenObjectStore.SpecialParts.PARTS_BEFORE_FIRST_XREF);

        for (final HashMap.Entry<COSBase, PDFObjectQueue.ObjectMetaData> entry : queue.entrySet()) {
            final COSBase obj = entry.getKey();
            final PDFObjectQueue.ObjectMetaData objData = entry.getValue();

            long beginObject = cumulateLength;

            cumulateLength += objData.objLength;

            XReferenceEntry xrefentry = getObjectXrefEntryBinary(xrefentries, objData.objNumber);

            ((NormalXReference) xrefentry).setOffset(beginObject);
            xrefentries_write.add(xrefentry);

            if (part4_end_marker == obj) {
                beginObject = cumulateLength;
                cumulateLength += objParts.getLength(WrittenObjectStore.SpecialParts.HINTSTREAM);
                xrefentry = getObjectXrefEntryBinary(xrefentries, queue.get(PDFObjectQueue.PDFDummyObjects.HINTSTREAM).objNumber);
                ((NormalXReference) xrefentry).setOffset(beginObject);
                xrefentries_write.add(xrefentry);
            } else if (part6_end_marker == obj) {
                xrefentries_write = xrefentries_sec;
            }
        }
        return cumulateLength;
    }


    private void writeObjects(final PDFObjectQueue queue, final LinearizedPDFWriter writer, final WrittenObjectStore objParts, final LinearizationInformation offsets, final StructuredPDFInfo info) throws IOException {
        long outlineCount = 0;
        long cumulateLength = offsets.firstXrefEnd;
        final COSBase part4EndMarker = info.getPart4EndMarker();
        final COSBase part6EndMarker = info.getPart6EndMarker();

        for (final HashMap.Entry<COSBase, PDFObjectQueue.ObjectMetaData> entry : queue.entrySet()) {
            final COSBase obj = entry.getKey();
            final PDFObjectQueue.ObjectMetaData objData = entry.getValue();

            final long objLength = writer.doWriteObjectInSequence(obj);

            objData.objLength = objLength;
            cumulateLength += objLength;

            if (outlineCount-- > 0) {
                offsets.outlineLength += objLength;
            }

            if (part4EndMarker == obj) {
                objParts.add(WrittenObjectStore.SpecialParts.FIRST_PART_OBJECTS, writer.getAndResetParts());
                offsets.linDictOffset = cumulateLength;
            } else if (part6EndMarker == obj) {
                offsets.part6EndOffset = cumulateLength;
            } else if ((info.outlineData.first_object != null) && (info.outlineData.first_object == (COSObject) obj)) {
                offsets.firstObjectOffset = cumulateLength - objLength;
                outlineCount = info.outlineData.nobjects - 1;
                offsets.outlineLength = objLength;
            } else if ((info.sharedObjectData.first_shared_obj != null) && (info.sharedObjectData.first_shared_obj == (COSObject) obj)) {
                offsets.firstSharedOffset = cumulateLength - objLength;
            }
        }
        objParts.add(WrittenObjectStore.SpecialParts.SECOND_PART_OBJECTS, writer.getAndResetParts());
    }


    private XReferenceEntry getObjectXrefEntryBinary(final List<XReferenceEntry> xrefentries, final long objNr) {
        int start = 0;
        int end = xrefentries.size() - 1;

        while (start <= end) {
            final int mid = (start + end) / 2;
            final XReferenceEntry midEntry = xrefentries.get(mid);

            if (midEntry == null) {
                break; // No element found, exception thrown at end of method
            }

            final long midNum = midEntry.getReferencedKey().getNumber();

            if (midNum == objNr) {
                return midEntry;
            }
            if (midNum < objNr) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        throw new ArithmeticException("No Xref-entry found for object number " + objNr);
    }


    private COSObject createLinearizedDictionary(final int lindict_id, final LinearizedPDFWriter writer) throws IOException {
        final COSObject linearizedDictionary = createDummyLinearizedDictionary();

        linearizedDictionary.setKey(new COSObjectKey(lindict_id, 0));
        writer.getObjectKeys().put(linearizedDictionary, new COSObjectKey(lindict_id, 0));  // always gen 0
        writer.getObjectKeys().put(linearizedDictionary.getObject(), new COSObjectKey(lindict_id, 0));  // always gen 0
        return linearizedDictionary;
    }


    private COSObject createHintStream(final int hint_id, final LinearizedPDFWriter writer) throws IOException {
        final COSStream hintStream = new COSStream();
        final COSObject hintStreamObject = new COSObject(hintStream);

        hintStream.setItem(COSName.FILTER, COSName.FLATE_DECODE);
        hintStreamObject.setKey(new COSObjectKey(hint_id, 0));
        writer.getObjectKeys().put(hintStreamObject, new COSObjectKey(hint_id, 0));  // always gen 0
        writer.getObjectKeys().put(hintStream, new COSObjectKey(hint_id, 0));  // always gen 0
        return hintStreamObject;
    }


    private COSObject createDummyLinearizedDictionary() throws IOException {
        final long DUMMY_MAX_VALUE_LONG = Long.MAX_VALUE;
        final int DUMMY_MAX_VALUE_INT = Integer.MAX_VALUE;

        // Set longest possible dummy values and fill them in later
        final COSDictionary linDict = new COSDictionary();

        linDict.setInt(COSName.getPDFName("Linearized"), 1);
        linDict.setLong(COSName.L, DUMMY_MAX_VALUE_LONG);

        final COSArray linDictH = new COSArray();

        linDictH.add(COSInteger.get(DUMMY_MAX_VALUE_INT));
        linDictH.add(COSInteger.get(DUMMY_MAX_VALUE_INT));
        linDict.setItem(COSName.H, linDictH);
        linDict.setInt(COSName.O, DUMMY_MAX_VALUE_INT);
        linDict.setLong(COSName.E, DUMMY_MAX_VALUE_LONG);
        linDict.setInt(COSName.N, DUMMY_MAX_VALUE_INT);
        linDict.setLong(COSName.T, DUMMY_MAX_VALUE_LONG);

        final COSObject retVal = new COSObject(linDict);

        retVal.setKey(new COSObjectKey(DUMMY_MAX_VALUE_LONG, 0));
        return retVal;
    }

    //~ Inner Classes ---------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!CLASS_DESCRIPTION!]
     *
     * @author Jonathan Rahn
     * @version $Id$
     */
    class LinearizationInformation {
        //~ Instance members ---------------------------------------------------------------------------------------------------------------------------

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long firstObjectOffset = 0;

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long outlineLength = 0;

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long linDictOffset = 0;

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long firstXrefOffset;

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long firstSharedOffset = 0;

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long part6EndOffset = 0;

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long mainXrefOffset = 0;

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long firstXrefEnd = 0;

        /**
         * [!FIELD_DESCRIPTION!]
         */
        long hintLength = 0;

        /**
         * [!FIELD_DESCRIPTION!]
         */
        int firstTrailerSize = 0;

        /**
         * [!FIELD_DESCRIPTION!]
         */
        int secondTrailerSize = 0;

        //~ Methods ------------------------------------------------------------------------------------------------------------------------------------

        /**
         * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
         *
         * @return [!RETURN_DESCRIPTION!]
         */
        long getEndFirstPage() {
            return this.hintLength + this.part6EndOffset;
        }


        /**
         * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
         *
         * @return [!RETURN_DESCRIPTION!]
         */
        int getFirstXrefStart() {
            return this.secondTrailerSize;
        }


        /**
         * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
         *
         * @return [!RETURN_DESCRIPTION!]
         */
        int getFirstXrefLength() {
            return this.firstTrailerSize - this.secondTrailerSize;
        }


        /**
         * [!ONE_SENTENCE_SHORT_DESCRIPTION!].[!METHOD_DESCRIPTION!]
         *
         * @return [!RETURN_DESCRIPTION!]
         */
        int getSecondXrefLength() {
            return this.secondTrailerSize - 1;
        }
    }
}
