package wiki.xsx.core.pdf.component;


import org.apache.pdfbox.pdmodel.PDPageContentStream;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;

import java.io.Serializable;

/**
 * pdf组件接口
 *
 * @author xsx
 * @date 2020/3/3
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public interface XEasyPdfComponent extends Serializable {

    /**
     * 设置坐标
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回pdf组件
     */
    XEasyPdfComponent setPosition(float beginX, float beginY);

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回pdf组件
     */
    XEasyPdfComponent setWidth(float width);

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回pdf组件
     */
    XEasyPdfComponent setHeight(float height);

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回pdf组件
     */
    XEasyPdfComponent setContentMode(ContentMode mode);

    /**
     * 开启上下文重置
     *
     * @return 返回pdf组件
     */
    XEasyPdfComponent enableResetContext();

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    void draw(XEasyPdfDocument document, XEasyPdfPage page);

    /**
     * 内容模式
     */
    enum ContentMode {
        /**
         * 覆盖
         */
        OVERWRITE(PDPageContentStream.AppendMode.OVERWRITE),
        /**
         * 追加
         */
        APPEND(PDPageContentStream.AppendMode.APPEND),
        /**
         * 前置
         */
        PREPEND(PDPageContentStream.AppendMode.PREPEND);

        /**
         * pdfbox追加模式
         */
        private final PDPageContentStream.AppendMode appendMode;

        /**
         * 构造方法
         *
         * @param appendMode pdfbox追加模式
         */
        ContentMode(PDPageContentStream.AppendMode appendMode) {
            this.appendMode = appendMode;
        }

        /**
         * 获取追加模式
         *
         * @return 返回pdfbox追加模式
         */
        public PDPageContentStream.AppendMode getMode() {
            return this.appendMode;
        }
    }
}
