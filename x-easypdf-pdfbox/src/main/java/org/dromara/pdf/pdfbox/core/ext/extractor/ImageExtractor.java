package org.dromara.pdf.pdfbox.core.ext.extractor;

import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.util.*;

/**
 * 图像提取器
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
public class ImageExtractor implements Closeable {

    /**
     * pdfbox文档
     */
    private PDDocument document;

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public ImageExtractor(Document document) {
        this.document = document.getTarget();
    }

    /**
     * 提取文本
     *
     * @param pageIndexes 页面索引
     * @return 返回文本字典（key = 页面索引，value = 提取图像列表）
     */
    public Map<Integer, List<BufferedImage>> extract(int... pageIndexes) {
        // 定义图像字典
        Map<Integer, List<BufferedImage>> data = new HashMap<>(32);
        // 获取页面树
        PDPageTree pageTree = this.document.getPages();
        // 页面索引非空
        if (Objects.nonNull(pageIndexes) && pageIndexes.length > 0) {
            // 遍历页面索引
            Arrays.stream(pageIndexes).sorted().forEach(
                    // 添加数据
                    index -> data.put(index, this.extract(pageTree.get(index)))
            );
        } else {
            // 定义索引
            int index = 0;
            // 遍历页面树
            for (PDPage page : pageTree) {
                // 添加数据
                data.put(index, this.extract(page));
                // 索引自增
                index++;
            }
        }
        // 返回图像字典
        return data;
    }

    /**
     * 提取图像（表单）
     *
     * @return 返回图像字典（key = 字段名称，value = 提取图像）
     */
    @SneakyThrows
    public Map<String, BufferedImage> extractForForm() {
        // 定义图像字典
        Map<String, BufferedImage> data = new HashMap<>(32);
        // 获取pdfBox表单
        PDAcroForm form = this.document.getDocumentCatalog().getAcroForm();
        // 如果表单不为空，则提取表单内容
        if (Objects.nonNull(form)) {
            // 获取表单字段列表
            List<PDField> fields = form.getFields();
            // 遍历表单字段列表
            for (PDField field : fields) {
                // 获取部件列表
                List<PDAnnotationWidget> widgets = field.getWidgets();
                // 非空
                if (!widgets.isEmpty()) {
                    // 获取外观
                    PDAppearanceCharacteristicsDictionary appearanceCharacteristics = field.getWidgets().get(0).getAppearanceCharacteristics();
                    // 非空
                    if (Objects.nonNull(appearanceCharacteristics)) {
                        // 获取普通图标
                        PDFormXObject normalIcon = appearanceCharacteristics.getNormalIcon();
                        // 非空
                        if (Objects.nonNull(normalIcon)) {
                            // 获取资源
                            PDResources resources = normalIcon.getResources();
                            // 获取资源内容名称
                            Iterable<COSName> objectNames = resources.getXObjectNames();
                            // 遍历资源内容名称
                            for (COSName objectName : objectNames) {
                                // 获取资源内容
                                PDXObject xObject = resources.getXObject(objectName);
                                // 添加图像
                                if (xObject instanceof PDImage) {
                                    // 添加图像
                                    data.put(field.getFullyQualifiedName(), ((PDImage) xObject).getImage());
                                    // 结束
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return data;
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        this.document = null;
    }

    /**
     * 提取图像
     *
     * @param page pdfbox页面
     * @return 返回图像列表
     */
    protected List<BufferedImage> extract(PDPage page) {
        List<BufferedImage> data = new ArrayList<>(16);
        ImageUtil.extract(data, page.getResources());
        return data;
    }
}
