package wiki.xsx.core.pdf.doc;

import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import wiki.xsx.core.pdf.component.image.XEasyPdfImage;
import wiki.xsx.core.pdf.footer.XEasyPdfFooter;
import wiki.xsx.core.pdf.header.XEasyPdfHeader;
import wiki.xsx.core.pdf.mark.XEasyPdfWatermark;
import wiki.xsx.core.pdf.util.XEasyPdfFileUtil;

import javax.print.PrintServiceLookup;
import java.awt.*;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * pdf文档
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
public class XEasyPdfDocument implements Closeable {

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
     * @param filePath pdf文件路径
     */
    @SneakyThrows
    public XEasyPdfDocument(String filePath) {
        // 读取文件流
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            this.init(inputStream);
        }
    }

    /**
     * 有参构造
     * @param inputStream 数据流
     */
    public XEasyPdfDocument(InputStream inputStream) {
        this.init(inputStream);
    }

    /**
     * 初始化
     * @param inputStream 数据流
     */
    @SneakyThrows
    private void init(InputStream inputStream) {
        // 读取pdfBox文档
        this.param.setSource(PDDocument.load(inputStream, MemoryUsageSetting.setupTempFileOnly()));
        // 获取pdfBox页面树
        PDPageTree pages = this.param.getSource().getPages();
        // 遍历pdfBox页面树
        for (PDPage page : pages) {
            // 添加pdfBox页面
            this.param.getPageList().add(new XEasyPdfPage(page));
        }
        // 设置总页数
        this.param.initTotalPage(pages.getCount());
        // 初始化文档信息
        this.param.initInfo(this);
    }

    /**
     * 获取文档信息
     * @return 返回pdf文档信息
     */
    public XEasyPdfDocumentInfo information() {
        // 设置重置
        this.param.setReset(true);
        // 返回文档信息
        return this.param.getDocumentInfo()!=null?this.param.getDocumentInfo():new XEasyPdfDocumentInfo(this);
    }

    /**
     * 获取文档权限
     * @return 返回pdf文档权限
     */
    public XEasyPdfDocumentPermission permission() {
        // 设置重置
        this.param.setReset(true);
        // 返回文档权限
        return this.param.getPermission()!=null?this.param.getPermission():new XEasyPdfDocumentPermission(this);
    }

    /**
     * 获取文档书签
     * @return 返回pdf文档书签
     */
    public XEasyPdfDocumentBookmark bookmark() {
        // 设置重置
        this.param.setReset(true);
        // 返回文档书签
        return this.param.getBookmark()!=null?this.param.getBookmark():new XEasyPdfDocumentBookmark(this);
    }

    /**
     * 设置文档背景色
     * @param backgroundColor 背景色
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setGlobalBackgroundColor(Color backgroundColor) {
        // 设置重置
        this.param.setReset(true);
        // 设置文档背景色
        this.param.setBackgroundColor(backgroundColor);
        return this;
    }

    /**
     * 获取文档背景色
     * @return 返回文档背景色
     */
    public Color getGlobalBackgroundColor() {
        return this.param.getBackgroundColor();
    }

    /**
     * 设置文档背景图片（每个页面都将添加背景图片）
     * @param globalBackgroundImage 背景图片
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setGlobalBackgroundImage(XEasyPdfImage globalBackgroundImage) {
        // 设置重置
        this.param.setReset(true);
        // 设置背景图片
        this.param.setGlobalBackgroundImage(globalBackgroundImage);
        return this;
    }

    /**
     * 获取文档背景图片
     * @return 返回pdf图片
     */
    public XEasyPdfImage getGlobalBackgroundImage() {
        return this.param.getGlobalBackgroundImage();
    }

    /**
     * 设置文档水印（每个页面都将添加水印）
     * @param globalWatermark 页面水印
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setGlobalWatermark(XEasyPdfWatermark globalWatermark) {
        // 设置重置
        this.param.setReset(true);
        // 设置文档水印
        this.param.setGlobalWatermark(globalWatermark);
        return this;
    }

    /**
     * 获取全局水印
     * @return 返回pdf水印
     */
    public XEasyPdfWatermark getGlobalWatermark() {
        return this.param.getGlobalWatermark();
    }

    /**
     * 设置文档页眉（每个页面都将添加页眉）
     * @param globalHeader 页眉
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setGlobalHeader(XEasyPdfHeader globalHeader) {
        // 设置重置
        this.param.setReset(true);
        // 设置文档页眉
        this.param.setGlobalHeader(globalHeader);
        return this;
    }

    /**
     * 获取全局页眉
     * @return 返回pdf页眉
     */
    public XEasyPdfHeader getGlobalHeader() {
        return this.param.getGlobalHeader();
    }

    /**
     * 设置文档页脚（每个页面都将添加页脚）
     * @param globalFooter 页脚
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setGlobalFooter(XEasyPdfFooter globalFooter) {
        // 设置重置
        this.param.setReset(true);
        // 设置文档页脚
        this.param.setGlobalFooter(globalFooter);
        return this;
    }

    /**
     * 获取全局页脚
     * @return 返回pdf页脚
     */
    public XEasyPdfFooter getGlobalFooter() {
        return this.param.getGlobalFooter();
    }

    /**
     * 设置字体路径
     * @param fontPath 字体路径
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setFontPath(String fontPath) {
        // 设置重置
        this.param.setReset(true);
        // 设置字体路径
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     * @param style 默认字体样式
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        // 设置重置
        this.param.setReset(true);
        // 设置字体样式
        this.param.setDefaultFontStyle(style);
        return this;
    }

    /**
     * 设置版本
     * @param version 版本
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setVersion(float version) {
        // 如果版本小于1.0且大于1.7，则提示错误
        if (version<1.0F&&version>1.7F) {
            // 提示错误
            throw new IllegalArgumentException("the version must be between 1.0 and 1.7");
        }
        // 设置重置
        this.param.setReset(true);
        // 设置版本
        this.param.setVersion(version);
        return this;
    }

    /**
     * 设置临时目录（用于flush操作），需读写权限
     * <p>默认在项目路径的根目录</p>
     * <p>eg：当前项目在“D:\test\pdf”目录下，临时文件存放目录则为“D:\”</p>
     * @param tempDir 临时目录
     * @return 返回pdf文档
     */
    public XEasyPdfDocument setTempDir(String tempDir) {
        if (!Files.isDirectory(Paths.get(tempDir))) {
            throw new IllegalArgumentException("the url must be directory");
        }
        this.param.setTempDir(tempDir);
        return this;
    }

    /**
     * 添加文档字体
     * @param fontPath 字体路径
     * @param font pdfbox字体
     */
    public void addFont(String fontPath, PDFont font) {
        // 添加字体缓存
        this.param.getFontCache().put(fontPath, font);
    }

    /**
     * 获取文档字体
     * @return 返回pdfBox字体
     */
    public PDFont getFont() {
        return this.param.getFont();
    }

    /**
     * 获取文档字体
     * @param fontPath 字体路径
     * @return 返回pdfbox字体
     */
    public PDFont getFont(String fontPath) {
        return this.param.getFontCache().get(fontPath);
    }

    /**
     * 获取任务文档
     * @return 返回任务文档
     */
    public PDDocument getTarget() {
        // 如果任务文档未初始化或文档被重置，则进行新任务创建
        if (this.param.getTarget()==null||this.param.isReset()) {
            // 初始化
            this.param.init(this);
        }
        return this.param.getTarget();
    }

    /**
     * 获取pdf页面列表
     * @return 返回pdf页面列表
     */
    public List<XEasyPdfPage> getPageList() {
        return this.param.getPageList();
    }

    /**
     * 获取文档总页数
     * @return 返回文档总页数
     */
    public int getTotalPage() {
        return this.param.getTotalPage();
    }

    /**
     * 添加pdf页面
     * @param pages pdf页面
     * @return 返回pdf文档
     */
    public XEasyPdfDocument addPage(XEasyPdfPage...pages) {
        // 设置重置
        this.param.setReset(true);
        // 添加页面
        Collections.addAll(this.param.getPageList(), pages);
        return this;
    }

    /**
     * 添加pdf页面
     * @param pages pdf页面
     * @return 返回pdf文档
     */
    public XEasyPdfDocument addPage(List<XEasyPdfPage> pages) {
        if (pages!=null) {
            return this.addPage(pages.toArray(new XEasyPdfPage[0]));
        }
        return this;
    }

    /**
     * 插入pdf页面
     * @param pageIndex 页面索引
     * @param pages pdf页面
     * @return 返回pdf文档
     */
    public XEasyPdfDocument insertPage(int pageIndex, XEasyPdfPage...pages) {
        // 设置重置
        this.param.setReset(true);
        // 获取pdf页面列表
        List<XEasyPdfPage> pageList = this.param.getPageList();
        // 如果pdf页面列表数量大于索引，则插入页面，否则添加页面
        if (pageList.size()>=pageIndex) {
            // 遍历pdf页面
            for (XEasyPdfPage page : pages) {
                // 插入页面
                pageList.add(Math.max(pageIndex, 0), page);
            }
        }else {
            // 添加页面
            this.addPage(pages);
        }
        return this;
    }

    /**
     * 插入pdf页面
     * @param pageIndex 页面索引
     * @param pages pdf页面列表
     * @return 返回pdf文档
     */
    public XEasyPdfDocument insertPage(int pageIndex, List<XEasyPdfPage> pages) {
        if (pages!=null) {
            return this.insertPage(pageIndex, pages.toArray(new XEasyPdfPage[0]));
        }
        return this;
    }

    /**
     * 修改页面尺寸
     * @param pageSize pdfbox页面尺寸
     * @param pageIndex 页面索引
     * @return 返回pdf文档
     */
    public XEasyPdfDocument modifyPageSize(PDRectangle pageSize, int ...pageIndex) {
        // 设置重置
        this.param.setReset(true);
        // 获取pdf页面列表
        List<XEasyPdfPage> pageList = this.param.getPageList();
        // 如果页面索引不为空，则根据给定索引设置，否则全部页面进行设置
        if (pageIndex!=null&&pageIndex.length>0) {
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
     * @param pageIndex 页面索引
     * @return 返回pdf文档
     */
    public XEasyPdfDocument removePage(int ...pageIndex) {
        // 设置重置
        this.param.setReset(true);
        // 获取pdf页面列表
        List<XEasyPdfPage> pageList = this.param.getPageList();
        // 遍历页面索引
        for (int index : pageIndex) {
            // 获取对应pdf页面
            XEasyPdfPage page = pageList.get(index);
            // 初始化总页数
            this.param.initTotalPage(-(page.getParam().getPageList().size()+page.getParam().getNewPageList().size()));
            // 移除页面
            pageList.remove(index);
        }
        return this;
    }

    /**
     * 合并文档
     * @param documents pdf文档
     * @return 返回pdf文档
     */
    public XEasyPdfDocument merge(XEasyPdfDocument ...documents) {
        // 设置重置
        this.param.setReset(true);
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
     * 文档替换器
     * @return 返回pdf文档替换器
     */
    public XEasyPdfDocumentReplacer replacer() {
        return new XEasyPdfDocumentReplacer(this);
    }

    /**
     * 表单填充器
     * @return 返回pdf文档表单填写器
     */
    public XEasyPdfDocumentFormFiller formFiller() {
        return new XEasyPdfDocumentFormFiller(this);
    }

    /**
     * 文档图像器
     * @return 返回pdf文档图像器
     */
    public XEasyPdfDocumentImager imager() {
        return new XEasyPdfDocumentImager(this);
    }

    /**
     * 文档拆分器
     * @return 返回pdf文档拆分器
     */
    public XEasyPdfDocumentSplitter splitter() {
        return new XEasyPdfDocumentSplitter(this);
    }

    /**
     * 文档提取器
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor extractor() {
        return new XEasyPdfDocumentExtractor(this);
    }

    /**
     * 文档签名器
     * @return 返回pdf文档签名器
     */
    public XEasyPdfDocumentSigner signer() {
        return new XEasyPdfDocumentSigner(this);
    }

    /**
     * 构建文档
     * @return 返回pdfbox文档
     */
    public PDDocument build() {
        // 获取任务文档
        PDDocument target = this.getTarget();
        // 初始化页面
        this.param.initPage(this);
        // 设置文档信息、保护策略及书签
        this.setInfoAndPolicyAndBookmark(target);
        return target;
    }

    /**
     * 刷新（临时保存）
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
        if (log.isInfoEnabled()) {
            // 打印当前刷新次数
            log.info("current count of flush：" + this.param.getTempTargetList().size());
        }
        return this;
    }

    /**
     * 保存（页面构建）
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
     * @param outputStream 文件输出流
     * @return 返回pdf文档
     */
    @SneakyThrows
    public XEasyPdfDocument save(OutputStream outputStream) {
        // 如果临时任务列表为空，则保存当前文档
        if (this.param.getTempTargetList().isEmpty()) {
            // 构建文档
            PDDocument target = this.build();
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
     * @param count 打印数量
     */
    public XEasyPdfDocument print(int count) {
        return this.print(count, XEasyPdfPrintStyle.PORTRAIT, Scaling.ACTUAL_SIZE);
    }

    /**
     * 打印文档（默认打印机）
     * @param count 打印数量
     * @param style 打印形式（横向、纵向、反向横向）
     * @param scaling 缩放比例
     */
    @SneakyThrows
    public XEasyPdfDocument print(int count, XEasyPdfPrintStyle style, Scaling scaling) {
        // 获取打印任务
        PrinterJob job = PrinterJob.getPrinterJob();
        // 设置打印服务（默认）
        job.setPrintService(PrintServiceLookup.lookupDefaultPrintService());
        // 创建打印任务
        Book book = new Book();
        // 创建页面格式对象
        PageFormat pageFormat = new PageFormat();
        // 设置打印方向
        pageFormat.setOrientation(style.getOrientation());
        // 设置打印纸张
        book.append(new PDFPrintable(this.getTarget(), scaling), pageFormat);
        // 设置打印任务
        job.setPageable(book);
        // 设置打印数量
        job.setCopies(count);
        // 执行打印
        job.print();
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
        if (this.param.getSource()!=null) {
            // 关闭源文档
            this.param.getSource().close();
        }
        // 如果任务文档不为空且非刷新，则关闭
        if (this.param.getTarget()!=null) {
            // 关闭任务文档
            this.param.getTarget().close();
            // 清空字体
            this.param.getFontCache().clear();
        }
        // 重置字体为空
        this.param.setFont(null);
        // 重置任务文档为空
        this.param.setTarget(null);
        // 清空页面列表
        this.param.getPageList().clear();
    }

    /**
     * 设置文档信息
     * @param info pdf文档信息
     */
    void setInfo(XEasyPdfDocumentInfo info) {
        this.param.setDocumentInfo(info);
    }

    /**
     * 设置文档权限
     * @param permission pdf文档权限
     */
    void setPermission(XEasyPdfDocumentPermission permission) {
        this.param.setPermission(permission);
    }

    /**
     * 设置文档书签
     * @param bookmark pdf文档书签
     */
    void setBookmark(XEasyPdfDocumentBookmark bookmark) {
        this.param.setBookmark(bookmark);
    }

    /**
     * 设置文档信息、保护策略及书签
     * @param target 任务文档
     */
    @SneakyThrows
    void setInfoAndPolicyAndBookmark(PDDocument target) {
        // 如果文档信息不为空，则进行设置
        if (this.param.getDocumentInfo()!=null) {
            // 设置文档信息
            target.setDocumentInformation(this.param.getDocumentInfo().getInfo());
        }
        // 如果pdfBox保护策略不为空，则进行设置
        if (this.param.getPermission()!=null) {
            // 设置pdfBox保护策略
            target.protect(this.param.getPermission().getPolicy());
        }
        // 初始化文档书签
        this.initBookmark(target);
    }

    /**
     * 初始化文档书签
     * @param target 任务文档
     */
    void initBookmark(PDDocument target) {
        // 获取pdfbox书签
        PDDocumentOutline outline = target.getDocumentCatalog().getDocumentOutline();
        // 如果书签不为空，则设置书签信息
        if (this.param.getBookmark()!=null) {
            // 如果书签为空，则创建书签
            if (outline==null) {
                // 创建书签
                outline = new PDDocumentOutline();
            }
            // 获取书签项
            List<PDOutlineItem> items = this.param.getBookmark().getItemList();
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
     * 获取pdf文档参数
     * @return 返回pdf文档参数
     */
    XEasyPdfDocumentParam getParam() {
        return this.param;
    }

    /**
     * 保存临时任务
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
            // 读取临时文件
            File file = new File(tempPath);
            // 加载临时pdfbox文档
            PDDocument temp = PDDocument.load(file, MemoryUsageSetting.setupTempFileOnly());
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
            // 删除临时文件
            file.deleteOnExit();
        }
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
}
