package org.dromara.pdf.pdfbox.component.layout;

import org.dromara.pdf.pdfbox.component.table.XEasyPdfCell;
import org.dromara.pdf.pdfbox.component.table.XEasyPdfRow;
import org.dromara.pdf.pdfbox.component.table.XEasyPdfTable;
import org.dromara.pdf.pdfbox.doc.XEasyPdfDocument;
import org.dromara.pdf.pdfbox.doc.XEasyPdfPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * pdf水平布局
 *
 * @author xsx
 * @date 2022/3/17
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfHorizontalLayout implements XEasyPdfLayout {

    private static final long serialVersionUID = 5850233027447702010L;

    /**
     * pdf布局参数
     */
    private final XEasyPdfLayoutParam param = new XEasyPdfLayoutParam();

    /**
     * 无参构造
     */
    public XEasyPdfHorizontalLayout() {
    }

    /**
     * 设置位置
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回水平布局组件
     */
    @Override
    public XEasyPdfHorizontalLayout setPosition(float beginX, float beginY) {
        this.param.getTable().setPosition(beginX, beginY);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回水平布局组件
     */
    @Override
    public XEasyPdfHorizontalLayout setWidth(float width) {
        this.param.setWidth(Math.abs(width));
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回水平布局组件
     */
    @Override
    public XEasyPdfHorizontalLayout setHeight(float height) {
        this.param.setHeight(Math.abs(height));
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回水平布局组件
     */
    @Override
    public XEasyPdfHorizontalLayout setContentMode(ContentMode mode) {
        this.param.getTable().setContentMode(mode);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回水平布局组件
     */
    public XEasyPdfHorizontalLayout setMarginLeft(float marginLeft) {
        this.param.getTable().setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回水平布局组件
     */
    public XEasyPdfHorizontalLayout setMarginTop(float marginTop) {
        this.param.getTable().setMarginTop(marginTop);
        return this;
    }

    /**
     * 开启边框
     *
     * @return 返回水平布局组件
     */
    @Override
    public XEasyPdfHorizontalLayout enableBorder() {
        this.param.setHasTableBorder(Boolean.TRUE);
        return this;
    }

    /**
     * 开启重置上下文
     *
     * @return 返回水平布局组件
     */
    @Override
    public XEasyPdfHorizontalLayout enableResetContext() {
        this.param.getTable().enableResetContext();
        return this;
    }

    /**
     * 添加组件
     *
     * @param components 组件列表
     * @return 返回水平布局组件
     */
    @Override
    public XEasyPdfHorizontalLayout addLayoutComponent(XEasyPdfLayoutComponent... components) {
        if (components != null) {
            Collections.addAll(this.param.getComponents(), components);
        }
        return this;
    }

    /**
     * 添加组件
     *
     * @param components 组件列表
     * @return 返回水平布局组件
     */
    @Override
    public XEasyPdfHorizontalLayout addLayoutComponent(List<XEasyPdfLayoutComponent> components) {
        if (components != null) {
            this.param.getComponents().addAll(components);
        }
        return this;
    }

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    @Override
    public void draw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果组件列表为空，则直接返回
        if (this.param.getComponents().isEmpty()) {
            // 返回
            return;
        }
        // 获取组件列表
        List<XEasyPdfLayoutComponent> components = this.param.getComponents();
        // 定义pdf单元格列表
        List<XEasyPdfCell> cells = new ArrayList<>(components.size());
        // 遍历组件列表
        for (XEasyPdfLayoutComponent layoutComponent : components) {
            // 添加单元格
            cells.add(
                    new XEasyPdfCell(
                            layoutComponent.getWidth(),
                            layoutComponent.getHeight()
                    ).addContent(
                            layoutComponent.getComponent()
                    ).enableComponentSelfStyle()
            );
        }
        // 获取pdf表格
        XEasyPdfTable table = this.param.getTable();
        // 如果不包含表格边框，则关闭表格边框
        if (!this.param.getHasTableBorder()) {
            // 关闭表格边框
            table.disableBorder();
        }
        // 添加表格行并绘制
        table.addRow(
                new XEasyPdfRow().addCell(cells)
        ).setMarginBottom(0F).draw(document, page);
    }
}
