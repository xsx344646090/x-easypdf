package org.dromara.pdf.pdfbox.support.fonts;

/**
 * Information about a font on the system.
 *
 * @author John Hewson
 */
public abstract class FontInfo extends org.apache.pdfbox.pdmodel.font.FontInfo{

    /**
     * Returns the usWeightClass field as a Panose Weight.
     */
    final int getWeightClassAsPanose()
    {
        int usWeightClass = getWeightClass();
        switch (usWeightClass)
        {
            case -1: return 0;
            case 0: return 0;
            case 100: return 2;
            case 200: return 3;
            case 300: return 4;
            case 400: return 5;
            case 500: return 6;
            case 600: return 7;
            case 700: return 8;
            case 800: return 9;
            case 900: return 10;
            default: return 0;
        }
    }

    /**
     * Returns the ulCodePageRange1 and ulCodePageRange1 field of the "OS/2" table, or 0.
     */
    final long getCodePageRange()
    {
        long range1 = getCodePageRange1() & 0x00000000ffffffffL;
        long range2 = getCodePageRange2() & 0x00000000ffffffffL;
        return range2 << 32 | range1;
    }
}
