package wiki.xsx.core.pdf.doc;

import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.fixup.AcroFormDefaultFixup;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.form.*;
import wiki.xsx.core.pdf.util.XEasyPdfFileUtil;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;
import wiki.xsx.core.pdf.util.XEasyPdfTextUtil;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * pdf文档表单填写器
 *
 * @author xsx
 * @date 2021/10/3
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
public class XEasyPdfDocumentFormFiller implements Serializable {

    private static final long serialVersionUID = -178730035810612354L;

    /**
     * pdf文档
     */
    private final XEasyPdfDocument pdfDocument;
    /**
     * pdfbox文档
     */
    private final PDDocument document;
    /**
     * pdfbox表单
     */
    private PDAcroForm form;
    /**
     * 字体替换正则（未包含字体大小）
     */
    private static final String FONT_REGEX = "/\\S*";
    /**
     * 字体替换正则（包含字体大小）
     */
    private static final String FONT_SIZE_REGEX = "/.*Tf";
    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 字体大小
     */
    private String fontSize;
    /**
     * 是否只读
     */
    private Boolean isReadOnly = Boolean.FALSE;
    /**
     * 是否需要外观
     */
    private Boolean isNeedAppearance = Boolean.FALSE;
    /**
     * 是否压缩
     */
    private Boolean isCompress = Boolean.FALSE;

    /**
     * 构造方法
     *
     * @param pdfDocument pdf文档
     */
    XEasyPdfDocumentFormFiller(XEasyPdfDocument pdfDocument) {
        this.pdfDocument = pdfDocument;
        this.document = this.pdfDocument.build(true);
        this.form = this.initAcroForm(this.document, false);
    }

    /**
     * 开启只读（填充后）
     *
     * @return 返回pdf表单填写器
     */
    public XEasyPdfDocumentFormFiller enableReadOnly() {
        this.isReadOnly = Boolean.TRUE;
        return this;
    }

    /**
     * 开启表单修复（可能会更改原始内容）
     *
     * @return 返回pdf表单填写器
     */
    public XEasyPdfDocumentFormFiller enableFixForm() {
        this.form = this.initAcroForm(this.document, true);
        return this;
    }

    /**
     * 开启外观（将使用原有字体）
     *
     * @return 返回pdf表单填写器
     */
    public XEasyPdfDocumentFormFiller enableAppearance() {
        this.isNeedAppearance = Boolean.TRUE;
        this.form.setNeedAppearances(Boolean.TRUE);
        return this;
    }

    /**
     * 开启压缩
     *
     * @return 返回pdf表单填写器
     */
    public XEasyPdfDocumentFormFiller enableCompress() {
        this.isCompress = Boolean.TRUE;
        return this;
    }

    /**
     * 设置字体路径（开启外观后失效）
     *
     * @param fontPath 字体路径
     * @return 返回pdf表单填写器
     */
    public XEasyPdfDocumentFormFiller setFontPath(String fontPath) {
        this.fontPath = fontPath;
        return this;
    }

    /**
     * 设置字体大小（开启外观后失效）
     *
     * @param fontSize 字体大小
     * @return 返回pdf表单填写器
     */
    public XEasyPdfDocumentFormFiller setFontSize(int fontSize) {
        this.fontSize = String.valueOf(Math.abs(fontSize));
        return this;
    }

    /**
     * 设置默认字体样式（开启外观后失效）
     *
     * @param style 默认字体样式
     * @return 返回pdf表单填写器
     */
    public XEasyPdfDocumentFormFiller setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.fontPath = style.getPath();
        }
        return this;
    }

    /**
     * 填充表单
     *
     * @param formMap 表单字典
     * @return 返回pdf表单填写器
     */
    public XEasyPdfDocumentFormFiller fill(Map<String, String> formMap) {
        // 如果表单字典有内容，则进行填充
        if (formMap != null && !formMap.isEmpty()) {
            // 如果pdfBox表单不为空，则进行填充
            if (this.form != null) {
                // 如果需要外观，则使用外观填充模式
                if (this.form.getNeedAppearances()) {
                    // 使用外观填充模式
                    this.fillForAppearance(formMap);
                }
                // 否则使用普通填充模式
                else {
                    // 使用普通填充模式
                    this.fillForNormal(formMap);
                }
                // 重置字体大小
                this.fontSize = null;
            }
        }
        return this;
    }

    /**
     * 清空表单字段
     *
     * @return 返回pdf表单
     */
    public XEasyPdfDocumentFormFiller clear() {
        this.form.getFields().forEach(this::clearField);
        return this;
    }

    /**
     * 创建表单
     *
     * @return 返回pdf表单
     */
    public XEasyPdfDocumentForm create() {
        return new XEasyPdfDocumentForm(this);
    }

    /**
     * 文档签名器
     *
     * @return 返回pdf文档签名器
     */
    public XEasyPdfDocumentSigner signer() {
        return new XEasyPdfDocumentSigner(this.pdfDocument, this.document);
    }

    /**
     * 完成操作
     *
     * @param outputPath 文件输出路径
     */
    @SneakyThrows
    public void finish(String outputPath) {
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(XEasyPdfFileUtil.createDirectories(Paths.get(outputPath))))) {
            this.finish(outputStream);
        }
    }

    /**
     * 完成操作
     *
     * @param outputStream 文件输出流
     */
    @SneakyThrows
    public void finish(OutputStream outputStream) {
        // 如果开启压缩，则重置表单为空（可以减少文件大小）
        if (this.isCompress && !this.form.getNeedAppearances()) {
            // 重置表单为空
            this.document.getDocumentCatalog().setAcroForm(null);
        }
        // 设置基础信息（文档信息、保护策略、版本、xmp信息及书签）
        this.pdfDocument.setBasicInfo(this.document);
        // 替换总页码占位符
        this.pdfDocument.replaceTotalPagePlaceholder(this.document, false);
        // 保存文档
        this.document.save(outputStream);
        // 关闭文档
        this.pdfDocument.close();
    }

    /**
     * 获取pdfbox文档
     *
     * @return 返回pdfbox文档
     */
    PDDocument getDocument() {
        return this.document;
    }

    /**
     * 获取pdfbox表单
     *
     * @return 返回pdfbox表单
     */
    PDAcroForm getForm() {
        if (this.form == null) {
            this.form = new PDAcroForm(this.document);
        }
        return this.form;
    }

    /**
     * 初始化表单
     *
     * @param document  pdfbox文档
     * @param isFixForm 是否修复表单
     * @return 返回pdfbox表单
     */
    private PDAcroForm initAcroForm(PDDocument document, boolean isFixForm) {
        // 获取pdfbox文档大纲
        PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
        // 定义pdfbox表单
        PDAcroForm acroForm;
        // 如果开启修复表单，则初始化修复后的表单
        if (isFixForm) {
            // 初始化修复后的表单
            acroForm = documentCatalog.getAcroForm(new AcroFormDefaultFixup(document));
        }
        // 否则初始化原始表单
        else {
            // 初始化原始表单
            acroForm = documentCatalog.getAcroForm(null);
        }
        // 如果表单依然为空，则初始化空表单
        if (acroForm == null) {
            // 初始化空表单
            acroForm = new PDAcroForm(document);
        }
        // 设置外观
        acroForm.setNeedAppearances(this.isNeedAppearance);
        // 返回表单
        return acroForm;
    }

    /**
     * 普通填充模式
     *
     * @param formMap 表单字典
     */
    @SneakyThrows
    private void fillForNormal(Map<String, String> formMap) {
        // 初始化字体
        PDFont font = this.initFont();
        // 获取表单默认资源
        PDResources defaultResources = this.form.getDefaultResources();
        // 添加自定义字体
        defaultResources.put(COSName.getPDFName(font.getName()), font);
        // 如果填充表单成功，则嵌入字体
        if (this.fill(formMap, font)) {
            // 嵌入字体
            this.pdfDocument.getParam().embedFont(Collections.singleton(font));
        }
    }

    /**
     * 外观填充模式
     *
     * @param formMap 表单字典
     */
    private void fillForAppearance(Map<String, String> formMap) {
        this.fill(formMap, null);
    }

    /**
     * 填充表单
     *
     * @param formMap 表单字典
     * @param font    pdfbox字体
     * @return 返回布尔值，是为true，否为false
     */
    @SneakyThrows
    private boolean fill(Map<String, String> formMap, PDFont font) {
        // 定义填充标记
        boolean flag = false;
        // 定义pdfBox表单字段
        PDField field;
        // 定义新值
        String newValue;
        // 获取表单字典键值集合
        Set<Map.Entry<String, String>> entrySet = formMap.entrySet();
        // 遍历表单字典
        for (Map.Entry<String, String> entry : entrySet) {
            // 获取表单字典中对应的pdfBox表单字段
            field = this.form.getField(entry.getKey());
            // 获取新值
            newValue = entry.getValue();
            // 如果pdfBox表单字段不为空，则填充值
            if (field != null) {
                // 如果新值不为空，则设置新值
                if (XEasyPdfTextUtil.isNotBlank(newValue)) {
                    // 重置新值
                    this.resetValue(font, field, newValue);
                    // 重置填充标记
                    flag = true;
                }
                // 如果开启只读，则设置为只读
                if (this.isReadOnly) {
                    // 设置为只读
                    field.setReadOnly(true);
                }
            }
        }
        return flag;
    }

    /**
     * 初始化字体
     *
     * @return 返回pdfbox字体
     */
    private PDFont initFont() {
        // 如果字体路径为空，则初始化字体路径
        if (this.fontPath == null) {
            // 初始化字体路径为文档字体路径
            this.fontPath = this.pdfDocument.getFontPath();
        }
        // 读取字体
        return XEasyPdfFontUtil.loadFont(this.pdfDocument, this.fontPath, true);
    }

    /**
     * 重置新值
     *
     * @param font     pdfbox字体
     * @param field    pdfbox表单字段
     * @param newValue 字段新值
     */
    @SneakyThrows
    private void resetValue(PDFont font, PDField field, String newValue) {
        // 如果字体不为空，则替换与嵌入字体
        if (font != null) {
            // 如果表单字段为文本字段，则重置默认外观
            if (field instanceof PDTextField) {
                // 转换为文本字体
                PDTextField textField = (PDTextField) field;
                // 获取默认外观
                String defaultAppearance = textField.getDefaultAppearance();
                // 如果字体大小为空，则重置默认外观（使用原字体大小）
                if (this.fontSize == null) {
                    // 重置默认外观（使用原字体大小）
                    textField.setDefaultAppearance(
                            defaultAppearance.replaceFirst(
                                    FONT_REGEX,
                                    XEasyPdfTextUtil.join("", "/", font.getName())
                            )
                    );
                }
                // 否则重置默认外观（包含字体大小）
                else {
                    // 重置默认外观（包含字体大小）
                    textField.setDefaultAppearance(
                            defaultAppearance.replaceFirst(
                                    FONT_SIZE_REGEX,
                                    XEasyPdfTextUtil.join(" ", "/", font.getName(), this.fontSize, "Tf")
                            )
                    );
                }
            }
            // 添加文本关联
            XEasyPdfFontUtil.addToSubset(font, newValue);
        }
        // 设置新值
        field.setValue(newValue);
    }

    /**
     * 清空表单字段
     *
     * @param field 表单字段
     */
    private void clearField(PDField field) {
        // 如果为终端字段，则删除页面注释
        if (field instanceof PDTerminalField) {
            // 删除页面注释
            field.getWidgets().forEach(v -> Optional.ofNullable(v.getPage()).ifPresent(this::clearAnnotations));
        }
        // 如果为非终端字段，则递归清空
        else if (field instanceof PDNonTerminalField) {
            // 递归清空
            ((PDNonTerminalField) field).getChildren().forEach(this::clearField);
        }
    }

    /**
     * 清空注解
     *
     * @param page pdfbox页面
     */
    @SneakyThrows
    private void clearAnnotations(PDPage page) {
        page.getAnnotations().clear();
    }
}
