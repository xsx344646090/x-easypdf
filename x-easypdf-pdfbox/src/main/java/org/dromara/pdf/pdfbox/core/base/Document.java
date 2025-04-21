package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdfwriter.compress.CompressParameters;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.ResourceCache;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.PublicKeyProtectionPolicy;
import org.apache.pdfbox.pdmodel.encryption.PublicKeyRecipient;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.base.config.FontConfiguration;
import org.dromara.pdf.pdfbox.core.base.config.MarginConfiguration;
import org.dromara.pdf.pdfbox.core.enums.FontStyle;
import org.dromara.pdf.pdfbox.core.enums.PWLength;
import org.dromara.pdf.pdfbox.core.ext.processor.MetadataProcessor;
import org.dromara.pdf.pdfbox.core.ext.processor.PageProcessor;
import org.dromara.pdf.pdfbox.core.info.CatalogInfo;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.support.DefaultResourceCache;
import org.dromara.pdf.pdfbox.support.linearizer.Linearizer;
import org.dromara.pdf.pdfbox.util.FileUtil;
import org.dromara.pdf.pdfbox.util.IdUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.List;

/**
 * 文档
 *
 * @author xsx
 * @date 2023/6/1
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Document extends AbstractBase implements Closeable {

    /**
     * 边框配置
     */
    protected MarginConfiguration marginConfiguration;
    /**
     * 字体配置
     */
    protected FontConfiguration fontConfiguration;
    /**
     * 背景颜色
     */
    protected Color backgroundColor;
    /**
     * 背景图片
     */
    protected BufferedImage backgroundImage;
    /**
     * 任务文档
     */
    protected PDDocument target;
    /**
     * 页面列表
     */
    protected List<Page> pages;
    /**
     * pdf访问权限
     */
    protected AccessPermission accessPermission;
    /**
     * 文档版本
     */
    protected Float version;
    /**
     * 总页码
     */
    protected Integer totalPageNumber;
    /**
     * 是否刷新元数据
     */
    protected Boolean isFlushMetadata;
    /**
     * 是否线性化
     */
    protected Boolean isLinearization;

    /**
     * 无参构造
     */
    public Document() {
        // 初始化参数
        this.init();
    }

    /**
     * 有参构造
     *
     * @param policy 内存策略
     */
    public Document(MemoryPolicy policy) {
        // 检查参数
        Objects.requireNonNull(policy, "the policy can not be null");
        // 初始化参数
        this.init(policy);
    }

    /**
     * 有参构造
     *
     * @param file     文件
     * @param password 文件密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @param policy   内存策略
     */
    public Document(File file, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        // 初始化参数
        this.init(file, password, keyStore, alias, policy);
    }

    /**
     * 有参构造
     *
     * @param bytes    字节数组
     * @param password 文件密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     * @param policy   内存策略
     */
    public Document(byte[] bytes, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        // 初始化参数
        this.init(bytes, password, keyStore, alias, policy);
    }

    /**
     * 有参构造
     *
     * @param inputStream 文件输入流
     * @param password    文件密码
     * @param keyStore    证书输入流
     * @param alias       证书别名
     * @param policy      内存策略
     */
    public Document(InputStream inputStream, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        // 初始化参数
        this.init(inputStream, password, keyStore, alias, policy);
    }

    /**
     * 设置版本
     *
     * @param version 版本
     */
    public void setVersion(float version) {
        // 最大版本
        float maxVersion = 1.7F;
        // 最小版本
        float minVersion = 1.0F;
        // 如果版本小于1.0且大于1.7，则提示错误
        if (version < minVersion || version > maxVersion) {
            // 提示错误
            throw new IllegalArgumentException("the version must be between 1.0 and 1.7");
        }
        // 重置版本
        this.version = version;
    }

    /**
     * 设置缓存
     *
     * @param cache 缓存
     */
    public void setResourceCache(ResourceCache cache) {
        Objects.requireNonNull(cache, "the cache can not be null");
        this.target.setResourceCache(cache);
    }

    /**
     * 设置是否刷新元数据
     *
     * @param isFlushMetadata 是否刷新元数据
     */
    public void setIsFlushMetadata(boolean isFlushMetadata) {
        this.isFlushMetadata = isFlushMetadata;
    }

    /**
     * 设置是否线性化
     *
     * @param isLinearization 是否线性化
     */
    public void setIsLinearization(boolean isLinearization) {
        this.isLinearization = isLinearization;
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     */
    public void setMargin(float margin) {
        this.marginConfiguration.setMargin(margin);
    }

    /**
     * 获取字体
     *
     * @return 返回字体
     */
    public PDFont getFont() {
        return this.getContext().getFont(this.fontConfiguration.getFontName());
    }

    /**
     * 获取上边距
     *
     * @return 返回上边距
     */
    public Float getMarginTop() {
        return this.marginConfiguration.getMarginTop();
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     */
    public void setMarginTop(float margin) {
        this.marginConfiguration.setMarginTop(margin);
    }

    /**
     * 获取下边距
     *
     * @return 返回下边距
     */
    public Float getMarginBottom() {
        return this.marginConfiguration.getMarginBottom();
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     */
    public void setMarginBottom(float margin) {
        this.marginConfiguration.setMarginBottom(margin);
    }

    /**
     * 获取左边距
     *
     * @return 返回左边距
     */
    public Float getMarginLeft() {
        return this.marginConfiguration.getMarginLeft();
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     */
    public void setMarginLeft(float margin) {
        this.marginConfiguration.setMarginLeft(margin);
    }

    /**
     * 获取右边距
     *
     * @return 返回右边距
     */
    public Float getMarginRight() {
        return this.marginConfiguration.getMarginRight();
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     */
    public void setMarginRight(float margin) {
        this.marginConfiguration.setMarginRight(margin);
    }

    /**
     * 获取字体名称
     *
     * @return 返回字体名称
     */
    public String getFontName() {
        return this.fontConfiguration.getFontName();
    }

    /**
     * 设置字体名称
     *
     * @param fontName 字体名称
     */
    public void setFontName(String fontName) {
        this.fontConfiguration.setFontName(fontName);
        this.getContext().addFontCache(fontName);
    }

    /**
     * 获取特殊字体名称
     *
     * @return 返回特殊字体名称
     */
    public List<String> getSpecialFontNames() {
        return this.fontConfiguration.getSpecialFontNames();
    }

    /**
     * 设置特殊字体名称
     *
     * @param fontNames 字体名称
     */
    public void setSpecialFontNames(String... fontNames) {
        this.getContext().addFontCache(fontNames);
        if (Objects.isNull(this.fontConfiguration.getSpecialFontNames())) {
            this.fontConfiguration.setSpecialFontNames(new ArrayList<>(10));
        }
        Collections.addAll(this.fontConfiguration.getSpecialFontNames(), fontNames);
    }

    /**
     * 获取字体大小
     *
     * @return 返回字体大小
     */
    public Float getFontSize() {
        return this.fontConfiguration.getFontSize();
    }

    /**
     * 设置字体大小
     *
     * @param size 大小
     */
    public void setFontSize(float size) {
        this.fontConfiguration.setFontSize(size);
    }

    /**
     * 获取字体颜色
     *
     * @return 返回字体颜色
     */
    public Color getFontColor() {
        return this.fontConfiguration.getFontColor();
    }

    /**
     * 设置字体颜色
     *
     * @param color 颜色
     */
    public void setFontColor(Color color) {
        this.fontConfiguration.setFontColor(color);
    }

    /**
     * 获取字体描边颜色
     *
     * @return 返回字体描边颜色
     */
    public Color getStrokColor() {
        return this.fontConfiguration.getStrokColor();
    }

    /**
     * 设置字体描边颜色
     *
     * @param color 颜色
     */
    public void setStrokColor(Color color) {
        this.fontConfiguration.setStrokColor(color);
    }

    /**
     * 获取字体透明度
     *
     * @return 返回字体透明度
     */
    public Float getFontAlpha() {
        return this.fontConfiguration.getFontAlpha();
    }

    /**
     * 设置字体透明度
     *
     * @param alpha 透明度
     */
    public void setFontAlpha(float alpha) {
        this.fontConfiguration.setFontAlpha(alpha);
    }

    /**
     * 获取字体样式
     *
     * @return 返回字体样式
     */
    public FontStyle getFontStyle() {
        return this.fontConfiguration.getFontStyle();
    }

    /**
     * 设置字体样式
     *
     * @param style 样式
     */
    public void setFontStyle(FontStyle style) {
        this.fontConfiguration.setFontStyle(style);
        if (Objects.nonNull(style)) {
            if (style.isItalic()) {
                this.fontConfiguration.setFontSlope(Constants.DEFAULT_FONT_ITALIC_SLOPE);
            } else {
                this.fontConfiguration.setFontSlope(Constants.DEFAULT_FONT_SLOPE);
            }
        }
    }

    /**
     * 获取字体斜率
     *
     * @return 返回字体斜率
     */
    public Float getFontSlope() {
        return this.fontConfiguration.getFontSlope();
    }

    /**
     * 设置字体斜率（斜体字）
     *
     * @param slope 斜率
     */
    public void setFontSlope(float slope) {
        this.fontConfiguration.setFontSlope(slope);
    }

    /**
     * 获取字符间距
     *
     * @return 返回字符间距
     */
    public Float getCharacterSpacing() {
        return this.fontConfiguration.getCharacterSpacing();
    }

    /**
     * 设置字符间距
     *
     * @param spacing 间距
     */
    public void setCharacterSpacing(float spacing) {
        this.fontConfiguration.setCharacterSpacing(spacing);
    }

    /**
     * 获取行间距
     *
     * @return 返回行间距
     */
    public Float getLeading() {
        return this.fontConfiguration.getLeading();
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     */
    public void setLeading(float leading) {
        this.fontConfiguration.setLeading(leading);
    }

    /**
     * 获取总页数
     *
     * @return 返回总页数
     */
    public int getTotalPageNumber() {
        return Optional.ofNullable(this.totalPageNumber).orElse(this.context.getPageCount());
    }

    /**
     * 获取当前页面
     *
     * @return 返回页面
     */
    public Page getCurrentPage() {
        return this.getContext().getPage();
    }

    /**
     * 获取指定页面
     *
     * @param index 页面索引
     * @return 返回页面
     */
    public Page getPage(int index) {
        if (Objects.isNull(this.pages) || this.pages.size() <= index) {
            return null;
        }
        return this.pages.get(index);
    }

    /**
     * 获取目录列表
     *
     * @return 返回目录列表
     */
    public List<CatalogInfo> getCatalogs() {
        return this.getContext().getCatalogs();
    }

    /**
     * 加密（标准）
     */
    public void encryption() {
        this.encryption(false, PWLength.LENGTH_40, "", "");
    }

    /**
     * 加密（标准）
     *
     * @param preferAES     AES加密（密码长度为128位时生效）
     * @param length        密码长度（40位，128位，256位）
     * @param ownerPassword 拥有者密码
     * @param userPassword  用户密码
     */
    @SneakyThrows
    public void encryption(boolean preferAES, PWLength length, String ownerPassword, String userPassword) {
        // 初始化标准保护策略
        StandardProtectionPolicy policy = new StandardProtectionPolicy(ownerPassword, userPassword, this.getAccessPermission());
        // 设置AES加密
        policy.setPreferAES(preferAES);
        // 设置密钥长度
        policy.setEncryptionKeyLength(Optional.ofNullable(length).orElse(PWLength.LENGTH_40).getLength());
        // 设置文档权限
        this.getTarget().protect(policy);
    }

    /**
     * 加密（公钥）
     * <p>注：仅支持"X.509"</p>
     *
     * @param certificateInputStream 公钥证书数据流
     */
    @SneakyThrows
    public void encryption(InputStream certificateInputStream) {
        // 初始化公钥接收者
        PublicKeyRecipient recipient = new PublicKeyRecipient();
        // 设置访问权限
        recipient.setPermission(this.getAccessPermission());
        // 设置X509证书
        recipient.setX509((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(certificateInputStream));
        // 初始化公钥保护策略
        PublicKeyProtectionPolicy policy = new PublicKeyProtectionPolicy();
        // 设置接收者
        policy.addRecipient(recipient);
        // 设置文档权限
        this.getTarget().protect(policy);
    }

    /**
     * 解密
     * <p>注：需读取文档时，传入密码</p>
     */
    public void decrypt() {
        this.getTarget().setAllSecurityToBeRemoved(true);
    }

    /**
     * 插入页面
     *
     * @param index 页面索引
     * @param page  页面
     */
    public void insertPage(int index, Page page) {
        PageProcessor processor = new PageProcessor(this);
        processor.insert(index, page);
        processor.flush();
    }

    /**
     * 追加页面
     *
     * @param pages 页面
     */
    public void appendPage(Page... pages) {
        PageProcessor processor = new PageProcessor(this);
        for (Page page : pages) {
            processor.append(page);
        }
        processor.flush();
    }

    /**
     * 追加页面
     *
     * @param pages 页面
     */
    public void appendPage(List<Page> pages) {
        this.appendPage(pages.toArray(new Page[0]));
    }

    /**
     * 设置页面（替换）
     *
     * @param index 页面索引
     * @param page  页面
     */
    public void setPage(int index, Page page) {
        PageProcessor processor = new PageProcessor(this);
        processor.set(index, page);
        processor.flush();
    }

    /**
     * 获取字节数组
     *
     * @return 返回字节数组
     */
    @SneakyThrows
    public byte[] getBytes() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(8192)) {
            this.save(outputStream);
            return outputStream.toByteArray();
        }
    }

    /**
     * 获取临时文件
     *
     * @return 返回字节数组
     */
    @SneakyThrows
    public File getTempFile() {
        File temp = new File(Constants.TEMP_FILE_PATH, IdUtil.get() + ".pdf");
        this.save(temp);
        return temp;
    }

    /**
     * 保存文档
     *
     * @param file 文件
     */
    public void save(File file) {
        // 参数校验
        Objects.requireNonNull(file, "the file can not be null");
        this.save(file.getAbsolutePath());
    }

    /**
     * 保存关闭文档
     *
     * @param file 文件
     */
    public void saveAndClose(File file) {
        // 参数校验
        Objects.requireNonNull(file, "the file can not be null");
        this.saveAndClose(file.getAbsolutePath());
    }

    /**
     * 保存文档
     *
     * @param path 路径
     */
    @SneakyThrows
    public void save(String path) {
        // 参数校验
        Objects.requireNonNull(path, "the path can not be null");
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(path))))) {
            this.save(outputStream);
        }
    }

    /**
     * 保存关闭文档
     *
     * @param path 路径
     */
    @SneakyThrows
    public void saveAndClose(String path) {
        // 参数校验
        Objects.requireNonNull(path, "the path can not be null");
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(FileUtil.createDirectories(Paths.get(path))))) {
            this.saveAndClose(outputStream);
        }
    }

    /**
     * 保存文档
     *
     * @param outputStream 输出流
     */
    @SneakyThrows
    public void save(OutputStream outputStream) {
        // 参数校验
        Objects.requireNonNull(outputStream, "the output stream can not be null");
        // 获取页面数
        int number = this.getTarget().getNumberOfPages();
        // 检查页面
        if (number == 0) {
            log.error("the document has no page, please add a page before saving");
        }
        // 设置文档版本
        this.getTarget().setVersion(this.getVersion());
        // 刷新元数据
        if (this.getIsFlushMetadata()) {
            MetadataProcessor processor = new MetadataProcessor(this);
            processor.flush();
        }
        // 保存文档
        if (this.getIsLinearization()) {
            this.linearize(outputStream);
        } else {
            this.getTarget().save(outputStream, new CompressParameters(number));
        }
    }

    /**
     * 保存关闭文档
     *
     * @param outputStream 输出流
     */
    @SneakyThrows
    public void saveAndClose(OutputStream outputStream) {
        this.save(outputStream);
        this.close();
    }

    /**
     * 关闭文档
     */
    @Override
    public void close() {
        // 关闭页面
        this.getPages().forEach(Page::close);
        // 清理上下文
        this.getContext().clear();
        try {
            // 关闭任务文档
            this.getTarget().close();
        } catch (Exception e) {
            log.warn("an error occurred when the document was closed", e);
        }
    }

    /**
     * 初始化基础
     */
    public void initBase() {
        super.init(new Context(this));
        this.marginConfiguration = new MarginConfiguration();
        this.fontConfiguration = new FontConfiguration();
        this.initOtherParams();
    }

    /**
     * 初始化
     */
    protected void init() {
        this.init(MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 初始化
     */
    protected void init(MemoryPolicy policy) {
        // 初始化任务文档
        this.target = new PDDocument(policy.getSetting().streamCache);
        // 初始化版本
        this.target.getDocument().setVersion(Constants.DEFAULT_VERSION);
        // 初始化资源缓存
        this.target.setResourceCache(new DefaultResourceCache());
        // 初始化基础参数
        this.initBase();
        // 初始化页面
        this.initPages();
    }

    /**
     * 初始化
     *
     * @param file     文件
     * @param password 密码
     * @param keyStore 证书
     * @param alias    证书别名
     * @param policy   内存策略
     */
    @SneakyThrows
    protected void init(File file, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        // 检查参数
        Objects.requireNonNull(policy, "the policy can not be null");
        // 初始化任务文档
        this.target = Loader.loadPDF(file, password, keyStore, alias, policy.getSetting().streamCache);
        // 初始化加载
        this.initLoad();
    }

    /**
     * 初始化
     *
     * @param bytes    字节数组
     * @param password 密码
     * @param keyStore 证书
     * @param alias    证书别名
     * @param policy   内存策略
     */
    @SneakyThrows
    protected void init(byte[] bytes, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        this.initLoad(new RandomAccessReadBuffer(bytes), password, keyStore, alias, policy);
    }

    /**
     * 初始化
     *
     * @param inputStream 输入流
     * @param password    密码
     * @param keyStore    证书
     * @param alias       证书别名
     * @param policy      内存策略
     */
    @SneakyThrows
    protected void init(InputStream inputStream, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        this.initLoad(new RandomAccessReadBuffer(inputStream), password, keyStore, alias, policy);
    }

    /**
     * 初始化
     *
     * @param buffer   访问缓冲
     * @param password 密码
     * @param keyStore 证书
     * @param alias    证书别名
     * @param policy   内存策略
     */
    @SneakyThrows
    protected void initLoad(RandomAccessReadBuffer buffer, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        // 检查参数
        Objects.requireNonNull(policy, "the policy can not be null");
        // 初始化任务文档
        this.target = Loader.loadPDF(buffer, password, keyStore, alias, policy.getSetting().streamCache);
        // 初始化加载
        this.initLoad();
    }

    /**
     * 初始化加载
     */
    protected void initLoad() {
        // 初始化资源缓存
        this.target.setResourceCache(new DefaultResourceCache());
        // 初始化基础参数
        this.initBase();
        // 初始化页面
        this.initPages();
    }

    /**
     * 初始化页面列表
     */
    protected void initPages() {
        // 获取总页数
        int count = this.getTarget().getNumberOfPages();
        // 初始化页面列表
        this.pages = new ArrayList<>(this.getTarget().getNumberOfPages());
        // 获取页面树
        PDPageTree pageTree = this.getTarget().getPages();
        // // 遍历页面树
        for (int i = 0; i < count; i++) {
            // 添加页面
            this.pages.add(new Page(this, pageTree.get(i)));
        }
    }

    /**
     * 初始化其他参数
     */
    protected void initOtherParams() {
        // 添加字体缓存
        this.context.addFontCache(this.fontConfiguration.getFontName());
        // 初始化文档访问权限
        this.accessPermission = this.target.getCurrentAccessPermission();
        // 初始化文档版本
        this.version = this.target.getVersion();
        // 初始化是否刷新元数据
        this.isFlushMetadata = Boolean.TRUE;
        // 初始化是否线性化
        this.isLinearization = Boolean.FALSE;
        // 初始化背景颜色
        this.backgroundColor = Color.WHITE;
    }

    /**
     * 线性化
     *
     * @param outputStream 输出流
     */
    protected void linearize(OutputStream outputStream) {
        Linearizer linearizer = new Linearizer(this.getTarget());
        linearizer.linearize().write(outputStream);
        linearizer.close();
    }
}
