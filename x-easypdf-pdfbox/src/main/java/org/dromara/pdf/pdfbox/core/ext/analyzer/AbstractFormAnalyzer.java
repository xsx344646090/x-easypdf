package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.FormFieldInfo;

import java.util.*;

/**
 * 抽象表单分析器
 *
 * @author xsx
 * @date 2024/2/21
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
@Getter
public abstract class AbstractFormAnalyzer extends AbstractAnalyzer {

    /**
     * 字段列表
     */
    protected List<PDField> fieldList;
    /**
     * 字段信息字典
     */
    protected Map<Integer, Set<FormFieldInfo>> infoMap;
    /**
     * 字段信息列表
     */
    protected final Set<FormFieldInfo> infoSet = new LinkedHashSet<>(16);
    /**
     * 临时列表
     */
    protected Set<FormFieldInfo> tempSet = new LinkedHashSet<>(16);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractFormAnalyzer(Document document) {
        super(document);
    }

    /**
     * 处理表单
     *
     * @param pageIndex 页面索引
     * @param page      pdfbox页面
     */
    public abstract void processForm(int pageIndex, PDPage page);

    /**
     * 获取字段信息列表
     *
     * @param pageIndex 页面索引
     * @return 返回字段信息列表
     */
    protected Set<FormFieldInfo> getFields(int pageIndex) {
        // 初始化
        if (Objects.isNull(this.infoMap)) {
            this.initInfoMap();
        }
        // 获取字段信息列表
        Set<FormFieldInfo> infos = Optional.ofNullable(this.infoMap.get(pageIndex)).orElse(Collections.emptySet());
        // 如果日志打印开启，则打印日志
        if (log.isDebugEnabled()) {
            // 遍历列表
            for (FormFieldInfo info : infos) {
                // 打印日志
                log.debug(
                        "\n********************************************ANALYZE FORM FIELD BEGIN********************************************" +
                                "\npage index: " + info.getPageIndex() +
                                "\npage width: " + info.getPageWidth() +
                                "\npage height: " + info.getPageHeight() +
                                "\nfield type: " + info.getType() +
                                "\nfield name: " + info.getName() +
                                "\nfield value: " + info.getValue() +
                                "\nfield is readOnly: " + info.getIsReadOnly() +
                                "\nfield is required: " + info.getIsRequired() +
                                "\nfield is no export: " + info.getIsNoExport() +
                                "\n*********************************************ANALYZE FORM FIELD END*********************************************"
                );
            }
        }
        // 返回字段信息列表
        return infos;
    }

    /**
     * 初始化字段信息字典
     */
    protected void initInfoMap() {
        // 获取文档的表单
        PDAcroForm form = this.getDocument().getDocumentCatalog().getAcroForm(null);
        // 如果表单为空，则将infoMap置为空
        if (Objects.isNull(form)) {
            this.infoMap = Collections.emptyMap();
        } else {
            // 否则，创建一个HashMap，大小为文档页数加1
            this.infoMap = new HashMap<>(this.getDocument().getPages().getCount() + 1);
            // 遍历表单字段，处理每个字段
            form.getFields().forEach(this::processField);
        }
        // 处理页面信息
        this.processPageInfo();
    }

    /**
     * 处理字段
     *
     * @param field pdfbox字段
     */
    protected void processField(PDField field) {
        // 获取PDF页面
        PDPage page = null;
        // 获取字段的小部件
        List<PDAnnotationWidget> widgets = field.getWidgets();
        // 如果小部件不为空
        if (!widgets.isEmpty()) {
            // 获取小部件所在的页面
            page = widgets.get(0).getPage();
        }
        // 构建字段信息
        FormFieldInfo info = FormFieldInfo.builder()
                .type(field.getFieldType())
                .name(field.getFullyQualifiedName())
                .value(field.getValueAsString())
                .isReadOnly(field.isReadOnly())
                .isRequired(field.isRequired())
                .isNoExport(field.isNoExport())
                .field(field)
                .page(page)
                .build();
        // 添加字段
        this.tempSet.add(info);
    }

    /**
     * 处理页面信息
     */
    @SneakyThrows
    protected void processPageInfo() {
        // 如果临时集合不为空
        if (!this.tempSet.isEmpty()) {
            // 定义未知索引为-1
            final int unknownIndex = -1;
            // 获取文档的页面树
            PDPageTree pageTree = this.getDocument().getPages();
            // 遍历临时集合中的表单信息
            for (FormFieldInfo info : this.tempSet) {
                // 定义是否未知为true
                boolean isUnknown = true;
                // 遍历页面树
                for (int index = 0; index < pageTree.getCount(); index++) {
                    // 获取表单信息所在的页面
                    PDPage page = info.getPage();
                    // 如果页面不为空
                    if (Objects.nonNull(page)) {
                        // 如果页面与页面树中的页面相等
                        if (page.equals(pageTree.get(index))) {
                            // 添加表单信息到页面和索引
                            this.addInfo(info, page, index);
                            // 将是否未知设置为false
                            isUnknown = false;
                            // 跳出循环
                            break;
                        }
                    } else {
                        // 获取页面树中的页面
                        page = pageTree.get(index);
                        // 获取页面的注释
                        List<PDAnnotation> annotations = page.getAnnotations();
                        // 如果注释不为空
                        if (Objects.nonNull(annotations)) {
                            // 遍历注释
                            for (PDAnnotation annotation : annotations) {
                                // 如果注释是PDAnnotationWidget类型
                                if (annotation instanceof PDAnnotationWidget) {
                                    // 如果表单信息的部分名称与注释的名称相等
                                    if (Objects.equals(info.getField().getPartialName(), annotation.getCOSObject().getString(COSName.T))) {
                                        // 添加表单信息到页面和索引
                                        this.addInfo(info, page, index);
                                        // 将是否未知设置为false
                                        isUnknown = false;
                                        // 跳出循环
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                // 如果是未知
                if (isUnknown) {
                    // 获取未知索引对应的表单信息集合
                    Set<FormFieldInfo> infos = this.infoMap.get(unknownIndex);
                    // 如果集合为空
                    if (Objects.isNull(infos)) {
                        // 创建新的集合
                        infos = new LinkedHashSet<>(16);
                        // 将集合添加到infoMap中
                        this.infoMap.put(unknownIndex, infos);
                    }
                    // 将表单信息添加到集合中
                    infos.add(info);
                }
            }
        }
        // 重置临时列表
        this.tempSet = new LinkedHashSet<>(16);
    }

    /**
     * 添加字段信息
     *
     * @param info  字段信息
     * @param page  页面
     * @param index 页面索引
     */
    protected void addInfo(FormFieldInfo info, PDPage page, int index) {
        // 获取当前页的媒体框
        PDRectangle rectangle = page.getMediaBox();
        // 设置当前页的索引
        info.setPageIndex(index);
        // 设置当前页的宽度
        info.setPageWidth(rectangle.getWidth());
        // 设置当前页的高度
        info.setPageHeight(rectangle.getHeight());
        // 获取当前页的表单字段信息集合
        Set<FormFieldInfo> infos = this.infoMap.get(index);
        // 如果当前页的表单字段信息集合为空
        if (Objects.isNull(infos)) {
            // 创建一个新的表单字段信息集合
            infos = new LinkedHashSet<>(16);
            // 将新的表单字段信息集合放入infoMap中
            this.infoMap.put(index, infos);
        }
        // 将当前页的表单字段信息添加到集合中
        infos.add(info);
    }
}
