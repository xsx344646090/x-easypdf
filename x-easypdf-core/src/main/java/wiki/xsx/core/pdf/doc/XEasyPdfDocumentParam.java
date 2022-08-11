package wiki.xsx.core.pdf.doc;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDFont;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.image.XEasyPdfImage;
import wiki.xsx.core.pdf.footer.XEasyPdfFooter;
import wiki.xsx.core.pdf.header.XEasyPdfHeader;
import wiki.xsx.core.pdf.mark.XEasyPdfWatermark;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * pdf文档参数
 *
 * @author xsx
 * @date 2020/4/7
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
@Data
@Accessors(chain = true)
class XEasyPdfDocumentParam implements Serializable {

    private static final long serialVersionUID = 2578484264342461916L;

    /**
     * 生产者
     */
    private static final String PRODUCER = XEasyPdfConstants.PDFBOX_PRODUCER;
    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode = XEasyPdfComponent.ContentMode.APPEND;
    /**
     * 是否重置上下文
     */
    private Boolean isResetContext = Boolean.FALSE;
    /**
     * 是否替换总页码占位符
     */
    private Boolean isReplaceTotalPagePlaceholder = Boolean.FALSE;
    /**
     * 默认字体样式
     */
    private XEasyPdfDefaultFontStyle defaultFontStyle = XEasyPdfDefaultFontStyle.NORMAL;
    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 当前字体
     */
    private PDFont font;
    /**
     * 字体缓存
     */
    private final Map<String, PDFont> fontCache = new ConcurrentHashMap<>(8);
    /**
     * otf字体缓存
     */
    private final Map<String, PDFont> otfFontCache = new ConcurrentHashMap<>(8);
    /**
     * pdfBox文档（源文档）
     */
    private PDDocument source;
    /**
     * pdfBox文档（目标文档）
     */
    private PDDocument target;
    /**
     * 临时任务列表
     */
    private List<String> tempTargetList = new ArrayList<>(16);
    /**
     * 临时目录
     */
    private String tempDir;
    /**
     * 版本
     */
    private Float version = 1.5F;
    /**
     * 总页数
     */
    private Integer totalPage = 0;
    /**
     * 合并pdf源文档
     */
    private List<XEasyPdfDocument> mergeSourceList = new ArrayList<>(10);
    /**
     * pdf页面列表
     */
    private List<XEasyPdfPage> pageList = new ArrayList<>(32);
    /**
     * 全局左边距
     */
    private Float globalMarginLeft;
    /**
     * 全局右边距
     */
    private Float globalMarginRight;
    /**
     * 全局上边距
     */
    private Float globalMarginTop;
    /**
     * 全局下边距
     */
    private Float globalMarginBottom;
    /**
     * 全局页面背景色
     */
    private Color globalBackgroundColor = Color.WHITE;
    /**
     * 全局页面背景图片
     */
    private XEasyPdfImage globalBackgroundImage;
    /**
     * 全局页面水印
     */
    private XEasyPdfWatermark globalWatermark;
    /**
     * 全局页眉
     */
    private XEasyPdfHeader globalHeader;
    /**
     * 全局页脚
     */
    private XEasyPdfFooter globalFooter;
    /**
     * pdf文档权限
     */
    private XEasyPdfDocumentPermission permission;
    /**
     * pdf文档信息
     */
    private XEasyPdfDocumentInfo documentInfo;
    /**
     * pdf文档书签
     */
    private XEasyPdfDocumentBookmark bookmark;
    /**
     * 是否重置
     */
    private Boolean isReset = Boolean.FALSE;

    /**
     * 获取临时存放路径
     *
     * @return 返回临时存放路径
     */
    String getTempUrl() {
        return (this.tempDir != null ? this.tempDir : "") + File.separatorChar + this.tempTargetList.size();
    }

    /**
     * 初始化
     *
     * @param inputStream 输入流
     * @param password    密码
     * @param document    pdf文档
     */
    @SneakyThrows
    void init(InputStream inputStream, String password, XEasyPdfDocument document) {
        this.init(inputStream, password, null, null, document);
    }

    /**
     * 初始化
     *
     * @param inputStream 输入流
     * @param password    密码
     * @param keyStore    证书输入流
     * @param alias       证书别名
     * @param document    pdf文档
     */
    @SneakyThrows
    void init(
            InputStream inputStream,
            String password,
            InputStream keyStore,
            String alias,
            XEasyPdfDocument document
    ) {
        // 初始化pdfBox源文档
        this.source = PDDocument.load(inputStream, password, keyStore, alias, MemoryUsageSetting.setupMainMemoryOnly());
        // 获取pdfBox页面树
        PDPageTree pages = this.source.getPages();
        // 遍历pdfBox页面树
        for (PDPage page : pages) {
            // 添加pdfBox页面
            this.pageList.add(new XEasyPdfPage(page));
        }
        // 设置总页数
        this.initTotalPage(pages.getCount());
        // 初始化文档信息
        this.initInfo(document);
    }

    /**
     * 初始化任务
     *
     * @param document pdf文档
     */
    @SneakyThrows
    void initTarget(XEasyPdfDocument document) {
        // 如果任务文档不为空，则关闭
        if (this.target != null) {
            // 关闭文档
            this.target.close();
        }
        // 新建任务文档
        this.target = new PDDocument();
        // 如果源文档不为空，则设置文档表单
        if (this.source != null) {
            // 获取文档大纲
            PDDocumentCatalog documentCatalog = this.target.getDocumentCatalog();
            // 设置文档表单
            documentCatalog.setAcroForm(this.source.getDocumentCatalog().getAcroForm());
            // 设置文档目录
            documentCatalog.setDocumentOutline(this.source.getDocumentCatalog().getDocumentOutline());
        }
        // 设置重置为false
        this.isReset = Boolean.FALSE;
        // 初始化字体
        this.initFont(document);
    }

    /**
     * 初始化页面
     *
     * @param document pdf文档
     */
    @SneakyThrows
    void initPage(XEasyPdfDocument document) {
        // 定义pdfBox页面列表
        List<PDPage> pdfboxPageList;
        // 遍历pdf页面列表
        for (XEasyPdfPage pdfPage : this.pageList) {
            // pdf页面构建
            pdfPage.build(document);
            // 初始化pdfBox页面列表
            pdfboxPageList = pdfPage.getPageList();
            // 遍历pdfBox页面列表
            for (PDPage page : pdfboxPageList) {
                // 任务文档添加页面
                PDPage importPage = this.target.importPage(page);
                // 设置页面资源缓存
                importPage.setResources(page.getResources());
            }
            // 获取pdfbox页面树
            PDPageTree pageTree = this.target.getPages();
            // 初始化pdfBox新增页面列表
            pdfboxPageList = pdfPage.getNewPageList();
            // 遍历pdfBox页面列表
            for (PDPage page : pdfboxPageList) {
                // 任务文档添加页面
                pageTree.add(page);
            }
        }
        // 嵌入字体
        this.embedFont(this.fontCache.values());
    }

    /**
     * 嵌入字体
     *
     * @param font pdfbox字体
     */
    @SuppressWarnings("all")
    @SneakyThrows
    void embedFont(Collection<PDFont> fonts) {
        if (fonts != null && !fonts.isEmpty()) {
            Method method = this.target.getClass().getDeclaredMethod("getFontsToSubset");
            method.setAccessible(true);
            ((Set<PDFont>) method.invoke(this.target)).addAll(fonts);
        }
    }

    /**
     * 初始化总页数
     *
     * @param count 加减数量
     */
    void initTotalPage(int count) {
        this.totalPage += count;
    }

    /**
     * 获取生产者
     *
     * @return 返回生产者
     */
    String getProducer() {
        return PRODUCER;
    }

    /**
     * 初始化字体
     *
     * @param document pdf文档
     */
    private void initFont(XEasyPdfDocument document) {
        this.fontCache.clear();
        this.otfFontCache.clear();
        if (this.fontPath == null) {
            this.fontPath = this.defaultFontStyle.getPath();
        }
        this.font = XEasyPdfFontUtil.loadFont(document, this.fontPath, true);
    }

    /**
     * 初始化文档信息
     */
    private void initInfo(XEasyPdfDocument document) {
        PDDocumentInformation documentInformation = this.source.getDocumentInformation();
        this.documentInfo = new XEasyPdfDocumentInfo(document)
                .setTitle(documentInformation.getTitle())
                .setAuthor(documentInformation.getAuthor())
                .setSubject(documentInformation.getSubject())
                .setKeywords(documentInformation.getKeywords())
                .setCreator(documentInformation.getCreator())
                .setCreateTime(documentInformation.getCreationDate())
                .setUpdateTime(documentInformation.getModificationDate());
    }
}
