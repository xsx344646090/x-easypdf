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
package org.apache.pdfbox.contentstream.operator;

import java.nio.charset.StandardCharsets;

public final class OperatorName
{

    // non stroking color
    public static final String NON_STROKING_COLOR = "sc";
    public static final byte[] NON_STROKING_COLOR_BYTES = "sc".getBytes(StandardCharsets.US_ASCII);
    public static final String NON_STROKING_COLOR_N = "scn";
    public static final byte[] NON_STROKING_COLOR_N_BYTES = "scn".getBytes(StandardCharsets.US_ASCII);
    public static final String NON_STROKING_RGB = "rg";
    public static final byte[] NON_STROKING_RGB_BYTES = "rg".getBytes(StandardCharsets.US_ASCII);
    public static final String NON_STROKING_GRAY = "g";
    public static final byte[] NON_STROKING_GRAY_BYTES = "g".getBytes(StandardCharsets.US_ASCII);
    public static final String NON_STROKING_CMYK = "k";
    public static final byte[] NON_STROKING_CMYK_BYTES = "k".getBytes(StandardCharsets.US_ASCII);
    public static final String NON_STROKING_COLORSPACE = "cs";
    public static final byte[] NON_STROKING_COLORSPACE_BYTES = "cs".getBytes(StandardCharsets.US_ASCII);

    // stroking color
    public static final String STROKING_COLOR = "SC";
    public static final byte[] STROKING_COLOR_BYTES = "SC".getBytes(StandardCharsets.US_ASCII);
    public static final String STROKING_COLOR_N = "SCN";
    public static final byte[] STROKING_COLOR_N_BYTES = "SCN".getBytes(StandardCharsets.US_ASCII);
    public static final String STROKING_COLOR_RGB = "RG";
    public static final byte[] STROKING_COLOR_RGB_BYTES = "RG".getBytes(StandardCharsets.US_ASCII);
    public static final String STROKING_COLOR_GRAY = "G";
    public static final byte[] STROKING_COLOR_GRAY_BYTES = "G".getBytes(StandardCharsets.US_ASCII);
    public static final String STROKING_COLOR_CMYK = "K";
    public static final byte[] STROKING_COLOR_CMYK_BYTES = "K".getBytes(StandardCharsets.US_ASCII);
    public static final String STROKING_COLORSPACE = "CS";
    public static final byte[] STROKING_COLORSPACE_BYTES = "CS".getBytes(StandardCharsets.US_ASCII);

    // marked content
    public static final String BEGIN_MARKED_CONTENT_SEQ = "BDC";
    public static final String BEGIN_MARKED_CONTENT = "BMC";
    public static final String END_MARKED_CONTENT = "EMC";
    public static final String MARKED_CONTENT_POINT_WITH_PROPS = "DP";
    public static final String MARKED_CONTENT_POINT = "MP";
    public static final String DRAW_OBJECT = "Do";

    // state
    public static final String CONCAT = "cm";
    public static final String RESTORE = "Q";
    public static final String SAVE = "q";
    public static final String SET_FLATNESS = "i";
    public static final String SET_GRAPHICS_STATE_PARAMS = "gs";
    public static final String SET_LINE_CAPSTYLE = "J";
    public static final String SET_LINE_DASHPATTERN = "d";
    public static final String SET_LINE_JOINSTYLE = "j";
    public static final String SET_LINE_MITERLIMIT = "M";
    public static final String SET_LINE_WIDTH = "w";
    public static final String SET_MATRIX = "Tm";
    public static final String SET_RENDERINGINTENT = "ri";

    // graphics
    public static final String APPEND_RECT = "re";
    public static final String BEGIN_INLINE_IMAGE = "BI";
    public static final String BEGIN_INLINE_IMAGE_DATA = "ID";
    public static final String END_INLINE_IMAGE = "EI";
    public static final String CLIP_EVEN_ODD = "W*";
    public static final String CLIP_NON_ZERO = "W";
    public static final String CLOSE_AND_STROKE = "s";
    public static final String CLOSE_FILL_EVEN_ODD_AND_STROKE = "b*";
    public static final String CLOSE_FILL_NON_ZERO_AND_STROKE = "b";
    public static final String CLOSE_PATH = "h";
    public static final String CURVE_TO = "c";
    public static final String CURVE_TO_REPLICATE_FINAL_POINT = "y";
    public static final String CURVE_TO_REPLICATE_INITIAL_POINT = "v";
    public static final String ENDPATH = "n";
    public static final String FILL_EVEN_ODD_AND_STROKE = "B*";
    public static final String FILL_EVEN_ODD = "f*";
    public static final String FILL_NON_ZERO_AND_STROKE = "B";
    public static final String FILL_NON_ZERO = "f";
    public static final String LEGACY_FILL_NON_ZERO = "F";
    public static final String LINE_TO = "l";
    public static final String MOVE_TO = "m";
    public static final String SHADING_FILL = "sh";
    public static final String STROKE_PATH = "S";

    // text
    public static final String BEGIN_TEXT = "BT";
    public static final byte[] BEGIN_TEXT_BYTES = "BT".getBytes(StandardCharsets.US_ASCII);
    public static final String END_TEXT = "ET";
    public static final byte[] END_TEXT_BYTES = "ET".getBytes(StandardCharsets.US_ASCII);
    public static final String MOVE_TEXT = "Td";
    public static final byte[] MOVE_TEXT_BYTES = "Td".getBytes(StandardCharsets.US_ASCII);
    public static final String MOVE_TEXT_SET_LEADING = "TD";
    public static final String NEXT_LINE = "T*";
    public static final String SET_CHAR_SPACING = "Tc";
    public static final String SET_FONT_AND_SIZE = "Tf";
    public static final byte[] SET_FONT_AND_SIZE_BYTES = "Tf".getBytes(StandardCharsets.US_ASCII);
    public static final String SET_TEXT_HORIZONTAL_SCALING = "Tz";
    public static final String SET_TEXT_LEADING = "TL";
    public static final byte[] SET_TEXT_LEADING_BYTES = "TL".getBytes(StandardCharsets.US_ASCII);
    public static final String SET_TEXT_RENDERINGMODE = "Tr";
    public static final String SET_TEXT_RISE = "Ts";
    public static final String SET_WORD_SPACING = "Tw";
    public static final String SHOW_TEXT = "Tj";
    public static final byte[] SHOW_TEXT_BYTES = "Tj".getBytes(StandardCharsets.US_ASCII);
    public static final String SHOW_TEXT_ADJUSTED = "TJ";
    public static final String SHOW_TEXT_LINE = "'";
    public static final String SHOW_TEXT_LINE_AND_SPACE = "\"";

    // type3 font
    public static final String TYPE3_D0 = "d0";
    public static final String TYPE3_D1 = "d1";

    // compatibility section
    public static final String BEGIN_COMPATIBILITY_SECTION = "BX";
    public static final String END_COMPATIBILITY_SECTION = "EX";

    /**
     * private constructor
     */
    private OperatorName()
    {
    }

}
