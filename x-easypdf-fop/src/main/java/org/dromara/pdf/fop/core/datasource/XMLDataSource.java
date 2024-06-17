package org.dromara.pdf.fop.core.datasource;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * pdf模板-xml数据源
 *
 * @author xsx
 * @date 2022/8/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under the Mulan PSL v2.
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
public class XMLDataSource implements DataSource {

    /**
     * 模板路径（绝对路径）
     */
    private String templatePath;
    /**
     * xml路径（绝对路径）
     */
    private String xmlPath;
    /**
     * xml数据输入流
     */
    private InputStream xmlInputStream;

    /**
     * 获取总页数
     *
     * @param fopFactory fop工厂
     * @param foAgent    fo代理
     * @return 返回总页数
     */
    @Override
    public Integer getTotalPage(FopFactory fopFactory, FOUserAgent foAgent) {
        Integer totalPage = this.domTransform(fopFactory, foAgent, this.templatePath, NullOutputStream.INSTANCE);
        Optional.ofNullable(this.xmlInputStream).ifPresent(
                v -> {
                    try {
                        v.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return totalPage;
    }

    /**
     * 获取数据源读取器
     *
     * @return 返回数据源读取器
     */
    @SneakyThrows
    @Override
    public Reader getSourceReader() {
        // 如果不为空数据，则加载xml数据
        if (this.isNotEmptyData()) {
            try {
                // 从资源路径加载xml数据
                InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.xmlPath);
                // 如果不为空，则返回数据源读取器，否则从绝对路径重新加载模板
                this.xmlInputStream = inputStream != null ? inputStream : Files.newInputStream(Paths.get(this.xmlPath));
                // 返回输入流读取器
                return new InputStreamReader(this.xmlInputStream, StandardCharsets.UTF_8);
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
        this.domTransform(fopFactory, foAgent, this.templatePath, outputStream);
        Optional.ofNullable(this.xmlInputStream).ifPresent(
                v -> {
                    try {
                        v.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    /**
     * 获取xsl-fo文档内容
     *
     * @return 返回文档内容
     */
    @SneakyThrows
    @Override
    public String getDocumentContent() {
        try (
                // 创建输出流
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192);
                // 获取数据源读取器
                Reader reader = this.getSourceReader()
        ) {
            // 加载模板（从资源路径读取）
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.templatePath);
            try {
                // 如果输入流为空，则从绝对路径读取
                if (inputStream == null) {
                    // 加载模板（从绝对路径读取）
                    inputStream = Files.newInputStream(Paths.get(this.templatePath));
                }
            } catch (Exception e) {
                // 提示错误信息
                throw new IllegalArgumentException("the template can not be loaded，the path['" + this.templatePath + "'] is error");
            }
            // 创建输入流读取器
            try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                // 创建转换器
                Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(streamReader));
                // 设置编码类型
                transformer.setOutputProperty("encoding", StandardCharsets.UTF_8.toString());
                // 转换
                transformer.transform(new StreamSource(reader), new StreamResult(outputStream));
                // 返回字符串
                return outputStream.toString(StandardCharsets.UTF_8.toString());
            } finally {
                // 关闭输入流
                inputStream.close();
            }

        }
    }

    /**
     * 模板数据是否非空
     *
     * @return 返回布尔值，是为true，否为false
     */
    private boolean isNotEmptyData() {
        return (this.xmlPath != null && this.xmlPath.length() > 0) || this.xmlInputStream != null;
    }
}
