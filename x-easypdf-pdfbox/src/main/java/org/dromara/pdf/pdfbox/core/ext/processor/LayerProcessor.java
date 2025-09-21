package org.dromara.pdf.pdfbox.core.ext.processor;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentGroup;
import org.apache.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentProperties;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.util.CommonUtil;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * 图层处理器
 *
 * @author xsx
 * @date 2024/1/29
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
public class LayerProcessor extends AbstractProcessor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public LayerProcessor(Document document) {
        super(document);
    }

    /**
     * 追加图层
     *
     * @param layerName 图层名称
     * @param image     图像
     * @param page      页面
     */
    public void append(String layerName, BufferedImage image, Page page) {
        this.addImage(layerName, false, image, page);
    }

    /**
     * 设置图层（替换）
     *
     * @param layerName 图层名称
     * @param image     图像
     * @param page      页面
     */
    public void set(String layerName, BufferedImage image, Page page) {
        this.addImage(layerName, true, image, page);
    }

    /**
     * 添加图像
     *
     * @param layerName 图层名称
     * @param isReplace 是否替换
     * @param image     图像
     * @param page      页面
     */
    protected void addImage(String layerName, boolean isReplace, BufferedImage image, Page page) {
        // 参数校验
        Objects.requireNonNull(layerName, "the layer name can not be null");
        Objects.requireNonNull(image, "the image can not be null");
        Objects.requireNonNull(page, "the page can not be null");
        // 添加图像
        this.addImage(layerName, isReplace, image, page.getTarget());
    }

    /**
     * 添加图像
     *
     * @param layerName 图层名称
     * @param isReplace 是否替换
     * @param image     图像
     * @param page      页面
     */
    @SneakyThrows
    protected void addImage(String layerName, boolean isReplace, BufferedImage image, PDPage page) {
        // 初始化可选内容组
        PDOptionalContentGroup group = this.initOCGroup(layerName, isReplace);
        // 获取页面尺寸
        PDRectangle rectangle = page.getMediaBox();
        // 创建图像
        PDImageXObject imageXObject = CommonUtil.createImage(this.getContext(), image);
        // 创建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                this.getDocument(),
                page,
                PDPageContentStream.AppendMode.APPEND,
                true,
                true
        );
        // 开始标记内容
        contentStream.beginMarkedContent(COSName.OC, group);
        // 添加图像
        contentStream.drawImage(imageXObject, 0, 0, rectangle.getWidth(), rectangle.getHeight());
        // 结束标记内容
        contentStream.endMarkedContent();
        // 关闭内容流
        contentStream.close();
    }

    /**
     * 初始化可选内容组
     *
     * @param layerName 图层名称
     * @param isReplace 是否替换
     * @return 返回可选内容组
     */
    protected PDOptionalContentGroup initOCGroup(String layerName, boolean isReplace) {
        // 初始化可选内容属性
        PDOptionalContentProperties ocProperties = this.initOCProperties();
        // 检查图层名称
        if (!isReplace && ocProperties.hasGroup(layerName)) {
            throw new IllegalArgumentException("the layer name['" + layerName + "'] already exists");
        }
        // 定义可选内容组
        PDOptionalContentGroup group;
        // 替换
        if (isReplace) {
            // 获取可选内容组
            group = ocProperties.getGroup(layerName);
            // 为空
            if (Objects.isNull(group)) {
                // 创建可选内容组
                group = new PDOptionalContentGroup(layerName);
                // 添加可选内容组
                ocProperties.addGroup(group);
            }
        } else {
            // 创建可选内容组
            group = new PDOptionalContentGroup(layerName);
            // 添加可选内容组
            ocProperties.addGroup(group);
        }
        // 返回可选内容组
        return group;
    }

    /**
     * 初始化可选内容属性
     *
     * @return 返回可选内容属性
     */
    protected PDOptionalContentProperties initOCProperties() {
        // 获取大纲
        PDDocumentCatalog catalog = this.getDocument().getDocumentCatalog();
        // 获取可选内容属性
        PDOptionalContentProperties ocProperties = catalog.getOCProperties();
        // 添加属性
        if (Objects.isNull(ocProperties)) {
            // 创建属性
            ocProperties = new PDOptionalContentProperties();
            // 设置属性
            catalog.setOCProperties(ocProperties);
        }
        // 返回属性
        return ocProperties;
    }
}
