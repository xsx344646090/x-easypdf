package org.dromara.pdf.pdfbox.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdfwriter.compress.CompressParameters;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.pdfbox.enums.*;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.FileUtil;

import java.awt.*;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
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
public class Document extends BaseFont implements Closeable {

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(Document.class);
    /**
     * 生产者
     */
    private static final String PRODUCER = Constants.PDFBOX_PRODUCER;

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
        // 初始化任务文档
        this.target = new PDDocument();
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
            // 重置游标
            context.getCursor().reset(
                    page.getMarginLeft(),
                    page.getHeight() - page.getMarginTop()
            );
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
     * 获取总页数
     *
     * @return 返回总页数
     */
    public int getTotalPageNumber() {
        return Optional.ofNullable(this.totalPageNumber).orElse(this.pages.size());
    }

    /**
     * 重排序页面
     *
     * @param indexes 页面索引
     * @return 返回文档
     */
    public Document reorderPage(int... indexes) {
        // 获取页面列表
        List<Page> pages = this.getPages();
        // 创建排序列表
        List<Page> orderPages = new ArrayList<>(pages.size());
        // 遍历索引
        for (int i : indexes) {
            try {
                // 添加页面
                orderPages.add(pages.remove(i));
            } catch (Exception e) {
                // 提示信息
                log.warn("the index['" + i + "'] is invalid, will be ignored");
            }
        }
        // 添加剩余页面
        orderPages.addAll(pages);
        // 重置页面列表
        this.setPages(orderPages);
        // 重置页面
        this.resetPage();
        // 返回文档
        return this;
    }

    /**
     * 插入页面
     *
     * @param index 页面索引
     * @param page  页面
     * @return 返回文档
     */
    public Document insertPage(int index, Page page) {
        try {
            // 添加页面
            this.getPages().add(index, page);
            // 遍历
            while (true) {
                // 索引自增
                index++;
                // 获取子页面
                page = page.getSubPage();
                // 子页面不存在结束
                if (Objects.isNull(page)) {
                    break;
                }
                // 添加子页面
                this.getPages().add(index, page);
            }
            // 重置页面
            this.resetPage();
        } catch (Exception e) {
            // 提示信息
            log.warn("the index['" + index + "'] is invalid, will be ignored");
        }
        // 返回文档
        return this;
    }

    /**
     * 追加页面
     *
     * @param page 页面
     * @return 返回文档
     */
    public Document appendPage(Page page) {
        // 添加页面
        this.getPages().add(page);
        // 遍历
        while (true) {
            // 获取子页面
            page = page.getSubPage();
            // 子页面不存在结束
            if (Objects.isNull(page)) {
                break;
            }
            // 添加子页面
            this.getPages().add(page);
        }
        // 重置页面
        this.resetPage();
        // 返回文档
        return this;
    }

    /**
     * 设置页面（替换）
     *
     * @param index 页面索引
     * @param page  页面
     * @return 返回文档
     */
    public Document setPage(int index, Page page) {
        try {
            // 设置页面
            this.getPages().set(index, page);
            // 遍历
            while (true) {
                // 索引自增
                index++;
                // 获取子页面
                page = page.getSubPage();
                // 子页面不存在结束
                if (Objects.isNull(page)) {
                    break;
                }
                // 添加子页面
                this.getPages().add(index, page);
            }
            // 重置页面
            this.resetPage();
        } catch (Exception e) {
            // 提示信息
            log.warn("the index['" + index + "'] is invalid, will be ignored");
        }
        // 返回文档
        return this;
    }

    /**
     * 移除页面
     *
     * @param indexes 页面索引
     * @return 返回文档
     */
    public Document removePage(int... indexes) {
        // 创建临时列表
        List<Page> temp = new ArrayList<>(this.getPages());
        // 遍历索引
        for (int index : indexes) {
            try {
                // 移除页面
                this.getPages().remove(temp.get(index));
            } catch (Exception e) {
                // 提示信息
                log.warn("the index['" + index + "'] is invalid, will be ignored");
            }
        }
        // 重置页面
        this.resetPage();
        // 返回文档
        return this;
    }

    /**
     * 创建页面
     *
     * @param rectangle 页面尺寸
     * @return 返回页面
     */
    public Page createPage(PageRectangle rectangle) {
        return new Page(this.getContext(), rectangle);
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
    public List<Catalog> getCatalogs() {
        return this.getContext().getCatalogs();
    }

    /**
     * 刷新目录
     *
     * @return 返回文档
     */
    public Document flushCatalog() {
        // 获取目录
        List<Catalog> catalogs = this.getContext().getCatalogs();
        // 目录不为空
        if (catalogs != null && !catalogs.isEmpty()) {
            // 转为页面字典
            Map<String, Page> pageMap = this.getPages().stream().collect(
                    Collectors.toMap(Page::getId, Function.identity())
            );
            // 重置索引
            catalogs.forEach(catalog -> catalog.setPageIndex(pageMap.get(catalog.getPageId()).getIndex()));
        }
        // 返回文档
        return this;
    }

    /**
     * 保存文档
     *
     * @param path 路径
     * @return 返回文档
     */
    @SneakyThrows
    public Document save(String path) {
        return this.save(path, CompressParameters.DEFAULT_OBJECT_STREAM_SIZE);
    }

    /**
     * 保存文档
     *
     * @param path             路径
     * @param objectStreamSize 对象流大小（用于压缩文档）
     * @return 返回文档
     */
    @SneakyThrows
    public Document save(String path, int objectStreamSize) {
        try (OutputStream outputStream = Files.newOutputStream(FileUtil.createDirectories(Paths.get(path)))) {
            return this.save(outputStream, objectStreamSize);
        }
    }

    /**
     * 保存文档
     *
     * @param outputStream 输出流
     * @return 返回文档
     */
    @SneakyThrows
    public Document save(OutputStream outputStream) {
        return this.save(outputStream, CompressParameters.DEFAULT_OBJECT_STREAM_SIZE);
    }

    /**
     * 保存文档
     *
     * @param outputStream     输出流
     * @param objectStreamSize 对象流大小（用于压缩文档）
     * @return 返回文档
     */
    @SneakyThrows
    public Document save(OutputStream outputStream, int objectStreamSize) {
        if (this.getPages().isEmpty()) {
            log.error("the document has no pages");
        }
        this.getTarget().save(outputStream, new CompressParameters(objectStreamSize));
        return this;
    }

    /**
     * 保存并关闭文档
     *
     * @param path 路径
     */
    public void saveAndClose(String path) {
        this.save(path).close();
    }

    /**
     * 保存并关闭文档
     *
     * @param path             路径
     * @param objectStreamSize 对象流大小（用于压缩文档）
     */
    public void saveAndClose(String path, int objectStreamSize) {
        this.save(path, objectStreamSize).close();
    }

    /**
     * 保存并关闭文档
     *
     * @param outputStream 输出流
     */
    public void saveAndClose(OutputStream outputStream) {
        this.save(outputStream).close();
    }

    /**
     * 保存并关闭文档
     *
     * @param outputStream     输出流
     * @param objectStreamSize 对象流大小（用于压缩文档）
     */
    public void saveAndClose(OutputStream outputStream, int objectStreamSize) {
        this.save(outputStream, objectStreamSize).close();
    }

    /**
     * 关闭文档
     */
    @SneakyThrows
    @Override
    public void close() {
        // 关闭任务文档
        this.getTarget().close();
        // 重置字体
        super.setFont(null);
        // 关闭页面
        this.getPages().forEach(Page::close);
    }

    /**
     * 重置页面
     */
    private void resetPage() {
        // 遍历页面
        for (int i = 0; i < this.getPages().size(); i++) {
            // 设置索引
            this.getPages().get(i).setIndex(i + 1);
        }
        // 获取pdfbox页面树
        PDPageTree pageTree = this.getTarget().getPages();
        // 移除页面
        pageTree.forEach(pageTree::remove);
        // 重新添加
        this.getPages().forEach(page -> this.getTarget().addPage(page.getTarget()));
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
        super.setFont(
                PdfHandler.getFontHandler().getPDFont(
                        this.target, Constants.DEFAULT_FONT_NAME, true
                )
        );
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
    }
}
