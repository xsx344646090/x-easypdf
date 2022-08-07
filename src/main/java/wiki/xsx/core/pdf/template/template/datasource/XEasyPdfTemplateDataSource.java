package wiki.xsx.core.pdf.template.template.datasource;

import lombok.SneakyThrows;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParserFactory;
import java.io.OutputStream;
import java.io.Reader;

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
}


