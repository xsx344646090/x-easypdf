package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
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
     * 日志
     */
    private static final Log log = LogFactory.getLog(AbstractFormAnalyzer.class);

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
    protected final Set<FormFieldInfo> infoSet = new HashSet<>(16);
    /**
     * 临时列表
     */
    protected Set<FormFieldInfo> tempSet = new HashSet<>(16);

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
        PDAcroForm form = this.getDocument().getDocumentCatalog().getAcroForm(null);
        if (Objects.isNull(form)) {
            this.infoMap = Collections.emptyMap();
        } else {
            this.infoMap = new HashMap<>(this.getDocument().getPages().getCount() + 1);
            form.getFields().forEach(this::processField);
        }
        this.processPageInfo();
    }

    /**
     * 处理字段
     *
     * @param field pdfbox字段
     */
    protected void processField(PDField field) {
        PDPage page = null;
        List<PDAnnotationWidget> widgets = field.getWidgets();
        if (!widgets.isEmpty()) {
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
                .page(page)
                .build();
        // 添加字段
        this.tempSet.add(info);
    }

    protected void processPageInfo() {
        if (!this.tempSet.isEmpty()) {
            final int unknownIndex = -1;
            PDPageTree pageTree = this.getDocument().getPages();
            for (FormFieldInfo info : this.tempSet) {
                boolean isUnknown = true;
                for (int index = 0; index < pageTree.getCount(); index++) {
                    PDPage page = info.getPage();
                    if (Objects.nonNull(page)) {
                        if (page.equals(pageTree.get(index))) {
                            PDRectangle rectangle = page.getMediaBox();
                            info.setPageIndex(index);
                            info.setPageWidth(rectangle.getWidth());
                            info.setPageHeight(rectangle.getHeight());
                            Set<FormFieldInfo> infos = this.infoMap.get(index);
                            if (Objects.isNull(infos)) {
                                infos = new HashSet<>(16);
                                this.infoMap.put(index, infos);
                            }
                            infos.add(info);
                            isUnknown = false;
                            break;
                        }
                    }
                }
                if (isUnknown) {
                    Set<FormFieldInfo> infos = this.infoMap.get(unknownIndex);
                    if (Objects.isNull(infos)) {
                        infos = new HashSet<>(16);
                        this.infoMap.put(unknownIndex, infos);
                    }
                    infos.add(info);
                }
            }
        }
        // 重置临时列表
        this.tempSet = new HashSet<>(16);
    }
}