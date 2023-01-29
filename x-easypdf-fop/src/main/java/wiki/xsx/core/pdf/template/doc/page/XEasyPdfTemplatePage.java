package wiki.xsx.core.pdf.template.doc.page;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateAttributes;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateTags;
import wiki.xsx.core.pdf.template.doc.component.XEasyPdfTemplateComponent;
import wiki.xsx.core.pdf.template.doc.watermark.XEasyPdfTemplateWatermarkComponent;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * pdf模板-页面
 *
 * @author xsx
 * @date 2022/8/5
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class XEasyPdfTemplatePage implements XEasyPdfTemplatePageComponent {

    /**
     * pdf模板-页面参数
     */
    private final XEasyPdfTemplatePageParam param = new XEasyPdfTemplatePageParam();

    /**
     * 设置页面宽度
     *
     * @param width 页面宽度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setWidth(String width) {
        Optional.ofNullable(width).ifPresent(this.param::setWidth);
        return this;
    }

    /**
     * 设置页面高度
     *
     * @param height 页面高度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeight(String height) {
        Optional.ofNullable(height).ifPresent(this.param::setHeight);
        return this;
    }

    /**
     * 设置页面边距（上下左右边距）
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setMargin(String margin) {
        this.param.setMarginTop(margin).setMarginBottom(margin).setMarginLeft(margin).setMarginRight(margin);
        return this;
    }

    /**
     * 设置页面上边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setMarginTop(String margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置页面下边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setMarginBottom(String margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置页面左边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setMarginLeft(String margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置页面右边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setMarginRight(String margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置页面主体边距（上下左右边距）
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyMargin(String margin) {
        this.param.getRegionBodyParam().setMarginTop(margin).setMarginBottom(margin).setMarginLeft(margin).setMarginRight(margin);
        return this;
    }

    /**
     * 设置页面主体上边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyMarginTop(String margin) {
        this.param.getRegionBodyParam().setMarginTop(margin);
        return this;
    }

    /**
     * 设置页面主体下边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyMarginBottom(String margin) {
        this.param.getRegionBodyParam().setMarginBottom(margin);
        return this;
    }

    /**
     * 设置页面主体左边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyMarginLeft(String margin) {
        this.param.getRegionBodyParam().setMarginLeft(margin);
        return this;
    }

    /**
     * 设置页面主体右边距
     *
     * @param margin 边距
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyMarginRight(String margin) {
        this.param.getRegionBodyParam().setMarginRight(margin);
        return this;
    }

    /**
     * 设置左侧栏宽度
     *
     * @param width 宽度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartWidth(String width) {
        this.param.getRegionStartParam().setWidth(width);
        return this;
    }

    /**
     * 设置右侧栏宽度
     *
     * @param width 宽度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndWidth(String width) {
        this.param.getRegionEndParam().setWidth(width);
        return this;
    }

    /**
     * 设置页眉高度
     *
     * @param height 高度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeaderHeight(String height) {
        this.param.getRegionBeforeParam().setHeight(height);
        return this;
    }

    /**
     * 设置页脚高度
     *
     * @param height 高度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFooterHeight(String height) {
        this.param.getRegionAfterParam().setHeight(height);
        return this;
    }

    /**
     * 设置id
     *
     * @param id id
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setId(String id) {
        this.param.setId(id);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontFamily(String fontFamily) {
        this.param.setFontFamily(fontFamily);
        return this;
    }

    /**
     * 设置字体样式
     *
     * @param fontStyle 字体样式
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontStyle(String fontStyle) {
        this.param.setFontStyle(fontStyle);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontSize(String fontSize) {
        this.param.setFontSize(fontSize);
        return this;
    }

    /**
     * 设置字体重量
     *
     * @param fontWeight 字体重量
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontWeight(String fontWeight) {
        this.param.setFontWeight(fontWeight);
        return this;
    }

    /**
     * 设置字体大小调整
     *
     * @param fontSizeAdjust 字体大小调整
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontSizeAdjust(String fontSizeAdjust) {
        this.param.setFontSizeAdjust(fontSizeAdjust);
        return this;
    }

    /**
     * 设置字体颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 字体颜色
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFontColor(String color) {
        this.param.setFontColor(color);
        return this;
    }

    /**
     * 设置主体背景
     *
     * @param background 背景
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyBackground(String background) {
        this.param.getRegionBodyParam().setBackground(background);
        return this;
    }

    /**
     * 设置主体背景图片
     * <p>注：路径须写为”url('xxx.png')“的形式</p>
     * <p>注：当为windows系统绝对路径时，须添加前缀“/”，例如：”url('/E:\test\test.png')“</p>
     *
     * @param image 图片
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyBackgroundImage(String image) {
        this.param.getRegionBodyParam().setBackgroundImage(image);
        return this;
    }

    /**
     * 设置主体背景图片宽度
     *
     * @param width 图片宽度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyBackgroundImageWidth(String width) {
        this.param.getRegionBodyParam().setBackgroundImageWidth(width);
        return this;
    }

    /**
     * 设置主体背景图片高度
     *
     * @param height 图片高度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyBackgroundImageHeight(String height) {
        this.param.getRegionBodyParam().setBackgroundImageHeight(height);
        return this;
    }

    /**
     * 设置主体背景附件
     * <p>scroll：滚动</p>
     * <p>fixed：固定</p>
     *
     * @param attachment 附件
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyBackgroundAttachment(String attachment) {
        this.param.getRegionBodyParam().setBackgroundAttachment(attachment);
        return this;
    }

    /**
     * 设置主体背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyBackgroundColor(String color) {
        this.param.getRegionBodyParam().setBackgroundColor(color);
        return this;
    }

    /**
     * 设置主体背景图片定位
     * <p>第一个参数为X轴</p>
     * <p>第二个参数为Y轴</p>
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyBackgroundPosition(String position) {
        this.param.getRegionBodyParam().setBackgroundPosition(position);
        return this;
    }

    /**
     * 设置主体背景图片水平定位
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyBackgroundHorizontalPosition(String position) {
        this.param.getRegionBodyParam().setBackgroundPositionHorizontal(position);
        return this;
    }

    /**
     * 设置主体背景图片垂直定位
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyBackgroundVerticalPosition(String position) {
        this.param.getRegionBodyParam().setBackgroundPositionVertical(position);
        return this;
    }

    /**
     * 设置主体背景图片重复
     * <p>repeat：水平垂直重复</p>
     * <p>repeat-x：水平重复</p>
     * <p>repeat-y：垂直重复</p>
     * <p>no-repeat：不重复</p>
     *
     * @param repeat 重复
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyBackgroundRepeat(String repeat) {
        this.param.getRegionBodyParam().setBackgroundRepeat(repeat);
        return this;
    }

    /**
     * 设置主体水印
     *
     * @param watermark 水印组件
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setBodyWatermark(XEasyPdfTemplateWatermarkComponent watermark) {
        this.param.getRegionBodyParam().setWatermark(watermark);
        return this;
    }

    /**
     * 设置左侧栏背景
     *
     * @param background 背景
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartBackground(String background) {
        this.param.getRegionStartParam().setBackground(background);
        return this;
    }

    /**
     * 设置左侧栏背景图片
     * <p>注：路径须写为”url('xxx.png')“的形式</p>
     * <p>注：当为windows系统绝对路径时，须添加前缀“/”，例如：”url('/E:\test\test.png')“</p>
     *
     * @param image 图片
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartBackgroundImage(String image) {
        this.param.getRegionStartParam().setBackgroundImage(image);
        return this;
    }

    /**
     * 设置左侧栏背景图片宽度
     *
     * @param width 图片宽度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartBackgroundImageWidth(String width) {
        this.param.getRegionStartParam().setBackgroundImageWidth(width);
        return this;
    }

    /**
     * 设置左侧栏背景图片高度
     *
     * @param height 图片高度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartBackgroundImageHeight(String height) {
        this.param.getRegionStartParam().setBackgroundImageHeight(height);
        return this;
    }

    /**
     * 设置左侧栏背景附件
     * <p>scroll：滚动</p>
     * <p>fixed：固定</p>
     *
     * @param attachment 附件
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartBackgroundAttachment(String attachment) {
        this.param.getRegionStartParam().setBackgroundAttachment(attachment);
        return this;
    }

    /**
     * 设置左侧栏背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartBackgroundColor(String color) {
        this.param.getRegionStartParam().setBackgroundColor(color);
        return this;
    }

    /**
     * 设置左侧栏背景图片定位
     * <p>第一个参数为X轴</p>
     * <p>第二个参数为Y轴</p>
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartBackgroundPosition(String position) {
        this.param.getRegionStartParam().setBackgroundPosition(position);
        return this;
    }

    /**
     * 设置左侧栏背景图片水平定位
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartBackgroundHorizontalPosition(String position) {
        this.param.getRegionStartParam().setBackgroundPositionHorizontal(position);
        return this;
    }

    /**
     * 设置左侧栏背景图片垂直定位
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartBackgroundVerticalPosition(String position) {
        this.param.getRegionStartParam().setBackgroundPositionVertical(position);
        return this;
    }

    /**
     * 设置左侧栏背景图片重复
     * <p>repeat：水平垂直重复</p>
     * <p>repeat-x：水平重复</p>
     * <p>repeat-y：垂直重复</p>
     * <p>no-repeat：不重复</p>
     *
     * @param repeat 重复
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartBackgroundRepeat(String repeat) {
        this.param.getRegionStartParam().setBackgroundRepeat(repeat);
        return this;
    }

    /**
     * 设置左侧栏水印
     *
     * @param watermark 水印组件
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setStartWatermark(XEasyPdfTemplateWatermarkComponent watermark) {
        this.param.getRegionStartParam().setWatermark(watermark);
        return this;
    }

    /**
     * 设置右侧栏背景
     *
     * @param background 背景
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndBackground(String background) {
        this.param.getRegionEndParam().setBackground(background);
        return this;
    }

    /**
     * 设置右侧栏背景图片
     * <p>注：路径须写为”url('xxx.png')“的形式</p>
     * <p>注：当为windows系统绝对路径时，须添加前缀“/”，例如：”url('/E:\test\test.png')“</p>
     *
     * @param image 图片
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndBackgroundImage(String image) {
        this.param.getRegionEndParam().setBackgroundImage(image);
        return this;
    }

    /**
     * 设置右侧栏背景图片宽度
     *
     * @param width 图片宽度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndBackgroundImageWidth(String width) {
        this.param.getRegionEndParam().setBackgroundImageWidth(width);
        return this;
    }

    /**
     * 设置右侧栏背景图片高度
     *
     * @param height 图片高度
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndBackgroundImageHeight(String height) {
        this.param.getRegionEndParam().setBackgroundImageHeight(height);
        return this;
    }

    /**
     * 设置右侧栏背景附件
     * <p>scroll：滚动</p>
     * <p>fixed：固定</p>
     *
     * @param attachment 附件
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndBackgroundAttachment(String attachment) {
        this.param.getRegionEndParam().setBackgroundAttachment(attachment);
        return this;
    }

    /**
     * 设置右侧栏背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndBackgroundColor(String color) {
        this.param.getRegionEndParam().setBackgroundColor(color);
        return this;
    }

    /**
     * 设置右侧栏背景图片定位
     * <p>第一个参数为X轴</p>
     * <p>第二个参数为Y轴</p>
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndBackgroundPosition(String position) {
        this.param.getRegionEndParam().setBackgroundPosition(position);
        return this;
    }

    /**
     * 设置右侧栏背景图片水平定位
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndBackgroundHorizontalPosition(String position) {
        this.param.getRegionEndParam().setBackgroundPositionHorizontal(position);
        return this;
    }

    /**
     * 设置右侧栏背景图片垂直定位
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndBackgroundVerticalPosition(String position) {
        this.param.getRegionEndParam().setBackgroundPositionVertical(position);
        return this;
    }

    /**
     * 设置右侧栏背景图片重复
     * <p>repeat：水平垂直重复</p>
     * <p>repeat-x：水平重复</p>
     * <p>repeat-y：垂直重复</p>
     * <p>no-repeat：不重复</p>
     *
     * @param repeat 重复
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndBackgroundRepeat(String repeat) {
        this.param.getRegionEndParam().setBackgroundRepeat(repeat);
        return this;
    }

    /**
     * 设置右侧栏水印
     *
     * @param watermark 水印组件
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setEndWatermark(XEasyPdfTemplateWatermarkComponent watermark) {
        this.param.getRegionEndParam().setWatermark(watermark);
        return this;
    }

    /**
     * 设置页眉背景
     *
     * @param background 背景
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeaderBackground(String background) {
        this.param.getRegionBeforeParam().setBackground(background);
        return this;
    }

    /**
     * 设置页眉背景图片
     * <p>注：路径须写为”url('xxx.png')“的形式</p>
     * <p>注：当为windows系统绝对路径时，须添加前缀“/”，例如：”url('/E:\test\test.png')“</p>
     *
     * @param image 图片
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeaderBackgroundImage(String image) {
        this.param.getRegionBeforeParam().setBackgroundImage(image);
        return this;
    }

    /**
     * 设置页眉背景附件
     * <p>scroll：滚动</p>
     * <p>fixed：固定</p>
     *
     * @param attachment 附件
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeaderBackgroundAttachment(String attachment) {
        this.param.getRegionBeforeParam().setBackgroundAttachment(attachment);
        return this;
    }

    /**
     * 设置页眉背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeaderBackgroundColor(String color) {
        this.param.getRegionBeforeParam().setBackgroundColor(color);
        return this;
    }

    /**
     * 设置页眉背景图片定位
     * <p>第一个参数为X轴</p>
     * <p>第二个参数为Y轴</p>
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeaderBackgroundPosition(String position) {
        this.param.getRegionBeforeParam().setBackgroundPosition(position);
        return this;
    }

    /**
     * 设置页眉背景图片水平定位
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeaderBackgroundHorizontalPosition(String position) {
        this.param.getRegionBeforeParam().setBackgroundPositionHorizontal(position);
        return this;
    }

    /**
     * 设置页眉背景图片垂直定位
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeaderBackgroundVerticalPosition(String position) {
        this.param.getRegionBeforeParam().setBackgroundPositionVertical(position);
        return this;
    }

    /**
     * 设置页眉背景图片重复
     * <p>repeat：水平垂直重复</p>
     * <p>repeat-x：水平重复</p>
     * <p>repeat-y：垂直重复</p>
     * <p>no-repeat：不重复</p>
     *
     * @param repeat 重复
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeaderBackgroundRepeat(String repeat) {
        this.param.getRegionBeforeParam().setBackgroundRepeat(repeat);
        return this;
    }

    /**
     * 设置页眉水印
     *
     * @param watermark 水印组件
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setHeaderWatermark(XEasyPdfTemplateWatermarkComponent watermark) {
        this.param.getRegionBeforeParam().setWatermark(watermark);
        return this;
    }

    /**
     * 设置页脚背景
     *
     * @param background 背景
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFooterBackground(String background) {
        this.param.getRegionAfterParam().setBackground(background);
        return this;
    }

    /**
     * 设置页脚背景图片
     * <p>注：路径须写为”url('xxx.png')“的形式</p>
     * <p>注：当为windows系统绝对路径时，须添加前缀“/”，例如：”url('/E:\test\test.png')“</p>
     *
     * @param image 图片
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFooterBackgroundImage(String image) {
        this.param.getRegionAfterParam().setBackgroundImage(image);
        return this;
    }

    /**
     * 设置页脚背景附件
     * <p>scroll：滚动</p>
     * <p>fixed：固定</p>
     *
     * @param attachment 附件
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFooterBackgroundAttachment(String attachment) {
        this.param.getRegionAfterParam().setBackgroundAttachment(attachment);
        return this;
    }

    /**
     * 设置页脚背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 颜色
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFooterBackgroundColor(String color) {
        this.param.getRegionAfterParam().setBackgroundColor(color);
        return this;
    }

    /**
     * 设置页脚背景图片定位
     * <p>第一个参数为X轴</p>
     * <p>第二个参数为Y轴</p>
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFooterBackgroundPosition(String position) {
        this.param.getRegionAfterParam().setBackgroundPosition(position);
        return this;
    }

    /**
     * 设置页脚背景图片水平定位
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFooterBackgroundHorizontalPosition(String position) {
        this.param.getRegionAfterParam().setBackgroundPositionHorizontal(position);
        return this;
    }

    /**
     * 设置页脚背景图片垂直定位
     *
     * @param position 定位
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFooterBackgroundVerticalPosition(String position) {
        this.param.getRegionAfterParam().setBackgroundPositionVertical(position);
        return this;
    }

    /**
     * 设置页脚背景图片重复
     * <p>repeat：水平垂直重复</p>
     * <p>repeat-x：水平重复</p>
     * <p>repeat-y：垂直重复</p>
     * <p>no-repeat：不重复</p>
     *
     * @param repeat 重复
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFooterBackgroundRepeat(String repeat) {
        this.param.getRegionAfterParam().setBackgroundRepeat(repeat);
        return this;
    }

    /**
     * 设置页脚水印
     *
     * @param watermark 水印组件
     * @return 返回页面
     */
    public XEasyPdfTemplatePage setFooterWatermark(XEasyPdfTemplateWatermarkComponent watermark) {
        this.param.getRegionAfterParam().setWatermark(watermark);
        return this;
    }

    /**
     * 切换横向
     *
     * @return 返回页面
     */
    public XEasyPdfTemplatePage changeLandscape() {
        this.param.changeLandscape();
        return this;
    }

    /**
     * 添加页面主体组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addBodyComponent(XEasyPdfTemplateComponent... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getRegionBodyParam().getComponents(), v));
        return this;
    }

    /**
     * 添加页面主体组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addBodyComponent(List<XEasyPdfTemplateComponent> components) {
        Optional.ofNullable(components).ifPresent(this.param.getRegionBodyParam().getComponents()::addAll);
        return this;
    }

    /**
     * 添加页眉组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addHeaderComponent(XEasyPdfTemplateComponent... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getRegionBeforeParam().getComponents(), v));
        return this;
    }

    /**
     * 添加页眉组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addHeaderComponent(List<XEasyPdfTemplateComponent> components) {
        Optional.ofNullable(components).ifPresent(this.param.getRegionBeforeParam().getComponents()::addAll);
        return this;
    }

    /**
     * 添加页脚组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addFooterComponent(XEasyPdfTemplateComponent... components) {
        Optional.ofNullable(components).ifPresent(v -> Collections.addAll(this.param.getRegionAfterParam().getComponents(), v));
        return this;
    }

    /**
     * 添加页脚组件
     *
     * @param components 组件列表
     * @return 返回页面
     */
    public XEasyPdfTemplatePage addFooterComponent(List<XEasyPdfTemplateComponent> components) {
        Optional.ofNullable(components).ifPresent(this.param.getRegionAfterParam().getComponents()::addAll);
        return this;
    }

    /**
     * 创建元素
     *
     * @param index    当前索引
     * @param document fo文档
     * @param bookmark 书签元素
     * @return 返回节点
     */
    @Override
    public Element createElement(int index, Document document, Element bookmark) {
        // 获取根元素
        Element root = document.getDocumentElement();
        // 添加页面模板并获取页面模板名称
        String masterName = this.addLayoutMasterSet(index, document, root);
        // 添加书签
        Optional.ofNullable(bookmark).ifPresent(root::appendChild);
        // 添加页面序列
        return this.addPageSequence(document, masterName);
    }

    /**
     * 添加页面模板
     *
     * @param index    当前页面索引
     * @param document fo文档
     * @param root     根元素
     * @return 返回页面模板名称
     */
    private String addLayoutMasterSet(int index, Document document, Element root) {
        // 构建页面模板名称
        String masterName = "page" + index;
        // 创建页面模板
        Node layoutMasterSet = root.getElementsByTagName(XEasyPdfTemplateTags.LAYOUT_MASTER_SET).item(0);
        // 添加单页面模板
        layoutMasterSet.appendChild(this.createSimplePageMaster(document, masterName));
        // 返回页面名称
        return masterName;
    }

    /**
     * 创建单页面模板
     *
     * @param document   fo文档
     * @param masterName 页面模板名称
     * @return 返回单页面模板元素
     */
    private Element createSimplePageMaster(Document document, String masterName) {
        // 创建单页面模板
        Element simplePageMaster = document.createElement(XEasyPdfTemplateTags.SIMPLE_PAGE_MASTER);
        // 设置页面名称
        simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MASTER_NAME, masterName);
        // 设置页面宽度
        Optional.ofNullable(this.param.getWidth()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.PAGE_WIDTH, v.intern().toLowerCase()));
        // 设置页面高度
        Optional.ofNullable(this.param.getHeight()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.PAGE_HEIGHT, v.intern().toLowerCase()));
        // 设置页面上边距
        Optional.ofNullable(this.param.getMarginTop()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_TOP, v.intern().toLowerCase()));
        // 设置页面下边距
        Optional.ofNullable(this.param.getMarginBottom()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_BOTTOM, v.intern().toLowerCase()));
        // 设置页面左边距
        Optional.ofNullable(this.param.getMarginLeft()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_LEFT, v.intern().toLowerCase()));
        // 设置页面右边距
        Optional.ofNullable(this.param.getMarginRight()).ifPresent(v -> simplePageMaster.setAttribute(XEasyPdfTemplateAttributes.MARGIN_RIGHT, v.intern().toLowerCase()));
        // 添加页面主体区域
        simplePageMaster.appendChild(this.createRegionBody(document));
        // 添加页眉区域
        simplePageMaster.appendChild(this.createRegionBefore(document));
        // 添加页脚区域
        simplePageMaster.appendChild(this.createRegionAfter(document));
        // 添加页面左侧栏区域
        simplePageMaster.appendChild(this.createRegionStart(document));
        // 添加页面右侧栏区域
        simplePageMaster.appendChild(this.createRegionEnd(document));
        // 返回单页面模板
        return simplePageMaster;
    }

    /**
     * 创建页面主体区域
     *
     * @param document fo文档
     * @return 返回页面主体区域元素
     */
    private Element createRegionBody(Document document) {
        // 创建页面主体区域
        Element regionBody = document.createElement(XEasyPdfTemplateTags.REGION_BODY);
        // 设置页面主体区域上边距
        Optional.ofNullable(this.param.getRegionBodyParam().getMarginTop()).ifPresent(v -> regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_TOP, v.intern().toLowerCase()));
        // 设置页面主体区域下边距
        Optional.ofNullable(this.param.getRegionBodyParam().getMarginBottom()).ifPresent(v -> regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_BOTTOM, v.intern().toLowerCase()));
        // 设置页面主体区域左边距
        Optional.ofNullable(this.param.getRegionBodyParam().getMarginLeft()).ifPresent(v -> regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_LEFT, v.intern().toLowerCase()));
        // 设置页面主体区域右边距
        Optional.ofNullable(this.param.getRegionBodyParam().getMarginRight()).ifPresent(v -> regionBody.setAttribute(XEasyPdfTemplateAttributes.MARGIN_RIGHT, v.intern().toLowerCase()));
        // 初始化区域
        this.initRegion(document, regionBody, this.param.getRegionBodyParam());
        // 返回页面主体区域
        return regionBody;
    }

    /**
     * 创建左侧栏区域
     *
     * @param document fo文档
     * @return 返回左侧栏区域元素
     */
    private Element createRegionStart(Document document) {
        // 创建左侧栏区域
        Element regionStart = document.createElement(XEasyPdfTemplateTags.REGION_START);
        // 设置左侧栏区域范围
        Optional.ofNullable(this.param.getRegionStartParam().getWidth()).ifPresent(v -> regionStart.setAttribute(XEasyPdfTemplateAttributes.EXTENT, v.intern().toLowerCase()));
        // 初始化区域
        this.initRegion(document, regionStart, this.param.getRegionStartParam());
        // 返回左侧栏区域
        return regionStart;
    }

    /**
     * 创建右侧栏区域
     *
     * @param document fo文档
     * @return 返回左侧栏区域元素
     */
    private Element createRegionEnd(Document document) {
        // 创建右侧栏区域
        Element regionEnd = document.createElement(XEasyPdfTemplateTags.REGION_END);
        // 设置右侧栏区域范围
        Optional.ofNullable(this.param.getRegionEndParam().getWidth()).ifPresent(v -> regionEnd.setAttribute(XEasyPdfTemplateAttributes.EXTENT, v.intern().toLowerCase()));
        // 初始化区域
        this.initRegion(document, regionEnd, this.param.getRegionEndParam());
        // 返回右侧栏区域
        return regionEnd;
    }

    /**
     * 创建页眉区域
     *
     * @param document fo文档
     * @return 返回页眉区域元素
     */
    private Element createRegionBefore(Document document) {
        // 创建页眉区域
        Element regionBefore = document.createElement(XEasyPdfTemplateTags.REGION_BEFORE);
        // 设置页眉区域范围
        Optional.ofNullable(this.param.getRegionBeforeParam().getHeight()).ifPresent(v -> regionBefore.setAttribute(XEasyPdfTemplateAttributes.EXTENT, v.intern().toLowerCase()));
        // 初始化区域
        this.initRegion(document, regionBefore, this.param.getRegionBeforeParam());
        // 返回页眉区域
        return regionBefore;
    }

    /**
     * 创建页脚区域
     *
     * @param document fo文档
     * @return 返回页脚区域元素
     */
    private Element createRegionAfter(Document document) {
        // 创建页脚区域
        Element regionAfter = document.createElement(XEasyPdfTemplateTags.REGION_AFTER);
        // 设置页脚区域范围
        Optional.ofNullable(this.param.getRegionAfterParam().getHeight()).ifPresent(v -> regionAfter.setAttribute(XEasyPdfTemplateAttributes.EXTENT, v.intern().toLowerCase()));
        // 初始化区域
        this.initRegion(document, regionAfter, this.param.getRegionAfterParam());
        // 返回页脚区域
        return regionAfter;
    }

    /**
     * 初始化区域
     *
     * @param document fo文档
     * @param region   区域元素
     * @param param    区域参数
     */
    private void initRegion(Document document, Element region, XEasyPdfTemplateRegionBaseParam param) {
        // 设置背景颜色
        Optional.ofNullable(param.getBackgroundColor()).ifPresent(v -> region.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_COLOR, v.intern().toLowerCase()));
        // 设置背景
        Optional.ofNullable(param.getBackground()).ifPresent(v -> region.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND, v.intern().toLowerCase()));
        // 设置背景图片
        Optional.ofNullable(param.getBackgroundImage()).ifPresent(v -> region.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_IMAGE, v.intern()));
        // 设置背景图片宽度
        Optional.ofNullable(param.getBackgroundImageWidth()).ifPresent(v -> region.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_IMAGE_WIDTH, v.intern().toLowerCase()));
        // 设置背景图片高度
        Optional.ofNullable(param.getBackgroundImageHeight()).ifPresent(v -> region.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_IMAGE_HEIGHT, v.intern().toLowerCase()));
        // 设置背景附件
        Optional.ofNullable(param.getBackgroundAttachment()).ifPresent(v -> region.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_ATTACHMENT, v.intern().toLowerCase()));
        // 设置背景图片定位
        Optional.ofNullable(param.getBackgroundPosition()).ifPresent(v -> region.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_POSITION, v.intern().toLowerCase()));
        // 设置背景图片水平定位
        Optional.ofNullable(param.getBackgroundPositionHorizontal()).ifPresent(v -> region.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_POSITION_HORIZONTAL, v.intern().toLowerCase()));
        // 设置背景图片垂直定位
        Optional.ofNullable(param.getBackgroundPositionVertical()).ifPresent(v -> region.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_POSITION_VERTICAL, v.intern().toLowerCase()));
        // 设置背景图片重复
        Optional.ofNullable(param.getBackgroundRepeat()).ifPresent(v -> region.setAttribute(XEasyPdfTemplateAttributes.BACKGROUND_REPEAT, v.intern().toLowerCase()));
        // 设置水印
        Optional.ofNullable(param.getWatermark()).ifPresent(v -> v.createWatermark(document, region));
    }

    /**
     * 添加页面序列
     *
     * @param document   fo文档
     * @param masterName 页面模板名称
     * @return 返回页面序列元素
     */
    private Element addPageSequence(Document document, String masterName) {
        // 创建页面序列
        Element pageSequence = document.createElement(XEasyPdfTemplateTags.PAGE_SEQUENCE);
        // 设置页面模板名称
        pageSequence.setAttribute(XEasyPdfTemplateAttributes.MASTER_REFERENCE, masterName);
        // 设置id
        Optional.ofNullable(this.param.getId()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.ID, v.intern()));
        // 设置字体名称
        Optional.ofNullable(this.param.getFontFamily()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.FONT_FAMILY, v.intern().toLowerCase()));
        // 设置字体样式
        Optional.ofNullable(this.param.getFontStyle()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.FONT_STYLE, v.intern().toLowerCase()));
        // 设置字体大小
        Optional.ofNullable(this.param.getFontSize()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE, v.intern().toLowerCase()));
        // 设置字体大小调整
        Optional.ofNullable(this.param.getFontSizeAdjust()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.FONT_SIZE_ADJUST, v.intern().toLowerCase()));
        // 设置字体重量
        Optional.ofNullable(this.param.getFontWeight()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.FONT_WEIGHT, v.intern().toLowerCase()));
        // 设置字体颜色
        Optional.ofNullable(this.param.getFontColor()).ifPresent(v -> pageSequence.setAttribute(XEasyPdfTemplateAttributes.COLOR, v.intern().toLowerCase()));
        // 如果包含页眉，则添加页眉
        if (this.param.hasHeader()) {
            // 添加页眉
            pageSequence.appendChild(this.addRegionBefore(document));
        }
        // 如果包含页脚，则添加页脚
        if (this.param.hasFooter()) {
            // 添加页脚
            pageSequence.appendChild(this.addRegionAfter(document));
        }
        // 添加页面主体区域
        pageSequence.appendChild(this.addRegionBody(document));
        // 返回页面序列
        return pageSequence;
    }

    /**
     * 添加页面主体区域
     *
     * @param document fo文档
     * @return 返回页面主体区域元素
     */
    private Element addRegionBody(Document document) {
        // 获取添加区域
        Element element = this.addRegion(document, XEasyPdfTemplateTags.FLOW, "xsl-region-body", this.param.getRegionBodyParam());
        // 如果不包含主体内容，则添加空内容
        if (!this.param.hasBody()) {
            // 添加空内容
            element.appendChild(document.createElement(XEasyPdfTemplateTags.BLOCK));
        }
        // 返回页面主体内容
        return element;
    }

    /**
     * 添加左侧栏
     *
     * @param document fo文档
     * @return 返回左侧栏元素
     */
    private Element addRegionStart(Document document) {
        // 返回左侧栏内容
        return this.addRegion(document, XEasyPdfTemplateTags.FLOW, "xsl-region-start", this.param.getRegionStartParam());
    }

    /**
     * 添加右侧栏
     *
     * @param document fo文档
     * @return 返回右侧栏元素
     */
    private Element addRegionEnd(Document document) {
        // 返回右侧栏内容
        return this.addRegion(document, XEasyPdfTemplateTags.FLOW, "xsl-region-end", this.param.getRegionEndParam());
    }

    /**
     * 添加页眉
     *
     * @param document fo文档
     * @return 返回页眉元素
     */
    private Element addRegionBefore(Document document) {
        // 返回页眉内容
        return this.addRegion(document, XEasyPdfTemplateTags.STATIC_CONTENT, "xsl-region-before", this.param.getRegionBeforeParam());
    }

    /**
     * 添加页脚
     *
     * @param document fo文档
     * @return 返回页脚元素
     */
    private Element addRegionAfter(Document document) {
        // 返回页脚内容
        return this.addRegion(document, XEasyPdfTemplateTags.STATIC_CONTENT, "xsl-region-after", this.param.getRegionAfterParam());
    }

    /**
     * 添加区域
     *
     * @param document fo文档
     * @param tagName  fo元素名称
     * @param flowName 流向名称
     * @param param    区域参数
     * @return 返回元素
     */
    private Element addRegion(Document document, String tagName, String flowName, XEasyPdfTemplateRegionBaseParam param) {
        // 创建元素
        Element element = document.createElement(tagName);
        // 设置页面流向
        element.setAttribute(XEasyPdfTemplateAttributes.FLOW_NAME, flowName);
        // 遍历区域组件列表
        param.getComponents().forEach(
                // 添加组件
                v -> element.appendChild(v.transform(document))
        );
        // 返回区域内容
        return element;
    }
}
