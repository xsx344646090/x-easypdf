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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private String configPath;
    private final List<XEasyPdfTemplatePageComponent> pageList = new ArrayList<>(10);

    public XEasyPdfTemplateDocument setConfigPath(String configPath) {
        this.configPath = configPath;
        return this;
    }

    public XEasyPdfTemplateDocument addPage(XEasyPdfTemplatePageComponent... pages) {
        if (pages != null) {
            Collections.addAll(this.pageList, pages);
        }
        return this;
    }

    @SneakyThrows
    public void transform(String outputPath) {
        try (OutputStream outputStream = new FileOutputStream(outputPath)) {
            this.transform(outputStream);
        }
    }

    @SneakyThrows
    public void transform(OutputStream outputStream) {
        this.initTemplate().transform(outputStream);
    }

    @SneakyThrows
    public XEasyPdfDocument transform() {
        return this.initTemplate().transform();
    }

    @SneakyThrows
    public String getXMLContent() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)) {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("encoding", StandardCharsets.UTF_8.toString());
            transformer.transform(new DOMSource(this.initDocument()), new StreamResult(outputStream));
            return outputStream.toString(StandardCharsets.UTF_8.toString());
        }
    }

    List<XEasyPdfTemplatePageComponent> getPageList() {
        return this.pageList;
    }

    private XEasyPdfTemplate initTemplate() {
        return XEasyPdfTemplateHandler.Template.build()
                .setConfigPath(this.configPath)
                .setDataSource(XEasyPdfTemplateHandler.DataSource.Document.build().setDocument(this.initDocument()));
    }

    @SneakyThrows
    private Document initDocument() {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                this.getClass().getResourceAsStream(XEasyPdfTemplateConstants.DEFAULT_TEMPLATE_PATH)
        );
        Element root = document.getDocumentElement();
        int index = 0;
        for (XEasyPdfTemplatePageComponent page : this.pageList) {
            root.appendChild(page.transform(++index, document));
        }
        return document;
    }
}
