package wiki.xsx.core.pdf.component.text;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.XEasyPdfPagingCondition;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.doc.XEasyPdfPositionStyle;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;
import wiki.xsx.core.pdf.util.XEasyPdfTextUtil;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pdf文本参数
 *
 * @author xsx
 * @date 2020/3/9
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
@Data
@EqualsAndHashCode
@Accessors(chain = true)
class XEasyPdfTextParam implements Serializable {

    private static final long serialVersionUID = 8028404070423440838L;

    /**
     * 内容模式
     */
    private XEasyPdfComponent.ContentMode contentMode;
    /**
     * 是否重置上下文
     */
    private Boolean isResetContext;
    /**
     * 字体路径
     */
    private String fontPath;
    /**
     * 字体大小
     */
    private Float fontSize = 12F;
    /**
     * 字体缩放比例
     */
    private Float fontScaleRatio;
    /**
     * 字体高度
     */
    private Float fontHeight;
    /**
     * 行间距
     */
    private Float leading = 5F;
    /**
     * 文本间隔
     */
    private Float characterSpacing = 0F;
    /**
     * 左边距
     */
    private Float marginLeft = 0F;
    /**
     * 右边距
     */
    private Float marginRight = 0F;
    /**
     * 上边距
     */
    private Float marginTop = 0F;
    /**
     * 下边距
     */
    private Float marginBottom = 0F;
    /**
     * 最大宽度
     */
    private Float maxWidth;
    /**
     * 最大高度
     */
    private Float maxHeight;
    /**
     * 文本缩进值
     */
    private Integer indent;
    /**
     * 待添加文本
     */
    private String text;
    /**
     * 拆分后的待添加文本列表
     */
    private List<String> splitTextList;
    /**
     * 拆分后的待添加文本列表(模板)
     */
    private List<String> splitTemplateTextList;
    /**
     * 替换字符字典
     */
    private Map<String, String> replaceCharacterMap = this.initReplaceCharacterMap();
    /**
     * 水平样式（居左、居中、居右）
     */
    private XEasyPdfPositionStyle horizontalStyle = XEasyPdfPositionStyle.LEFT;
    /**
     * 垂直样式（居上、居中、居下）
     */
    private XEasyPdfPositionStyle verticalStyle = XEasyPdfPositionStyle.TOP;
    /**
     * 页面X轴起始坐标
     */
    private Float beginX;
    /**
     * 页面Y轴起始坐标
     */
    private Float beginY;
    /**
     * 字体颜色
     */
    private Color fontColor = Color.BLACK;
    /**
     * 下划线颜色
     */
    private Color underlineColor;
    /**
     * 删除线颜色
     */
    private Color deleteLineColor;
    /**
     * 高亮颜色
     */
    private Color highlightColor = Color.ORANGE;
    /**
     * 下划线宽度
     */
    private Float underlineWidth = 1F;
    /**
     * 删除线宽度
     */
    private Float deleteLineWidth = 1F;
    /**
     * 透明度（值越小越透明，0.0-1.0）
     */
    private Float alpha = 1.0F;
    /**
     * 文本弧度
     */
    private Double radians = 0D;
    /**
     * 超链接地址
     */
    private String linkUrl;
    /**
     * 评论
     */
    private String comment;
    /**
     * 是否使用自身样式
     */
    private Boolean useSelfStyle = Boolean.FALSE;
    /**
     * 是否换行
     */
    private Boolean isNewLine = Boolean.TRUE;
    /**
     * 是否分页检查
     */
    private Boolean checkPage = Boolean.TRUE;
    /**
     * 是否文本追加
     */
    private Boolean isTextAppend = Boolean.FALSE;
    /**
     * 是否子组件
     */
    private Boolean isChildComponent = Boolean.FALSE;
    /**
     * 是否添加下划线
     */
    private Boolean isUnderline = Boolean.FALSE;
    /**
     * 是否添加删除线
     */
    private Boolean isDeleteLine = Boolean.FALSE;
    /**
     * 是否高亮显示
     */
    private Boolean isHighlight = Boolean.FALSE;
    /**
     * 是否整行旋转
     */
    private Boolean isRotateLine = Boolean.FALSE;
    /**
     * 是否需要初始化
     */
    private Boolean isNeedInitialize = Boolean.TRUE;
    /**
     * 渲染模式
     */
    private XEasypdfTextRenderingMode renderingMode;
    /**
     * 分页条件
     */
    private XEasyPdfPagingCondition pagingCondition;

    /**
     * 获取宽度
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回文本宽度
     */
    float getWidth(XEasyPdfDocument document, XEasyPdfPage page) {
        if (this.maxWidth != null) {
            return this.maxWidth;
        }
        this.init(document, page);
        return this.maxWidth;
    }

    /**
     * 获取高度
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回文本高度
     */
    float getHeight(XEasyPdfDocument document, XEasyPdfPage page) {
        if (this.maxHeight != null) {
            return this.maxHeight;
        }
        this.init(document, page);
        return (this.fontHeight + this.leading) * this.splitTextList.size() - this.marginTop - this.marginBottom;
    }

    /**
     * 初始化
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    void init(XEasyPdfDocument document, XEasyPdfPage page) {
        // 获取pdfBox最新页面尺寸
        PDRectangle rectangle = page.getLastPage().getMediaBox();
        // 如果最大宽度未初始化，则进行初始化
        if (this.maxWidth == null) {
            // 初始化最大宽度，最大宽度 = 页面宽度
            this.maxWidth = rectangle.getWidth();
        }
        // 如果渲染模式未初始化，则初始化为正常
        if (this.renderingMode == null) {
            // 初始化为正常
            this.renderingMode = XEasypdfTextRenderingMode.NORMAL;
        }
        // 如果内容模式未初始化，则初始化为页面内容模式
        if (this.contentMode == null) {
            // 初始化为页面内容模式
            this.contentMode = page.getContentMode();
        }
        // 如果是否重置上下文未初始化，则初始化为页面是否重置上下文
        if (this.isResetContext == null) {
            // 初始化为页面是否重置上下文
            this.isResetContext = page.isResetContext();
        }
        // 如果字体路径未初始化，则初始化为页面字体路径
        if (this.fontPath == null) {
            // 初始化为页面字体路径
            this.fontPath = page.getFontPath();
        }
        // 如果字体缩放比例未初始化，则初始化为1F
        if (this.fontScaleRatio == null) {
            // 初始化为1F
            this.fontScaleRatio = 1F;
        }
        // 初始化字体高度
        this.fontHeight = this.fontSize;
        // 如果下划线颜色为空，则重置为字体颜色
        if (this.underlineColor == null) {
            // 重置下划线颜色为字体颜色
            this.underlineColor = this.fontColor;
        }
        // 如果删除线颜色为空，则重置为字体颜色
        if (this.deleteLineColor == null) {
            // 重置下划线颜色为字体颜色
            this.deleteLineColor = this.fontColor;
        }
        // 初始化待添加文本列表
        this.initTextList(document, page);
        // 如果最大高度未初始化，则进行初始化
        if (this.maxHeight == null) {
            // 初始化最大高度 = (字体高度+行间距) * 文本行数 - 上边距 - 下边距
            this.maxHeight = (this.fontHeight + this.leading) * this.splitTextList.size() - this.marginTop - this.marginBottom;
        }
        // 初始化Y轴起始坐标
        this.initBeginY(document, page);
    }

    /**
     * 初始化X轴起始坐标
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @param text     待添加文本
     * @return 返回X轴起始坐标
     */
    @SneakyThrows
    Float initBeginX(XEasyPdfDocument document, XEasyPdfPage page, String text) {
        // 定义X轴坐标
        Float x = this.beginX;
        // 如果页面X轴起始坐标未初始化，则进行初始化
        if (x == null) {
            // 如果为文本追加，则初始化为左边距+页面X轴起始坐标
            if (this.isTextAppend) {
                // 初始化为左边距+页面X轴起始坐标
                x = this.marginLeft + (page.getPageX() == null ? 0F : page.getPageX());
            }
            // 否则判断文本样式
            else {
                // 初始化样式
                x = this.initBeginXForStyle(document, page, text);
            }
        }
        // 否则重置X轴坐标=当前坐标+样式坐标
        else {
            // 重置X轴坐标=当前坐标+样式坐标
            x = x + this.initBeginXForStyle(document, page, text);
        }
        return x;
    }

    /**
     * 初始化X轴起始坐标样式
     *
     * @param text 待添加文本
     * @return 返回X轴起始坐标
     */
    @SneakyThrows
    float initBeginXForStyle(XEasyPdfDocument document, XEasyPdfPage page, String text) {
        // 如果为居左，则初始化为左边距
        if (this.horizontalStyle == XEasyPdfPositionStyle.LEFT) {
            // 初始化为左边距
            return this.marginLeft;
        }
        // 获取字体
        PDFont font = XEasyPdfFontUtil.loadFont(document, page, this.fontPath, true);
        // 如果为居中，则初始化为(最大宽度-左边距-右边距-文本宽度)/2
        if (this.horizontalStyle == XEasyPdfPositionStyle.CENTER) {
            // 初始化为(最大宽度-文本宽度)/2
            return (this.maxWidth - ((this.fontSize * font.getStringWidth(text) / 1000) + this.characterSpacing * text.length())) / 2 + this.marginLeft - this.marginRight;
        }
        // 否则为居右，初始化为最大宽度-右边距-文本宽度
        return this.maxWidth - ((this.fontSize * font.getStringWidth(text) / 1000) + this.characterSpacing * text.length()) + this.marginLeft - this.marginRight;
    }

    /**
     * 初始化替换字符字典
     *
     * @return 返回替换字符字典
     */
    private Map<String, String> initReplaceCharacterMap() {
        // 创建字典
        Map<String, String> map = new HashMap<>(8);
        // 添加替换字符(换行符(LF))
        map.put("\n", "");
        // 添加替换字符(回车符(CR))
        map.put("\r", "");
        // 添加替换字符(水平制表符(HT))
        map.put("\t", "");
        // 添加替换字符(退格符(BS))
        map.put("\b", "");
        // 添加替换字符(换页符(FF))
        map.put("\f", "");
        // 返回字典
        return map;
    }

    /**
     * 初始化Y轴起始坐标
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    private void initBeginY(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果Y轴起始坐标不为空，则重置Y轴起始坐标为Y轴起始坐标-上边距+下边距
        if (this.beginY != null) {
            // 重置Y轴起始坐标为Y轴起始坐标-上边距+下边距
            this.beginY = this.beginY - this.marginTop + this.marginBottom;
            return;
        }
        // 获取页面Y轴坐标
        Float pageY = page.getPageY();
        // 如果页面Y轴坐标为空，则获取最新页面高度
        if (pageY == null) {
            // 重置页面Y轴坐标为最新页面高度
            pageY = page.getLastPage().getMediaBox().getHeight();
        }
        // 如果为文本追加模式，则重置Y轴起始坐标为页面Y轴坐标-上边距+下边距
        if (this.isTextAppend) {
            // 重置Y轴起始坐标为页面Y轴坐标-上边距+下边距
            this.beginY = pageY - this.marginTop + this.marginBottom;
            return;
        }
        // 如果为居上样式，则重置Y轴起始坐标为页面Y轴坐标-字体高度-行间距-上边距+下边距
        if (this.verticalStyle == XEasyPdfPositionStyle.TOP) {
            // 重置Y轴起始坐标为页面Y轴坐标-字体高度-行间距-上边距+下边距
            this.beginY = pageY - this.fontHeight - this.leading - this.marginTop + this.marginBottom;
            return;
        }
        // 如果为居中样式，则重置Y轴起始坐标为页面Y轴坐标-(页面Y轴坐标-最大高度)/2-行间距-上边距+下边距
        if (this.verticalStyle == XEasyPdfPositionStyle.CENTER) {
            // 重置Y轴起始坐标为页面Y轴坐标-(页面Y轴坐标-最大高度)/2-行间距-上边距+下边距
            this.beginY = pageY - (pageY - this.maxHeight) / 2 - this.leading - this.marginTop + this.marginBottom;
            return;
        }
        // 如果为居下样式，则判断是否包含页脚
        if (this.verticalStyle == XEasyPdfPositionStyle.BOTTOM) {
            // 定义页脚高度
            float footerHeight = 0F;
            // 如果允许添加页脚，且页脚不为空则初始化页脚高度
            if (page.isAllowFooter() && page.getFooter() != null) {
                // 初始化页脚高度
                footerHeight = page.getFooter().getHeight(document, page);
            }
            // 如果页面Y轴坐标-最大高度小于页脚高度，则重置Y轴起始坐标为页面Y轴坐标-字体高度-行间距-上边距+下边距
            if (pageY - this.maxHeight < footerHeight) {
                // 重置Y轴起始坐标为页面Y轴坐标-字体高度-行间距-上边距+下边距
                this.beginY = pageY - this.fontHeight - this.leading - this.marginTop + this.marginBottom;
            }
            // 否则重置Y轴起始坐标为页脚高度+最大高度-上边距+下边距+0.01(防止分页)
            else {
                this.beginY = footerHeight + this.maxHeight - this.marginTop + this.marginBottom + 0.01F;
            }
        }
    }

    /**
     * 初始化文本X轴坐标
     *
     * @param page pdf页面
     * @return 返回文本X轴坐标
     */
    private float initTextX(XEasyPdfPage page) {
        // 定义X轴坐标
        float x = 0F;
        // 如果缩进值不为空，则重置X轴起始坐标
        if (this.indent != null) {
            // 重置X轴起始坐标 = X轴起始坐标 + 缩进值 * (字体大小 + 文本间隔)
            x = x + this.indent * (this.fontSize + this.characterSpacing);
        }
        // 开启文本追加
        if (this.isTextAppend) {
            // 如果X轴起始坐标为空，则初始化X轴坐标为页面X轴坐标
            if (this.beginX == null) {
                // 初始化X轴坐标为页面X轴坐标+左边距
                x = x + (page.getPageX() == null ? this.marginLeft : page.getPageX() + this.marginLeft);
            }
            // 否则初始化为X轴起始坐标
            else {
                // 初始化X轴坐标 = X轴坐标 + X轴起始坐标
                x = x + this.beginX;
            }
        }
        // 未开启文本追加
        else {
            // 初始化X轴坐标 = X轴坐标 + 左边距
            x = x + this.marginLeft;
        }
        return x;
    }

    /**
     * 初始化待添加文本列表
     *
     * @param page pdf页面
     */
    private void initTextList(XEasyPdfDocument document, XEasyPdfPage page) {
        // 如果拆分后的待添加文本列表未初始化，则进行初始化
        if (this.splitTextList == null) {
            // 处理待添加文本
            this.text = this.processText(this.text);
            // 初始化X轴坐标
            float x = this.initTextX(page);
            // 获取字体
            PDFont font = XEasyPdfFontUtil.loadFont(document, page, this.fontPath, true);
            // 获取第一行文本
            String firstLineText = XEasyPdfTextUtil.splitText(
                    // 待输入文本
                    this.text,
                    // 行宽度 = 最大宽度 - 左边距 - 右边距
                    this.maxWidth - x - this.marginRight,
                    // 字体
                    font,
                    // 字体大小 * 字体缩放比例
                    this.fontSize * this.fontScaleRatio,
                    // 文本间隔
                    this.characterSpacing
            );
            // 初始待添加文本列表
            this.initTextList(page, font, firstLineText);
        }
        // 否则处理待添加文本列表
        else {
            // 处理待添加文本列表
            this.splitTextList = this.processTextList(this.splitTextList);
        }
        // 如果模板列表未初始化，则进行初始化
        if (this.splitTemplateTextList == null) {
            // 模板列表重置为待添加文本列表
            this.splitTemplateTextList = new ArrayList<>(this.splitTextList);
        }
    }

    /**
     * 初始化待添加文本列表
     *
     * @param page          pdf页面
     * @param font          pdfbox字体
     * @param firstLineText 第一行文本
     */
    private void initTextList(XEasyPdfPage page, PDFont font, String firstLineText) {
        // 如果第一行文本不为空，则添加文本列表
        if (firstLineText != null) {
            // 初始化待添加文本列表
            this.splitTextList = new ArrayList<>(128);
            // 添加第一行文本
            this.splitTextList.add(firstLineText);
            // 第一行文本长度小于待输入文本，则继续拆分剩余文本
            if (firstLineText.length() < this.text.length()) {
                // 添加剩余文本
                this.splitTextList.addAll(
                        XEasyPdfTextUtil.splitLines(
                                // 截取剩余待输入文本
                                this.text.substring(firstLineText.length()),
                                // 行宽度 = 最大宽度 - 左边距 - 右边距
                                this.maxWidth - this.marginLeft - this.marginRight,
                                // 字体
                                font,
                                // 字体大小 * 字体缩放比例
                                this.fontSize * this.fontScaleRatio,
                                // 文本间隔
                                this.characterSpacing
                        )
                );
            }
        }
        // 否则进行文本全拆分（换行）
        else {
            // 初始化待添加文本列表
            this.splitTextList = XEasyPdfTextUtil.splitLines(
                    // 待输入文本
                    this.text,
                    // 行宽度 = 最大宽度 - 左边距 - 右边距
                    this.maxWidth - this.marginLeft - this.marginRight,
                    // 字体
                    font,
                    // 字体大小 * 字体缩放比例
                    this.fontSize * this.fontScaleRatio,
                    // 文本间隔
                    this.characterSpacing
            );
            // 重置页面X轴起始坐标（换行）
            this.beginX = this.marginLeft;
            // 重置页面X轴
            page.setPageX(null);
        }
    }

    /**
     * 处理文本
     *
     * @param text 待处理文本
     * @return 返回处理后文本
     */
    private String processText(String text) {
        // 如果待处理文本不为空，则进行处理
        if (XEasyPdfTextUtil.isNotBlank(text)) {
            // 替换文本
            return XEasyPdfTextUtil.replaceAll(text, this.replaceCharacterMap);
        }
        return text;
    }

    /**
     * 处理文本列表
     *
     * @param list 待处理文本列表
     * @return 返回处理后文本列表
     */
    private List<String> processTextList(List<String> list) {
        // 如果待处理文本列表为空，则返回待处理文本列表
        if (list == null || list.isEmpty()) {
            // 返回待处理文本列表
            return list;
        }
        // 创建替换文本列表
        List<String> replaceList = new ArrayList<>(this.splitTextList.size());
        // 遍历待处理文本列表
        for (String text : list) {
            // 添加替换文本
            replaceList.add(this.processText(text));
        }
        // 返回替换文本列表
        return replaceList;
    }
}
