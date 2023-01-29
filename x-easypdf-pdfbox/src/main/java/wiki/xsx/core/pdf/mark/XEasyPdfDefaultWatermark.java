package wiki.xsx.core.pdf.mark;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.blend.BlendMode;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.doc.XEasyPdfDefaultFontStyle;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.util.XEasyPdfFontUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


/**
 * pdf水印组件
 *
 * @author xsx
 * @date 2020/3/25
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
public class XEasyPdfDefaultWatermark implements XEasyPdfWatermark {

    private static final long serialVersionUID = 9095729509463996323L;

    /**
     * pdf水印参数
     */
    private final XEasyPdfWatermarkParam param = new XEasyPdfWatermarkParam();

    /**
     * 有参构造
     *
     * @param text 水印文本
     */
    public XEasyPdfDefaultWatermark(String... text) {
        this.param.setTexts(Arrays.asList(text));
    }

    /**
     * 有参构造
     *
     * @param fontSize 字体大小
     * @param text     水印文本
     */
    public XEasyPdfDefaultWatermark(float fontSize, String... text) {
        this.param.setFontSize(Math.abs(fontSize)).setTexts(Arrays.asList(text));
    }

    /**
     * 设置字体路径
     *
     * @param fontPath 字体路径
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setFontPath(String fontPath) {
        this.param.setFontPath(fontPath);
        return this;
    }

    /**
     * 设置默认字体样式
     *
     * @param style 默认字体样式
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setDefaultFontStyle(XEasyPdfDefaultFontStyle style) {
        if (style != null) {
            this.param.setFontPath(style.getPath());
        }
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setFontSize(float fontSize) {
        this.param.setFontSize(Math.abs(fontSize));
        return this;
    }

    /**
     * 设置字体颜色
     *
     * @param fontColor 字体颜色
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setFontColor(Color fontColor) {
        if (fontColor != null) {
            this.param.setFontColor(fontColor);
        }
        return this;
    }

    /**
     * 设置文本透明度
     *
     * @param alpha 文本透明度（0.0-1.0之间）
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setAlpha(float alpha) {
        if (0 <= alpha && alpha <= 1) {
            this.param.setAlpha(alpha);
        }
        return this;
    }

    /**
     * 设置文本弧度
     *
     * @param radians 文本弧度
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setRadians(double radians) {
        this.param.setRadians(radians);
        return this;
    }

    /**
     * 设置水印文本
     *
     * @param text 水印文本
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setText(String... text) {
        this.param.setTexts(Arrays.asList(text));
        return this;
    }

    /**
     * 设置水印文本间距
     *
     * @param wordSpace 水印文本间距
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setWordSpace(float wordSpace) {
        this.param.setWordSpace(Math.abs(wordSpace));
        return this;
    }

    /**
     * 设置水印单行文本数
     *
     * @param wordCount 水印单行文本数
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setWordCount(int wordCount) {
        this.param.setWordCount(Math.abs(wordCount));
        return this;
    }

    /**
     * 设置水印文本行数
     *
     * @param wordLine 水印文本行数
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setWordLine(int wordLine) {
        this.param.setWordLine(Math.abs(wordLine));
        return this;
    }

    /**
     * 设置水印文本字符间隔
     *
     * @param characterSpacing 水印文本字符间隔
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setCharacterSpacing(float characterSpacing) {
        this.param.setCharacterSpacing(Math.abs(characterSpacing));
        return this;
    }

    /**
     * 设置水印文本行间距
     *
     * @param leading 水印文本行间距
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setLeading(float leading) {
        this.param.setLeading(Math.abs(leading));
        return this;
    }

    /**
     * 设置坐标
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY);
        return this;
    }

    /**
     * 开启外水印模式
     *
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark enableOuterMode() {
        this.param.setContentMode(XEasyPdfComponent.ContentMode.APPEND);
        return this;
    }

    /**
     * 开启内水印模式
     *
     * @return 返回页面水印组件
     */
    public XEasyPdfDefaultWatermark enableInnerMode() {
        this.param.setContentMode(XEasyPdfComponent.ContentMode.PREPEND);
        return this;
    }

    /**
     * 开启重置上下文
     *
     * @return 返回页面水印组件
     */
    @Override
    public XEasyPdfDefaultWatermark enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
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
        // 如果需要初始化，则进行参数初始化
        if (this.param.getIsNeedInit()) {
            // 初始化水印参数
            this.param.init(document, page);
        }
        // 获取pdfbox字体
        PDFont font = XEasyPdfFontUtil.loadFont(document, page, this.param.getFontPath(), true);
        // 获取X轴起始坐标
        Float beginX = this.param.getBeginX();
        // 获取Y轴起始坐标
        Float beginY = this.param.getBeginY();
        // 获取任务文档
        PDDocument target = document.getTarget();
        // 获取pdfBox页面列表
        List<PDPage> pageList = page.getPageList();
        // 遍历pdfBox页面列表
        for (PDPage pdPage : pageList) {
            // 执行绘制水印
            this.doDraw(target, pdPage, font);
            // 重置X轴起始坐标
            this.param.setBeginX(beginX);
            // 重置Y轴起始坐标
            this.param.setBeginY(beginY);
        }
        // 获取新的pdfBox页面列表
        pageList = page.getNewPageList();
        // 遍历pdfBox页面列表
        for (PDPage pdPage : pageList) {
            // 执行画水印
            this.doDraw(target, pdPage, font);
            // 重置X轴起始坐标
            this.param.setBeginX(beginX);
            // 重置Y轴起始坐标
            this.param.setBeginY(beginY);
        }
    }

    /**
     * 执行绘制
     *
     * @param document pdfbox文档
     * @param page     pdfbox页面
     * @param font     pdfbox字体
     */
    @SneakyThrows
    private void doDraw(PDDocument document, PDPage page, PDFont font) {
        // 获取页面高度
        float height = page.getMediaBox().getHeight();
        // 获取页面宽度
        float width = page.getMediaBox().getWidth();
        // 如果X轴起始坐标未初始化，则初始化X轴起始坐标为0
        if (this.param.getBeginX() == null) {
            // 初始化X轴起始坐标为0
            this.param.setBeginX(0F);
        }
        // 如果Y轴起始坐标未初始化，则初始化Y轴起始坐标为页面高度-字体大小
        if (this.param.getBeginY() == null) {
            // 初始化Y轴起始坐标 = 页面高度-字体大小
            this.param.setBeginY(height - this.param.getFontSize());
        }
        // 初始化pdfBox扩展图形对象
        PDExtendedGraphicsState state = new PDExtendedGraphicsState();
        // 设置文本透明度
        state.setNonStrokingAlphaConstant(this.param.getAlpha());
        // 设置透明度标记
        state.setAlphaSourceFlag(true);
        // 设置混合模式
        state.setBlendMode(BlendMode.MULTIPLY);
        // 初始化内容流
        PDPageContentStream cs = new PDPageContentStream(
                document,
                page,
                this.param.getContentMode().getMode(),
                true,
                this.param.getIsResetContext()
        );
        // 设置图形参数
        cs.setGraphicsStateParameters(state);
        // 设置字体颜色
        cs.setNonStrokingColor(this.param.getFontColor());
        // 设置字体
        cs.setFont(font, this.param.getFontSize());
        // 设置字符间隔
        cs.setCharacterSpacing(this.param.getCharacterSpacing());
        // 写入文本
        this.writeText(cs, width * 2);
        // 关闭内容流
        cs.close();
    }

    /**
     * 写入文本
     *
     * @param cs       内容流
     * @param mixWidth 最大宽度
     */
    private void writeText(PDPageContentStream cs, float mixWidth) {
        // 如果单行文本数不为空，则根据单行文本数写入
        if (this.param.getWordCount() != null) {
            // 根据单行文本数写入
            this.writeTextWithCount(cs);
        }
        // 否则根据最大宽度写入文本
        else {
            // 根据最大宽度写入文本
            this.writeTextWithWidth(cs, mixWidth);
        }
    }

    /**
     * 根据单行文本数写入
     *
     * @param cs 内容流
     */
    private void writeTextWithCount(PDPageContentStream cs) {
        // 循环写入文本
        for (int i = 0; i < this.param.getWordLine(); i++) {
            // 定义当前行文本数索引
            int index = 0;
            // 循环写入
            do {
                // 写入文本
                this.writeText(cs);
                // 重置X轴起始坐标为X轴起始+文本间隔
                this.param.setBeginX(this.param.getBeginX() + this.param.getWordSpace());
                // 文本数索引自增
                index++;
            }
            // 如果文本数索引小于指定文本数，则继续
            while (index < this.param.getWordCount());
            // 重置X轴起始坐标为0
            this.param.setBeginX(0F);
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小-行间距
            this.param.setBeginY(this.param.getBeginY() - this.param.getFontSize() * this.param.getTexts().size() - this.param.getLeading());
        }
    }

    /**
     * 根据最大宽度写入文本
     *
     * @param cs       内容流
     * @param mixWidth 最大宽度
     */
    private void writeTextWithWidth(PDPageContentStream cs, Float mixWidth) {
        // 循环写入文本
        for (int i = 0; i < this.param.getWordLine(); i++) {
            // 循环写入
            do {
                // 写入文本
                this.writeText(cs);
                // 重置X轴起始坐标为X轴起始+文本间隔
                this.param.setBeginX(this.param.getBeginX() + this.param.getWordSpace());
            }
            // 如果X轴起始坐标小于页面宽度，则继续
            while (this.param.getBeginX() < mixWidth);
            // 重置X轴起始坐标为0
            this.param.setBeginX(0F);
            // 重置Y轴起始坐标为Y轴起始坐标-字体大小-行间距
            this.param.setBeginY(this.param.getBeginY() - this.param.getFontSize() * this.param.getTexts().size() - this.param.getLeading());
        }
    }

    /**
     * 写入文本
     *
     * @param cs 内容流
     */
    private void writeText(PDPageContentStream cs) {
        // 获取当前Y轴坐标
        float beginY = this.param.getBeginY();
        // 文本输入
        for (String text : this.param.getTexts()) {
            // 写入文本并重置当前Y轴坐标
            beginY = this.writeText(cs, text, beginY);
        }
    }

    /**
     * 写入文本
     *
     * @param cs     内容流
     * @param text   待写入文本
     * @param beginY 当前Y轴起始坐标
     * @return 返回当前Y轴起始坐标
     */
    @SneakyThrows
    private float writeText(PDPageContentStream cs, String text, float beginY) {
        // 开启文本输入
        cs.beginText();
        // 设置文本弧度
        cs.setTextMatrix(Matrix.getRotateInstance(Math.toRadians(this.param.getRadians()), 0F, 0F));
        // 设置文本坐标
        cs.newLineAtOffset(this.param.getBeginX(), beginY);
        // 文本输入
        cs.showText(text);
        // 结束文本写入
        cs.endText();
        // 返回当前Y轴坐标
        return beginY - this.param.getFontSize();
    }
}
