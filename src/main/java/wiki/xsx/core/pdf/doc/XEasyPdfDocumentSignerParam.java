package wiki.xsx.core.pdf.doc;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSigProperties;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSignDesigner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Calendar;

/**
 * pdf文档签名器参数
 *
 * @author xsx
 * @date 2021/12/15
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
class XEasyPdfDocumentSignerParam implements Serializable {

    private static final long serialVersionUID = -444541984762721143L;

    /**
     * pdfbox文档
     */
    private transient PDDocument document;
    /**
     * pdf文档
     */
    private XEasyPdfDocument pdfDocument;
    /**
     * 密钥库类型
     */
    private transient XEasyPdfDocumentSigner.KeyStoreType keyStoreType;
    /**
     * 证书文件
     */
    private File certificate;
    /**
     * 证书密码
     */
    private String certificatePassword;
    /**
     * pdfbox签名
     */
    private transient PDSignature signature = new PDSignature();
    /**
     * pdfbox签名选项
     */
    private transient final SignatureOptions signatureOptions = new SignatureOptions();
    /**
     * 签名算法
     */
    private transient XEasyPdfDocumentSigner.SignAlgorithm signAlgorithm;
    /**
     * 签名过滤器
     */
    private transient XEasyPdfDocumentSigner.SignFilter.Filter filter = XEasyPdfDocumentSigner.SignFilter.Filter.FILTER_ADOBE_PPKLITE;
    /**
     * 签名子过滤器
     */
    private transient XEasyPdfDocumentSigner.SignFilter.SubFilter subFilter = XEasyPdfDocumentSigner.SignFilter.SubFilter.SUBFILTER_ADBE_PKCS7_DETACHED;
    /**
     * pdfbox签名接口
     */
    private transient SignatureInterface customSignature;
    /**
     * pdf访问权限
     */
    private Integer accessPermissions = 1;
    /**
     * 签名图片
     */
    private transient BufferedImage image;
    /**
     * 签名图片左边距
     */
    private Float imageMarginLeft = 0F;
    /**
     * 签名图片上边距
     */
    private Float imageMarginTop = 0F;
    /**
     * 签名图片缩放比例
     */
    private Float imageScalePercent = 0F;
    /**
     * 签名内存大小（默认：250K）
     */
    private Integer preferredSignatureSize = 0x3e800;
    /**
     * 临时目录
     */
    private String tempDir = "";

    /**
     * 初始化
     *
     * @param pageIndex 页面索引
     */
    @SneakyThrows
    void init(int pageIndex) {
        // 如果密钥库类型未初始化，则提示错误
        if (this.keyStoreType == null) {
            throw new IllegalArgumentException("keyStore type can not be null");
        }
        // 如果证书文件未初始化，则提示错误
        if (this.certificate == null) {
            throw new FileNotFoundException("certificate can not be null");
        }
        // 如果证书密码未初始化，则提示错误
        if (this.certificatePassword == null) {
            throw new IllegalArgumentException("certificate password can not be null");
        }
        // 如果签名算法未初始化，则提示错误
        if (this.signAlgorithm == null) {
            throw new IllegalArgumentException("signAlgorithm can not be null");
        }
        // 设置过滤器
        this.signature.setFilter(this.filter.getFilter());
        // 设置子过滤器
        this.signature.setSubFilter(this.subFilter.getFilter());
        // 设置签名日期
        this.signature.setSignDate(Calendar.getInstance());
        // 如果签名图片已初始化，则设置可视化签名属性
        if (this.image != null) {
            // 定义可视化签名属性
            PDVisibleSigProperties signatureProperty = new PDVisibleSigProperties();
            // 定义可视化签名者
            PDVisibleSignDesigner designer = new PDVisibleSignDesigner(this.document, this.image, pageIndex + 1);
            // 设置签名图片缩放比例
            designer.zoom(this.imageScalePercent);
            // 设置签名图片左偏移
            designer.xAxis(this.imageMarginLeft);
            // 设置签名图片上偏移
            designer.yAxis(this.imageMarginTop);
            // 调整旋转
            designer.adjustForRotation();
            // 设置签名者名称
            signatureProperty.signerName(this.signature.getName())
                    // 设置签名位置
                    .signerLocation(this.signature.getLocation())
                    // 设置签名原因
                    .signatureReason(this.signature.getReason())
                    // 设置签名页面索引
                    .page(pageIndex + 1)
                    // 开启可视化
                    .visualSignEnabled(true)
                    // 开启签名者
                    .setPdVisibleSignature(designer)
                    // 构建签名
                    .buildSignature();
            // 设置可视化签名属性
            this.signatureOptions.setVisualSignature(signatureProperty);
            // 设置页码
            this.signatureOptions.setPage(pageIndex);
        }
        // 设置签名内存大小
        this.signatureOptions.setPreferredSignatureSize(this.preferredSignatureSize);
    }
}
