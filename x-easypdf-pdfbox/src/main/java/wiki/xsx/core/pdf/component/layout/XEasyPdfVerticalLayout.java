package wiki.xsx.core.pdf.component.layout;

import wiki.xsx.core.pdf.component.table.XEasyPdfCell;
import wiki.xsx.core.pdf.component.table.XEasyPdfRow;
import wiki.xsx.core.pdf.component.table.XEasyPdfTable;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * pdf垂直布局
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
public class XEasyPdfVerticalLayout implements XEasyPdfLayout {

    private static final long serialVersionUID = -6412230973258885503L;

    /**
     * pdf布局参数
     */
    private final XEasyPdfLayoutParam param = new XEasyPdfLayoutParam();

    /**
     * 无参构造
     */
    public XEasyPdfVerticalLayout() {
    }

    /**
     * 设置位置
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回垂直布局组件
     */
    @Override
    public XEasyPdfVerticalLayout setPosition(float beginX, float beginY) {
        this.param.getTable().setPosition(beginX, beginY);
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width 宽度
     * @return 返回垂直布局组件
     */
    @Override
    public XEasyPdfVerticalLayout setWidth(float width) {
        this.param.setWidth(Math.abs(width));
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return 返回垂直布局组件
     */
    @Override
    public XEasyPdfVerticalLayout setHeight(float height) {
        this.param.setHeight(Math.abs(height));
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回垂直布局组件
     */
    @Override
    public XEasyPdfVerticalLayout setContentMode(ContentMode mode) {
        this.param.getTable().setContentMode(mode);
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回垂直布局组件
     */
    public XEasyPdfVerticalLayout setMarginLeft(float marginLeft) {
        this.param.getTable().setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回垂直布局组件
     */
    public XEasyPdfVerticalLayout setMarginTop(float marginTop) {
        this.param.getTable().setMarginTop(marginTop);
        return this;
    }

    /**
     * 开启边框
     *
     * @return 返回垂直布局组件
     */
    @Override
    public XEasyPdfVerticalLayout enableBorder() {
        this.param.setHasTableBorder(Boolean.TRUE);
        return this;
    }

    /**
     * 开启重置上下文
     *
     * @return 返回垂直布局组件
     */
    @Override
    public XEasyPdfVerticalLayout enableResetContext() {
        this.param.getTable().enableResetContext();
        return this;
    }

    /**
     * 添加组件
     *
     * @param components 组件列表
     * @return 返回垂直布局组件
     */
    @Override
    public XEasyPdfVerticalLayout addLayoutComponent(XEasyPdfLayoutComponent... components) {
        if (components != null) {
            Collections.addAll(this.param.getComponents(), components);
        }
        return this;
    }

    /**
     * 添加组件
     *
     * @param components 组件列表
     * @return 返回垂直布局组件
     */
    @Override
    public XEasyPdfVerticalLayout addLayoutComponent(List<XEasyPdfLayoutComponent> components) {
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
        // 定义pdf表格行列表
        List<XEasyPdfRow> rows = new ArrayList<>(components.size());
        // 遍历组件列表
        for (XEasyPdfLayoutComponent layoutComponent : components) {
            // 添加表格行
            rows.add(
                    new XEasyPdfRow().addCell(
                            new XEasyPdfCell(
                                    layoutComponent.getWidth(),
                                    layoutComponent.getHeight()
                            ).addContent(
                                    layoutComponent.getComponent()
                            ).enableComponentSelfStyle()
                    )
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
        table.addRow(rows).setMarginBottom(0F).draw(document, page);
    }
}
