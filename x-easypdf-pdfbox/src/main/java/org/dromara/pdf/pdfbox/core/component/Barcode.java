package org.dromara.pdf.pdfbox.core.component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.QRCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.enums.BarcodeErrorLevel;
import org.dromara.pdf.pdfbox.core.enums.BarcodeType;
import org.dromara.pdf.pdfbox.core.enums.BarcodeWordsStyle;
import org.dromara.pdf.pdfbox.core.enums.ComponentType;
import org.dromara.pdf.pdfbox.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条形码组件
 *
 * @author xsx
 * @date 2023/8/30
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
@Data
@EqualsAndHashCode(callSuper = true)
public class Barcode extends AbstractComponent {

    /**
     * 缓存
     */
    protected static final Map<Integer, BufferedImage> CACHE = new HashMap<>(10);
    /**
     * 缓存锁
     */
    protected static final ReentrantLock LOCK = new ReentrantLock();
    /**
     * 编码设置
     */
    protected Map<EncodeHintType, Object> encodeHints = new HashMap<>(8);
    /**
     * 宽度（显示）
     */
    protected Integer width;
    /**
     * 高度（显示）
     */
    protected Integer height;
    /**
     * 图像宽度
     */
    protected Integer imageWidth;
    /**
     * 图像高度
     */
    protected Integer imageHeight;
    /**
     * 编码类型
     */
    protected BarcodeType codeType;
    /**
     * 前景颜色
     */
    protected Color onColor;
    /**
     * 背景颜色
     */
    protected Color offColor;
    /**
     * 内容
     */
    protected String content;
    /**
     * 文字
     */
    protected String words;
    /**
     * 文字字体名称
     */
    protected String wordsFontName;
    /**
     * 文字颜色
     */
    protected Color wordsColor;
    /**
     * 文字样式
     */
    protected BarcodeWordsStyle wordsStyle;
    /**
     * 文字大小
     */
    protected Integer wordsSize;
    /**
     * 文字X轴偏移量
     */
    protected Integer wordsOffsetX;
    /**
     * 文字Y轴偏移量
     */
    protected Integer wordsOffsetY;
    /**
     * 旋转角度
     */
    protected Float angle;
    /**
     * 是否显示文字
     */
    protected Boolean isShowWords;
    /**
     * 是否缓存
     */
    protected Boolean isCache;

    /**
     * 有参构造
     *
     * @param page 页面
     */
    public Barcode(Page page) {
        super(page);
    }

    /**
     * 设置宽度（显示）
     *
     * @param width 宽度
     */
    public void setWidth(int width) {
        if (width < 1) {
            throw new IllegalArgumentException("the width can not be less than 1");
        }
        this.width = width;
    }

    /**
     * 设置高度（显示）
     *
     * @param height 高度
     */
    public void setHeight(int height) {
        if (height < 1) {
            throw new IllegalArgumentException("the height can not be less than 1");
        }
        this.height = height;
    }

    /**
     * 设置图像宽度（生成）
     *
     * @param width 宽度
     */
    public void setImageWidth(int width) {
        if (width < 1) {
            throw new IllegalArgumentException("the image width can not be less than 1");
        }
        this.imageWidth = width;
    }

    /**
     * 设置图像高度（生成）
     *
     * @param height 高度
     */
    public void setImageHeight(int height) {
        if (height < 1) {
            throw new IllegalArgumentException("the image height can not be less than 1");
        }
        this.imageHeight = height;
    }

    /**
     * 设置条形码边距
     *
     * @param margin 条形码边距
     */
    public void setCodeMargin(int margin) {
        if (margin < 0) {
            throw new IllegalArgumentException("the code margin can not be less than 0");
        }
        // 设置边距
        this.encodeHints.put(EncodeHintType.MARGIN, margin);
    }

    /**
     * 设置纠错级别
     *
     * @param level 纠错级别
     */
    public void setErrorLevel(BarcodeErrorLevel level) {
        if (Objects.nonNull(level)) {
            this.encodeHints.put(EncodeHintType.ERROR_CORRECTION, level.getLevel());
        }
    }

    /**
     * 设置二维码版本（仅二维码有效）
     *
     * @param version 版本
     */
    public void setQrVersion(int version) {
        final int min = 0;
        final int max = 41;
        if (version > min && version < max) {
            this.encodeHints.put(EncodeHintType.QR_VERSION, version);
        } else {
            throw new IllegalArgumentException("the version must be between 1 and 40");
        }
    }

    /**
     * 设置二维码掩码模式（仅二维码有效）
     *
     * @param pattern 掩码模式
     */
    public void setQrMaskPattern(int pattern) {
        if (!QRCode.isValidMaskPattern(pattern)) {
            throw new IllegalArgumentException("the qr mask pattern must be between 0 and 7");
        }
        this.encodeHints.put(EncodeHintType.QR_MASK_PATTERN, pattern);
    }

    /**
     * 设置二维码紧凑模式（仅二维码有效）
     *
     * @param isCompact 是否紧凑
     */
    public void setQrCompact(boolean isCompact) {
        this.encodeHints.put(EncodeHintType.QR_COMPACT, isCompact);
    }

    /**
     * 设置编码提示
     *
     * @param type  类型
     * @param value 值
     */
    public void setEncodeHints(EncodeHintType type, Object value) {
        this.encodeHints.put(type, value);
    }

    /**
     * 获取类型
     *
     * @return 返回类型
     */
    @Override
    public ComponentType getType() {
        return ComponentType.BARCODE;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        // 初始化
        super.init();
        // 初始化类型
        Objects.requireNonNull(this.codeType, "the code type can not be null");
        // 初始化内容
        Objects.requireNonNull(this.content, "the content can not be null");
        // 初始化宽度（显示）
        if (Objects.isNull(this.width)) {
            this.width = this.imageWidth;
            Objects.requireNonNull(this.width, "the width or image width can not be null");
        }
        // 初始化高度（显示）
        if (Objects.isNull(this.height)) {
            this.height = this.imageHeight;
            Objects.requireNonNull(this.height, "the height or image height can not be null");
        }
        // 初始化图像宽度（生成）
        if (Objects.isNull(this.imageWidth)) {
            this.imageWidth = this.width;
        }
        // 初始化图像高度（生成）
        if (Objects.isNull(this.imageHeight)) {
            this.imageHeight = this.height;
        }
        // 初始化编码为utf-8
        this.encodeHints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        // 初始化纠错级别
        this.initErrorLevel();
        // 初始化前景颜色
        if (Objects.isNull(this.onColor)) {
            this.onColor = Color.BLACK;
        }
        // 初始化背景颜色
        if (Objects.isNull(this.offColor)) {
            this.offColor = this.getPage().getBackgroundColor();
        }
        // 初始化旋转角度
        if (Objects.isNull(this.angle)) {
            this.angle = 0F;
        }
        // 初始化是否显示文字
        if (Objects.isNull(this.isShowWords)) {
            this.isShowWords = Boolean.FALSE;
        }
        // 初始化是否缓存
        if (Objects.isNull(this.isCache)) {
            this.isCache = Boolean.FALSE;
        }
        // 检查换行
        this.checkWrap();
        // 初始化起始X轴坐标
        this.initBeginX(this.width);
        // 是否首个组件
        if (this.getContext().getIsFirstComponent()) {
            super.setBeginY(this.getBeginY() - this.getHeight());
        }
        // 检查分页
        this.checkPaging();

    }

    /**
     * 检查换行
     */
    protected void checkWrap() {
        // 初始化X轴坐标
        if (Objects.isNull(this.getBeginX())) {
            this.setBeginX(this.getContext().getCursor().getX() + this.getMarginLeft());
        }
        // 初始化换行
        if (this.isWrap()) {
            this.getContext().getCursor().reset(
                    this.getContext().getWrapBeginX(),
                    this.getContext().getCursor().getY() - this.getHeight()
            );
            this.setBeginX(this.getContext().getCursor().getX() + this.getMarginLeft());
        }
        // 初始化Y轴坐标
        if (Objects.isNull(this.getBeginY())) {
            this.setBeginY(this.getContext().getCursor().getY() - this.getMarginTop());
        }
    }

    /**
     * 获取最小宽度
     *
     * @return 返回最小宽度
     */
    @Override
    protected float getMinWidth() {
        return this.getWidth();
    }

    /**
     * 虚拟渲染
     */
    @Override
    public void virtualRender() {
        // 虚拟渲染图像
        this.createImage().virtualRender();
        // 重置
        super.reset(this.getType());
    }

    /**
     * 渲染
     */
    @Override
    public void render() {
        // 渲染图像
        this.createImage().render();
        // 重置
        super.reset(this.getType());
    }

    /**
     * 创建图像组件
     *
     * @return 返回图像组件
     */
    protected Image createImage() {
        // 初始化
        this.init();
        // 创建图像
        Image image = new Image(this);
        // 设置宽度
        image.setWidth(this.getWidth());
        // 设置高度
        image.setHeight(this.getHeight());
        // 设置旋转角度
        image.setAngle(this.getAngle());
        // 设置图像
        image.setImage(ImageUtil.toBytes(this.getBarcodeImage(), "png"));
        // 设置X轴坐标
        image.setBeginX(this.getBeginX());
        // 设置Y轴坐标
        image.setBeginY(this.getBeginY() - this.getHeight());
        // 设置X轴相对坐标
        image.setRelativeBeginX(this.getRelativeBeginX());
        // 设置Y轴相对坐标
        image.setRelativeBeginY(this.getRelativeBeginY());
        // 设置水平对齐方式
        image.setHorizontalAlignment(this.getHorizontalAlignment());
        return image;
    }

    /**
     * 初始化纠错级别
     */
    protected void initErrorLevel() {
        // 获取纠错级别
        Object errorLevel = this.encodeHints.get(EncodeHintType.ERROR_CORRECTION);
        // 初始化纠错级别
        if (Objects.isNull(errorLevel)) {
            // 如果条形码格式化类型为阿兹特克码或PDF-417码，则重置纠错级别
            if (BarcodeFormat.AZTEC == this.codeType.getCodeFormat() || BarcodeFormat.PDF_417 == this.codeType.getCodeFormat()) {
                // 重置纠错级别
                this.encodeHints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M.getBits());
            } else {
                // 重置纠错级别
                this.encodeHints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            }

        } else {
            // 类型转换
            ErrorCorrectionLevel level = (ErrorCorrectionLevel) errorLevel;
            // 如果条形码格式化类型为阿兹特克码或PDF-417码，则重置纠错级别
            if (BarcodeFormat.AZTEC == this.codeType.getCodeFormat() || BarcodeFormat.PDF_417 == this.codeType.getCodeFormat()) {
                // 重置纠错级别
                this.encodeHints.put(EncodeHintType.ERROR_CORRECTION, level.getBits());
            }
        }
    }

    /**
     * 是否显示文字
     *
     * @return 返回布尔值，是为true，否为false
     */
    protected boolean isShowWords() {
        return this.getIsShowWords();
    }

    /**
     * 获取条形码图像
     *
     * @return 返回条形码图像
     */
    @SuppressWarnings("all")
    @SneakyThrows
    protected BufferedImage getBarcodeImage() {
        // 是否缓存
        if (this.getIsCache()) {
            // 获取图像
            BufferedImage bufferedImage = CACHE.get(this.cacheKey());
            // 图像不存在
            if (Objects.isNull(bufferedImage)) {
                try {
                    // 加锁
                    LOCK.lock();
                    // 再次获取
                    bufferedImage = CACHE.get(this.cacheKey());
                    // 仍然不存在
                    if (Objects.isNull(bufferedImage)) {
                        // 创建图像
                        bufferedImage = this.createBarcodeImage();
                        // 放入缓存
                        CACHE.put(this.cacheKey(), bufferedImage);
                        // 日志打印
                        if (log.isDebugEnabled()) {
                            log.debug("Added barcode image: " + this.cacheKey());
                        }
                    }
                } finally {
                    // 解锁
                    LOCK.unlock();
                }
            }
            // 返回图像
            return bufferedImage;
        }
        // 返回图像
        return this.createBarcodeImage();
    }

    /**
     * 创建条形码图像
     *
     * @return 返回条形码图像
     */
    @SneakyThrows
    protected BufferedImage createBarcodeImage() {
        // 获取图像
        BufferedImage bufferedImage = this.toBufferedImage(
                new MultiFormatWriter().encode(
                        this.getContent(),
                        this.getCodeType().getCodeFormat(),
                        this.getImageWidth(),
                        this.getImageHeight(),
                        this.getEncodeHints()
                )
        );
        // 如果显示文字，则添加图像文字
        if (this.isShowWords()) {
            // 添加图像文字
            bufferedImage = this.addImageWords(bufferedImage);
        }
        // 返回图像
        return bufferedImage;
    }

    /**
     * 转图像
     *
     * @param matrix 位矩阵
     * @return 返回图像
     */
    @SneakyThrows
    protected BufferedImage toBufferedImage(BitMatrix matrix) {
        // 获取前景色
        final int onColor = this.getOnColor().getRGB();
        // 获取背景色
        final int offColor = this.getOffColor().getRGB();
        // 获取宽度
        int width = matrix.getWidth();
        // 获取高度
        int height = matrix.getHeight();
        // 定义图像
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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
        // 返回图像
        return image;
    }

    /**
     * 添加图像文字
     *
     * @param image 图像
     * @return 返回添加文字后的图像
     */
    @SuppressWarnings("all")
    protected BufferedImage addImageWords(BufferedImage image) {
        // 获取图像宽度
        int width = image.getWidth();
        // 获取图像高度
        int height = image.getHeight();
        // 获取文字
        String words = Optional.ofNullable(this.getWords()).orElse(this.getContent());
        // 获取文字字体名称
        String wordsFontName = this.getWordsFontName();
        // 获取文字颜色
        Color wordsColor = Optional.ofNullable(this.getWordsColor()).orElse(Color.BLACK);
        // 获取文字大小
        int wordsSize = Optional.ofNullable(this.getWordsSize()).orElse(10);
        // 获取文字样式
        int wordsStyle = Optional.ofNullable(this.getWordsStyle()).orElse(BarcodeWordsStyle.NORMAL).getStyle();
        // 获取文字X轴偏移量
        int wordsOffsetX = Optional.ofNullable(this.getWordsOffsetX()).orElse(0);
        // 获取文字Y轴偏移量
        int wordsOffsetY = Optional.ofNullable(this.getWordsOffsetY()).orElse(0);
        // 总高度
        int totalHeight = height + wordsSize;
        // 定义转换图像
        BufferedImage out = new BufferedImage(width, totalHeight, BufferedImage.TYPE_INT_RGB);
        // 创建图像图形
        Graphics2D graphics = ImageUtil.createGraphics(out);
        // 填充矩形
        graphics.fillRect(0, 0, width, totalHeight);
        // 设置文字颜色
        graphics.setColor(wordsColor);
        // 设置图像
        graphics.drawImage(image, 0, 0, width, height, null);
        // 设置字体
        graphics.setFont(new Font(wordsFontName, wordsStyle, wordsSize));
        // 文字长度
        int strWidth = graphics.getFontMetrics().stringWidth(words);
        // 定义X轴开始坐标（居中显示）
        int beginX = (width - strWidth) / 2 + wordsOffsetX;
        // 定义Y轴开始坐标
        int beginY = height + wordsOffsetY;
        // 设置文字
        graphics.drawString(words, beginX, beginY);
        // 资源释放
        graphics.dispose();
        // 刷新图像
        image.flush();
        // 返回图像
        return out;
    }

    /**
     * 缓存key
     *
     * @return 返回缓存key
     */
    protected int cacheKey() {
        return Objects.hash(
                this.encodeHints,
                this.width,
                this.height,
                this.imageWidth,
                this.imageHeight,
                this.codeType,
                this.onColor,
                this.offColor,
                this.content,
                this.words,
                this.wordsFontName,
                this.wordsColor,
                this.wordsStyle,
                this.wordsSize,
                this.wordsOffsetX,
                this.wordsOffsetY,
                this.isShowWords
        );
    }
}
