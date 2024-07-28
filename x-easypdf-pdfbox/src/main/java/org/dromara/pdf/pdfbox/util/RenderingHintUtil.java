package org.dromara.pdf.pdfbox.util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 渲染提示工具
 *
 * @author xsx
 * @date 2024/1/31
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class RenderingHintUtil {

    /**
     * 创建默认渲染提示
     *
     * @return 返回渲染提示
     */
    public static RenderingHints createDefaultRenderingHints() {
        // 创建提示
        Map<RenderingHints.Key, Object> hints = new HashMap<>(4);
        // 设置插值
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // 设置图像抗锯齿
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置文本抗锯齿
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 设置笔划规范化控制参数
        hints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        // 设置渲染提示
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        // 返回提示
        return new RenderingHints(hints);
    }
}
