package wiki.xsx.core.pdf.template.component;

import lombok.Data;

import java.awt.*;

/**
 * pdf模板组件公共参数
 *
 * @author xsx
 * @date 2022/8/11
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
public class XEasyPdfTemplateComponentParam {
    /**
     * id
     */
    protected String id;
    /**
     * 上下左右填充
     */
    protected String padding;
    /**
     * 上填充
     */
    protected String paddingTop;
    /**
     * 下填充
     */
    protected String paddingBottom;
    /**
     * 左填充
     */
    protected String paddingLeft;
    /**
     * 右填充
     */
    protected String paddingRight;
    /**
     * 上下左右边距
     */
    protected String margin;
    /**
     * 上边距
     */
    protected String marginTop;
    /**
     * 下边距
     */
    protected String marginBottom;
    /**
     * 左边距
     */
    protected String marginLeft;
    /**
     * 右边距
     */
    protected String marginRight;
    /**
     * 是否包含边框
     */
    protected Boolean hasBorder;
    /**
     * 水平样式
     * <p>left：居左</p>
     * <p>center：居中</p>
     * <p>right：居右</p>
     * <p>right：居右</p>
     * <p>justify：两端对齐</p>
     */
    protected String horizontalStyle;
    /**
     * 垂直样式
     * <p>before：居上</p>
     * <p>center：居中</p>
     * <p>after：居下</p>
     */
    protected String verticalStyle;
    /**
     * 颜色
     */
    protected Color color;
    /**
     * 语言
     *
     * @see <a href="https://www.runoob.com/tags/html-language-codes.html">ISO 639-1 语言代码</a>
     */
    protected String language;
    /**
     * 行间距
     */
    protected String leading;
    /**
     * 字符间距
     */
    protected String letterSpacing;
    /**
     * 单词间距
     */
    protected String wordSpacing;
    /**
     * 单词换行
     * <p>normal：正常</p>
     * <p>break-all：字符换行</p>
     * <p>keep-all：整词换行</p>
     */
    protected String wordBreak;
    /**
     * 空白空间
     * <p>normal：正常</p>
     * <p>pre：保留空格</p>
     * <p>nowrap：合并空格</p>
     */
    protected String whiteSpace;
    /**
     * 文本缩进
     */
    protected String textIndent;
    /**
     * 段前缩进
     */
    protected String startIndent;
    /**
     * 段后缩进
     */
    protected String endIndent;
    /**
     * 段前空白
     */
    protected String spaceBefore;
    /**
     * 段后空白
     */
    protected String spaceAfter;
    /**
     * 字体名称
     */
    protected String fontFamily;
    /**
     * 字体样式
     * <p>normal：正常</p>
     * <p>oblique：斜体</p>
     * <p>italic：斜体</p>
     * <p>backslant：斜体</p>
     */
    protected String fontStyle;
    /**
     * 字体大小
     */
    protected String fontSize;
    /**
     * 字体大小调整
     */
    protected String fontSizeAdjust;
    /**
     * 字体重量
     * <p>normal：正常（400）</p>
     * <p>bold：粗体（700）</p>
     * <p>bolder：加粗（900）</p>
     * <p>lighter：细体（100）</p>
     */
    protected String fontWeight;
    /**
     * 分页符-前
     * <p>auto：自动</p>
     * <p>column：分列</p>
     * <p>page：分页</p>
     * <p>even-page：在元素之前强制分页一次或两个，以便下一页将成为偶数页</p>
     * <p>odd-page：在元素之前强制分页一次或两个，以便下一页将成为奇数页</p>
     */
    protected String breakBefore;
    /**
     * 分页符-后
     * <p>auto：自动</p>
     * <p>column：分列</p>
     * <p>page：分页</p>
     * <p>even-page：在元素之后强制分页一次或两个，以便下一页将成为偶数页</p>
     * <p>odd-page：在元素之后强制分页一次或两个，以便下一页将成为奇数页</p>
     */
    protected String breakAfter;
    /**
     * 分页时保持
     * <p>auto：自动</p>
     * <p>always：总是</p>
     */
    protected String keepTogether;
    /**
     * 分页时与上一个元素保持
     * <p>auto：自动</p>
     * <p>always：总是</p>
     */
    protected String keepWithPrevious;
    /**
     * 分页时与下一个元素保持
     * <p>auto：自动</p>
     * <p>always：总是</p>
     */
    protected String keepWithNext;
}
