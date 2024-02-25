package org.dromara.pdf.fop.core.doc.watermark;

import lombok.SneakyThrows;
import org.apache.fop.util.ColorUtil;
import org.apache.xmlgraphics.util.UnitConv;
import org.dromara.pdf.fop.core.base.TemplateAttributes;
import org.dromara.pdf.fop.core.doc.page.PageRectangle;
import org.dromara.pdf.fop.util.FileUtil;
import org.dromara.pdf.fop.util.FontStyleUtil;
import org.dromara.pdf.fop.util.ImageUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * pdf模板-水印（文字）组件
 *
 * @author xsx
 * @date 2022/11/10
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class Watermark implements WatermarkComponent {

    /**
     * 水印（文本）参数
     */
    private final WatermarkParam param = new WatermarkParam();
    /**
     * 图像路径缓存
     */
    private static final Map<String, String> CACHE = new HashMap<>(10);
    /**
     * 缓存锁
     */
    private static final ReentrantLock LOCK = new ReentrantLock();

    /**
     * 设置临时目录
     *
     * @param tempDir 临时目录
     * @return 返回水印（文字）组件
     */
    public Watermark setTempDir(String tempDir) {
        Optional.ofNullable(tempDir).ifPresent(this.param::setTempDir);
        return this;
    }

    /**
     * 设置水印id
     *
     * @param id 水印id须唯一
     * @return 返回水印（文字）组件
     */
    public Watermark setId(String id) {
        this.param.setId(id);
        return this;
    }

    /**
     * 设置图像宽度
     *
     * @param width 图像宽度
     * @return 返回水印（文字）组件
     */
    public Watermark setWidth(String width) {
        this.param.setWidth(width);
        return this;
    }

    /**
     * 设置图像高度
     *
     * @param height 图像高度
     * @return 返回水印（文字）组件
     */
    public Watermark setHeight(String height) {
        this.param.setHeight(height);
        return this;
    }

    /**
     * 设置图像显示宽度
     *
     * @param width 图像显示宽度
     * @return 返回水印（文字）组件
     */
    public Watermark setShowWidth(String width) {
        this.param.setShowWidth(width);
        return this;
    }

    /**
     * 设置图像显示高度
     *
     * @param height 图像显示高度
     * @return 返回水印（文字）组件
     */
    public Watermark setShowHeight(String height) {
        this.param.setShowHeight(height);
        return this;
    }

    /**
     * 设置字体名称
     *
     * @param fontFamily 字体名称
     * @return 返回水印（文字）组件
     */
    public Watermark setFontFamily(String fontFamily) {
        this.param.setFontFamily(fontFamily);
        return this;
    }

    /**
     * 设置字体样式
     * <p>normal：正常</p>
     * <p>oblique：斜体</p>
     * <p>italic：斜体</p>
     * <p>backslant：斜体</p>
     *
     * @param fontStyle 字体样式
     * @return 返回水印（文字）组件
     */
    public Watermark setFontStyle(String fontStyle) {
        Optional.ofNullable(fontStyle).ifPresent(this.param::setFontStyle);
        return this;
    }

    /**
     * 设置字体大小
     *
     * @param fontSize 字体大小
     * @return 返回水印（文字）组件
     */
    public Watermark setFontSize(String fontSize) {
        Optional.ofNullable(fontSize).ifPresent(this.param::setFontSize);
        return this;
    }

    /**
     * 设置字体颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     *
     * @param color 字体颜色
     * @return 返回水印（文字）组件
     */
    public Watermark setFontColor(String color) {
        Optional.ofNullable(color).ifPresent(this.param::setFontColor);
        return this;
    }

    /**
     * 设置字体透明度
     * <p>0-255之间，值越小越透明</p>
     *
     * @param alpha 字体透明度
     * @return 返回水印（文字）组件
     */
    public Watermark setFontAlpha(String alpha) {
        this.param.setFontAlpha(alpha);
        return this;
    }

    /**
     * 设置背景图片定位
     * <p>第一个参数为X轴</p>
     * <p>第二个参数为Y轴</p>
     *
     * @param position 定位
     * @return 返回水印（文字）组件
     */
    public Watermark setPosition(String position) {
        this.param.setPosition(position);
        return this;
    }

    /**
     * 设置背景图片水平定位
     *
     * @param position 定位
     * @return 返回水印（文字）组件
     */
    public Watermark setHorizontalPosition(String position) {
        this.param.setPositionHorizontal(position);
        return this;
    }

    /**
     * 设置背景图片垂直定位
     *
     * @param position 定位
     * @return 返回水印（文字）组件
     */
    public Watermark setVerticalPosition(String position) {
        this.param.setPositionVertical(position);
        return this;
    }

    /**
     * 设置背景图片重复
     * <p>repeat：水平垂直重复</p>
     * <p>repeat-x：水平重复</p>
     * <p>repeat-y：垂直重复</p>
     * <p>no-repeat：不重复</p>
     *
     * @param repeat 重复
     * @return 返回水印（文字）组件
     */
    public Watermark setRepeat(String repeat) {
        this.param.setRepeat(repeat);
        return this;
    }

    /**
     * 设置旋转弧度
     *
     * @param radians 旋转弧度
     * @return 返回水印（文字）组件
     */
    public Watermark setRadians(String radians) {
        Optional.ofNullable(radians).ifPresent(this.param::setRadians);
        return this;
    }

    /**
     * 开启覆盖
     *
     * @return 返回水印（文字）组件
     */
    public Watermark enableOverwrite() {
        this.param.setIsOverwrite(Boolean.TRUE);
        return this;
    }

    /**
     * 设置文本列表
     *
     * @param texts 文本列表
     * @return 返回水印（文字）组件
     */
    public Watermark setText(String... texts) {
        Optional.ofNullable(texts).ifPresent(v -> Collections.addAll(this.param.getTexts(), v));
        return this;
    }

    /**
     * 设置文本列表
     *
     * @param texts 文本列表
     * @return 返回水印（文字）组件
     */
    public Watermark setText(List<String> texts) {
        Optional.ofNullable(texts).ifPresent(this.param.getTexts()::addAll);
        return this;
    }

    /**
     * 保存水印图像
     */
    public void saveImage() {
        this.getImageFile();
    }

    /**
     * 创建水印
     *
     * @param document fo文档
     * @param element  fo元素
     */
    @Override
    public void createWatermark(Document document, Element element) {
        // 如果元素为空，则直接返回
        if (element == null) {
            // 直接返回
            return;
        }
        // 如果文本列表为空，则直接返回
        if (this.param.getTexts().isEmpty()) {
            // 直接返回
            return;
        }
        // 设置背景图片
        element.setAttribute(TemplateAttributes.BACKGROUND_IMAGE, this.getImageUrl().intern());
        // 设置背景图片宽度
        Optional.ofNullable(this.param.getShowWidth()).ifPresent(v -> element.setAttribute(TemplateAttributes.BACKGROUND_IMAGE_WIDTH, v.intern().toLowerCase()));
        // 设置背景图片高度
        Optional.ofNullable(this.param.getShowHeight()).ifPresent(v -> element.setAttribute(TemplateAttributes.BACKGROUND_IMAGE_HEIGHT, v.intern().toLowerCase()));
        // 设置背景图片定位
        Optional.ofNullable(this.param.getPosition()).ifPresent(v -> element.setAttribute(TemplateAttributes.BACKGROUND_POSITION, v.intern().toLowerCase()));
        // 设置背景图片水平定位
        Optional.ofNullable(this.param.getPositionHorizontal()).ifPresent(v -> element.setAttribute(TemplateAttributes.BACKGROUND_POSITION_HORIZONTAL, v.intern().toLowerCase()));
        // 设置背景图片垂直定位
        Optional.ofNullable(this.param.getPositionVertical()).ifPresent(v -> element.setAttribute(TemplateAttributes.BACKGROUND_POSITION_VERTICAL, v.intern().toLowerCase()));
        // 设置背景图片重复
        Optional.ofNullable(this.param.getRepeat()).ifPresent(v -> element.setAttribute(TemplateAttributes.BACKGROUND_REPEAT, v.intern().toLowerCase()));
    }

    /**
     * 获取图像路径
     *
     * @return 返回图像路径
     */
    private String getImageUrl() {
        // 从缓存中获取图像路径
        String url = CACHE.get(this.param.getId());
        // 如果图像路径为空，则创建图像
        if (url == null) {
            try {
                // 加锁
                LOCK.lock();
                // 再次从缓存中获取图像路径
                url = CACHE.get(this.param.getId());
                // 如果图像路径仍然为空，则创建图像
                if (url == null) {
                    // 写入缓存
                    url = this.putCache();
                }
            } finally {
                // 解锁
                LOCK.unlock();
            }
        }
        return url;
    }

    /**
     * 放入缓存
     *
     * @return 返回图像路径
     */
    @SneakyThrows
    private String putCache() {
        // 获取图像文件
        File file = this.getImageFile();
        // 获取文件路径
        String url = "url('" + file.toURI().getPath() + "')";
        // 添加文件路径缓存
        CACHE.put(this.param.getId(), url);
        // 返回文件路径
        return url;
    }

    /**
     * 获取图像文件
     *
     * @return 返回文件
     */
    @SneakyThrows
    private File getImageFile() {
        // 创建文件
        File file = new File(FileUtil.createDirectories(Paths.get(this.param.getTempDir())).toString(), this.param.getId() + ".png");
        // 如果文件不存在或开启文件覆盖，则创建新图像
        if (!file.exists() || this.param.getIsOverwrite()) {
            // 如果水印id为空，则提示信息
            Optional.ofNullable(this.param.getId()).orElseThrow(() -> new IllegalArgumentException("the watermark id can not be null"));
            // 如果宽度为空，则提示信息
            Optional.ofNullable(this.param.getWidth()).orElseThrow(() -> new IllegalArgumentException("the watermark width can not be null"));
            // 如果高度为空，则提示信息
            Optional.ofNullable(this.param.getHeight()).orElseThrow(() -> new IllegalArgumentException("the watermark height can not be null"));
            // 创建图像
            BufferedImage image = this.createImage();
            // 写入文件
            ImageIO.write(image, "png", file);
            // 刷新图像
            image.flush();
        }
        // 返回文件
        return file;
    }

    /**
     * 创建图像
     *
     * @return 返回图像
     */
    @SneakyThrows
    @SuppressWarnings("all")
    private BufferedImage createImage() {
        // 获取宽度
        int width = this.parseUnit(this.param.getWidth());
        // 获取高度
        int height = this.parseUnit(this.param.getHeight());
        // 获取字体大小
        int fontSize = this.parseUnit(this.param.getFontSize());
        // 获取字体颜色
        Color fontColor = ColorUtil.parseColorString(null, this.param.getFontColor());
        // 获取字体透明度
        int fontAlpha = Optional.ofNullable(this.param.getFontAlpha()).map(Integer::parseInt).orElse(fontColor.getAlpha());
        // 获取旋转弧度
        double radians = Double.parseDouble(this.param.getRadians());
        // 定义转换图像
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // 创建图像图形
        Graphics2D graphics = image.createGraphics();
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
        // 设置文字颜色
        graphics.setColor(new Color(fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue(), fontAlpha));
        // 设置字体
        graphics.setFont(new Font(this.param.getFontFamily(), FontStyleUtil.getStyle(this.param.getFontStyle()), fontSize));
        // 获取文本列表
        List<String> texts = this.param.getTexts();
        // 定义Y轴开始坐标（居中显示）
        int beginY = (height - fontSize * texts.size()) / 2 + fontSize;
        // 遍历文本列表
        for (String word : texts) {
            // 文字长度
            int wordWidth = graphics.getFontMetrics().stringWidth(word);
            // 定义X轴开始坐标（居中显示）
            int beginX = (width - wordWidth) / 2;
            // 设置文字
            graphics.drawString(word, beginX, beginY);
            // 重置Y轴开始坐标
            beginY = beginY + fontSize;
        }
        // 资源释放
        graphics.dispose();
        // 返回旋转后的图像
        return ImageUtil.rotate(image, PageRectangle.getRotateRectangle(width, height, radians), radians);
    }

    /**
     * 解析单位
     *
     * @param unit 单位
     * @return 返回单位
     */
    @SneakyThrows
    private int parseUnit(String unit) {
        return UnitConv.convert(unit) / 1000;
    }
}
