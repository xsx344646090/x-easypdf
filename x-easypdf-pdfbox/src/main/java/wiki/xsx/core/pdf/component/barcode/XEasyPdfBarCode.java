package wiki.xsx.core.pdf.component.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import wiki.xsx.core.pdf.component.XEasyPdfComponent;
import wiki.xsx.core.pdf.component.image.XEasyPdfImageType;
import wiki.xsx.core.pdf.doc.XEasyPdfDocument;
import wiki.xsx.core.pdf.doc.XEasyPdfPage;
import wiki.xsx.core.pdf.util.XEasyPdfImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * pdf条形码(一维码/二维码)组件
 *
 * @author xsx
 * @date 2021/12/20
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
public class XEasyPdfBarCode implements XEasyPdfComponent {

    private static final long serialVersionUID = 5127121806423508565L;

    /**
     * 条形码参数
     */
    private final XEasyPdfBarCodeParam param = new XEasyPdfBarCodeParam();

    /**
     * 有参构造
     *
     * @param codeType 条形码类型
     * @param content  条形码内容
     */
    public XEasyPdfBarCode(CodeType codeType, String content) {
        this(codeType, content, null);
    }

    /**
     * 有参构造
     *
     * @param codeType 条形码类型
     * @param content  条形码内容
     * @param words    条形码文字
     */
    public XEasyPdfBarCode(CodeType codeType, String content, String words) {
        if (codeType != null) {
            this.param.setCodeType(codeType);
        }
        this.param.setContent(content).setWords(words);
    }

    /**
     * 有参构造
     *
     * @param codeType 条形码类型
     * @param content  条形码内容
     * @param beginX   X轴起始坐标
     * @param beginY   Y轴起始坐标
     */
    public XEasyPdfBarCode(CodeType codeType, String content, float beginX, float beginY) {
        this(codeType, content, null, beginX, beginY);
    }

    /**
     * 有参构造
     *
     * @param codeType 条形码类型
     * @param content  条形码内容
     * @param words    条形码文字
     * @param beginX   X轴起始坐标
     * @param beginY   Y轴起始坐标
     */
    public XEasyPdfBarCode(CodeType codeType, String content, String words, float beginX, float beginY) {
        if (codeType != null) {
            this.param.setCodeType(codeType);
        }
        this.param.setContent(content).setWords(words).setBeginX(beginX).setBeginY(beginY);
    }

    /**
     * 设置定位
     *
     * @param beginX X轴起始坐标
     * @param beginY Y轴起始坐标
     * @return 返回条形码组件
     */
    @Override
    public XEasyPdfBarCode setPosition(float beginX, float beginY) {
        this.param.setBeginX(beginX).setBeginY(beginY);
        return this;
    }

    /**
     * 设置宽度（图像显示宽度）
     *
     * @param width 宽度
     * @return 返回条形码组件
     */
    @Override
    public XEasyPdfBarCode setWidth(float width) {
        this.param.setImageWidth((int) Math.abs(width));
        return this;
    }

    /**
     * 设置高度（图像显示宽度）
     *
     * @param height 高度
     * @return 返回条形码组件
     */
    @Override
    public XEasyPdfBarCode setHeight(float height) {
        this.param.setImageHeight((int) Math.abs(height));
        return this;
    }

    /**
     * 设置最大宽度（图像实际宽度）
     *
     * @param width 宽度
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setMaxWidth(float width) {
        this.param.setImageMaxWidth((int) Math.abs(width));
        return this;
    }

    /**
     * 设置最大高度（图像实际高度）
     *
     * @param height 高度
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setMaxHeight(float height) {
        this.param.setImageMaxHeight((int) Math.abs(height));
        return this;
    }

    /**
     * 设置内容模式
     *
     * @param mode 内容模式
     * @return 返回条形码组件
     */
    @Override
    public XEasyPdfBarCode setContentMode(ContentMode mode) {
        if (mode != null) {
            this.param.setContentMode(mode);
        }
        return this;
    }

    /**
     * 设置条形码类型
     *
     * @param codeType 条形码类型
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setCodeType(CodeType codeType) {
        this.param.setCodeType(codeType);
        return this;
    }

    /**
     * 设置条形码边距
     *
     * @param codeMargin 条形码边距
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setCodeMargin(int codeMargin) {
        this.param.setCodeMargin(Math.abs(codeMargin));
        return this;
    }

    /**
     * 设置纠错级别
     *
     * @param errorLevel 条形码纠错级别
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setErrorLevel(ErrorLevel errorLevel) {
        if (errorLevel != null) {
            this.param.setEncodeHints(EncodeHintType.ERROR_CORRECTION, errorLevel.level);
        }
        return this;
    }

    /**
     * 设置二维码版本（仅二维码有效）
     *
     * @param version 二维码版本
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setQrVersion(int version) {
        final int min = 0;
        final int max = 41;
        if (version > min && version < max) {
            this.param.setEncodeHints(EncodeHintType.QR_VERSION, version);
        } else {
            throw new IllegalArgumentException("the version must be between 1 and 40");
        }
        return this;
    }

    /**
     * 设置条形码内容
     *
     * @param content 条形码内容
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setContent(String content) {
        this.param.setContent(content);
        return this;
    }

    /**
     * 设置条形码前景颜色
     *
     * @param onColor 条形码前景颜色
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setOnColor(Color onColor) {
        if (onColor != null) {
            this.param.setOnColor(onColor);
        }
        return this;
    }

    /**
     * 设置条形码背景颜色
     *
     * @param offColor 条形码背景颜色
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setOffColor(Color offColor) {
        if (offColor != null) {
            this.param.setOffColor(offColor);
        }
        return this;
    }

    /**
     * 设置条形码文字
     *
     * @param words 条形码文字
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setWords(String words) {
        this.param.setWords(words);
        return this;
    }

    /**
     * 设置条形码文字颜色
     *
     * @param wordsColor 条形码文字颜色
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setWordsColor(Color wordsColor) {
        if (wordsColor != null) {
            this.param.setWordsColor(wordsColor);
        }
        return this;
    }

    /**
     * 设置条形码文字样式
     *
     * @param wordsStyle 条形码文字样式
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setWordsStyle(WordsStyle wordsStyle) {
        if (wordsStyle != null) {
            this.param.setWordsStyle(wordsStyle);
        }
        return this;
    }

    /**
     * 设置条形码文字大小
     *
     * @param wordsSize 条形码文字大小
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setWordsSize(int wordsSize) {
        this.param.setWordsSize(Math.abs(wordsSize));
        return this;
    }

    /**
     * 设置条形码旋转弧度
     *
     * @param radians 条形码旋转弧度
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setRadians(double radians) {
        radians = radians % 360;
        if (radians != 0) {
            if (radians < 0) {
                radians += 360;
            }
            this.param.setRadians(radians);
        }
        return this;
    }

    /**
     * 设置左边距
     *
     * @param marginLeft 左边距
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setMarginLeft(float marginLeft) {
        this.param.setMarginLeft(marginLeft);
        return this;
    }

    /**
     * 设置右边距
     *
     * @param marginRight 右边距
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setMarginRight(float marginRight) {
        this.param.setMarginRight(marginRight);
        return this;
    }

    /**
     * 设置上边距
     *
     * @param marginTop 上边距
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setMarginTop(float marginTop) {
        this.param.setMarginTop(marginTop);
        return this;
    }

    /**
     * 设置下边距
     *
     * @param marginBottom 下边距
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode setMarginBottom(float marginBottom) {
        this.param.setMarginBottom(marginBottom);
        return this;
    }

    /**
     * 开启文字显示
     *
     * @return 返回条形码组件
     */
    public XEasyPdfBarCode enableShowWords() {
        this.param.setIsShowWords(Boolean.TRUE);
        return this;
    }

    /**
     * 开启上下文重置
     *
     * @return 返回条形码组件
     */
    @Override
    public XEasyPdfBarCode enableResetContext() {
        this.param.setIsResetContext(Boolean.TRUE);
        return this;
    }

    /**
     * 绘制
     *
     * @param document pdf文档
     * @param page     pdf页面
     */
    @SneakyThrows
    @Override
    public void draw(XEasyPdfDocument document, XEasyPdfPage page) {
        // 初始化参数
        this.param.init(document, page);
        // 获取任务文档
        PDDocument target = document.getTarget();
        // 获取条形码图片
        BufferedImage bufferedImage = this.getBarCodeImage(this.createBitMatrix());
        // 创建pdfBox图片
        PDImageXObject pdImage = PDImageXObject.createFromByteArray(
                target,
                XEasyPdfImageUtil.toBytes(bufferedImage, XEasyPdfImageType.PNG.name()),
                XEasyPdfImageType.PNG.name()
        );
        // 新建内容流
        PDPageContentStream contentStream = new PDPageContentStream(
                target,
                page.getLastPage(),
                this.param.getContentMode().getMode(),
                true,
                this.param.getIsResetContext()
        );
        // 添加图片
        contentStream.drawImage(pdImage, this.param.getBeginX(), this.param.getBeginY(), this.param.getImageWidth(), this.param.getImageHeight());
        // 关闭内容流
        contentStream.close();
        // 如果允许页面重置定位，则进行重置
        if (page.isAllowResetPosition()) {
            // 设置文档页面X轴坐标Y轴坐标
            page.setPageX(this.param.getBeginX()).setPageY(this.param.getBeginY());
        }
    }

    /**
     * 初始化（公共参数）
     */
    public void init() {
        // 初始化参数
        this.param.init();
    }

    /**
     * 创建位矩阵
     * @return 返回位矩阵
     */
    @SneakyThrows
    public BitMatrix createBitMatrix() {
        // 编码
        return new MultiFormatWriter().encode(
                this.param.getContent(),
                this.param.getCodeType().codeFormat,
                this.param.getImageMaxWidth(),
                this.param.getImageMaxHeight(),
                this.param.getEncodeHints()
        );
    }

    /**
     * 获取条形码图片
     *
     * @param matrix 位矩阵
     * @return 返回条形码图片
     */
    public BufferedImage getBarCodeImage(BitMatrix matrix) {
        // 获取图片
        BufferedImage bufferedImage = this.toBufferedImage(matrix);
        // 如果显示文字，则添加图片文字
        if (this.param.getIsShowWords()) {
            // 添加图片文字
            bufferedImage = this.addImageWords(bufferedImage);
        }
        // 如果需要旋转且不旋转文字，则重置图片为旋转后的图片
        if (this.param.isRotate()) {
            // 重置图片为旋转后的图片
            bufferedImage = XEasyPdfImageUtil.rotate(bufferedImage, this.param.getRadians());
            // 重置Y轴起始坐标
            this.param.resetBeginY(bufferedImage.getHeight());
        }
        return bufferedImage;
    }

    /**
     * 获取高度
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @return 返回高度
     */
    public float getHeight(XEasyPdfDocument document, XEasyPdfPage page) {
        if (this.param.getImageHeight() == null) {
            this.param.init(document, page);
        }
        return this.param.getImageHeight();
    }

    /**
     * 转图片
     *
     * @param matrix 位矩阵
     * @return 返回图片
     */
    @SneakyThrows
    private BufferedImage toBufferedImage(BitMatrix matrix) {
        // 获取前景色
        final int onColor = this.param.getOnColor().getRGB();
        // 获取背景色
        final int offColor = this.param.getOffColor().getRGB();
        // 获取宽度
        int width = matrix.getWidth();
        // 获取高度
        int height = matrix.getHeight();
        // 定义图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // 定义行像素
        int[] rowPixels = new int[width];
        // 定义位数组
        BitArray row = new BitArray(width);
        // 循环高度
        for (int y = 0; y < height; y++) {
            // 获取位数组
            row = matrix.getRow(y, row);
            // 循环宽度
            for (int x = 0; x < width; x++) {
                // 初始化行像素
                rowPixels[x] = row.get(x) ? onColor : offColor;
            }
            // 设置RGB
            image.setRGB(0, y, width, 1, rowPixels, 0, width);
        }
        return image;
    }

    /**
     * 添加图像文字
     *
     * @param image 图像
     * @return 返回添加文字后的图像
     */
    private BufferedImage addImageWords(BufferedImage image) {
        // 获取图像宽度
        int width = image.getWidth();
        // 获取图像高度
        int height = image.getHeight();
        // 定义转换图像
        BufferedImage out = new BufferedImage(width, height + this.param.getWordsSize(), BufferedImage.TYPE_INT_ARGB);
        // 创建图像图形
        Graphics2D graphics = out.createGraphics();
        // 设置插值
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // 设置图像抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置文本抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 设置笔划规范化控制参数
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        // 设置笔划
        graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        // 设置条形码背景色
        graphics.setColor(this.param.getOffColor());
        // 填充矩形
        graphics.fillRect(0, 0, width, height + this.param.getWordsSize());
        // 设置文字颜色
        graphics.setColor(this.param.getWordsColor());
        // 设置图像
        graphics.drawImage(image, 0, 0, width, height, null);
        // 设置字体
        graphics.setFont(new Font(null, this.param.getWordsStyle().style, this.param.getWordsSize()));
        // 文字长度
        int strWidth = graphics.getFontMetrics().stringWidth(this.param.getWords());
        // 定义X轴开始坐标（居中显示）
        int beginX = (width - strWidth) / 2;
        // 定义Y轴开始坐标
        int beginY = height + this.param.getWordsSize();
        // 设置文字
        graphics.drawString(this.param.getWords(), beginX, beginY);
        // 资源释放
        graphics.dispose();
        // 刷新图像
        image.flush();
        return out;
    }

    /**
     * 编码类型
     */
    public enum CodeType {

        // ************** 一维码 **************  //
        /**
         * 库德巴码
         */
        CODA_BAR(BarcodeFormat.CODABAR, 1),
        /**
         * 标准39码
         */
        CODE_39(BarcodeFormat.CODE_39, 1),
        /**
         * 标准93码
         */
        CODE_93(BarcodeFormat.CODE_93, 1),
        /**
         * 标准128码
         */
        CODE_128(BarcodeFormat.CODE_128, 1),
        /**
         * 缩短版国际商品条码
         */
        EAN_8(BarcodeFormat.EAN_8, 1),
        /**
         * 标准版国际商品条码
         */
        EAN_13(BarcodeFormat.EAN_13, 1),
        /**
         * 交叉码
         */
        ITF(BarcodeFormat.ITF, 1),
        /**
         * 美国商品码A
         */
        UPC_A(BarcodeFormat.UPC_A, 1),
        /**
         * 美国商品码E
         */
        UPC_E(BarcodeFormat.UPC_E, 1),

        // ************** 二维码 **************  //
        /**
         * QR码
         */
        QR_CODE(BarcodeFormat.QR_CODE, 1),
        /**
         * 阿兹特克码
         */
        AZTEC(BarcodeFormat.AZTEC, 1),
        /**
         * DM码
         */
        DATA_MATRIX(BarcodeFormat.DATA_MATRIX, 1),
        /**
         * MaxiCode
         */
        MAXI_CODE(BarcodeFormat.MAXICODE, 1),
        /**
         * PDF-417码
         */
        PDF_417(BarcodeFormat.PDF_417, 1);

        /**
         * 条形码格式化类型
         */
        final BarcodeFormat codeFormat;
        /**
         * 条形码边距
         */
        final int margin;

        /**
         * 有参构造
         *
         * @param codeFormat 格式化类型
         * @param margin     边距
         */
        CodeType(BarcodeFormat codeFormat, int margin) {
            this.codeFormat = codeFormat;
            this.margin = margin;
        }

        /**
         * 是否二维码类型
         *
         * @return 返回布尔值，是为true，否为false
         */
        boolean isQrType() {
            switch (this) {
                case QR_CODE:
                case AZTEC:
                case DATA_MATRIX:
                case MAXI_CODE:
                case PDF_417:
                    return true;
                default:
                    return false;
            }
        }
    }

    /**
     * 纠错级别
     */
    public enum ErrorLevel {
        /**
         * L 水平 7%的字码可被修正
         */
        L(ErrorCorrectionLevel.L),
        /**
         * M 水平 15%的字码可被修正
         */
        M(ErrorCorrectionLevel.M),
        /**
         * Q 水平 25%的字码可被修正
         */
        Q(ErrorCorrectionLevel.Q),
        /**
         * H 水平 30%的字码可被修正
         */
        H(ErrorCorrectionLevel.H);

        /**
         * 纠错级别
         */
        private final ErrorCorrectionLevel level;

        /**
         * 有参构造
         *
         * @param level 纠错级别
         */
        ErrorLevel(ErrorCorrectionLevel level) {
            this.level = level;
        }
    }

    /**
     * 文字样式
     */
    public enum WordsStyle {
        /**
         * 正常
         */
        NORMAL(Font.PLAIN),
        /**
         * 粗体
         */
        BOLD(Font.BOLD),
        /**
         * 斜体
         */
        ITALIC(Font.ITALIC);
        /**
         * 样式
         */
        private final int style;

        /**
         * 有参构造
         *
         * @param style 样式
         */
        WordsStyle(int style) {
            this.style = style;
        }
    }
}
