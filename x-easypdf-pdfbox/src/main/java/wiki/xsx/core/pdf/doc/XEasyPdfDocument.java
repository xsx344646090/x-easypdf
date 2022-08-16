package wiki.xsx.core.pdf.doc;

import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.AdobePDFSchema;
import org.apache.xmpbox.xml.XmpSerializer;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.image.XEasyPdfImage;
import wiki.xsx.core.pdf.footer.XEasyPdfFooter;
import wiki.xsx.core.pdf.handler.XEasyPdfHandler;
import wiki.xsx.core.pdf.header.XEasyPdfHeader;
import wiki.xsx.core.pdf.mark.XEasyPdfWatermark;
import wiki.xsx.core.pdf.util.XEasyPdfFileUtil;
import wiki.xsx.core.pdf.util.XEasyPdfTextUtil;

import javax.print.PrintServiceLookup;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.*;

/**
 * pdf文档
 *
 * @author xsx
 * @date 2020/3/3
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfDocument implements Closeable, Serializable {

    private static final long serialVersionUID = -4298644812517253946L;

    /**
     * 日志
     */
    private final Log log = LogFactory.getLog(XEasyPdfDocument.class);

    /**
     * pdf文档参数
     */
    private final XEasyPdfDocumentParam param = new XEasyPdfDocumentParam();

    /**
     * 无参构造
     */
    public XEasyPdfDocument() {
        // 设置模板文档
        this.param.setSource(new PDDocument());
    }

    /**
     * 有参构造
     *
     * @param filePath pdf文件路径
     */
    @SneakyThrows
    public XEasyPdfDocument(String filePath) {
        this(filePath, "");
    }

    /**
     * 有参构造
     *
     * @param filePath pdf文件路径
     * @param password 密码
     */
    @SneakyThrows
    public XEasyPdfDocument(String filePath, String password) {
        // 读取文件流
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            this.param.init(inputStream, password, this);
        }
    }

    /**
     * 有参构造
     *
     * @param filePath pdf文件路径
     * @param password 密码
     * @param keyStore 证书输入流
     * @param alias    证书别名
     */
    @SneakyThrows
    public XEasyPdfDocument(String filePath, String password, InputStream keyStore, String alias) {
        // 读取文件流
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            this.param.init(inputStream, password, keyStore, alias, this);
        }
    }

    /**
     * 有参构造
     *
     * @param inputStream 数据流
     */
    public XEasyPdfDocument(InputStream inputStream) {
        this.param.init(inputStream, "", this);
    }

    /**
     * 有参构造
     *
     * @param inputStream 数据流
     * @param password    密码
     */
    public XEasyPdfDocument(InputStream inputStream, String password) {
        this(inputStream, password, null, null);
    }

    /**
     * 有参构造
     *
     * @param inputStream 数据流
     * @param password    密码
     * @param keyStore    证书输入流
     * @param alias       证书别名
     */
    public XEasyPdfDocument(InputStream inputStream, String password, InputStream keyStore, String alias) {
        this.param.init(inputStream, password, keyStore, alias, this);
    }

    /**
     * 开启重置上下文
     *
     * @return 返回pdf文档
     */
    public XEasyPdfDocument enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 开启总页码占位符替换
     *
     * @return 返回pdf文档
     */
    public XEasyPdfDocument enableReplaceTotalPagePlaceholder() {
        this.param.setIsReplaceTotalPagePlaceholder(Boolean.TRUE);
        return this;
    }

    /**
     * 设置文档内容模式（每个页面都将设置该模式）
     *
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setGlobalContentMode(XEasyPdfComponent.ContentMode contentMode) {
        if (contentMode != null) {
            this.param.setContentMode(contentMode);
        }
        return this;
    }

    /**
     * 获取文档内容模式
     *
     * @return 返回文档内容模式
     */
    public XEasyPdfComponent.ContentMode getGlobalContentMode() {
        return this.param.getContentMode();
    }

    /**
     * 设置文档背景色（每个页面都将添加背景色）
     *
     * @param globalBackgroundColor 背景色
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setGlobalBackgroundColor(Color globalBackgroundColor) {
        // 如果背景色不为空，则设置
        if (globalBackgroundColor != null) {
            // 设置重置
            this.param.setIsReset(Boolean.TRUE);
            // 设置文档背景色
            this.param.setGlobalBackgroundColor(globalBackgroundColor);
        }
        return this;
    }

    /**
     * 获取文档背景色
     *
     * @return 返回文档背景色
     */
    public Color getGlobalBackgroundColor() {
        return this.param.getGlobalBackgroundColor();
    }

    /**
     * 设置文档背景图片（每个页面都将添加背景图片）
     *
     * @param globalBackgroundImage 背景图片
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setGlobalBackgroundImage(XEasyPdfImage globalBackgroundImage) {
        // 设置重置
        this.param.setIsReset(Boolean.TRUE);
        // 设置背景图片
        this.param.setGlobalBackgroundImage(globalBackgroundImage);
        return this;
    }

    /**
     * 获取文档背景图片
     *
     * @return 返回pdf图片
     */
    public XEasyPdfImage getGlobalBackgroundImage() {
        return this.param.getGlobalBackgroundImage();
    }

    /**
     * 设置文档水印（每个页面都将添加水印）
     *
     * @param globalWatermark 页面水印
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setGlobalWatermark(XEasyPdfWatermark globalWatermark) {
        // 设置重置
        this.param.setIsReset(Boolean.TRUE);
        // 设置文档水印
        this.param.setGlobalWatermark(globalWatermark);
        return this;
    }

    /**
     * 获取全局水印
     *
     * @return 返回pdf水印
     */
    public XEasyPdfWatermark getGlobalWatermark() {
        return this.param.getGlobalWatermark();
    }

    /**
     * 设置文档页眉（每个页面都将添加页眉）
     *
     * @param globalHeader 页眉
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setGlobalHeader(XEasyPdfHeader globalHeader) {
        // 设置重置
        this.param.setIsReset(Boolean.TRUE);
        // 设置文档页眉
        this.param.setGlobalHeader(globalHeader);
        return this;
    }

    /**
     * 获取全局页眉
     *
     * @return 返回pdf页眉
     */
    public XEasyPdfHeader getGlobalHeader() {
        return this.param.getGlobalHeader();
    }

    /**
     * 设置文档页脚（每个页面都将添加页脚）
     *
     * @param globalFooter 页脚
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setGlobalFooter(XEasyPdfFooter globalFooter) {
        // 设置重置
        this.param.setIsReset(Boolean.TRUE);
        // 设置文档页脚
        this.param.setGlobalFooter(globalFooter);
        return this;
    }

    /**
     * 获取全局页脚
     *
     * @return 返回pdf页脚
     */
    public XEasyPdfFooter getGlobalFooter() {
        return this.param.getGlobalFooter();
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setFontPath(String fontPath) {
        // 设置重置
        this.param.setIsReset(Boolean.TRUE);
        // 设置字体路径
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        // 设置重置
        this.param.setIsReset(Boolean.TRUE);
        // 设置字体样式
        this.param.setDefaultFontStyle(style);
        return this;
    }

    /**
     * 设置版本
     *
     * @param version 版本
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setVersion(float version) {
        // 最大版本
        float maxVersion = 1.7F;
        // 最小版本
        float minVersion = 1.0F;
        // 如果版本小于1.0且大于1.7，则提示错误
        if (version < minVersion || version > maxVersion) {
            // 提示错误
            throw new IllegalArgumentException("the version must be between 1.0 and 1.7");
        }
        // 设置版本
        this.param.setVersion(version);
        return this;
    }

    /**
     * 设置临时目录（用于flush操作），需读写权限
     * <p>默认在项目路径的根目录</p>
     * <p>eg：当前项目在“D:\test\pdf”目录下，临时文件存放目录则为“D:\”</p>
     *
     * @param tempDir 临时目录
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setTempDir(String tempDir) {
        // 如果路径不是目录，则提示错误信息
        if (!Files.isDirectory(Paths.get(tempDir))) {
            // 提示错误信息
            throw new IllegalArgumentException("the url must be directory");
        }
        // 设置临时目录
        this.param.setTempDir(tempDir);
        return this;
    }

    /**
     * 添加文档字体
     *
     * @param fontPath 字体路径
     * @param font     pdfbox字体
     */
    public void addFont(String fontPath, PDFont font) {
        // 添加字体缓存
        this.param.getFontCache().put(fontPath, font);
    }

    /**
     * 添加文档otf字体
     *
     * @param fontPath 字体路径
     * @param font     pdfbox字体
     */
    public void addOtfFont(String fontPath, PDFont font) {
        // 添加字体缓存
        this.param.getOtfFontCache().put(fontPath, font);
    }

    /**
     * 获取文档字体
     *
     * @return 返回pdfBox字体
     */
    public PDFont getFont() {
        return this.param.getFont();
    }

    /**
     * 获取文档字体
     *
     * @param fontPath 字体路径
     * @return 返回pdfbox字体
     */
    public PDFont getFont(String fontPath) {
        return this.param.getFontCache().get(fontPath);
    }

    /**
     * 获取文档otf字体
     *
     * @param fontPath 字体路径
     * @return 返回pdfbox字体
     */
    public PDFont getOtfFont(String fontPath) {
        return this.param.getOtfFontCache().get(fontPath);
    }

    /**
     * 获取文档字体路径
     *
     * @return 返回文档字体路径
     */
    public String getFontPath() {
        return this.param.getFontPath();
    }

    /**
     * 获取任务文档
     *
     * @return 返回任务文档
     */
    public PDDocument getTarget() {
        // 如果任务文档未初始化或文档被重置，则进行新任务创建
        if (this.param.getTarget() == null || this.param.getIsReset()) {
            // 初始化
            this.param.initTarget(this);
        }
        return this.param.getTarget();
    }

    /**
     * 获取pdf页面列表
     *
     * @return 返回pdf页面列表
     */
    public List<XEasyPdfPage> getPageList() {
        return this.param.getPageList();
    }

    /**
     * 获取文档总页数
     *
     * @return 返回文档总页数
     */
    public int getTotalPage() {
        return this.param.getTotalPage();
    }

    /**
     * 添加pdf页面
     *
     * @param pages pdf页面
     * @return 返回pdf文档
     */
    public XEasyPdfDocument addPage(XEasyPdfPage... pages) {
        // 设置重置
        this.param.setIsReset(Boolean.TRUE);
        // 添加页面
        Collections.addAll(this.param.getPageList(), pages);
        return this;
    }

    /**
     * 添加pdf页面
     *
     * @param pages pdf页面
     * @return 返回pdf文档
     */
    public XEasyPdfDocument addPage(List<XEasyPdfPage> pages) {
        if (pages != null) {
            return this.addPage(pages.toArray(new XEasyPdfPage[0]));
        }
        return this;
    }

    /**
     * 插入pdf页面
     *
     * @param pageIndex 页面索引
     * @param pages     pdf页面
     * @return 返回pdf文档
     */
    public XEasyPdfDocument insertPage(int pageIndex, XEasyPdfPage... pages) {
        // 设置重置
        this.param.setIsReset(Boolean.TRUE);
        // 获取pdf页面列表
        List<XEasyPdfPage> pageList = this.param.getPageList();
        // 如果pdf页面列表数量大于索引，则插入页面
        if (pageList.size() >= pageIndex) {
            // 遍历pdf页面
            for (XEasyPdfPage page : pages) {
                // 插入页面
                pageList.add(Math.max(pageIndex, 0), page);
            }
        }
        // 否则添加页面
        else {
            // 添加页面
            this.addPage(pages);
        }
        return this;
    }

    /**
     * 插入pdf页面
     *
     * @param pageIndex 页面索引
     * @param pages     pdf页面列表
     * @return 返回pdf文档
     */
    public XEasyPdfDocument insertPage(int pageIndex, List<XEasyPdfPage> pages) {
        if (pages != null) {
            return this.insertPage(pageIndex, pages.toArray(new XEasyPdfPage[0]));
        }
        return this;
    }

    /**
     * 修改页面尺寸
     *
     * @param pageSize  pdf页面尺寸
     * @param pageIndex 页面索引
     * @return 返回pdf文档
     */
    public XEasyPdfDocument modifyPageSize(XEasyPdfPageRectangle pageSize, int... pageIndex) {
        // 设置重置
        this.param.setIsReset(Boolean.TRUE);
        // 获取pdf页面列表
        List<XEasyPdfPage> pageList = this.param.getPageList();
        // 如果页面索引不为空，则根据给定索引设置，否则全部页面进行设置
        if (pageIndex != null && pageIndex.length > 0) {
            // 遍历页面索引
            for (int index : pageIndex) {
                // 修改页面尺寸
                pageList.get(index).modifyPageSize(pageSize);
            }
        }
        // 如果页面索引为空，则全部页面进行设置
        else {
            // 遍历pdf页面
            for (XEasyPdfPage xEasyPdfPage : pageList) {
                // 修改页面尺寸
                xEasyPdfPage.modifyPageSize(pageSize);
            }
        }
        return this;
    }

    /**
     * 移除pdf页面
     *
     * @param pageIndex 页面索引
     * @return 返回pdf文档
     */
    public XEasyPdfDocument removePage(int... pageIndex) {
        // 设置重置
        this.param.setIsReset(Boolean.TRUE);
        // 获取pdf页面列表
        List<XEasyPdfPage> pageList = this.param.getPageList();
        // 遍历页面索引
        for (int index : pageIndex) {
            // 获取对应pdf页面
            XEasyPdfPage page = pageList.get(index);
            // 初始化总页数
            this.param.initTotalPage(-(page.getPageList().size() + page.getNewPageList().size()));
            // 移除页面
            pageList.remove(index);
        }
        return this;
    }

    /**
     * 合并文档
     *
     * @param documents pdf文档
     * @return 返回pdf文档
     */
    public XEasyPdfDocument merge(XEasyPdfDocument... documents) {
        // 设置重置
        this.param.setIsReset(Boolean.TRUE);
        // 遍历待合并文档
        for (XEasyPdfDocument document : documents) {
            // 添加合并源pdf文档
            this.param.getMergeSourceList().add(document);
            // 添加pdf页面
            this.param.getPageList().addAll(document.getPageList());
        }
        return this;
    }

    /**
     * 获取文档信息
     *
     * @return 返回pdf文档信息
     */
    public XEasyPdfDocumentInfo information() {
        // 返回文档信息
        return this.param.getDocumentInfo() != null ? this.param.getDocumentInfo() : new XEasyPdfDocumentInfo(this);
    }

    /**
     * 获取文档权限
     *
     * @return 返回pdf文档权限
     */
    public XEasyPdfDocumentPermission permission() {
        // 返回文档权限
        return this.param.getPermission() != null ? this.param.getPermission() : new XEasyPdfDocumentPermission(this);
    }

    /**
     * 获取文档书签
     *
     * @return 返回pdf文档书签
     */
    public XEasyPdfDocumentBookmark bookmark() {
        // 返回文档书签
        return this.param.getBookmark() != null ? this.param.getBookmark() : new XEasyPdfDocumentBookmark(this);
    }

    /**
     * 获取文档替换器
     *
     * @return 返回pdf文档替换器
     */
    public XEasyPdfDocumentReplacer replacer() {
        return new XEasyPdfDocumentReplacer(this);
    }

    /**
     * 获取表单填充器
     *
     * @return 返回pdf文档表单填写器
     */
    public XEasyPdfDocumentFormFiller formFiller() {
        return new XEasyPdfDocumentFormFiller(this);
    }

    /**
     * 获取文档图像器
     *
     * @return 返回pdf文档图像器
     */
    public XEasyPdfDocumentImager imager() {
        return new XEasyPdfDocumentImager(this);
    }

    /**
     * 获取文档拆分器
     *
     * @return 返回pdf文档拆分器
     */
    public XEasyPdfDocumentSplitter splitter() {
        return new XEasyPdfDocumentSplitter(this);
    }

    /**
     * 获取文档提取器
     *
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor extractor() {
        return new XEasyPdfDocumentExtractor(this);
    }

    /**
     * 获取文档签名器
     *
     * @return 返回pdf文档签名器
     */
    public XEasyPdfDocumentSigner signer() {
        return new XEasyPdfDocumentSigner(this);
    }

    /**
     * 获取文档分析器
     *
     * @return 返回pdf文档分析器
     */
    public XEasyPdfDocumentAnalyzer analyzer() {
        return new XEasyPdfDocumentAnalyzer(this);
    }

    /**
     * 刷新（临时保存）
     *
     * @return 返回pdf文档
     */
    @SneakyThrows
    public XEasyPdfDocument flush() {
        // 构建文档
        PDDocument target = this.build();
        // 获取临时路径
        String tempPath = this.param.getTempUrl();
        // 临时保存文档
        target.save(new File(tempPath));
        // 添加临时任务列表
        this.param.getTempTargetList().add(tempPath);
        // 关闭文档
        this.close();
        // 打印日志
        if (log.isDebugEnabled()) {
            // 打印当前刷新次数
            log.debug("current count of flush：" + this.param.getTempTargetList().size());
        }
        return this;
    }

    /**
     * 保存（页面构建）
     *
     * @param outputPath 文件输出路径
     * @return 返回pdf文档
     */
    @SneakyThrows
    public XEasyPdfDocument save(String outputPath) {
        try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(XEasyPdfFileUtil.createDirectories(Paths.get(outputPath))))) {
            return this.save(outputStream);
        }
    }

    /**
     * 保存（页面构建）
     *
     * @param outputStream 文件输出流
     * @return 返回pdf文档
     */
    @SneakyThrows
    public XEasyPdfDocument save(OutputStream outputStream) {
        // 如果临时任务列表为空，则保存当前文档
        if (this.param.getTempTargetList().isEmpty()) {
            // 构建文档
            PDDocument target = this.build();
            // 替换总页码占位符
            this.replaceTotalPagePlaceholder(target, false);
            // 设置基础信息（文档信息、保护策略、版本、xmp信息及书签）
            this.setBasicInfo(target);
            // 保存文档
            target.save(outputStream);
            return this;
        }
        // 否则保存临时任务列表中的文档
        else {
            // 保存临时任务
            this.saveTemp(outputStream);
        }
        return this;
    }

    /**
     * 打印文档（默认打印机）
     *
     * @param count 打印数量
     */
    public XEasyPdfDocument print(int count) {
        return this.print(count, XEasyPdfPrintStyle.PORTRAIT, Scaling.ACTUAL_SIZE);
    }

    /**
     * 打印文档（默认打印机）
     *
     * @param count   打印数量
     * @param style   打印形式（横向、纵向、反向横向）
     * @param scaling 缩放比例
     */
    @SneakyThrows
    public XEasyPdfDocument print(int count, XEasyPdfPrintStyle style, Scaling scaling) {
        // 构建文档
        PDDocument document = this.build(true);
        // 创建打印选项
        PDFPrintable printable = new PDFPrintable(document, scaling);
        // 创建打印任务
        PDFPageable target = new PDFPageable(document);
        // 创建页面格式对象
        PageFormat pageFormat = new PageFormat();
        // 设置打印方向
        pageFormat.setOrientation(style.getOrientation());
        // 添加打印页面
        target.append(printable, pageFormat);
        // 获取打印任务
        PrinterJob job = PrinterJob.getPrinterJob();
        // 设置打印服务（默认）
        job.setPrintService(PrintServiceLookup.lookupDefaultPrintService());
        // 设置打印任务
        job.setPageable(target);
        // 设置打印数量
        job.setCopies(count);
        // 执行打印
        job.print();
        // 设置重置
        this.getParam().setIsReset(Boolean.TRUE);
        return this;
    }

    /**
     * 关闭文档
     */
    @SneakyThrows
    @Override
    public void close() {
        // 如果合并pdf源文档列表不为空，则进行关闭
        if (!this.param.getMergeSourceList().isEmpty()) {
            // 获取合并pdf源文档列表
            List<XEasyPdfDocument> mergeSourceList = this.param.getMergeSourceList();
            // 遍历合并pdf源文档列表
            for (XEasyPdfDocument mergeSource : mergeSourceList) {
                // 关闭源文档
                mergeSource.close();
            }
            // 清空合并pdf源文档列表
            mergeSourceList.clear();
        }
        // 如果源文档不为空，则关闭
        if (this.param.getSource() != null) {
            // 关闭源文档
            this.param.getSource().close();
        }
        // 如果任务文档不为空且非刷新，则关闭
        if (this.param.getTarget() != null) {
            // 关闭任务文档
            this.param.getTarget().close();
            // 清空字体
            this.param.getFontCache().clear();
            // 清空otf字体
            this.param.getOtfFontCache().clear();
        }
        // 重置字体为空
        this.param.setFont(null);
        // 重置任务文档为空
        this.param.setTarget(null);
        // 清空页面列表
        this.param.getPageList().clear();
    }

    /**
     * 构建文档
     *
     * @return 返回pdfbox文档
     */
    PDDocument build() {
        return this.build(false);
    }

    /**
     * 构建文档
     *
     * @param isReplaceTotalPagePlaceholder 是否替换总页码占位符
     * @return 返回pdfbox文档
     */
    PDDocument build(boolean isReplaceTotalPagePlaceholder) {
        // 获取任务文档
        PDDocument target = this.getTarget();
        // 初始化页面
        this.param.initPage(this);
        // 如果替换总页码占位符，则进行替换
        if (isReplaceTotalPagePlaceholder) {
            // 替换总页码占位符
            this.replaceTotalPagePlaceholder(target, false);
        }
        return target;
    }

    /**
     * 设置文档信息
     *
     * @param info pdf文档信息
     */
    void setInfo(XEasyPdfDocumentInfo info) {
        this.param.setDocumentInfo(info);
    }

    /**
     * 设置文档权限
     *
     * @param permission pdf文档权限
     */
    void setPermission(XEasyPdfDocumentPermission permission) {
        this.param.setPermission(permission);
    }

    /**
     * 设置文档书签
     *
     * @param bookmark pdf文档书签
     */
    void setBookmark(XEasyPdfDocumentBookmark bookmark) {
        this.param.setBookmark(bookmark);
    }

    /**
     * 设置基础信息（文档信息、保护策略、版本、xmp信息及书签）
     *
     * @param target 任务文档
     */
    @SneakyThrows
    void setBasicInfo(PDDocument target) {
        // 如果文档信息不为空，则进行设置
        if (this.param.getDocumentInfo() != null) {
            // 设置文档信息
            target.setDocumentInformation(this.param.getDocumentInfo().getInfo());
        }
        // 如果pdfBox保护策略不为空，则进行设置
        if (this.param.getPermission() != null) {
            // 设置pdfBox保护策略
            target.protect(this.param.getPermission().getPolicy());
        }
        // 设置版本
        target.setVersion(this.param.getVersion());
        // 初始化文档xmp信息
        this.initXMPMetadata(target);
        // 初始化文档书签
        this.initBookmark(target);
    }

    /**
     * 替换总页码占位符
     *
     * @param target 任务文档
     */
    void replaceTotalPagePlaceholder(PDDocument target, boolean isMultiDocument) {
        // 如果开启替换总页码占位符，则进行替换
        if (this.param.getIsReplaceTotalPagePlaceholder()) {
            // 获取文档总页码
            int totalPage = target.getNumberOfPages();
            // 如果页码大于0，则进行替换
            if (totalPage > 0) {
                // 创建pdf文本替换器
                XEasyPdfDocumentReplacer replacer = new XEasyPdfDocumentReplacer(this, target);
                // 开启替换cos数组
                replacer.enableReplaceCOSArray();
                // 定义待替换文本字典
                Map<String, String> replaceMap = new HashMap<>(1);
                // 设置替换文本
                replaceMap.put(XEasyPdfTextUtil.escapeForRegex(XEasyPdfHandler.Page.getTotalPagePlaceholder()), String.valueOf(totalPage));
                // 如果为多文档，则使用多文档替换方式
                if (isMultiDocument) {
                    // 替换总页码占位符
                    this.replaceTotalPagePlaceholder(target, replacer, replaceMap);
                }
                // 否则使用单文档替换方式
                else {
                    // 替换总页码占位符
                    this.replaceTotalPagePlaceholder(replacer, replaceMap, this.getPageList());
                }
                // 完成操作
                replacer.finish();
            }
        }
    }

    /**
     * 获取pdf文档参数
     *
     * @return 返回pdf文档参数
     */
    XEasyPdfDocumentParam getParam() {
        return this.param;
    }

    /**
     * 保存临时任务
     *
     * @param outputStream 文件输出流
     */
    @SneakyThrows
    private void saveTemp(OutputStream outputStream) {
        // 刷新
        this.flush();
        // 创建任务
        PDDocument target = new PDDocument();
        // 获取临时任务列表
        List<String> tempTargetList = this.param.getTempTargetList();
        // 创建临时pdfbox文档列表
        List<PDDocument> tempList = new ArrayList<>(tempTargetList.size());
        // 遍历临时任务
        for (String tempPath : tempTargetList) {
            // 创建输入流
            try (InputStream inputStream = Files.newInputStream(Paths.get(tempPath), StandardOpenOption.DELETE_ON_CLOSE)) {
                // 加载临时pdfbox文档
                PDDocument temp = PDDocument.load(inputStream);
                // 获取临时pdfbox页面树
                PDPageTree tempPageTree = temp.getPages();
                // 遍历临时页面树
                for (PDPage page : tempPageTree) {
                    // 添加页面至任务文档
                    PDPage importPage = target.importPage(page);
                    // 添加页面资源
                    importPage.setResources(page.getResources());
                }
                // 添加临时文档列表
                tempList.add(temp);
            }
        }
        // 替换总页码占位符
        this.replaceTotalPagePlaceholder(target, true);
        // 设置基础信息（文档信息、保护策略、版本、xmp信息及书签）
        this.setBasicInfo(target);
        // 保存任务文档
        target.save(outputStream);
        // 设置任务文档
        this.param.setTarget(target);
        // 遍历临时文档列表
        for (PDDocument temp : tempList) {
            // 关闭临时文档
            temp.close();
        }
    }

    /**
     * 初始化文档书签
     *
     * @param target 任务文档
     */
    @SneakyThrows
    private void initXMPMetadata(PDDocument target) {
        // 定义pdf元数据
        PDMetadata pdMetadata = new PDMetadata(target);
        // 创建xmp元数据
        XMPMetadata xmpMetadata = XMPMetadata.createXMPMetadata();
        // 创建adobe纲要
        AdobePDFSchema adobePdfSchema = xmpMetadata.createAndAddAdobePDFSchema();
        // 设置pdf版本
        adobePdfSchema.setPDFVersion(this.param.getVersion().toString());
        // 设置生产者
        adobePdfSchema.setProducer(this.param.getProducer());
        // 添加adobe纲要
        xmpMetadata.addSchema(adobePdfSchema);
        // 定义输出流
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 序列化xmp元数据
            new XmpSerializer().serialize(xmpMetadata, outputStream, true);
            // 导入xmp元数据
            pdMetadata.importXMPMetadata(outputStream.toByteArray());
        }
        // 设置元数据
        target.getDocumentCatalog().setMetadata(pdMetadata);
    }

    /**
     * 初始化文档书签
     *
     * @param target 任务文档
     */
    private void initBookmark(PDDocument target) {
        // 获取pdfbox书签
        PDDocumentOutline outline = target.getDocumentCatalog().getDocumentOutline();
        // 如果书签不为空，则设置书签信息
        if (this.param.getBookmark() != null) {
            // 如果书签为空，则创建书签
            if (outline == null) {
                // 创建书签
                outline = new PDDocumentOutline();
            }
            // 获取书签项
            List<PDOutlineItem> items = this.param.getBookmark().getBookMark();
            // 遍历书签项
            for (PDOutlineItem item : items) {
                // 添加书签项
                outline.addLast(item);
            }
        }
        // 设置书签
        target.getDocumentCatalog().setDocumentOutline(outline);
    }

    /**
     * 替换总页码占位符（多文档）
     *
     * @param target     任务文档
     * @param replacer   pdf文本替换器
     * @param replaceMap 待替换文本字典
     */
    private void replaceTotalPagePlaceholder(
            PDDocument target,
            XEasyPdfDocumentReplacer replacer,
            Map<String, String> replaceMap
    ) {
        // 获取pdfbox页面树
        PDPageTree pages = target.getPages();
        // 设置字体路径
        replacer.setFontPath(this.param.getFontPath());
        // 遍历页面树
        for (PDPage pdfboxPage : pages) {
            // 替换文本
            replacer.replaceText(pdfboxPage, replaceMap);
        }
    }

    /**
     * 替换总页码占位符（单文档）
     *
     * @param replacer   pdf文本替换器
     * @param replaceMap 待替换文本字典
     * @param pageList   待替换pdf页面列表
     */
    private void replaceTotalPagePlaceholder(
            XEasyPdfDocumentReplacer replacer,
            Map<String, String> replaceMap,
            List<XEasyPdfPage> pageList
    ) {
        // 遍历pdf页面列表
        for (XEasyPdfPage page : pageList) {
            // 获取pdf页眉
            XEasyPdfHeader header = page.getHeader();
            // 如果页眉不为空，则替换
            if (header != null) {
                // 替换总页码占位符
                this.replaceTotalPagePlaceholder(header.getTextFontPath(), replacer, replaceMap, page);
            }
            // 获取pdf页脚
            XEasyPdfFooter footer = page.getFooter();
            // 如果页脚不为空，则替换
            if (footer != null) {
                // 替换总页码占位符
                this.replaceTotalPagePlaceholder(footer.getTextFontPath(), replacer, replaceMap, page);
            }
        }
    }

    /**
     * 替换总页码占位符
     *
     * @param fontPath   字体路径
     * @param replacer   pdf文档替换器
     * @param replaceMap 代替换字典
     * @param page       pdf页面
     */
    private void replaceTotalPagePlaceholder(
            String fontPath,
            XEasyPdfDocumentReplacer replacer,
            Map<String, String> replaceMap,
            XEasyPdfPage page
    ) {
        // 如果字体路径不为空，则替换
        if (fontPath != null) {
            // 设置字体路径
            replacer.setFontPath(fontPath);
            // 获取原有pdfBox页面列表
            List<PDPage> pdfboxPageList = page.getPageList();
            // 遍历页面列表
            for (PDPage pdfboxPage : pdfboxPageList) {
                // 替换文本
                replacer.replaceText(pdfboxPage, replaceMap);
            }
            // 获取新增的pdfBox页面列表
            pdfboxPageList = page.getNewPageList();
            // 遍历页面列表
            for (PDPage pdfboxPage : pdfboxPageList) {
                // 替换文本
                replacer.replaceText(pdfboxPage, replaceMap);
            }
        }
    }
}
