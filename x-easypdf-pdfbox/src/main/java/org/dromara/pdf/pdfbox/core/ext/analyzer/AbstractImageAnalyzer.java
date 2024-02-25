package org.dromara.pdf.pdfbox.core.ext.analyzer;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.OperatorName;
import org.apache.pdfbox.contentstream.operator.state.*;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.info.ImageInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 抽象图像分析器
 *
 * @author xsx
 * @date 2023/10/19
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
@Getter
public abstract class AbstractImageAnalyzer extends AbstractAnalyzer {

    /**
     * 日志
     */
    private static final Log log = LogFactory.getLog(AbstractImageAnalyzer.class);

    /**
     * 默认流引擎
     */
    protected DefaultStreamEngine streamEngine;
    /**
     * 图像信息列表
     */
    protected final Set<ImageInfo> infoSet = new HashSet<>(16);

    /**
     * 有参构造
     *
     * @param document 文档
     */
    public AbstractImageAnalyzer(Document document) {
        super(document);
    }

    /**
     * 处理图像
     *
     * @param pageIndex 页面索引
     * @param page      pdfbox页面
     */
    public abstract void processImage(int pageIndex, PDPage page);

    /**
     * 默认流引擎
     */
    @Setter
    @Getter
    protected static class DefaultStreamEngine extends PDFStreamEngine {
        /**
         * 页面索引
         */
        protected Integer pageIndex;
        /**
         * 图像索引
         */
        protected Integer imageIndex;
        /**
         * 图像信息列表
         */
        protected Set<ImageInfo> infoSet;

        /**
         * 无参构造
         */
        public DefaultStreamEngine() {
            this.addOperator(new Concatenate(this));
            this.addOperator(new DrawObject(this));
            this.addOperator(new SetGraphicsStateParameters(this));
            this.addOperator(new Save(this));
            this.addOperator(new Restore(this));
            this.addOperator(new SetMatrix(this));
            this.infoSet = new HashSet<>(16);
        }

        /**
         * 处理操作标记
         *
         * @param operator 操作标记
         * @param operands 标记列表
         */
        @SneakyThrows
        @Override
        protected void processOperator(Operator operator, List<COSBase> operands) {
            // 如果操作标记名称为绘制对象，则分析图像
            if (OperatorName.DRAW_OBJECT.equals(operator.getName())) {
                // 获取pdf对象
                PDXObject xObject = this.getResources().getXObject((COSName) operands.get(0));
                // 如果对象为pdf图像，则分析图像
                if (xObject instanceof PDImageXObject) {
                    // 转换为pdf图像
                    PDImageXObject image = (PDImageXObject) xObject;
                    // 获取当前页面尺寸
                    PDRectangle rectangle = this.getCurrentPage().getMediaBox();
                    // 获取页面矩阵
                    Matrix matrix = this.getGraphicsState().getCurrentTransformationMatrix();
                    // 构建图像信息
                    ImageInfo imageInfo = ImageInfo.builder()
                            .pageIndex(this.pageIndex)
                            .pageWidth(rectangle.getWidth())
                            .pageHeight(rectangle.getHeight())
                            .imageIndex(this.imageIndex)
                            .imageType(image.getSuffix())
                            .imageRealWidth(image.getWidth())
                            .imageRealHeight(image.getHeight())
                            .imageDisplayWidth((int) matrix.getScalingFactorX())
                            .imageDisplayHeight((int) matrix.getScalingFactorY())
                            .imagePosition(this.getPosition(matrix, image.getWidth() == ((int) matrix.getScalingFactorX()), image.getHeight() == ((int) matrix.getScalingFactorY())))
                            .image(image.getImage())
                            .build();
                    // 添加图像列表
                    this.infoSet.add(imageInfo);
                    // 如果日志打印开启，则打印日志
                    if (log.isDebugEnabled()) {
                        // 打印日志
                        log.debug(
                                "\n********************************************ANALYZE IMAGE BEGIN********************************************" +
                                        "\npage index: " + imageInfo.getPageIndex() +
                                        "\npage width: " + imageInfo.getPageWidth() +
                                        "\npage height: " + imageInfo.getPageHeight() +
                                        "\nimage index: " + imageInfo.getImageIndex() +
                                        "\nimage type: " + imageInfo.getImageType() +
                                        "\nimage real width: " + imageInfo.getImageRealWidth() +
                                        "\nimage real height: " + imageInfo.getImageRealHeight() +
                                        "\nimage display width: " + imageInfo.getImageDisplayWidth() +
                                        "\nimage display height: " + imageInfo.getImageDisplayHeight() +
                                        "\nimage position: " + imageInfo.getImagePosition() +
                                        "\n*********************************************ANALYZE IMAGE END*********************************************"
                        );
                    }
                    // 图像索引自增
                    this.imageIndex++;
                } else if (xObject instanceof PDFormXObject) {
                    // 处理表单
                    this.showForm((PDFormXObject) xObject);
                }
            } else {
                // 使用父类方法处理
                super.processOperator(operator, operands);
            }
        }

        /**
         * 获取位置坐标
         *
         * @param matrix      页面矩阵
         * @param equalWidth  宽度相等
         * @param equalHeight 高度相等
         * @return 返回位置坐标
         */
        protected String getPosition(Matrix matrix, boolean equalWidth, boolean equalHeight) {
            // 定义字符串构建器
            StringBuilder builder = new StringBuilder();
            // 如果真实宽度与显示宽度相等，且X轴坐标小于0，则添加原点坐标
            if (equalWidth && matrix.getTranslateX() < 0) {
                // 添加原点坐标
                builder.append("0.0");
            } else {
                // 添加实际X轴坐标
                builder.append(matrix.getTranslateX());
            }
            // 添加分隔符
            builder.append(',');
            // 如果真实高度与显示高度相等，且Y轴坐标小于0，则添加原点坐标
            if (equalHeight && matrix.getTranslateY() < 0) {
                // 添加原点坐标
                builder.append("0.0");
            } else {
                // 添加实际Y轴坐标
                builder.append(matrix.getTranslateY());
            }
            return builder.toString();
        }
    }
}