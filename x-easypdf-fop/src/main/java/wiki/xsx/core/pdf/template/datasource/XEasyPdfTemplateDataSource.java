package wiki.xsx.core.pdf.template.datasource;

import lombok.SneakyThrows;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * pdf模板数据源
 *
 * @author xsx
 * @date 2022/8/5
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
public interface XEasyPdfTemplateDataSource {

    /**
     * 获取数据源读取器
     *
     * @return 返回数据源读取器
     */
    Reader getSourceReader();

    /**
     * 转换
     *
     * @param fopFactory   fop工厂
     * @param foAgent      fo代理
     * @param outputStream 输出流
     */
    void transform(FopFactory fopFactory, FOUserAgent foAgent, OutputStream outputStream);

    /**
     * SAX转换
     *
     * @param fopFactory   fop工厂
     * @param foAgent      fo代理
     * @param outputStream 输出流
     */
    @SneakyThrows
    default void saxTransform(FopFactory fopFactory, FOUserAgent foAgent, OutputStream outputStream) {
        // 创建sax解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // 设置命名空间支持
        factory.setNamespaceAware(true);
        // 获取输入流
        try (Reader reader = this.getSourceReader()) {
            // 转换文件
            factory.newSAXParser().parse(new InputSource(reader), fopFactory.newFop(MimeConstants.MIME_PDF, foAgent, outputStream).getDefaultHandler());
        }
    }

    /**
     * dom转换
     *
     * @param fopFactory   fop工厂
     * @param foAgent      fo代理
     * @param templatePath 模板路径
     * @param outputStream 输出流
     */
    @SneakyThrows
    default void domTransform(FopFactory fopFactory, FOUserAgent foAgent, String templatePath, OutputStream outputStream) {
        // 加载模板（从资源路径读取）
        InputStream inputStream = this.getClass().getResourceAsStream(templatePath);
        try {
            // 如果输入流为空，则从绝对路径读取
            if (inputStream == null) {
                // 加载模板（从绝对路径读取）
                inputStream = Files.newInputStream(Paths.get(templatePath));
            }
        } catch (Exception e) {
            // 提示错误信息
            throw new IllegalArgumentException("the template can not be loaded，the path['" + templatePath + "'] is error");
        }
        // 创建输入流读取器
        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            // 创建转换器
            Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(streamReader));
            // 获取数据源读取器
            try (Reader reader = this.getSourceReader()) {
                // 转换文件
                transformer.transform(new StreamSource(reader), new SAXResult(fopFactory.newFop(MimeConstants.MIME_PDF, foAgent, outputStream).getDefaultHandler()));
            }
        } finally {
            // 关闭输入流
            inputStream.close();
        }
    }

    /**
     * 获取xsl-fo文档内容
     *
     * @return 返回文档内容
     */
    @SneakyThrows
    default String getDocumentContent() {
        try (
                // 创建输出流
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192);
                // 获取数据源读取器
                Reader reader = this.getSourceReader()
        ) {
            // 创建转换器
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            // 设置编码类型
            transformer.setOutputProperty("encoding", StandardCharsets.UTF_8.toString());
            // 转换
            transformer.transform(new StreamSource(reader), new StreamResult(outputStream));
            // 返回字符串
            return outputStream.toString(StandardCharsets.UTF_8.toString());
        }
    }
}
