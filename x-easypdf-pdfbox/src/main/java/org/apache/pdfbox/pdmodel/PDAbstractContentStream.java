package org.apache.pdfbox.pdmodel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fontbox.ttf.CmapLookup;
import org.apache.fontbox.ttf.gsub.CompoundCharacterTokenizer;
import org.apache.fontbox.ttf.gsub.GsubWorker;
import org.apache.fontbox.ttf.gsub.GsubWorkerFactory;
import org.apache.fontbox.ttf.model.GsubData;
import org.apache.pdfbox.contentstream.operator.OperatorName;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSNumber;
import org.apache.pdfbox.pdfwriter.COSWriter;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.color.*;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDInlineImage;
import org.apache.pdfbox.pdmodel.graphics.shading.PDShading;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.util.NumberFormatUtil;
import org.apache.pdfbox.util.StringUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

/**
 * @author xsx
 * @date 2024/5/20
 * @since 1.8
 */
public abstract class PDAbstractContentStream implements Closeable {
    protected final Log LOG = LogFactory.getLog(this.getClass());

    protected final PDDocument document; // may be null

    protected final OutputStream outputStream;
    protected PDResources resources;
    protected final Deque<PDFont> fontStack = new ArrayDeque<>();
    protected final Deque<PDColorSpace> nonStrokingColorSpaceStack = new ArrayDeque<>();
    protected final Deque<PDColorSpace> strokingColorSpaceStack = new ArrayDeque<>();
    protected final Map<PDType0Font, GsubWorker> gsubWorkers = new HashMap<>();
    // number format
    protected final NumberFormat formatDecimal = NumberFormat.getNumberInstance(Locale.US);
    private final byte[] formatBuffer = new byte[32];
    private final GsubWorkerFactory gsubWorkerFactory = new GsubWorkerFactory();
    protected boolean inTextMode = false;

    /**
     * Create a new appearance stream.
     *
     * @param document     may be null
     * @param outputStream The appearances output stream to write to.
     */
    PDAbstractContentStream(PDDocument document, OutputStream outputStream) {
        this.document = document;
        this.outputStream = outputStream;

        formatDecimal.setMaximumFractionDigits(4);
        formatDecimal.setGroupingUsed(false);
    }

    /**
     * Create a new appearance stream.
     *
     * @param document     may be null
     * @param outputStream The appearances output stream to write to.
     * @param resources    The resources to use
     */
    PDAbstractContentStream(PDDocument document, OutputStream outputStream, PDResources resources) {
        this(document, outputStream);
        this.resources = resources;
    }

    /**
     * Sets the maximum number of digits allowed for fractional numbers.
     *
     * @param fractionDigitsNumber the maximum number of digits allowed for fractional numbers
     * @see NumberFormat#setMaximumFractionDigits(int)
     */
    protected void setMaximumFractionDigits(int fractionDigitsNumber) {
        formatDecimal.setMaximumFractionDigits(fractionDigitsNumber);
    }

    /**
     * Begin some text operations.
     *
     * @throws IOException           If there is an error writing to the stream or if you attempt to
     *                               nest beginText calls.
     * @throws IllegalStateException If the method was not allowed to be called at this time.
     */
    public void beginText() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: Nested beginText() calls are not allowed.");
        }
        writeOperator(OperatorName.BEGIN_TEXT_BYTES);
        inTextMode = true;
    }

    /**
     * End some text operations.
     *
     * @throws IOException           If there is an error writing to the stream or if you attempt to
     *                               nest endText calls.
     * @throws IllegalStateException If the method was not allowed to be called at this time.
     */
    public void endText() throws IOException {
        if (!inTextMode) {
            throw new IllegalStateException("Error: You must call beginText() before calling endText.");
        }
        writeOperator(OperatorName.END_TEXT_BYTES);
        inTextMode = false;
    }

    /**
     * Set the font and font size to draw text with.
     *
     * @param font     The font to use.
     * @param fontSize The font size to draw the text.
     * @throws IOException If there is an error writing the font information.
     */
    public void setFont(PDFont font, float fontSize) throws IOException {
        if (fontStack.isEmpty()) {
            fontStack.add(font);
        } else {
            fontStack.pop();
            fontStack.push(font);
        }
        // keep track of fonts which are configured for subsetting
        if (font.willBeSubset()) {
            if (document != null) {
                document.getFontsToSubset().add(font);
            } else {
                LOG.warn("Using the subsetted font '" + font.getName() + "' without a PDDocument context; call subset() before saving");
            }
        } else if (!font.isEmbedded() && !font.isStandard14()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("attempting to use font '" + font.getName() + "' that isn't embedded");
            }
        }

        // complex text layout
        if (font instanceof PDType0Font) {
            PDType0Font pdType0Font = (PDType0Font) font;
            GsubData gsubData = pdType0Font.getGsubData();
            if (gsubData != GsubData.NO_DATA_FOUND) {
                GsubWorker gsubWorker = gsubWorkerFactory.getGsubWorker(pdType0Font.getCmapLookup(), gsubData);
                gsubWorkers.put((PDType0Font) font, gsubWorker);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("No GSUB data found in font" + font.getName());
                }
            }
        }
        writeOperand(resources.add(font));
        writeOperand(fontSize);
        writeOperator(OperatorName.SET_FONT_AND_SIZE_BYTES);
    }

    /**
     * Shows the given text at the location specified by the current text matrix with the given
     * interspersed positioning. This allows the user to efficiently position each glyph or sequence
     * of glyphs.
     *
     * @param textWithPositioningArray An array consisting of String and Float types. Each String is
     *                                 output to the page using the current text matrix. Using the default coordinate system, each
     *                                 interspersed number adjusts the current text matrix by translating to the left or down for
     *                                 horizontal and vertical text respectively. The number is expressed in thousands of a text
     *                                 space unit, and may be negative.
     * @throws IOException if an io exception occurs.
     */
    public void showTextWithPositioning(Object[] textWithPositioningArray) throws IOException {
        write("[");
        for (Object obj : textWithPositioningArray) {
            if (obj instanceof String) {
                showTextInternal((String) obj);
            } else if (obj instanceof Float) {
                writeOperand((Float) obj);
            } else {
                throw new IllegalArgumentException("Argument must consist of array of Float and String types");
            }
        }
        write("] ");
        writeOperator(OperatorName.SHOW_TEXT_ADJUSTED_BYTES);
    }

    /**
     * Shows the given text at the location specified by the current text matrix.
     *
     * @param text The Unicode text to show.
     * @throws IOException              If an io exception occurs.
     * @throws IllegalArgumentException if a character isn't supported by the current font
     */
    public void showText(String text) throws IOException {
        showTextInternal(text);
        endTextInternal();
    }

    /**
     * Shows the given text at the location specified by the current text matrix.
     *
     * @param character The Unicode character to show.
     * @throws IOException              If an io exception occurs.
     * @throws IllegalArgumentException if a character isn't supported by the current font
     */
    public void showCharacter(char character) throws IOException {
        showCharacterInternal(character);
        endTextInternal();
    }

    /**
     * Outputs a string using the correct encoding and subsetting as required.
     *
     * @param character The Unicode character to show.
     * @throws IOException If an io exception occurs.
     */
    public void showCharacterInternal(char character) throws IOException {
        if (!inTextMode) {
            throw new IllegalStateException("Must call beginText() before showText()");
        }

        if (fontStack.isEmpty()) {
            throw new IllegalStateException("Must call setFont() before showText()");
        }
        PDFont font = fontStack.peek();

        // Unicode code points to keep when subsetting
        if (font.willBeSubset()) {
            font.addToSubset(character);
        }

        COSWriter.writeString(font.encode(character), outputStream);
    }

    /**
     * end show character.
     *
     * @throws IOException If an io exception occurs.
     */
    public void endTextInternal() throws IOException {
        outputStream.write(' ');
        writeOperator(OperatorName.SHOW_TEXT_BYTES);
    }

    /**
     * Outputs a string using the correct encoding and subsetting as required.
     *
     * @param text The Unicode text to show.
     * @throws IOException If an io exception occurs.
     */
    protected void showTextInternal(String text) throws IOException {
        if (!inTextMode) {
            throw new IllegalStateException("Must call beginText() before showText()");
        }

        if (fontStack.isEmpty()) {
            throw new IllegalStateException("Must call setFont() before showText()");
        }

        PDFont font = fontStack.peek();

        // complex text layout
        byte[] encodedText = font.encode(text);
        // if (font instanceof PDType0Font) {
        //
        //     GsubWorker gsubWorker = gsubWorkers.get(font);
        //     if (gsubWorker != null) {
        //         PDType0Font pdType0Font = (PDType0Font) font;
        //         Set<Integer> glyphIds = new HashSet<>();
        //         encodedText = encodeForGsub(gsubWorker, glyphIds, pdType0Font, text);
        //         if (pdType0Font.willBeSubset()) {
        //             pdType0Font.addGlyphsToSubset(glyphIds);
        //         }
        //     }
        // }

        // if (encodedText == null) {
        //     encodedText = font.encode(text);
        // }

        // Unicode code points to keep when subsetting
        if (font.willBeSubset()) {
            int offset = 0;
            while (offset < text.length()) {
                int codePoint = text.codePointAt(offset);
                font.addToSubset(codePoint);
                offset += Character.charCount(codePoint);
            }
        }

        COSWriter.writeString(encodedText, outputStream);
    }

    /**
     * Sets the text leading.
     *
     * @param leading The leading in unscaled text units.
     * @throws IOException If there is an error writing to the stream.
     */
    public void setLeading(float leading) throws IOException {
        writeOperand(leading);
        writeOperator(OperatorName.SET_TEXT_LEADING_BYTES);
    }

    /**
     * Move to the start of the next line of text. Requires the leading (see {@link #setLeading})
     * to have been set.
     *
     * @throws IOException If there is an error writing to the stream.
     */
    public void newLine() throws IOException {
        if (!inTextMode) {
            throw new IllegalStateException("Must call beginText() before newLine()");
        }
        writeOperator(OperatorName.NEXT_LINE_BYTES);
    }

    /**
     * The Td operator.
     * Move to the start of the next line, offset from the start of the current line by (tx, ty).
     *
     * @param tx The x translation.
     * @param ty The y translation.
     * @throws IOException           If there is an error writing to the stream.
     * @throws IllegalStateException If the method was not allowed to be called at this time.
     */
    public void newLineAtOffset(float tx, float ty) throws IOException {
        if (!inTextMode) {
            throw new IllegalStateException("Error: must call beginText() before newLineAtOffset()");
        }
        writeOperand(tx);
        writeOperand(ty);
        writeOperator(OperatorName.MOVE_TEXT_BYTES);
    }

    /**
     * The Tm operator. Sets the text matrix to the given values.
     * A current text matrix will be replaced with the new one.
     *
     * @param matrix the transformation matrix
     * @throws IOException           If there is an error writing to the stream.
     * @throws IllegalStateException If the method was not allowed to be called at this time.
     */
    public void setTextMatrix(Matrix matrix) throws IOException {
        if (!inTextMode) {
            throw new IllegalStateException("Error: must call beginText() before setTextMatrix");
        }
        writeAffineTransform(matrix.createAffineTransform());
        writeOperator(OperatorName.SET_MATRIX_BYTES);
    }

    /**
     * Draw an image at the x,y coordinates, with the default size of the image.
     *
     * @param image The image to draw.
     * @param x     The x-coordinate to draw the image.
     * @param y     The y-coordinate to draw the image.
     * @throws IOException If there is an error writing to the stream.
     */
    public void drawImage(PDImageXObject image, float x, float y) throws IOException {
        drawImage(image, x, y, image.getWidth(), image.getHeight());
    }

    /**
     * Draw an image at the x,y coordinates, with the given size.
     *
     * @param image  The image to draw.
     * @param x      The x-coordinate to draw the image.
     * @param y      The y-coordinate to draw the image.
     * @param width  The width to draw the image.
     * @param height The height to draw the image.
     * @throws IOException           If there is an error writing to the stream.
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void drawImage(PDImageXObject image, float x, float y, float width, float height) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: drawImage is not allowed within a text block.");
        }

        saveGraphicsState();

        AffineTransform transform = new AffineTransform(width, 0, 0, height, x, y);
        transform(new Matrix(transform));

        writeOperand(resources.add(image));
        writeOperator(OperatorName.DRAW_OBJECT_BYTES);

        restoreGraphicsState();
    }

    /**
     * Draw an image at the origin with the given transformation matrix.
     *
     * @param image  The image to draw.
     * @param matrix The transformation matrix to apply to the image.
     * @throws IOException           If there is an error writing to the stream.
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void drawImage(PDImageXObject image, Matrix matrix) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: drawImage is not allowed within a text block.");
        }

        saveGraphicsState();

        AffineTransform transform = matrix.createAffineTransform();
        transform(new Matrix(transform));

        writeOperand(resources.add(image));
        writeOperator(OperatorName.DRAW_OBJECT_BYTES);

        restoreGraphicsState();
    }

    /**
     * Draw an inline image at the x,y coordinates, with the default size of the image.
     *
     * @param inlineImage The inline image to draw.
     * @param x           The x-coordinate to draw the inline image.
     * @param y           The y-coordinate to draw the inline image.
     * @throws IOException If there is an error writing to the stream.
     */
    public void drawImage(PDInlineImage inlineImage, float x, float y) throws IOException {
        drawImage(inlineImage, x, y, inlineImage.getWidth(), inlineImage.getHeight());
    }

    /**
     * Draw an inline image at the x,y coordinates and a certain width and height.
     *
     * @param inlineImage The inline image to draw.
     * @param x           The x-coordinate to draw the inline image.
     * @param y           The y-coordinate to draw the inline image.
     * @param width       The width of the inline image to draw.
     * @param height      The height of the inline image to draw.
     * @throws IOException           If there is an error writing to the stream.
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void drawImage(PDInlineImage inlineImage, float x, float y, float width, float height) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: drawImage is not allowed within a text block.");
        }

        saveGraphicsState();
        transform(new Matrix(width, 0, 0, height, x, y));

        // create the image dictionary
        StringBuilder sb = new StringBuilder();
        sb.append(OperatorName.BEGIN_INLINE_IMAGE);

        sb.append("\n /W ");
        sb.append(inlineImage.getWidth());

        sb.append("\n /H ");
        sb.append(inlineImage.getHeight());

        sb.append("\n /CS ");
        sb.append('/');
        sb.append(inlineImage.getColorSpace().getName());

        COSArray decodeArray = inlineImage.getDecode();
        if (decodeArray != null && decodeArray.size() > 0) {
            sb.append("\n /D ");
            sb.append('[');
            for (COSBase base : decodeArray) {
                sb.append(((COSNumber) base).intValue());
                sb.append(' ');
            }
            sb.append(']');
        }

        if (inlineImage.isStencil()) {
            sb.append("\n /IM true");
        }

        sb.append("\n /BPC ");
        sb.append(inlineImage.getBitsPerComponent());

        // image dictionary
        write(sb.toString());
        writeLine();

        // binary data
        writeOperator(OperatorName.BEGIN_INLINE_IMAGE_DATA_BYTES);
        writeBytes(inlineImage.getData());
        writeLine();
        writeOperator(OperatorName.END_INLINE_IMAGE_BYTES);

        restoreGraphicsState();
    }

    /**
     * Draws the given Form XObject at the current location.
     *
     * @param form Form XObject
     * @throws IOException           if the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void drawForm(PDFormXObject form) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: drawForm is not allowed within a text block.");
        }

        writeOperand(resources.add(form));
        writeOperator(OperatorName.DRAW_OBJECT_BYTES);
    }

    /**
     * The cm operator. Concatenates the given matrix with the current transformation matrix (CTM),
     * which maps user space coordinates used within a PDF content stream into output device
     * coordinates. More details on coordinates can be found in the PDF 32000 specification, 8.3.2
     * Coordinate Spaces.
     *
     * @param matrix the transformation matrix
     * @throws IOException If there is an error writing to the stream.
     */
    public void transform(Matrix matrix) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: Modifying the current transformation matrix is not allowed within text objects.");
        }

        writeAffineTransform(matrix.createAffineTransform());
        writeOperator(OperatorName.CONCAT_BYTES);
    }

    /**
     * q operator. Saves the current graphics state.
     *
     * @throws IOException If an error occurs while writing to the stream.
     */
    public void saveGraphicsState() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: Saving the graphics state is not allowed within text objects.");
        }

        if (!fontStack.isEmpty()) {
            fontStack.push(fontStack.peek());
        }
        if (!strokingColorSpaceStack.isEmpty()) {
            strokingColorSpaceStack.push(strokingColorSpaceStack.peek());
        }
        if (!nonStrokingColorSpaceStack.isEmpty()) {
            nonStrokingColorSpaceStack.push(nonStrokingColorSpaceStack.peek());
        }
        writeOperator(OperatorName.SAVE_BYTES);
    }

    /**
     * Q operator. Restores the current graphics state.
     *
     * @throws IOException If an error occurs while writing to the stream.
     */
    public void restoreGraphicsState() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: Restoring the graphics state is not allowed within text objects.");
        }

        if (!fontStack.isEmpty()) {
            fontStack.pop();
        }
        if (!strokingColorSpaceStack.isEmpty()) {
            strokingColorSpaceStack.pop();
        }
        if (!nonStrokingColorSpaceStack.isEmpty()) {
            nonStrokingColorSpaceStack.pop();
        }
        writeOperator(OperatorName.RESTORE_BYTES);
    }

    protected COSName getName(PDColorSpace colorSpace) {
        if (colorSpace instanceof PDDeviceGray || colorSpace instanceof PDDeviceRGB || colorSpace instanceof PDDeviceCMYK) {
            return COSName.getPDFName(colorSpace.getName());
        } else {
            return resources.add(colorSpace);
        }
    }

    /**
     * Sets the stroking color and, if necessary, the stroking color space.
     *
     * @param color Color in a specific color space.
     * @throws IOException If an IO error occurs while writing to the stream.
     */
    public void setStrokingColor(PDColor color) throws IOException {
        if (strokingColorSpaceStack.isEmpty() || strokingColorSpaceStack.peek() != color.getColorSpace()) {
            writeOperand(getName(color.getColorSpace()));
            writeOperator(OperatorName.STROKING_COLORSPACE_BYTES);
            setStrokingColorSpaceStack(color.getColorSpace());
        }

        for (float value : color.getComponents()) {
            writeOperand(value);
        }

        if (color.getColorSpace() instanceof PDPattern) {
            writeOperand(color.getPatternName());
        }

        if (color.getColorSpace() instanceof PDPattern || color.getColorSpace() instanceof PDSeparation || color.getColorSpace() instanceof PDDeviceN || color.getColorSpace() instanceof PDICCBased) {
            writeOperator(OperatorName.STROKING_COLOR_N_BYTES);
        } else {
            writeOperator(OperatorName.STROKING_COLOR_BYTES);
        }
    }

    /**
     * Set the stroking color using an AWT color. Conversion uses the default sRGB color space.
     *
     * @param color The color to set.
     * @throws IOException If an IO error occurs while writing to the stream.
     */
    public void setStrokingColor(Color color) throws IOException {
        float[] components = new float[]{color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f};
        PDColor pdColor = new PDColor(components, PDDeviceRGB.INSTANCE);
        setStrokingColor(pdColor);
    }

    /**
     * Set the stroking color in the DeviceRGB color space. Range is 0..1.
     *
     * @param r The red value
     * @param g The green value.
     * @param b The blue value.
     * @throws IOException              If an IO error occurs while writing to the stream.
     * @throws IllegalArgumentException If the parameters are invalid.
     */
    public void setStrokingColor(float r, float g, float b) throws IOException {
        if (isOutsideOneInterval(r) || isOutsideOneInterval(g) || isOutsideOneInterval(b)) {
            throw new IllegalArgumentException("Parameters must be within 0..1, but are " + String.format("(%.2f,%.2f,%.2f)", r, g, b));
        }
        writeOperand(r);
        writeOperand(g);
        writeOperand(b);
        writeOperator(OperatorName.STROKING_COLOR_RGB_BYTES);
        setStrokingColorSpaceStack(PDDeviceRGB.INSTANCE);
    }

    /**
     * Set the stroking color in the DeviceCMYK color space. Range is 0..1
     *
     * @param c The cyan value.
     * @param m The magenta value.
     * @param y The yellow value.
     * @param k The black value.
     * @throws IOException              If an IO error occurs while writing to the stream.
     * @throws IllegalArgumentException If the parameters are invalid.
     */
    public void setStrokingColor(float c, float m, float y, float k) throws IOException {
        if (isOutsideOneInterval(c) || isOutsideOneInterval(m) || isOutsideOneInterval(y) || isOutsideOneInterval(k)) {
            throw new IllegalArgumentException("Parameters must be within 0..1, but are " + String.format("(%.2f,%.2f,%.2f,%.2f)", c, m, y, k));
        }
        writeOperand(c);
        writeOperand(m);
        writeOperand(y);
        writeOperand(k);
        writeOperator(OperatorName.STROKING_COLOR_CMYK_BYTES);
        setStrokingColorSpaceStack(PDDeviceCMYK.INSTANCE);
    }

    /**
     * Set the stroking color in the DeviceGray color space. Range is 0..1.
     *
     * @param g The gray value.
     * @throws IOException              If an IO error occurs while writing to the stream.
     * @throws IllegalArgumentException If the parameter is invalid.
     */
    public void setStrokingColor(float g) throws IOException {
        if (isOutsideOneInterval(g)) {
            throw new IllegalArgumentException("Parameter must be within 0..1, but is " + g);
        }
        writeOperand(g);
        writeOperator(OperatorName.STROKING_COLOR_GRAY_BYTES);
        setStrokingColorSpaceStack(PDDeviceGray.INSTANCE);
    }

    /**
     * Sets the non-stroking color and, if necessary, the non-stroking color space.
     *
     * @param color Color in a specific color space.
     * @throws IOException If an IO error occurs while writing to the stream.
     */
    public void setNonStrokingColor(PDColor color) throws IOException {
        if (nonStrokingColorSpaceStack.isEmpty() || nonStrokingColorSpaceStack.peek() != color.getColorSpace()) {
            writeOperand(getName(color.getColorSpace()));
            writeOperator(OperatorName.NON_STROKING_COLORSPACE_BYTES);
            setNonStrokingColorSpaceStack(color.getColorSpace());
        }

        for (float value : color.getComponents()) {
            writeOperand(value);
        }

        if (color.getColorSpace() instanceof PDPattern) {
            writeOperand(color.getPatternName());
        }

        if (color.getColorSpace() instanceof PDPattern || color.getColorSpace() instanceof PDSeparation || color.getColorSpace() instanceof PDDeviceN || color.getColorSpace() instanceof PDICCBased) {
            writeOperator(OperatorName.NON_STROKING_COLOR_N_BYTES);
        } else {
            writeOperator(OperatorName.NON_STROKING_COLOR_BYTES);
        }
    }

    /**
     * Set the non-stroking color using an AWT color. Conversion uses the default sRGB color space.
     *
     * @param color The color to set.
     * @throws IOException If an IO error occurs while writing to the stream.
     */
    public void setNonStrokingColor(Color color) throws IOException {
        float[] components = new float[]{color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f};
        PDColor pdColor = new PDColor(components, PDDeviceRGB.INSTANCE);
        setNonStrokingColor(pdColor);
    }

    /**
     * Set the non-stroking color in the DeviceRGB color space. Range is 0..1.
     *
     * @param r The red value.
     * @param g The green value.
     * @param b The blue value.
     * @throws IOException              If an IO error occurs while writing to the stream.
     * @throws IllegalArgumentException If the parameters are invalid.
     */
    public void setNonStrokingColor(float r, float g, float b) throws IOException {
        if (isOutsideOneInterval(r) || isOutsideOneInterval(g) || isOutsideOneInterval(b)) {
            throw new IllegalArgumentException("Parameters must be within 0..1, but are " + String.format("(%.2f,%.2f,%.2f)", r, g, b));
        }
        writeOperand(r);
        writeOperand(g);
        writeOperand(b);
        writeOperator(OperatorName.NON_STROKING_RGB_BYTES);
        setNonStrokingColorSpaceStack(PDDeviceRGB.INSTANCE);
    }

    /**
     * Set the non-stroking color in the DeviceCMYK color space. Range is 0..1.
     *
     * @param c The cyan value.
     * @param m The magenta value.
     * @param y The yellow value.
     * @param k The black value.
     * @throws IOException If an IO error occurs while writing to the stream.
     */
    public void setNonStrokingColor(float c, float m, float y, float k) throws IOException {
        if (isOutsideOneInterval(c) || isOutsideOneInterval(m) || isOutsideOneInterval(y) || isOutsideOneInterval(k)) {
            throw new IllegalArgumentException("Parameters must be within 0..1, but are " + String.format("(%.2f,%.2f,%.2f,%.2f)", c, m, y, k));
        }
        writeOperand(c);
        writeOperand(m);
        writeOperand(y);
        writeOperand(k);
        writeOperator(OperatorName.NON_STROKING_CMYK_BYTES);
        setNonStrokingColorSpaceStack(PDDeviceCMYK.INSTANCE);
    }

    /**
     * Set the non-stroking color in the DeviceGray color space. Range is 0..1.
     *
     * @param g The gray value.
     * @throws IOException              If an IO error occurs while writing to the stream.
     * @throws IllegalArgumentException If the parameter is invalid.
     */
    public void setNonStrokingColor(float g) throws IOException {
        if (isOutsideOneInterval(g)) {
            throw new IllegalArgumentException("Parameter must be within 0..1, but is " + g);
        }
        writeOperand(g);
        writeOperator(OperatorName.NON_STROKING_GRAY_BYTES);
        setNonStrokingColorSpaceStack(PDDeviceGray.INSTANCE);
    }

    /**
     * Add a rectangle to the current path.
     *
     * @param x      The lower left x coordinate.
     * @param y      The lower left y coordinate.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @throws IOException           If the content stream could not be written.
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void addRect(float x, float y, float width, float height) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: addRect is not allowed within a text block.");
        }
        writeOperand(x);
        writeOperand(y);
        writeOperand(width);
        writeOperand(height);
        writeOperator(OperatorName.APPEND_RECT_BYTES);
    }

    /**
     * Add a rectangle to give the current rectangle.
     *
     * @param rectangle The rectangle.
     * @throws IOException           If the content stream could not be written.
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void addRect(PDRectangle rectangle) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: addRect is not allowed within a text block.");
        }
        writeOperand(rectangle.getLowerLeftX());
        writeOperand(rectangle.getLowerLeftY());
        writeOperand(rectangle.getWidth());
        writeOperand(rectangle.getHeight());
        writeOperator(OperatorName.APPEND_RECT_BYTES);
    }

    /**
     * Append a cubic Bézier curve to the current path. The curve extends from the current point to
     * the point (x3, y3), using (x1, y1) and (x2, y2) as the Bézier control points.
     *
     * @param x1 x coordinate of the point 1
     * @param y1 y coordinate of the point 1
     * @param x2 x coordinate of the point 2
     * @param y2 y coordinate of the point 2
     * @param x3 x coordinate of the point 3
     * @param y3 y coordinate of the point 3
     * @throws IOException           If the content stream could not be written.
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void curveTo(float x1, float y1, float x2, float y2, float x3, float y3) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: curveTo is not allowed within a text block.");
        }
        writeOperand(x1);
        writeOperand(y1);
        writeOperand(x2);
        writeOperand(y2);
        writeOperand(x3);
        writeOperand(y3);
        writeOperator(OperatorName.CURVE_TO_BYTES);
    }

    /**
     * Append a cubic Bézier curve to the current path. The curve extends from the current point to
     * the point (x3, y3), using the current point and (x2, y2) as the Bézier control points.
     *
     * @param x2 x coordinate of the point 2
     * @param y2 y coordinate of the point 2
     * @param x3 x coordinate of the point 3
     * @param y3 y coordinate of the point 3
     * @throws IllegalStateException If the method was called within a text block.
     * @throws IOException           If the content stream could not be written.
     */
    public void curveTo2(float x2, float y2, float x3, float y3) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: curveTo2 is not allowed within a text block.");
        }
        writeOperand(x2);
        writeOperand(y2);
        writeOperand(x3);
        writeOperand(y3);
        writeOperator(OperatorName.CURVE_TO_REPLICATE_INITIAL_POINT_BYTES);
    }

    /**
     * Append a cubic Bézier curve to the current path. The curve extends from the current point to
     * the point (x3, y3), using (x1, y1) and (x3, y3) as the Bézier control points.
     *
     * @param x1 x coordinate of the point 1
     * @param y1 y coordinate of the point 1
     * @param x3 x coordinate of the point 3
     * @param y3 y coordinate of the point 3
     * @throws IOException           If the content stream could not be written.
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void curveTo1(float x1, float y1, float x3, float y3) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: curveTo1 is not allowed within a text block.");
        }
        writeOperand(x1);
        writeOperand(y1);
        writeOperand(x3);
        writeOperand(y3);
        writeOperator(OperatorName.CURVE_TO_REPLICATE_FINAL_POINT_BYTES);
    }

    /**
     * Move the current position to the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @throws IOException           If the content stream could not be written.
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void moveTo(float x, float y) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: moveTo is not allowed within a text block.");
        }
        writeOperand(x);
        writeOperand(y);
        writeOperator(OperatorName.MOVE_TO_BYTES);
    }

    /**
     * Draw a line from the current position to the given coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @throws IOException           If the content stream could not be written.
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void lineTo(float x, float y) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: lineTo is not allowed within a text block.");
        }
        writeOperand(x);
        writeOperand(y);
        writeOperator(OperatorName.LINE_TO_BYTES);
    }

    /**
     * Stroke the path.
     *
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void stroke() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: stroke is not allowed within a text block.");
        }
        writeOperator(OperatorName.STROKE_PATH_BYTES);
    }

    /**
     * Close and stroke the path.
     *
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void closeAndStroke() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: closeAndStroke is not allowed within a text block.");
        }
        writeOperator(OperatorName.CLOSE_AND_STROKE_BYTES);
    }

    /**
     * Fills the path using the nonzero winding number rule.
     *
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void fill() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: fill is not allowed within a text block.");
        }
        writeOperator(OperatorName.FILL_NON_ZERO_BYTES);
    }

    /**
     * Fills the path using the even-odd winding rule.
     *
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void fillEvenOdd() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: fillEvenOdd is not allowed within a text block.");
        }
        writeOperator(OperatorName.FILL_EVEN_ODD_BYTES);
    }

    /**
     * Fill and then stroke the path, using the nonzero winding number rule to determine the region
     * to fill. This shall produce the same result as constructing two identical path objects,
     * painting the first with {@link #fill() } and the second with {@link #stroke() }.
     *
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void fillAndStroke() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: fillAndStroke is not allowed within a text block.");
        }
        writeOperator(OperatorName.FILL_NON_ZERO_AND_STROKE_BYTES);
    }

    /**
     * Fill and then stroke the path, using the even-odd rule to determine the region to
     * fill. This shall produce the same result as constructing two identical path objects, painting
     * the first with {@link #fillEvenOdd() } and the second with {@link #stroke() }.
     *
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void fillAndStrokeEvenOdd() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: fillAndStrokeEvenOdd is not allowed within a text block.");
        }
        writeOperator(OperatorName.FILL_EVEN_ODD_AND_STROKE_BYTES);
    }

    /**
     * Close, fill, and then stroke the path, using the nonzero winding number rule to determine the
     * region to fill. This shall have the same effect as the sequence {@link #closePath() }
     * and then {@link #fillAndStroke() }.
     *
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void closeAndFillAndStroke() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: closeAndFillAndStroke is not allowed within a text block.");
        }
        writeOperator(OperatorName.CLOSE_FILL_NON_ZERO_AND_STROKE_BYTES);
    }

    /**
     * Close, fill, and then stroke the path, using the even-odd rule to determine the region to
     * fill. This shall have the same effect as the sequence {@link #closePath() }
     * and then {@link #fillAndStrokeEvenOdd() }.
     *
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void closeAndFillAndStrokeEvenOdd() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: closeAndFillAndStrokeEvenOdd is not allowed within a text block.");
        }
        writeOperator(OperatorName.CLOSE_FILL_EVEN_ODD_AND_STROKE_BYTES);
    }

    /**
     * Fills the clipping area with the given shading.
     *
     * @param shading Shading resource
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void shadingFill(PDShading shading) throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: shadingFill is not allowed within a text block.");
        }

        writeOperand(resources.add(shading));
        writeOperator(OperatorName.SHADING_FILL_BYTES);
    }

    /**
     * Closes the current subpath.
     *
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void closePath() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: closePath is not allowed within a text block.");
        }
        writeOperator(OperatorName.CLOSE_PATH_BYTES);
    }

    /**
     * Intersects the current clipping path with the current path, using the nonzero rule.
     *
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void clip() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: clip is not allowed within a text block.");
        }
        writeOperator(OperatorName.CLIP_NON_ZERO_BYTES);

        // end path without filling or stroking
        writeOperator(OperatorName.ENDPATH_BYTES);
    }

    /**
     * Intersects the current clipping path with the current path, using the even-odd rule.
     *
     * @throws IOException           If the content stream could not be written
     * @throws IllegalStateException If the method was called within a text block.
     */
    public void clipEvenOdd() throws IOException {
        if (inTextMode) {
            throw new IllegalStateException("Error: clipEvenOdd is not allowed within a text block.");
        }
        writeOperator(OperatorName.CLIP_EVEN_ODD_BYTES);

        // end path without filling or stroking
        writeOperator(OperatorName.ENDPATH_BYTES);
    }

    /**
     * Set line width to the given value.
     *
     * @param lineWidth The width which is used for drawing.
     * @throws IOException If the content stream could not be written
     */
    public void setLineWidth(float lineWidth) throws IOException {
        writeOperand(lineWidth);
        writeOperator(OperatorName.SET_LINE_WIDTH_BYTES);
    }

    /**
     * Set the line join style.
     *
     * @param lineJoinStyle 0 for miter join, 1 for round join, and 2 for bevel join.
     * @throws IOException              If the content stream could not be written.
     * @throws IllegalArgumentException If the parameter is not a valid line join style.
     */
    public void setLineJoinStyle(int lineJoinStyle) throws IOException {
        if (lineJoinStyle >= 0 && lineJoinStyle <= 2) {
            writeOperand(lineJoinStyle);
            writeOperator(OperatorName.SET_LINE_JOINSTYLE_BYTES);
        } else {
            throw new IllegalArgumentException("Error: unknown value for line join style");
        }
    }

    /**
     * Set the line cap style.
     *
     * @param lineCapStyle 0 for butt cap, 1 for round cap, and 2 for projecting square cap.
     * @throws IOException              If the content stream could not be written.
     * @throws IllegalArgumentException If the parameter is not a valid line cap style.
     */
    public void setLineCapStyle(int lineCapStyle) throws IOException {
        if (lineCapStyle >= 0 && lineCapStyle <= 2) {
            writeOperand(lineCapStyle);
            writeOperator(OperatorName.SET_LINE_CAPSTYLE_BYTES);
        } else {
            throw new IllegalArgumentException("Error: unknown value for line cap style");
        }
    }

    /**
     * Set the line dash pattern.
     *
     * @param pattern The pattern array
     * @param phase   The phase of the pattern
     * @throws IOException If the content stream could not be written.
     */
    public void setLineDashPattern(float[] pattern, float phase) throws IOException {
        write("[");
        for (float value : pattern) {
            writeOperand(value);
        }
        write("] ");
        writeOperand(phase);
        writeOperator(OperatorName.SET_LINE_DASHPATTERN_BYTES);
    }

    /**
     * Set the miter limit.
     *
     * @param miterLimit the new miter limit.
     * @throws IOException              If the content stream could not be written.
     * @throws IllegalArgumentException If the parameter is \u2264 0.
     */
    public void setMiterLimit(float miterLimit) throws IOException {
        if (miterLimit <= 0.0) {
            throw new IllegalArgumentException("A miter limit <= 0 is invalid and will not render in Acrobat Reader");
        }
        writeOperand(miterLimit);
        writeOperator(OperatorName.SET_LINE_MITERLIMIT_BYTES);
    }

    /**
     * Begin a marked content sequence.
     *
     * @param tag the tag to be added to the content stream
     * @throws IOException If the content stream could not be written
     */
    public void beginMarkedContent(COSName tag) throws IOException {
        writeOperand(tag);
        writeOperator(OperatorName.BEGIN_MARKED_CONTENT_BYTES);
    }

    /**
     * Begin a marked content sequence with a reference to an entry in the page resources' Properties dictionary.
     *
     * @param tag          the tag to be added to the content stream
     * @param propertyList property list to be added to the content stream
     * @throws IOException If the content stream could not be written
     */
    public void beginMarkedContent(COSName tag, PDPropertyList propertyList) throws IOException {
        writeOperand(tag);
        writeOperand(resources.add(propertyList));
        writeOperator(OperatorName.BEGIN_MARKED_CONTENT_SEQ_BYTES);
    }

    /**
     * End a marked content sequence.
     *
     * @throws IOException If the content stream could not be written
     */
    public void endMarkedContent() throws IOException {
        writeOperator(OperatorName.END_MARKED_CONTENT_BYTES);
    }

    /**
     * Set an extended graphics state.
     *
     * @param state The extended graphics state to be added to the content stream
     * @throws IOException If the content stream could not be written.
     */
    public void setGraphicsStateParameters(PDExtendedGraphicsState state) throws IOException {
        writeOperand(resources.add(state));
        writeOperator(OperatorName.SET_GRAPHICS_STATE_PARAMS_BYTES);
    }

    /**
     * Write a comment line.
     *
     * @param comment the comment to be added to the content stream
     * @throws IOException              If the content stream could not be written.
     * @throws IllegalArgumentException If the comment contains a newline. This is not allowed, because the next line
     *                                  could be ordinary PDF content.
     */
    public void addComment(String comment) throws IOException {
        if (comment.indexOf('\n') >= 0 || comment.indexOf('\r') >= 0) {
            throw new IllegalArgumentException("comment should not include a newline");
        }
        outputStream.write('%');
        outputStream.write(comment.getBytes(StandardCharsets.US_ASCII));
        outputStream.write('\n');
    }

    /**
     * Writes a real number to the content stream.
     *
     * @param real the real number to be added to the content stream
     * @throws IOException              If the underlying stream has a problem being written to.
     * @throws IllegalArgumentException if the parameter is not a finite number
     */
    protected void writeOperand(float real) throws IOException {
        if (!Float.isFinite(real)) {
            throw new IllegalArgumentException(real + " is not a finite number");
        }
        int byteCount = NumberFormatUtil.formatFloatFast(real, formatDecimal.getMaximumFractionDigits(), formatBuffer);

        if (byteCount == -1) {
            // Fast formatting failed
            write(formatDecimal.format(real));
        } else {
            outputStream.write(formatBuffer, 0, byteCount);
        }
        outputStream.write(' ');
    }

    /**
     * Writes an integer number to the content stream.
     *
     * @param integer the integer to be added to the content stream
     * @throws IOException If the underlying stream has a problem being written to.
     */
    protected void writeOperand(int integer) throws IOException {
        write(formatDecimal.format(integer));
        outputStream.write(' ');
    }

    /**
     * Writes a COSName to the content stream.
     *
     * @param name the name to be added to the content stream
     * @throws IOException If the underlying stream has a problem being written to.
     */
    protected void writeOperand(COSName name) throws IOException {
        name.writePDF(outputStream);
        outputStream.write(' ');
    }

    /**
     * Writes a COSName to the content stream.
     *
     * @param text the text to be added to the content stream
     * @throws IOException If the underlying stream has a problem being written to.
     */
    protected void writeOperand(String text) throws IOException {
        write(text);
        outputStream.write(' ');
    }

    /**
     * Writes a COSName to the content stream.
     *
     * @param data the text to be added to the content stream
     * @throws IOException If the underlying stream has a problem being written to.
     */
    protected void writeOperand(byte[] data) throws IOException {
        writeBytes(data);
        outputStream.write(' ');
    }

    /**
     * Writes a string to the content stream as ASCII.
     *
     * @param text the text to be added to the content stream followed by a newline
     * @throws IOException If the underlying stream has a problem being written to.
     */
    public void writeOperator(String text) throws IOException {
        write(text);
        writeLine();
    }

    /**
     * Writes a string to the content stream as ASCII.
     *
     * @param data the text to be added to the content stream followed by a newline
     * @throws IOException If the underlying stream has a problem being written to.
     */
    public void writeOperator(byte[] data) throws IOException {
        writeBytes(data);
        writeLine();
    }

    /**
     * Writes a string to the content stream as ASCII.
     *
     * @param text the text to be added to the content stream
     * @throws IOException If the underlying stream has a problem being written to.
     */
    protected void write(String text) throws IOException {
        writeBytes(text.getBytes(StandardCharsets.US_ASCII));
    }

    /**
     * Writes a newline to the content stream as ASCII.
     *
     * @throws IOException If the underlying stream has a problem being written to.
     */
    protected void writeLine() throws IOException {
        outputStream.write('\n');
    }

    /**
     * Writes binary data to the content stream.
     *
     * @param data as byte formatted to be added to the content stream
     * @throws IOException If the underlying stream has a problem being written to.
     */
    protected void writeBytes(byte[] data) throws IOException {
        outputStream.write(data);
    }

    /**
     * Writes an AffineTransform to the content stream as an array.
     *
     * @param transform AffineTransfrom to be added to the content stream
     * @throws IOException If the underlying stream has a problem being written to.
     */
    private void writeAffineTransform(AffineTransform transform) throws IOException {
        double[] values = new double[6];
        transform.getMatrix(values);
        for (double v : values) {
            writeOperand((float) v);
        }
    }

    /**
     * Close the content stream.  This must be called when you are done with this object.
     *
     * @throws IOException If the underlying stream has a problem being written to.
     */
    @Override
    public void close() throws IOException {
        if (inTextMode) {
            LOG.warn("You did not call endText(), some viewers won't display your text");
        }
        outputStream.close();
    }

    protected boolean isOutside255Interval(int val) {
        return val < 0 || val > 255;
    }

    private boolean isOutsideOneInterval(double val) {
        return val < 0 || val > 1;
    }

    protected void setStrokingColorSpaceStack(PDColorSpace colorSpace) {
        if (strokingColorSpaceStack.isEmpty()) {
            strokingColorSpaceStack.add(colorSpace);
        } else {
            strokingColorSpaceStack.pop();
            strokingColorSpaceStack.push(colorSpace);
        }
    }

    protected void setNonStrokingColorSpaceStack(PDColorSpace colorSpace) {
        if (nonStrokingColorSpaceStack.isEmpty()) {
            nonStrokingColorSpaceStack.add(colorSpace);
        } else {
            nonStrokingColorSpaceStack.pop();
            nonStrokingColorSpaceStack.push(colorSpace);
        }
    }

    /**
     * Set the character spacing. The value shall be added to the horizontal or vertical component
     * of the glyph's displacement, depending on the writing mode.
     *
     * @param spacing character spacing
     * @throws IOException If the content stream could not be written.
     */
    public void setCharacterSpacing(float spacing) throws IOException {
        writeOperand(spacing);
        writeOperator(OperatorName.SET_CHAR_SPACING_BYTES);
    }

    /**
     * Set the word spacing. The value shall be added to the horizontal or vertical component of the
     * ASCII SPACE character, depending on the writing mode.
     * <p>
     * This will have an effect only with Type1 and TrueType fonts, not with Type0 fonts. The PDF
     * specification tells why: "Word spacing shall be applied to every occurrence of the
     * single-byte character code 32 in a string when using a simple font or a composite font that
     * defines code 32 as a single-byte code. It shall not apply to occurrences of the byte value 32
     * in multiple-byte codes."
     *
     * @param spacing word spacing
     * @throws IOException If the content stream could not be written.
     */
    public void setWordSpacing(float spacing) throws IOException {
        writeOperand(spacing);
        writeOperator(OperatorName.SET_WORD_SPACING_BYTES);
    }

    /**
     * Set the horizontal scaling to scale / 100.
     *
     * @param scale number specifying the percentage of the normal width. Default value: 100 (normal
     *              width).
     * @throws IOException If the content stream could not be written.
     */
    public void setHorizontalScaling(float scale) throws IOException {
        writeOperand(scale);
        writeOperator(OperatorName.SET_TEXT_HORIZONTAL_SCALING_BYTES);
    }

    /**
     * Set the text rendering mode. This determines whether showing text shall cause glyph outlines
     * to be stroked, filled, used as a clipping boundary, or some combination of the three.
     *
     * @param rm The text rendering mode.
     * @throws IOException If the content stream could not be written.
     */
    public void setRenderingMode(RenderingMode rm) throws IOException {
        writeOperand(rm.intValue());
        writeOperand(OperatorName.SET_TEXT_RENDERINGMODE_BYTES);
    }

    /**
     * Set the text rise value, i.e. move the baseline up or down. This is useful for drawing superscripts or
     * subscripts.
     *
     * @param rise Specifies the distance, in unscaled text space units, to move the baseline up or down from its
     *             default location. 0 restores the default location.
     * @throws IOException If the content stream could not be written.
     */
    public void setTextRise(float rise) throws IOException {
        writeOperand(rise);
        writeOperator(OperatorName.SET_TEXT_RISE_BYTES);
    }

    protected byte[] encodeForGsub(GsubWorker gsubWorker, Set<Integer> glyphIds, PDType0Font font, String text) throws IOException {
        // break the entire chunk of text into words by splitting it with space
        List<String> words = new CompoundCharacterTokenizer(StringUtil.PATTERN_SPACE).tokenize(text);

        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);

        for (String word : words) {
            if (StringUtil.PATTERN_SPACE.matcher(word).matches()) {
                out.write(font.encode(word));
            } else {
                glyphIds.addAll(applyGSUBRules(gsubWorker, out, font, word));
            }
        }

        return out.toByteArray();
    }

    protected List<Integer> applyGSUBRules(GsubWorker gsubWorker, ByteArrayOutputStream out, PDType0Font font, String word) throws IOException {
        char[] charArray = word.toCharArray();
        List<Integer> originalGlyphIds = new ArrayList<>(charArray.length);
        CmapLookup cmapLookup = font.getCmapLookup();

        // convert characters into glyphIds
        for (char unicodeChar : charArray) {
            int glyphId = cmapLookup.getGlyphId(unicodeChar);
            if (glyphId <= 0) {
                throw new IllegalStateException("could not find the glyphId for the character: " + unicodeChar);
            }
            originalGlyphIds.add(glyphId);
        }

        List<Integer> glyphIdsAfterGsub = gsubWorker.applyTransforms(originalGlyphIds);

        for (Integer glyphId : glyphIdsAfterGsub) {
            out.write(font.encodeGlyphId(glyphId));
        }

        return glyphIdsAfterGsub;

    }

    protected byte[] encodeForGsub(GsubWorker gsubWorker, Set<Integer> glyphIds, PDType0Font font, Character character) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        if (character == ' ') {
            glyphIds.addAll(applyGSUBRules(gsubWorker, out, font, character));
        } else {
            out.write(font.encode(character));
        }
        return out.toByteArray();
    }

    protected List<Integer> applyGSUBRules(GsubWorker gsubWorker, ByteArrayOutputStream out, PDType0Font font, Character unicodeChar) throws IOException {
        List<Integer> originalGlyphIds = new ArrayList<>(1);
        CmapLookup cmapLookup = font.getCmapLookup();

        int glyphId = cmapLookup.getGlyphId(unicodeChar);
        if (glyphId <= 0) {
            throw new IllegalStateException("could not find the glyphId for the character: " + unicodeChar);
        }
        originalGlyphIds.add(glyphId);

        List<Integer> glyphIdsAfterGsub = gsubWorker.applyTransforms(originalGlyphIds);

        for (Integer id : glyphIdsAfterGsub) {
            out.write(font.encodeGlyphId(id));
        }

        return glyphIdsAfterGsub;

    }
}
