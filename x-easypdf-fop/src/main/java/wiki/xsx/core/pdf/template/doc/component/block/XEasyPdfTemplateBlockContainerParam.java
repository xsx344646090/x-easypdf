package wiki.xsx.core.pdf.template.doc.component.block;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import wiki.xsx.core.pdf.template.doc.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.doc.component.XEasyPdfTemplateComponentParam;

import java.util.ArrayList;
import java.util.List;

/**
 * pdf模板-块容器参数
 *
 * @author xsx
 * @date 2022/11/6
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
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
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
class XEasyPdfTemplateBlockContainerParam extends XEasyPdfTemplateComponentParam {

    /**
     * 组件
     */
    private List<XEasyPdfTemplateComponent> components = new ArrayList<>(10);
    /**
     * 垂直对齐
     * <p>top：上对齐</p>
     * <p>bottom：下对齐</p>
     */
    private String verticalAlign;
    /**
     * 宽度
     */
    private String width;
    /**
     * 高度
     */
    private String height;
    /**
     * 边框
     */
    private String border;
    /**
     * 边框样式
     * <p>none：无</p>
     * <p>hidden：隐藏</p>
     * <p>dotted：点虚线</p>
     * <p>dashed：短虚线</p>
     * <p>solid：实线</p>
     * <p>double：双实线</p>
     * <p>groove：凹线（槽）</p>
     * <p>ridge：凸线（脊）</p>
     * <p>inset：嵌入</p>
     * <p>outset：凸出</p>
     */
    private String borderStyle;
    /**
     * 边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    private String borderColor;
    /**
     * 边框宽度
     */
    private String borderWidth;
    /**
     * 边框折叠
     * <p>collapse：合并</p>
     * <p>separate：分开</p>
     */
    private String borderCollapse;
    /**
     * 边框圆角
     */
    private String borderRadius;
    /**
     * 上边框
     */
    private String borderTop;
    /**
     * 上边框样式
     * <p>none：无</p>
     * <p>hidden：隐藏</p>
     * <p>dotted：点虚线</p>
     * <p>dashed：短虚线</p>
     * <p>solid：实线</p>
     * <p>double：双实线</p>
     * <p>groove：凹线（槽）</p>
     * <p>ridge：凸线（脊）</p>
     * <p>inset：嵌入</p>
     * <p>outset：凸出</p>
     */
    private String borderTopStyle;
    /**
     * 上边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    private String borderTopColor;
    /**
     * 上边框宽度
     */
    private String borderTopWidth;
    /**
     * 上边框圆角
     */
    private String borderTopRadius;
    /**
     * 下边框
     */
    private String borderBottom;
    /**
     * 下边框样式
     * <p>none：无</p>
     * <p>hidden：隐藏</p>
     * <p>dotted：点虚线</p>
     * <p>dashed：短虚线</p>
     * <p>solid：实线</p>
     * <p>double：双实线</p>
     * <p>groove：凹线（槽）</p>
     * <p>ridge：凸线（脊）</p>
     * <p>inset：嵌入</p>
     * <p>outset：凸出</p>
     */
    private String borderBottomStyle;
    /**
     * 下边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    private String borderBottomColor;
    /**
     * 下边框宽度
     */
    private String borderBottomWidth;
    /**
     * 下边框圆角
     */
    private String borderBottomRadius;
    /**
     * 左边框
     */
    private String borderLeft;
    /**
     * 左边框样式
     * <p>none：无</p>
     * <p>hidden：隐藏</p>
     * <p>dotted：点虚线</p>
     * <p>dashed：短虚线</p>
     * <p>solid：实线</p>
     * <p>double：双实线</p>
     * <p>groove：凹线（槽）</p>
     * <p>ridge：凸线（脊）</p>
     * <p>inset：嵌入</p>
     * <p>outset：凸出</p>
     */
    private String borderLeftStyle;
    /**
     * 左边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    private String borderLeftColor;
    /**
     * 左边框宽度
     */
    private String borderLeftWidth;
    /**
     * 左边框圆角
     */
    private String borderLeftRadius;
    /**
     * 右边框
     */
    private String borderRight;
    /**
     * 右边框样式
     * <p>none：无</p>
     * <p>hidden：隐藏</p>
     * <p>dotted：点虚线</p>
     * <p>dashed：短虚线</p>
     * <p>solid：实线</p>
     * <p>double：双实线</p>
     * <p>groove：凹线（槽）</p>
     * <p>ridge：凸线（脊）</p>
     * <p>inset：嵌入</p>
     * <p>outset：凸出</p>
     */
    private String borderRightStyle;
    /**
     * 右边框颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    private String borderRightColor;
    /**
     * 右边框宽度
     */
    private String borderRightWidth;
    /**
     * 右边框圆角
     */
    private String borderRightRadius;
}
