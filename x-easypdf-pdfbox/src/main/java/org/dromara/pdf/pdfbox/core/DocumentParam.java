package org.dromara.pdf.pdfbox.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.pdfbox.enums.BorderStyle;
import org.dromara.pdf.pdfbox.enums.ContentMode;
import org.dromara.pdf.pdfbox.enums.FontStyle;
import org.dromara.pdf.pdfbox.enums.HorizontalAlignment;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.pdfbox.support.Constants;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * pdf文档参数
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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DocumentParam extends BaseParam {

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
     * 获取最新页面
     *
     * @return 返回页面
     */
    public Page getLastPage() {
        if (this.pages.isEmpty()) {
            return null;
        }
        return this.pages.get(this.pages.size() - 1);
    }

    /**
     * 初始化
     */
    @SneakyThrows
    void init() {
        // 初始化pdfBox源文档
        this.target = new PDDocument();
        // 初始化基础参数
        this.initBaseParams();
        // 初始化页面列表
        this.pages = new ArrayList<>();
    }

    /**
     * 初始化
     *
     * @param inputStream 输入流
     * @param password    密码
     * @param keyStore    证书输入流
     * @param alias       证书别名
     * @param policy      内存策略
     */
    @SneakyThrows
    void init(
            InputStream inputStream,
            String password,
            InputStream keyStore,
            String alias,
            MemoryPolicy policy
    ) {
        // 初始化任务文档
        this.target = Loader.loadPDF(inputStream, password, keyStore, alias, policy.getSetting());
        // 初始化基础参数
        this.initBaseParams();
        // 初始化页面列表
        this.pages = this.initPages();
    }

    /**
     * 初始化边距
     */
    void initMargin(float margin) {
        this.setMarginTop(margin);
        this.setMarginBottom(margin);
        this.setMarginLeft(margin);
        this.setMarginRight(margin);
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
     * 释放资源
     */
    @SneakyThrows
    void release() {
        this.target.close();
        this.getFontParam().setFont(null);
        this.pages.forEach(Page::release);
    }

    /**
     * 初始化基础参数
     */
    private void initBaseParams() {
        // 初始化字体参数
        this.initFontParams();
        // 初始化边框参数
        this.initBorderParams();
        // 初始化其他参数
        this.initOtherParams();
    }

    /**
     * 初始化字体参数
     */
    private void initFontParams() {
        // 初始化字体
        this.getFontParam().setFont(PdfHandler.getFontHandler().getPDFont(this.target, Constants.DEFAULT_FONT_NAME, true));
        // 初始化字体大小
        this.getFontParam().setFontSize(12F);
        // 初始化字体颜色
        this.getFontParam().setFontColor(Color.BLACK);
        // 初始化字体样式
        this.getFontParam().setFontStyle(FontStyle.NORMAL);
        // 初始化字符间距
        this.getFontParam().setCharacterSpacing(0F);
        // 初始化行间距
        this.getFontParam().setLeading(0F);
    }

    /**
     * 初始化边框参数
     */
    private void initBorderParams() {
        // 初始化样式
        this.getBorderParam().setStyle(BorderStyle.SOLID);
        // 初始化线宽
        this.getBorderParam().setWidth(1F);
        // 初始化线长
        this.getBorderParam().setLineLength(1F);
        // 初始化间隔
        this.getBorderParam().setLineSpace(1F);
        // 初始化上边框颜色
        this.getBorderParam().setTopColor(Color.BLACK);
        // 初始化下边框颜色
        this.getBorderParam().setBottomColor(Color.BLACK);
        // 初始化左边框颜色
        this.getBorderParam().setLeftColor(Color.BLACK);
        // 初始化右边框颜色
        this.getBorderParam().setRightColor(Color.BLACK);
        // 初始化是否上边框
        this.getBorderParam().setIsTop(Boolean.FALSE);
        // 初始化是否下边框
        this.getBorderParam().setIsBottom(Boolean.FALSE);
        // 初始化是否左边框
        this.getBorderParam().setIsLeft(Boolean.FALSE);
        // 初始化是否右边框
        this.getBorderParam().setIsRight(Boolean.FALSE);
    }

    /**
     * 初始化其他参数
     */
    private void initOtherParams() {
        // 初始化对齐方式
        this.setHorizontalAlignment(HorizontalAlignment.LEFT);
        // 初始化内容模式
        this.setContentMode(ContentMode.APPEND);
        // 初始化背景颜色
        this.setBackgroundColor(Color.WHITE);
    }

    /**
     * 初始化页面列表
     *
     * @return 返回页面列表
     */
    private List<Page> initPages() {
        int count = this.target.getNumberOfPages();
        List<Page> pages = new ArrayList<>(count);
        PDPageTree pageTree = this.target.getPages();
        for (int i = 0; i < count; i++) {
            pages.add(new Page(i + 1, pageTree.get(i), this));
        }
        return pages;
    }
}
