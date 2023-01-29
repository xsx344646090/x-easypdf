package wiki.xsx.core.pdf.template.datasource;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.doc.XEasyPdfTemplateDocumentComponent;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;

/**
 * pdf模板-document数据源
 *
 * @author xsx
 * @date 2022/8/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class XEasyPdfTemplateDocumentDataSource extends XEasyPdfTemplateAbstractDataSource {

    /**
     * 文档组件
     */
    private XEasyPdfTemplateDocumentComponent document;

    /**
     * 无参构造
     */
    public XEasyPdfTemplateDocumentDataSource() {
        // 初始化模板数据
        this.templateData = Collections.singletonMap("", "");
    }

    /**
     * 处理模板
     *
     * @return 返回模板输入流
     */
    @SneakyThrows
    @Override
    protected InputStream processTemplate() {
        // 如果模板数据不为空，则处理模板
        if (this.document == null) {
            // 提示错误信息
            throw new IllegalArgumentException("the document can not be null");
        }
        // 创建输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)) {
            // 转换文档（document转为xsl-fo）
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(this.document.getDocument()), new StreamResult(outputStream));
            // 返回输入流
            return new BufferedInputStream(new ByteArrayInputStream(outputStream.toByteArray()));
        }
    }
}
