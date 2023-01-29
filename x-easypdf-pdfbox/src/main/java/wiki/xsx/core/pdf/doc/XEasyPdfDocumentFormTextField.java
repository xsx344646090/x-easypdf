package wiki.xsx.core.pdf.doc;

import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;

import java.io.Serializable;

/**
 * pdf文档表单文本属性
 *
 * @author xsx
 * @date 2022/4/2
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class XEasyPdfDocumentFormTextField implements Serializable {

    private static final long serialVersionUID = -6308558126594489097L;

    /**
     * 打印选项
     */
    private static final int FLAG_PRINTED = 1 << 2;
    /**
     * pdf文档表单
     */
    private final XEasyPdfDocumentForm form;
    /**
     * pdfbox文本属性
     */
    private final PDTextField textField;
    /**
     * X轴起始坐标
     */
    private Float beginX = 0F;
    /**
     * Y轴起始坐标
     */
    private Float beginY = 0F;
    /**
     * 宽度
     */
    private Float width = 100F;
    /**
     * 高度
     */
    private Float height = 12F;
    /**
     * 页面索引
     */
    private Integer pageIndex = 0;

    /**
     * 有参构造
     *
     * @param form pdf文档表单
     */
    XEasyPdfDocumentFormTextField(XEasyPdfDocumentForm form) {
        this.form = form;
        this.textField = new PDTextField(this.form.getFormFiller().getForm());
    }

    /**
     * 设置映射名称
     *
     * @param mappingName 映射名称
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField setMappingName(String mappingName) {
        this.textField.setPartialName(mappingName);
        return this;
    }

    /**
     * 设置替代名称（工具提示）
     *
     * @param alternateName 替代名称（工具提示）
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField setAlternateName(String alternateName) {
        this.textField.setAlternateFieldName(alternateName);
        return this;
    }

    /**
     * 设置位置
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField setPosition(float beginX, float beginY) {
        this.beginX = beginX;
        this.beginY = beginY;
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField setWidth(float width) {
        this.width = Math.abs(width);
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField setHeight(float height) {
        this.height = Math.abs(height);
        return this;
    }

    /**
     * 设置页面索引
     *
     * @param pageIndex 页面索引
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField setPageIndex(int pageIndex) {
        this.pageIndex = Math.abs(pageIndex);
        return this;
    }

    /**
     * 设置默认值
     *
     * @param defaultValue 默认值
     * @return 返回pdf文档表单文本属性
     */
    @SneakyThrows
    public XEasyPdfDocumentFormTextField setDefaultValue(String defaultValue) {
        if (defaultValue != null) {
            this.textField.setDefaultValue(defaultValue);
        }
        return this;
    }

    /**
     * 设置富文本值
     *
     * @param richTextValue 富文本值
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField setRichTextValue(String richTextValue) {
        if (richTextValue != null) {
            this.textField.setRichTextValue(richTextValue);
        }
        return this;
    }

    /**
     * 设置最大字符数
     *
     * @param maxLength 最大字符数
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField setMaxLength(int maxLength) {
        this.textField.setMaxLen(maxLength);
        return this;
    }

    /**
     * 设置文本样式（居左、居中、居右）
     *
     * @param style 文本样式
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField setStyle(TextStyle style) {
        this.textField.setQ(style.style);
        return this;
    }

    /**
     * 开启只读
     *
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField enableReadOnly() {
        this.textField.setReadOnly(true);
        return this;
    }

    /**
     * 开启必须
     *
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField enableRequired() {
        this.textField.setRequired(true);
        return this;
    }

    /**
     * 开启打印
     *
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField enablePrint() {
        this.textField.getCOSObject().setFlag(COSName.F, FLAG_PRINTED, true);
        return this;
    }

    /**
     * 开启富文本（RTF格式）
     *
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField enableRichText() {
        this.textField.setRichText(true);
        return this;
    }

    /**
     * 开启多行
     *
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField enableMultiline() {
        this.textField.setMultiline(true);
        return this;
    }

    /**
     * 开启密码
     *
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField enablePassword() {
        this.textField.setPassword(true);
        return this;
    }

    /**
     * 开启组合
     *
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField enableCombo() {
        this.textField.setComb(true);
        return this;
    }

    /**
     * 关闭拼写检查
     *
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField disableSpellCheck() {
        this.textField.setDoNotSpellCheck(true);
        return this;
    }

    /**
     * 关闭滚动
     *
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField disableScroll() {
        this.textField.setDoNotScroll(true);
        return this;
    }

    /**
     * 关闭导出
     *
     * @return 返回pdf文档表单文本属性
     */
    public XEasyPdfDocumentFormTextField disableExport() {
        this.textField.setNoExport(true);
        return this;
    }

    /**
     * 完成操作
     *
     * @return 返回pdf文档表单
     */
    @SneakyThrows
    public XEasyPdfDocumentForm finish() {
        // 获取pdf页面
        PDPage page = this.form.getFormFiller().getDocument().getPage(this.pageIndex);
        // 获取pdf控件
        PDAnnotationWidget widget = this.textField.getWidgets().get(0);
        // 设置尺寸
        widget.setRectangle(new PDRectangle(this.beginX, this.beginY, this.width, this.height));
        // 设置页面
        widget.setPage(page);
        // 添加控件
        page.getAnnotations().add(widget);
        // 添加文本属性
        this.form.getFormFiller().getForm().getFields().add(this.textField);
        // 返回表单
        return this.form;
    }

    /**
     * 文本样式
     */
    public enum TextStyle {
        /**
         * 居左
         */
        LEFT(PDVariableText.QUADDING_LEFT),
        /**
         * 居中
         */
        CENTER(PDVariableText.QUADDING_CENTERED),
        /**
         * 居右
         */
        RIGHT(PDVariableText.QUADDING_RIGHT);

        /**
         * 样式
         */
        private final Integer style;

        /**
         * 有参构造
         *
         * @param style 样式
         */
        TextStyle(Integer style) {
            this.style = style;
        }
    }
}
