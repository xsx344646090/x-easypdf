package wiki.xsx.core.pdf.template.template.datasource;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * pdf模板-xml数据源
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
@Setter
@Accessors(chain = true)
public class XEasyPdfTemplateXMLDataSource implements XEasyPdfTemplateDataSource {

    /**
     * 模板路径（绝对路径）
     */
    private String templatePath;
    /**
     * xml路径（绝对路径）
     */
    private String xmlPath;

    /**
     * 获取数据源读取器
     *
     * @return 返回数据源读取器
     */
    @Override
    public Reader getSourceReader() {
        // 如果不为空数据，则加载xml数据
        if (this.isNotEmptyData()) {
            // 加载xml
            try (InputStream inputStream = new FileInputStream(this.xmlPath)) {
                // 返回数据源读取器
                return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            } catch (Exception e) {
                // 提示错误信息
                throw new IllegalArgumentException("the xml can not be loaded，the path['" + this.xmlPath + "'] is error");
            }
        }
        // 返回空数据源读取器
        return null;
    }

    /**
     * 转换
     *
     * @param fopFactory   fop工厂
     * @param foAgent      fo代理
     * @param outputStream 输出流
     */
    @SneakyThrows
    @Override
    public void transform(FopFactory fopFactory, FOUserAgent foAgent, OutputStream outputStream) {
        // 定义转换器
        Transformer transformer;
        try (
                // 加载模板
                InputStream inputStream = new FileInputStream(this.templatePath);
                // 创建读取器
                InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        ) {
            // 创建转换器
            transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(reader));
        } catch (Exception e) {
            // 提示错误信息
            throw new IllegalArgumentException("the template can not be loaded，the path['" + this.templatePath + "'] is error");
        }
        // 设置参数版本
        transformer.setParameter("versionParam", "2.0");
        // 获取数据源读取器
        try (Reader reader = this.getSourceReader()) {
            // 转换文件
            transformer.transform(new StreamSource(reader), new SAXResult(fopFactory.newFop(MimeConstants.MIME_PDF, foAgent, outputStream).getDefaultHandler()));
        }
    }

    /**
     * 模板数据是否非空
     *
     * @return 返回布尔值，是为true，否为false
     */
    private boolean isNotEmptyData() {
        return this.xmlPath != null && this.xmlPath.length() > 0;
    }
}
