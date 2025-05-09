package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.EqualsAndHashCode;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.ext.processor.form.FormProcessor;
import org.dromara.pdf.pdfbox.core.ext.processor.sign.SignProcessor;

/**
 * 文档处理器
 *
 * @author xsx
 * @date 2024/2/26
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
@EqualsAndHashCode(callSuper = true)
public class DocumentProcessor extends AbstractProcessor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public DocumentProcessor(Document document) {
        super(document);
    }

    /**
     * 获取合并处理器
     *
     * @return 返回合并处理器
     */
    public MergeProcessor getMergeProcessor() {
        return new MergeProcessor(this.document);
    }

    /**
     * 获取拆分处理器
     *
     * @return 返回拆分处理器
     */
    public SplitProcessor getSplitProcessor() {
        return new SplitProcessor(this.document);
    }

    /**
     * 获取渲染处理器
     *
     * @return 返回渲染处理器
     */
    public RenderProcessor getRenderProcessor() {
        return new RenderProcessor(this.document);
    }

    /**
     * 获取替换处理器
     *
     * @return 返回替换处理器
     */
    public ReplaceProcessor getReplaceProcessor() {
        return new ReplaceProcessor(this.document);
    }

    /**
     * 获取打印处理器
     *
     * @return 返回打印处理器
     */
    public PrintProcessor getPrintProcessor() {
        return new PrintProcessor(this.document);
    }

    /**
     * 获取页面处理器
     *
     * @return 返回页面处理器
     */
    public PageProcessor getPageProcessor() {
        return new PageProcessor(this.document);
    }

    /**
     * 获取书签处理器
     *
     * @return 返回书签处理器
     */
    public BookmarkProcessor getBookmarkProcessor() {
        return new BookmarkProcessor(this.document);
    }

    /**
     * 获取评论处理器
     *
     * @return 返回评论处理器
     */
    public CommentProcessor getCommentProcessor() {
        return new CommentProcessor(this.document);
    }

    /**
     * 获取表单处理器
     *
     * @return 返回表单处理器
     */
    public FormProcessor getFormProcessor() {
        return new FormProcessor(this.document);
    }

    /**
     * 获取图层处理器
     *
     * @return 返回图层处理器
     */
    public LayerProcessor getLayerProcessor() {
        return new LayerProcessor(this.document);
    }

    /**
     * 获取元数据处理器
     *
     * @return 返回元数据处理器
     */
    public MetadataProcessor getMetadataProcessor() {
        return new MetadataProcessor(this.document);
    }

    /**
     * 获取签名处理器
     *
     * @return 返回签名处理器
     */
    public SignProcessor getSignProcessor() {
        return new SignProcessor(this.document);
    }

    /**
     * 获取图像处理器
     *
     * @return 返回图像处理器
     */
    public ImageProcessor getImageProcessor() {
        return new ImageProcessor(this.document);
    }

    /**
     * 获取附件处理器
     *
     * @return 返回附件处理器
     */
    public AttachmentProcessor getAttachmentProcessor() {
        return new AttachmentProcessor(this.document);
    }

    /**
     * 获取线性化处理器
     *
     * @return 返回线性化处理器
     */
    public LinearizationProcessor getLinearizationProcessor() {
        return new LinearizationProcessor(this.document);
    }
}
