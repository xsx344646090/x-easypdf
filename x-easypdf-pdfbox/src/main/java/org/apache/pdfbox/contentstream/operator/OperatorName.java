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
    public static final byte[] NON_STROKING_COLOR_BYTES = "sc".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] NON_STROKING_COLOR_N_BYTES = "scn".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] NON_STROKING_RGB_BYTES = "rg".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] NON_STROKING_GRAY_BYTES = "g".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] NON_STROKING_CMYK_BYTES = "k".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] NON_STROKING_COLORSPACE_BYTES = "cs".getBytes(StandardCharsets.US_ASCII);

    // stroking color
    public static final byte[] STROKING_COLOR_BYTES = "SC".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] STROKING_COLOR_N_BYTES = "SCN".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] STROKING_COLOR_RGB_BYTES = "RG".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] STROKING_COLOR_GRAY_BYTES = "G".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] STROKING_COLOR_CMYK_BYTES = "K".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] STROKING_COLORSPACE_BYTES = "CS".getBytes(StandardCharsets.US_ASCII);

    // marked content
    public static final byte[] BEGIN_MARKED_CONTENT_SEQ_BYTES = "BDC".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] BEGIN_MARKED_CONTENT_BYTES = "BMC".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] END_MARKED_CONTENT_BYTES = "EMC".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] MARKED_CONTENT_POINT_WITH_PROPS_BYTES = "DP".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] MARKED_CONTENT_POINT_BYTES = "MP".getBytes(StandardCharsets.US_ASCII);
    public static final String DRAW_OBJECT = "Do";
    public static final byte[] DRAW_OBJECT_BYTES = "Do".getBytes(StandardCharsets.US_ASCII);

    // state
    public static final String CONCAT = "cm";
    public static final byte[] CONCAT_BYTES = "cm".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] RESTORE_BYTES = "Q".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SAVE_BYTES = "q".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_FLATNESS_BYTES = "i".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_GRAPHICS_STATE_PARAMS_BYTES = "gs".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_LINE_CAPSTYLE_BYTES = "J".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_LINE_DASHPATTERN_BYTES = "d".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_LINE_JOINSTYLE_BYTES = "j".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_LINE_MITERLIMIT_BYTES = "M".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_LINE_WIDTH_BYTES = "w".getBytes(StandardCharsets.US_ASCII);
    public static final String SET_MATRIX = "Tm";
    public static final byte[] SET_MATRIX_BYTES = "Tm".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_RENDERINGINTENT_BYTES = "ri".getBytes(StandardCharsets.US_ASCII);

    // graphics
    public static final byte[] APPEND_RECT_BYTES = "re".getBytes(StandardCharsets.US_ASCII);
    public static final String BEGIN_INLINE_IMAGE = "BI";
    public static final byte[] BEGIN_INLINE_IMAGE_BYTES = "BI".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] BEGIN_INLINE_IMAGE_DATA_BYTES = "ID".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] END_INLINE_IMAGE_BYTES = "EI".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] CLIP_EVEN_ODD_BYTES = "W*".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] CLIP_NON_ZERO_BYTES = "W".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] CLOSE_AND_STROKE_BYTES = "s".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] CLOSE_FILL_EVEN_ODD_AND_STROKE_BYTES = "b*".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] CLOSE_FILL_NON_ZERO_AND_STROKE_BYTES = "b".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] CLOSE_PATH_BYTES = "h".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] CURVE_TO_BYTES = "c".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] CURVE_TO_REPLICATE_FINAL_POINT_BYTES = "y".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] CURVE_TO_REPLICATE_INITIAL_POINT_BYTES = "v".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] ENDPATH_BYTES = "n".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] FILL_EVEN_ODD_AND_STROKE_BYTES = "B*".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] FILL_EVEN_ODD_BYTES = "f*".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] FILL_NON_ZERO_AND_STROKE_BYTES = "B".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] FILL_NON_ZERO_BYTES = "f".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] LEGACY_FILL_NON_ZERO_BYTES = "F".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] LINE_TO_BYTES = "l".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] MOVE_TO_BYTES = "m".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SHADING_FILL_BYTES = "sh".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] STROKE_PATH_BYTES = "S".getBytes(StandardCharsets.US_ASCII);

    // text
    public static final byte[] BEGIN_TEXT_BYTES = "BT".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] END_TEXT_BYTES = "ET".getBytes(StandardCharsets.US_ASCII);
    public static final String MOVE_TEXT = "Td";
    public static final byte[] MOVE_TEXT_BYTES = "Td".getBytes(StandardCharsets.US_ASCII);
    public static final String MOVE_TEXT_SET_LEADING = "TD";
    public static final byte[] MOVE_TEXT_SET_LEADING_BYTES = "TD".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] NEXT_LINE_BYTES = "T*".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_CHAR_SPACING_BYTES = "Tc".getBytes(StandardCharsets.US_ASCII);
    public static final String SET_FONT_AND_SIZE = "Tf";
    public static final byte[] SET_FONT_AND_SIZE_BYTES = "Tf".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_TEXT_HORIZONTAL_SCALING_BYTES = "Tz".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_TEXT_LEADING_BYTES = "TL".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_TEXT_RENDERINGMODE_BYTES = "Tr".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_TEXT_RISE_BYTES = "Ts".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SET_WORD_SPACING_BYTES = "Tw".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SHOW_TEXT_BYTES = "Tj".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SHOW_TEXT_ADJUSTED_BYTES = "TJ".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SHOW_TEXT_LINE_BYTES = "'".getBytes(StandardCharsets.US_ASCII);
    public static final byte[] SHOW_TEXT_LINE_AND_SPACE_BYTES = "\"".getBytes(StandardCharsets.US_ASCII);

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
