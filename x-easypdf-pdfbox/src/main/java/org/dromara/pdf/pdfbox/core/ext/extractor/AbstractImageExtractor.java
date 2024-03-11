package org.dromara.pdf.pdfbox.core.ext.extractor;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * 抽象图像提取器
 *
 * @author xsx
 * @date 2023/10/24
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
public abstract class AbstractImageExtractor extends AbstractExtractor {

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractImageExtractor(Document document) {
        super(document);
    }

    /**
     * 提取图像
     *
     * @param pageIndexes 页面索引
     * @return 返回图像字典 <p>key = 页面索引，value = 提取图像列表</p>
     */
    public abstract Map<Integer, List<BufferedImage>> extract(int... pageIndexes);

    /**
     * 处理图像
     *
     * @param pageIndexes 页面索引
     * @return 返回图像字典 <p>key = 页面索引，value = 提取图像列表</p>
     */
    protected Map<Integer, List<BufferedImage>> processImage(int... pageIndexes) {
        // 定义图像字典
        Map<Integer, List<BufferedImage>> data = new HashMap<>(32);
        // 获取页面树
        PDPageTree pageTree = this.getDocument().getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            Arrays.stream(pageIndexes).sorted().forEach(
                    // 添加数据
                    index -> data.put(index, this.processImage(pageTree.get(index)))
            );
        } else {
            // 定义索引
            int index = 0;
            // 遍历页面树
            for (PDPage page : pageTree) {
                // 添加数据
                data.put(index, this.processImage(page));
                // 索引自增
                index++;
            }
        }
        // 返回图像字典
        return data;
    }

    /**
     * 处理图像
     *
     * @param page pdfbox页面
     * @return 返回图像列表
     */
    protected List<BufferedImage> processImage(PDPage page) {
        List<BufferedImage> data = new ArrayList<>(16);
        ImageUtil.extract(data, page.getResources());
        return data;
    }
}
