package wiki.xsx.core.pdf.template.datasource;

import lombok.SneakyThrows;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * pdf模板-freemarker数据源
 *
 * @author xsx
 * @date 2022/11/8
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
abstract class XEasyPdfTemplateAbstractDataSource implements XEasyPdfTemplateDataSource {

    /**
     * 模板路径
     */
    protected String templatePath;
    /**
     * 模板数据
     */
    protected Map<String, Object> templateData;

    /**
     * 获取数据源读取器
     *
     * @return 返回数据源读取器
     */
    @SneakyThrows
    @Override
    public Reader getSourceReader() {
        return new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8);
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
        this.saxTransform(fopFactory, foAgent, outputStream);
    }

    /**
     * 处理模板
     *
     * @return 返回模板输入流
     */
    protected abstract InputStream processTemplate();

    /**
     * 获取输入流
     *
     * @return 返回输入流
     */
    @SneakyThrows
    private InputStream getInputStream() {
        // 如果模板数据不为空，则处理模板，否则直接加载模板
        return this.isNotEmptyTemplateData() ? this.processTemplate() : this.loadTemplateInputStream();
    }

    /**
     * 加载模板输入流
     *
     * @return 返回模板输入流
     */
    @SneakyThrows
    private InputStream loadTemplateInputStream() {
        try {
            // 从资源路径加载模板
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(this.templatePath);
            // 如果不为空，则返回，否则从绝对路径加载模板
            return inputStream != null ? inputStream : Files.newInputStream(Paths.get(this.templatePath));
        } catch (Exception e) {
            // 提示错误信息
            throw new IllegalArgumentException("the template can not be loaded，the path['" + this.templatePath + "'] is error");
        }
    }

    /**
     * 模板数据是否非空
     *
     * @return 返回布尔值，是为true，否为false
     */
    private boolean isNotEmptyTemplateData() {
        return this.templateData != null && !this.templateData.isEmpty();
    }
}
