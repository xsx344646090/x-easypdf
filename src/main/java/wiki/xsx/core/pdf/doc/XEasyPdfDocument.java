package wiki.xsx.core.pdf.doc;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.apache.pdfbox.rendering.PDFRenderer;
import wiki.xsx.core.pdf.component.footer.XEasyPdfFooter;
import wiki.xsx.core.pdf.component.header.XEasyPdfHeader;
import wiki.xsx.core.pdf.component.image.XEasyPdfImage;
import wiki.xsx.core.pdf.component.mark.XEasyPdfWatermark;
import wiki.xsx.core.pdf.page.XEasyPdfPage;
import wiki.xsx.core.pdf.page.XEasyPdfPageParam;

import javax.imageio.ImageIO;
import javax.print.PrintServiceLookup;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * pdf文档
 * @author xsx
 * @date 2020/3/3
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
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
public class XEasyPdfDocument {

    /**
     * pdf文档参数
     */
    private final XEasyPdfDocumentParam param = new XEasyPdfDocumentParam();

    /**
     * 无参构造
     */
    public XEasyPdfDocument() {
        this.param.setSource(new PDDocument());
    }

    /**
     * 有参构造
     * @param filePath pdf文件路径
     * @throws IOException IO异常
     */
    public XEasyPdfDocument(String filePath) throws IOException {
        // 读取文件流
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            // 读取pdfBox文档
            this.param.setSource(PDDocument.load(inputStream));
            // 获取pdfBox页面树
            PDPageTree pages = this.param.getSource().getPages();
            // 遍历pdfBox页面树
            for (PDPage page : pages) {
                // 添加pdfBox页面
                this.param.getPageList().add(new XEasyPdfPage(page));
            }
            // 初始化文档信息
            this.param.initInfo(this);
        }
    }

    /**
     * 有参构造
     * @param inputStream 数据流
     * @throws IOException IO异常
     */
    public XEasyPdfDocument(InputStream inputStream) throws IOException {
        // 读取pdfBox文档
        this.param.setSource(PDDocument.load(inputStream));
        // 获取pdfBox页面树
        PDPageTree pages = this.param.getSource().getPages();
        // 遍历pdfBox页面树
        for (PDPage page : pages) {
            // 添加pdfBox页面
            this.param.getPageList().add(new XEasyPdfPage(page));
        }
        // 初始化文档信息
        this.param.initInfo(this);
    }

    /**
     * 设置文档信息
     * @return 返回pdf文档
     */
    public XEasyPdfDocumentInfo information() {
        // 设置重置
        this.param.setReset(true);
        return this.param.getDocumentInfo()!=null?this.param.getDocumentInfo():new XEasyPdfDocumentInfo(this);
    }

    /**
     * 设置文档权限
     * @return 返回pdf文档
     */
    public XEasyPdfDocumentPermission permission() {
        // 设置重置
        this.param.setReset(true);
        // 返回文档权限
        return this.param.getPermission()!=null?this.param.getPermission():new XEasyPdfDocumentPermission(this);
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
     * 获取文档字体
     * @return 返回pdfBox字体
     */
    public PDFont getFont() {
        return this.param.getFont();
    }

    /**
     * 获取文档总页数
     * @return 返回文档总页数
     */
    public int getTotalPage() {
        // 定义总页数
        int total = 0;
        // 定义页面参数
        XEasyPdfPageParam pageParam;
        // 获取pdf页面列表
        List<XEasyPdfPage> pageList = this.getPageList();
        // 遍历pdf页面列表
        for (XEasyPdfPage xEasyPdfPage : pageList) {
            // 获取页面参数
            pageParam = xEasyPdfPage.getParam();
            // 总页数 = 包含的pdfBox页面列表数量 + 新增的pdfBox页面列表数量
            total += pageParam.getPageList().size() + pageParam.getNewPageList().size();
        }
        return total;
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
     * 插入pdf页面
     * @param index 页面索引
     * @param pages pdf页面
     * @return 返回pdf文档
     */
    public XEasyPdfDocument insertPage(int index, XEasyPdfPage...pages) {
        // 设置重置
        this.param.setReset(true);
        // 获取pdf页面列表
        List<XEasyPdfPage> pageList = this.param.getPageList();
        // 如果pdf页面列表数量大于索引，则插入页面，否则添加页面
        if (pageList.size()>=index) {
            // 遍历pdf页面
            for (XEasyPdfPage page : pages) {
                // 插入页面
                pageList.add(Math.max(index, 0), page);
            }
        }else {
            // 添加页面
            this.addPage(pages);
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
            // 移除页面
            pageList.remove(index);
        }
        return this;
    }

    /**
     * 填充表单
     * @param formMap 表单字典
     * @return 返回pdf文档
     */
    public XEasyPdfDocument fillForm(Map<String, String> formMap) {
        // 设置重置
        this.param.setReset(true);
        // 设置表单字典
        this.param.setFormMap(formMap);
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
            // 添加pdf页面
            this.param.getPageList().addAll(document.getPageList());
        }
        return this;
    }

    /**
     * 转为图片（整个文档）
     * @param outputPath 输出路径（目录）
     * @param imageType 图片类型
     * @return 返回pdf文档
     * @throws IOException IO异常
     */
    public XEasyPdfDocument image(String outputPath, String imageType) throws IOException {
        return this.image(outputPath, imageType, null);
    }

    /**
     * 转为图片（整个文档）
     * @param outputPath 输出路径（目录）
     * @param imageType 图片类型
     * @param prefix 图片名称前缀
     * @return 返回pdf文档
     * @throws IOException IO异常
     */
    public XEasyPdfDocument image(String outputPath, String imageType, String prefix) throws IOException {
        // 如果文档名称前缀为空，则设置默认值为"x-easypdf"
        if (prefix==null) {
            // 初始化文档名称前缀
            prefix = "x-easypdf";
        }
        // 获取任务文档
        PDDocument target = this.createTarget();
        // 文件名称构造器
        StringBuilder fileNameBuilder;
        // 任务文档页面总数
        int pageCount = target.getNumberOfPages();
        // 遍历文档页面
        for (int i = 0; i < pageCount; i++) {
            // 新建文件名称构造器
            fileNameBuilder = new StringBuilder();
            // 构建文件名称
            fileNameBuilder.append(outputPath).append(File.separator).append(prefix).append(i + 1).append('.').append(imageType);
            // 获取输出流
            try (OutputStream outputStream = Files.newOutputStream(Paths.get(fileNameBuilder.toString()))) {
                // 初始化pdfBox文档渲染器
                PDFRenderer renderer = new PDFRenderer(target);
                // 渲染图片
                BufferedImage bufferedImage = renderer.renderImage(i);
                // 写出图片
                ImageIO.write(bufferedImage, imageType, outputStream);
            }
        }
        return this;
    }

    /**
     * 转为图片（根据页面索引）
     * @param outputStream 输出流
     * @param imageType 图片类型
     * @param pageIndex 页面索引
     * @return 返回pdf文档
     * @throws IOException IO异常
     */
    public XEasyPdfDocument image(OutputStream outputStream, String imageType, int pageIndex) throws IOException {
        // 获取任务文档
        PDDocument target = this.createTarget();
        // 重置页面索引（0至文档总页面索引）
        pageIndex = Math.min(Math.max(pageIndex, 0), target.getNumberOfPages()-1);
        // 初始化pdfBox文档渲染器
        PDFRenderer renderer = new PDFRenderer(target);
        // 渲染图片
        BufferedImage bufferedImage = renderer.renderImage(pageIndex);
        // 写出图片
        ImageIO.write(bufferedImage, imageType, outputStream);
        return this;
    }

    /**
     * 文档拆分器
     * @return 返回pdf文档
     */
    public XEasyPdfDocumentSplitter splitter() {
        return new XEasyPdfDocumentSplitter(this);
    }

    /**
     * 提取文本器
     * @return 返回pdf文档提取器
     */
    public XEasyPdfDocumentExtractor extractor() {
        return new XEasyPdfDocumentExtractor(this);
    }

    /**
     * 保存（页面构建）
     * @param outputPath 文件输出路径
     * @throws IOException IO异常
     */
    public void save(String outputPath) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(Paths.get(outputPath))) {
            this.save(outputStream);
        }
    }

    /**
     * 保存（页面构建）
     * @param outputStream 文件输出流
     * @throws IOException IO异常
     */
    public void save(OutputStream outputStream) throws IOException {
        try  {
            // 创建任务文档
            PDDocument target = this.createTarget();
            // 设置文档信息及保护策略
            this.setInfoAndPolicy(target);
            // 保存任务文档
            target.save(outputStream);
        } finally {
            // 关闭pdfBox文档
            this.close();
        }
    }

    /**
     * 打印文档（默认打印机）
     * @param count 打印数量
     * @throws IOException IO异常
     * @throws PrinterException 打印异常
     */
    public void print(int count) throws IOException, PrinterException {
        this.print(count, XEasyPdfPrintStyle.PORTRAIT, Scaling.ACTUAL_SIZE);
    }

    /**
     * 打印文档（默认打印机）
     * @param count 打印数量
     * @param style 打印形式（横向、纵向、反向横向）
     * @param scaling 缩放比例
     * @throws IOException IO异常
     * @throws PrinterException 打印异常
     */
    public void print(int count, XEasyPdfPrintStyle style, Scaling scaling) throws IOException, PrinterException {
        try {
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
            book.append(new PDFPrintable(this.createTarget(), scaling), pageFormat);
            // 设置打印任务
            job.setPageable(book);
            // 设置打印数量
            job.setCopies(count);
            // 执行打印
            job.print();
        }finally {
            // 关闭pdfBox文档
            this.close();
        }
    }

    /**
     * 关闭文档
     * @throws IOException IO异常
     */
    public void close() throws IOException {
        // 如果源文档不为空，则关闭
        if (this.param.getSource()!=null) {
            // 如果任务文档不为空，则关闭
            if (this.param.getTarget()!=null) {
                // 关闭任务文档
                this.param.getTarget().close();
            }
            // 关闭源文档
            this.param.getSource().close();
        }
    }

    /**
     * 获取任务文档
     * @return 返回任务文档
     * @throws IOException IO异常
     */
    public PDDocument getTarget() throws IOException {
        return this.createTarget();
    }

    /**
     * 获取pdf页面列表
     * @return 返回pdf页面列表
     */
    public List<XEasyPdfPage> getPageList() {
        return this.param.getPageList();
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
     * 设置文档信息及保护策略
     * @param target 任务文档
     * @throws IOException IO异常
     */
    void setInfoAndPolicy(PDDocument target) throws IOException {
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
    }

    /**
     * 创建任务
     * @return 返回pdfBox文档
     * @throws IOException IO异常
     */
    private PDDocument createTarget() throws IOException {
        // 如果任务文档未初始化或文档被重置，则进行新任务创建
        if (this.param.getTarget()==null||this.param.isReset()) {
            // 初始化任务文档
            this.initTarget();
        }
        return this.param.getTarget();
    }

    /**
     * 初始化任务文档
     * @throws IOException IO异常
     */
    private void initTarget() throws IOException {
        // 新建任务文档
        PDDocument target = new PDDocument();
        // 初始化新任务文档
        this.param.setTarget(target);
        // 初始化字体
        this.param.initFont(this);
        // 获取pdf页面列表
        List<XEasyPdfPage> pageList = this.param.getPageList();
        // 定义pdfBox页面列表
        List<PDPage> pdfboxPageList;
        // 遍历pdf页面列表
        for (XEasyPdfPage pdfPage : pageList) {
            // pdf页面构建
            pdfPage.build(this);
            // 初始化pdfBox页面列表
            pdfboxPageList = pdfPage.getParam().getPageList();
            // 遍历pdfBox页面列表
            for (PDPage page : pdfboxPageList) {
                // 任务文档添加页面
                PDPage importPage = target.importPage(page);
                // 设置页面资源缓存
                importPage.setResources(page.getResources());
            }
            // 初始化pdfBox新增页面列表
            pdfboxPageList = pdfPage.getParam().getNewPageList();
            // 遍历pdfBox页面列表
            for (PDPage page : pdfboxPageList) {
                // 任务文档添加页面
                target.addPage(page);
            }
        }
        // 填充表单
        this.fillForm();
        // 设置新任务文档
        this.param.setTarget(this.recreateTarget(target));
        // 关闭文档重置
        this.param.setReset(false);
    }

    /**
     * 重新创建任务文档（用于关联字体）
     * @param document pdfBox文档
     * @return 返回任务文档
     * @throws IOException IO异常
     */
    private PDDocument recreateTarget(PDDocument document) throws IOException {
        // 初始化字节流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        // 保存文档
        document.save(byteArrayOutputStream);
        // 关闭文档
        document.close();
        // 重新读取文档
        return PDDocument.load(byteArrayOutputStream.toByteArray());
    }

    /**
     * 填充表单
     * @throws IOException IO异常
     */
    private void fillForm() throws IOException {
        // 获取表单字典
        Map<String, String> formMap = this.param.getFormMap();
        // 如果表单字典有内容，则进行填充
        if (formMap!=null&&formMap.size()>0) {
            // 如果文档字体未初始化，则提示错误
            if (this.getFont()==null) {
                throw new RuntimeException("the document font must be set");
            }
            // 定义pdfBox表单字段
            PDField field;
            // 获取pdfBox表单
            PDAcroForm acroForm = this.getTarget().getDocumentCatalog().getAcroForm();
            // 如果pdfBox表单不为空，则进行填充
            if (acroForm!=null) {
                // 定义pdfBox数据源
                PDResources resources = new PDResources();
                // 内置默认字体名称
                final String defaultCosName = "AdobeSongStd-Light";
                // 设置字体
                resources.put(COSName.getPDFName(defaultCosName), this.getFont());
                // 设置pdfBox表单默认的数据源
                acroForm.setDefaultResources(resources);
                // 获取表单字典键值集合
                Set<Map.Entry<String, String>> entrySet = formMap.entrySet();
                // 遍历表单字典
                for (Map.Entry<String, String> entry : entrySet) {
                    // 获取表单字典中对应的pdfBox表单字段
                    field = acroForm.getField(entry.getKey());
                    // 如果pdfBox表单字段不为空，则填充值
                    if (field!=null) {
                        // 设置值
                        field.setValue(entry.getValue());
                    }
                }
            }
        }
    }


    /**
     * 文本替换（预留，未实现）
     * @param startPage 起始页面
     * @param endPage 结束页面
     * @param list 待替换字符串列表
     * @return 返回pdf文档
     * @throws IOException IO异常
     */
    protected XEasyPdfDocument replace(int startPage, int endPage, List<String> list) throws IOException {
        PDDocument target = this.createTarget();
        startPage = Math.max(startPage, 0);
        endPage = Math.min(endPage, target.getNumberOfPages());
        for (int i = startPage; i < endPage; i++) {
            PDPage page = target.getPage(i);
            PDFont font = page.getResources().getFont(page.getResources().getFontNames().iterator().next());
            PDFStreamParser parser = new PDFStreamParser(page);
            Object token = parser.parseNextToken();
            while (token!=null) {
                if (token instanceof COSString) {
                    this.processOperator(font, (COSString) token, list);
                }
                token = parser.parseNextToken();
            }
        }
        return this;
    }

    /**
     * 处理操作符
     * @param font pdfbox字体
     * @param previous 原有字符串
     * @param list 待替换字符串列表
     * @throws IOException IO异常
     */
    protected void processOperator(PDFont font, COSString previous, List<String> list) throws IOException {
        StringBuilder builder = new StringBuilder();
        try(InputStream in = new ByteArrayInputStream(previous.getBytes())) {
            while (in.available() > 0) {
                builder.append(font.toUnicode(font.readCode(in)));
            }
            String text = builder.toString();
//            System.out.println("text = " + text);
            list.add(text);
        }
    }
}
