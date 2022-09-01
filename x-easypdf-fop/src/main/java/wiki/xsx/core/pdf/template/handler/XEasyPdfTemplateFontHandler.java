package wiki.xsx.core.pdf.template.handler;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.fop.fonts.apps.TTFReader;

/**
 * pdf模板字体助手
 *
 * @author xsx
 * @date 2022/7/31
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
@Setter
@Accessors(chain = true)
public class XEasyPdfTemplateFontHandler {

    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 输出路径
     */
    private String outputPath;

    /**
     * 转换
     */
    public void transform() {
        // 如果字体路径未初始化，则提示错误
        if (this.fontPath == null) {
            // 提示错误
            throw new IllegalArgumentException("the font path can not be null");
        }
        // 如果输出路径未初始化，则提示错误
        if (this.outputPath == null) {
            // 提示错误
            throw new IllegalArgumentException("the output path can not be null");
        }
        // 定义参数
        String[] args = new String[2];
        // 设置字体路径
        args[0] = this.fontPath;
        // 设置输出路径
        args[1] = this.outputPath;
        // 生成字体xml
        TTFReader.main(args);
    }
}
