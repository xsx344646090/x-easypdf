package org.dromara.pdf.pdfbox.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdfwriter.compress.CompressParameters;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.PublicKeyProtectionPolicy;
import org.apache.pdfbox.pdmodel.encryption.PublicKeyRecipient;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.AdobePDFSchema;
import org.apache.xmpbox.xml.XmpSerializer;
import org.dromara.pdf.pdfbox.core.enums.*;
import org.dromara.pdf.pdfbox.core.ext.processor.PageProcessor;
import org.dromara.pdf.pdfbox.core.info.CatalogInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.FileUtil;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
public class Document extends AbstractBaseFont implements Closeable {

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(Document.class);
    /**
     * 生产者
     */
    private static final String PRODUCER = Constants.PDFBOX_PRODUCER;
    /**
     * pdf访问权限
     */
    private AccessPermission accessPermission;
    /**
     * 文档信息
     */
    private PDDocumentInformation info;
    /**
     * 文档版本
     */
    private Float version;
    /**
     * 文档元数据
     */
    private PDMetadata metadata;
    /**
     * 任务文档
     */
    private PDDocument target;
    /**
     * 页面列表
     */
    private List<Page> pages;
    /**
     * 总页码
     */
    private Integer totalPageNumber;

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
     * 初始化
     */
    @Override
    public void init() {
        this.init(MemoryPolicy.setupMainMemoryOnly());
    }

    /**
     * 初始化
     */
    public void init(MemoryPolicy policy) {
        // 初始化任务文档
        this.target = new PDDocument(policy.getSetting().streamCache);
        // 初始化版本
        this.target.setVersion(Constants.DEFAULT_VERSION);
        // 初始化基础参数
        this.initBase();
        // 初始化页面
        this.initPages();
    }

    /**
     * 初始化基础
     */
    @Override
    public void initBase() {
        // 初始化上下文
        super.setContext(new Context(this));
        // 初始化字体参数
        this.initFontParams();
        // 初始化边框参数
        this.initBorderParams();
        // 初始化边距
        this.initMargin();
        // 初始化其他参数
        this.initOtherParams();
    }

    @SneakyThrows
    public void init(InputStream inputStream, String password, InputStream keyStore, String alias, MemoryPolicy policy) {
        // 检查参数
        Objects.requireNonNull(policy, "the policy can not be null");
        // 初始化任务文档
        this.target = Loader.loadPDF(new RandomAccessReadBuffer(inputStream), password, keyStore, alias, policy.getSetting().streamCache);
        // 初始化基础参数
        this.initBase();
        // 初始化页面
        this.initPages();
        // 重置上下文
        if (!this.pages.isEmpty()) {
            // 获取最新页面
            Page page = this.pages.get(this.pages.size() - 1);
            // 获取上下文
            Context context = this.getContext();
            // 设置页面
            context.setPage(page);
            // 设置是否分页
            context.setIsAlreadyPaging(Boolean.FALSE);
            // 重置光标
            context.getCursor().reset(page.getMarginLeft(), page.getHeight() - page.getMarginTop());
        }
    }

    /**
     * 设置字体名称
     *
     * @param fontName 字体名称
     */
    public void setFontName(String fontName) {
        super.setFontName(fontName);
        super.setFont(PdfHandler.getFontHandler().getPDFont(this.target, fontName, true));
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
     * 获取总页数
     *
     * @return 返回总页数
     */
    public int getTotalPageNumber() {
        return Optional.ofNullable(this.totalPageNumber).orElse(this.pages.size());
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
     * 创建页面
     *
     * @param rectangle 页面尺寸
     * @return 返回页面
     */
    public Page createPage(PageSize rectangle) {
        return new Page(Optional.ofNullable(this.getContext()).orElse(new Context(this)), rectangle);
    }

    /**
     * 创建页面
     *
     * @return 返回页面
     */
    public Page createPage() {
        return this.createPage(PageSize.A4);
    }

    /**
     * 插入页面
     *
     * @param index 页面索引
     * @param page  页面
     */
    public void insertPage(int index, Page page) {
        try (PageProcessor processor = new PageProcessor(this)) {
            processor.insert(index, page);
            processor.flush();
        }
    }

    /**
     * 追加页面
     *
     * @param pages 页面
     */
    public void appendPage(Page... pages) {
        try (PageProcessor processor = new PageProcessor(this)) {
            for (Page page : pages) {
                processor.append(page);
            }
            processor.flush();
        }
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
        try (PageProcessor processor = new PageProcessor(this)) {
            processor.set(index, page);
            processor.flush();
        }
    }

    /**
     * 刷新目录
     */
    public void flushCatalog() {
        // 获取目录
        List<CatalogInfo> catalogs = this.getContext().getCatalogs();
        // 目录不为空
        if (Objects.nonNull(catalogs) && !catalogs.isEmpty()) {
            // 获取页面字典
            Map<String, Page> pageMap = this.getPages().stream().collect(Collectors.toMap(Page::getId, Function.identity()));
            // 重置索引
            catalogs.forEach(catalog -> catalog.setPageIndex(pageMap.get(catalog.getPageId()).getIndex()));
        }
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
     * 保存文档
     *
     * @param path 路径
     */
    @SneakyThrows
    public void save(String path) {
        // 参数校验
        Objects.requireNonNull(path, "the path can not be null");
        try (OutputStream outputStream = Files.newOutputStream(FileUtil.createDirectories(Paths.get(path)))) {
            this.save(outputStream);
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
        // 检查页面
        if (this.getTarget().getNumberOfPages() == 0) {
            log.error("the document has no pages");
        }
        // 设置文档版本
        this.getTarget().setVersion(this.getVersion());
        // 设置文档信息
        this.getTarget().setDocumentInformation(this.getInfo());
        // 设置元数据
        this.getTarget().getDocumentCatalog().setMetadata(this.getMetadata());
        // 保存文档
        this.getTarget().save(outputStream, new CompressParameters(Integer.MAX_VALUE));
    }

    /**
     * 关闭文档
     */
    @Override
    public void close() {
        // 关闭页面
        this.getPages().forEach(Page::close);
        // 重置字体
        super.setFont(null);
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
     * 初始化页面列表
     */
    private void initPages() {
        // 获取总页数
        int count = this.getTarget().getNumberOfPages();
        // 初始化页面列表
        this.pages = new ArrayList<>(this.getTarget().getNumberOfPages());
        // 获取页面树
        PDPageTree pageTree = this.getTarget().getPages();
        // 遍历页面树
        for (int i = 0; i < count; i++) {
            // 添加页面
            this.pages.add(new Page(this.getContext(), pageTree.get(i)));
        }
    }

    /**
     * 初始化边框参数
     */
    private void initBorderParams() {
        // 初始化样式
        super.setBorderStyle(BorderStyle.SOLID);
        // 初始化线宽
        super.setBorderWidth(1F);
        // 初始化线长
        super.setBorderLineLength(1F);
        // 初始化间隔
        super.setBorderLineSpacing(1F);
        // 初始化上边框颜色
        super.setBorderTopColor(Color.BLACK);
        // 初始化下边框颜色
        super.setBorderBottomColor(Color.BLACK);
        // 初始化左边框颜色
        super.setBorderLeftColor(Color.BLACK);
        // 初始化右边框颜色
        super.setBorderRightColor(Color.BLACK);
        // 初始化是否上边框
        super.setIsBorderTop(Boolean.FALSE);
        // 初始化是否下边框
        super.setIsBorderBottom(Boolean.FALSE);
        // 初始化是否左边框
        super.setIsBorderLeft(Boolean.FALSE);
        // 初始化是否右边框
        super.setIsBorderRight(Boolean.FALSE);
    }

    /**
     * 初始化字体参数
     */
    private void initFontParams() {
        // 初始化字体
        super.setFont(PdfHandler.getFontHandler().getPDFont(this.target, Constants.DEFAULT_FONT_NAME, true));
        // 初始化字体大小
        super.setFontSize(12F);
        // 初始化字体颜色
        super.setFontColor(Color.BLACK);
        // 初始化字体样式
        super.setFontStyle(FontStyle.NORMAL);
        // 初始化字符间距
        super.setCharacterSpacing(0F);
        // 初始化行间距
        super.setLeading(0F);
        // 初始化换行高度
        super.getContext().setWrapHeight(this.getFontSize());
    }

    /**
     * 初始化其他参数
     */
    private void initOtherParams() {
        // 初始化水平对齐方式
        super.setHorizontalAlignment(HorizontalAlignment.LEFT);
        // 初始化垂直对齐方式
        super.setVerticalAlignment(VerticalAlignment.TOP);
        // 初始化内容模式
        super.setContentMode(ContentMode.APPEND);
        // 初始化是否重置内容流
        super.setIsResetContentStream(Boolean.FALSE);
        // 初始化背景颜色
        super.setBackgroundColor(Color.WHITE);
        // 初始化文档访问权限
        this.accessPermission = this.target.getCurrentAccessPermission();
        // 初始化文档信息
        this.info = this.target.getDocumentInformation();
        // 初始化文档版本
        this.version = this.target.getVersion();
        // 初始化文档元数据
        this.metadata = this.initXMPMetadata();
    }

    /**
     * 初始化文档元数据
     *
     * @return 返回文档元数据
     */
    @SneakyThrows
    private PDMetadata initXMPMetadata() {
        // 获取pdf元数据
        PDMetadata pdMetadata = this.getTarget().getDocumentCatalog().getMetadata();
        // pdf元数据不存在
        if (Objects.isNull(pdMetadata)) {
            // 创建pdf元数据
            pdMetadata = new PDMetadata(this.getTarget());
            // 创建xmp元数据
            XMPMetadata xmpMetadata = XMPMetadata.createXMPMetadata();
            // 创建adobe纲要
            AdobePDFSchema adobePdfSchema = xmpMetadata.createAndAddAdobePDFSchema();
            // 设置pdf版本
            adobePdfSchema.setPDFVersion(this.getVersion().toString());
            // 设置生产者
            adobePdfSchema.setProducer(PRODUCER);
            // 添加adobe纲要
            xmpMetadata.addSchema(adobePdfSchema);
            // 定义输出流
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                // 序列化xmp元数据
                new XmpSerializer().serialize(xmpMetadata, outputStream, true);
                // 导入xmp元数据
                pdMetadata.importXMPMetadata(outputStream.toByteArray());
            }
        }
        // 返回pdf元数据
        return pdMetadata;
    }
}
