package wiki.xsx.core.pdf.template.doc;

import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.handler.XEasyPdfTemplateHandler;
import wiki.xsx.core.pdf.template.page.XEasyPdfTemplatePageComponent;
import wiki.xsx.core.pdf.template.template.XEasyPdfTemplate;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * pdf模板-文档
 *
 * @author xsx
 * @date 2022/8/6
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
public class XEasyPdfTemplateDocument {

    /**
     * pdf模板文档参数
     */
    private final XEasyPdfTemplateDocumentParam param = new XEasyPdfTemplateDocumentParam();

    /**
     * 设置配置文件路径（fop配置文件路径）
     * <p>注：此路径为绝对路径</p>
     *
     * @param configPath 配置文件路径（绝对路径）
     * @return 返回pdf模板-文档
     */
    public XEasyPdfTemplateDocument setConfigPath(String configPath) {
        this.param.setConfigPath(configPath);
        return this;
    }

    /**
     * 添加页面组件
     * <p>注：不推荐添加多个页面，会影响总页码的准确性</p>
     *
     * @param pages 页面组件列表
     * @return 返回pdf模板-文档
     */
    public XEasyPdfTemplateDocument addPage(XEasyPdfTemplatePageComponent... pages) {
        if (pages != null) {
            Collections.addAll(this.param.getPageList(), pages);
        }
        return this;
    }

    /**
     * 转换
     *
     * @param outputPath 输出路径
     */
    @SneakyThrows
    public void transform(String outputPath) {
        try (OutputStream outputStream = new FileOutputStream(outputPath)) {
            this.transform(outputStream);
        }
    }

    /**
     * 转换
     *
     * @param outputStream 输出流
     */
    @SneakyThrows
    public void transform(OutputStream outputStream) {
        this.initTemplate().transform(outputStream);
    }

    /**
     * 转换
     *
     * @return 返回pdf文档
     */
    @SneakyThrows
    public XEasyPdfDocument transform() {
        return this.initTemplate().transform();
    }

    /**
     * 获取xml（xsl-fo）文档内容
     *
     * @return 返回文档内容
     */
    @SneakyThrows
    public String getXMLContent() {
        // 创建输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)) {
            // 创建转换器
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            // 设置编码类型
            transformer.setOutputProperty("encoding", StandardCharsets.UTF_8.toString());
            // 转换
            transformer.transform(new DOMSource(this.initDocument()), new StreamResult(outputStream));
            // 返回字符串
            return outputStream.toString(StandardCharsets.UTF_8.toString());
        }
    }

    /**
     * 初始化模板
     *
     * @return 返回pdf模板
     */
    private XEasyPdfTemplate initTemplate() {
        return XEasyPdfTemplateHandler.Template.build()
                .setConfigPath(this.param.getConfigPath())
                .setDataSource(XEasyPdfTemplateHandler.DataSource.Document.build().setDocument(this.initDocument()));
    }

    /**
     * 初始化文档
     *
     * @return 返回xsl-fo文档
     */
    @SneakyThrows
    private Document initDocument() {
        // 定义文档
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                this.getClass().getResourceAsStream(XEasyPdfTemplateConstants.DEFAULT_TEMPLATE_PATH)
        );
        // 获取根元素
        Element root = document.getDocumentElement();
        // 定义页面索引
        int index = 0;
        // 遍历页面组件
        for (XEasyPdfTemplatePageComponent page : this.param.getPageList()) {
            // 添加页面节点
            root.appendChild(page.transform(++index, document));
        }
        // 返回文档
        return document;
    }
}
