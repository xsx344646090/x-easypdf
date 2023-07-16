package org.dromara.pdf.pdfbox.core;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdfwriter.compress.CompressParameters;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.enums.ContentMode;
import org.dromara.pdf.pdfbox.enums.FontStyle;
import org.dromara.pdf.pdfbox.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.util.FileUtil;

import java.awt.*;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
public class Document implements Closeable {

    /**
     * 参数
     */
    private final DocumentParam param = new DocumentParam();

    /**
     * 无参构造
     */
    public Document() {
        this.param.init();
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
        this.param.init(inputStream, password, keyStore, alias, policy);
    }

    /**
     * 设置边距（上下左右）
     *
     * @param margin 边距
     * @return 返回文档
     */
    public Document setMargin(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.param.initMargin(margin);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param margin 边距
     * @return 返回文档
     */
    public Document setMarginLeft(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param margin 边距
     * @return 返回文档
     */
    public Document setMarginRight(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param margin 边距
     * @return 返回文档
     */
    public Document setMarginTop(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param margin 边距
     * @return 返回文档
     */
    public Document setMarginBottom(float margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the margin must be positive");
        }
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回文档
     */
    public Document setContentMode(ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param color 颜色
     * @return 返回文档
     */
    public Document setBackgroundColor(Color color) {
        if (color != null) {
            this.param.setBackgroundColor(color);
        }
        return this;
    }

    /**
     * 设置水平对齐方式
     *
     * @param alignment 对齐方式
     * @return 返回文档
     */
    public Document setHorizontalAlignment(HorizontalAlignment alignment) {
        if (alignment != null) {
            this.param.setHorizontalAlignment(alignment);
        }
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontName 字体名称
     * @return 返回文档
     */
    public Document setFontName(String fontName) {
        this.param.getFontParam().setFont(PdfHandler.getFontHandler().getPDFont(this.getPDDocument(), fontName, true));
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回文档
     */
    public Document setFontSize(float fontSize) {
        if (fontSize < 1) {
            throw new IllegalArgumentException("the font size must be greater than 1");
        }
        this.param.getFontParam().setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param color 字体颜色
     * @return 返回文档
     */
    public Document setFontColor(Color color) {
        this.param.getFontParam().setFontColor(color);
        return this;
    }

    /**
     * 设置字体样式
     *
     * @param style 字体样式
     * @return 返回文档
     */
    public Document setFontStyle(FontStyle style) {
        this.param.getFontParam().setFontStyle(style);
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param spacing 字符间距
     * @return 返回文档
     */
    public Document setCharacterSpacing(float spacing) {
        if (spacing < 0) {
            throw new IllegalArgumentException("the character spacing must be greater than 0");
        }
        this.param.getFontParam().setCharacterSpacing(spacing);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param leading 行间距
     * @return 返回文档
     */
    public Document setLeading(float leading) {
        if (leading < 0) {
            throw new IllegalArgumentException("the leading must be greater than 0");
        }
        this.param.getFontParam().setLeading(leading);
        return this;
    }

    /**
     * 设置总页数
     *
     * @param number 页码
     * @return 返回文档
     */
    public Document setTotalPageNumber(int number) {
        this.param.setTotalPageNumber(number);
        return this;
    }

    /**
     * 获取总页数
     *
     * @return 返回总页数
     */
    public int getTotalPageNumber() {
        return Optional.ofNullable(this.param.getTotalPageNumber()).orElse(this.param.getPages().size());
    }

    /**
     * 获取pdfbox文档
     *
     * @return pdfbox文档
     */
    public PDDocument getPDDocument() {
        return this.param.getTarget();
    }

    /**
     * 获取当前使用字体
     *
     * @return 返回pdf字体
     */
    public PDFont getPDFont() {
        return this.param.getFontParam().getFont();
    }

    /**
     * 获取页面列表
     *
     * @return 返回页面列表
     */
    public List<Page> getPages() {
        return this.param.getPages();
    }

    /**
     * 获取指定页面
     *
     * @param index 页面索引
     * @return 返回页面
     */
    public Page getPage(int index) {
        List<Page> pages = this.param.getPages();
        if (pages == null || pages.size() <= index) {
            return null;
        }
        return pages.get(index);
    }

    /**
     * 获取文档参数
     *
     * @return 返回文档参数
     */
    public DocumentParam getParam() {
        return this.param;
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
        this.getPDDocument().save(outputStream, new CompressParameters(objectStreamSize));
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
    @Override
    public void close() {
        this.param.release();
    }
}
