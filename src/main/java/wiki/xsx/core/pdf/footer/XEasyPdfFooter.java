package wiki.xsx.core.pdf.footer;


import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;

/**
 * pdf页脚组件接口
 * @author xsx
 * @date 2020/6/7
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
public interface XEasyPdfFooter {

    /**
     * 设置边距（左右下）
     * @param margin 边距
     * @return 返回页脚组件
     */
    XEasyPdfFooter setMargin(float margin);

    /**
     * 设置左边距
     * @param margin 边距
     * @return 返回页脚组件
     */
    XEasyPdfFooter setMarginLeft(float margin);

    /**
     * 设置右边距
     * @param margin 边距
     * @return 返回页脚组件
     */
    XEasyPdfFooter setMarginRight(float margin);

    /**
     * 设置下边距
     * @param margin 边距
     * @return 返回页脚组件
     */
    XEasyPdfFooter setMarginBottom(float margin);

    /**
     * 获取页脚高度
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回页脚高度
     */
    float getHeight(XEasyPdfDocument document, XEasyPdfPage page);

    /**
     * 检查组件
     * @param component 组件
     * @return 返回布尔值，true为是，false为否
     */
    boolean check(XEasyPdfComponent component);

    /**
     * 绘制
     * @param document pdf文档
     * @param page     pdf页面
     */
    void draw(XEasyPdfDocument document, XEasyPdfPage page);
}
