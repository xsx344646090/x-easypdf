package org.dromara.pdf.pdfbox.core.ext.comparator;

import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.info.ImageCompareInfo;
import org.dromara.pdf.pdfbox.util.CommonUtil;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * 图像比较器
 *
 * @author xsx
 * @date 2024/8/5
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
public class ImageComparator extends AbstractComparator {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public ImageComparator(Document document) {
        super(document);

    }

    /**
     * 比较
     *
     * @param other       文档
     * @param pageIndexes 页面索引
     */
    public Map<Integer, List<ImageCompareInfo>> compareTo(Document other, int... pageIndexes) {
        // 定义结果
        Map<Integer, List<ImageCompareInfo>> result = new HashMap<>(this.document.getTotalPageNumber());
        // 判断是否指定页码
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页码
            for (int pageIndex : pageIndexes) {
                // 添加比较结果
                result.put(pageIndex, this.compareImages(other, pageIndex));
            }
        } else {
            // 遍历所有页码
            for (int pageIndex = 0; pageIndex < this.document.getTotalPageNumber(); pageIndex++) {
                // 添加比较结果
                result.put(pageIndex, this.compareImages(other, pageIndex));
            }
        }
        // 返回结果
        return result;
    }

    /**
     * 比较图像
     *
     * @param other     文档
     * @param pageIndex 页面索引
     * @return 返回比较结果
     */
    protected List<ImageCompareInfo> compareImages(Document other, int pageIndex) {
        // 定义结果
        List<ImageCompareInfo> results = new ArrayList<>(64);
        // 获取当前文档的图像
        List<BufferedImage> list = this.processPageImage(this.document, pageIndex);
        // 获取比较文档的图像
        List<BufferedImage> otherList = this.processPageImage(other, pageIndex);
        // 遍历文本
        for (int i = 0; i < list.size(); i++) {
            // 获取图像
            BufferedImage image = list.get(i);
            // 判断是否有比较图像
            if (Objects.nonNull(otherList) && otherList.size() > i) {
                // 获取比较图像
                BufferedImage otherImage = otherList.get(i);
                // 判断图像是否相同
                if (!this.compareImage(image, otherImage)) {
                    // 添加比较结果
                    results.add(ImageCompareInfo.builder().pageIndex(pageIndex).imageIndex(i).content(image).compareContent(otherImage).build());
                }
            } else {
                // 添加比较结果
                results.add(ImageCompareInfo.builder().pageIndex(pageIndex).imageIndex(i).content(image).build());
            }
        }
        // 返回结果
        return results;
    }

    /**
     * 比较图像
     *
     * @param image      图像
     * @param otherImage 待比较图像
     * @return 返回布尔值，true为是，false为否
     */
    protected boolean compareImage(BufferedImage image, BufferedImage otherImage) {
        // 判断image和otherImage是否为空
        if (Objects.isNull(image) || Objects.isNull(otherImage)) {
            return false;
        }
        // 判断image和otherImage的宽度和高度是否相等
        if (image.getWidth() != otherImage.getWidth() || image.getHeight() != otherImage.getHeight()) {
            return false;
        }
        // 遍历image和otherImage的每个像素点
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                // 判断image和otherImage的每个像素点的RGB值是否相等
                if (image.getRGB(x, y) != otherImage.getRGB(x, y)) {
                    return false;
                }
            }
        }
        // 如果所有像素点的RGB值都相等，则返回true
        return true;
    }

    /**
     * 处理页面图像
     *
     * @param document  文档
     * @param pageIndex 页面索引
     * @return 返回图像列表
     */
    @SneakyThrows
    protected List<BufferedImage> processPageImage(Document document, int pageIndex) {
        // 获取页面
        Page page = document.getPage(pageIndex);
        // 如果页码不存在，则返回null
        if (Objects.isNull(page)) {
            return null;
        }
        // 返回图像列表
        return CommonUtil.extractImage(page.getTarget());
    }
}
