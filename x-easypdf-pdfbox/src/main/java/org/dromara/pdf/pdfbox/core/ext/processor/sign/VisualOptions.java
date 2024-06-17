package org.dromara.pdf.pdfbox.core.ext.processor.sign;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSigProperties;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSignDesigner;

import java.awt.image.BufferedImage;

/**
 * 可视化签名选项
 *
 * @author xsx
 * @date 2024/3/13
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
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
@Builder
public class VisualOptions {
    /**
     * 签名图片
     */
    private BufferedImage image;
    /**
     * 签名图片左边距
     */
    private Float imageMarginLeft;
    /**
     * 签名图片上边距
     */
    private Float imageMarginTop;
    /**
     * 签名图片缩放比例
     */
    private Float imageScalePercent;

    @SneakyThrows
    protected void initVisualOptions(PDDocument document, PDSignature signature, SignatureOptions signatureOptions) {
        // 定义可视化签名属性
        PDVisibleSigProperties signatureProperty = new PDVisibleSigProperties();
        // 定义可视化签名者
        PDVisibleSignDesigner designer = new PDVisibleSignDesigner(document, this.image, signatureOptions.getPage() + 1);
        // 设置签名图片缩放比例
        designer.zoom(this.imageScalePercent);
        // 设置签名图片左偏移
        designer.xAxis(this.imageMarginLeft);
        // 设置签名图片上偏移
        designer.yAxis(this.imageMarginTop);
        // 调整旋转
        designer.adjustForRotation();
        // 设置签名者名称
        signatureProperty.signerName(signature.getName())
                // 设置签名位置
                .signerLocation(signature.getLocation())
                // 设置签名原因
                .signatureReason(signature.getReason())
                // 设置签名页面索引
                .page(signatureOptions.getPage() + 1)
                // 开启可视化
                .visualSignEnabled(true)
                // 开启签名者
                .setPdVisibleSignature(designer)
                // 构建签名
                .buildSignature();
        // 设置可视化签名属性
        signatureOptions.setVisualSignature(signatureProperty);
    }
}
