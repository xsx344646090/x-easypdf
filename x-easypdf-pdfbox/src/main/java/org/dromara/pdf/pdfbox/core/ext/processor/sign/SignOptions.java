package org.dromara.pdf.pdfbox.core.ext.processor.sign;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;

import java.util.Objects;

/**
 * 签名选项
 *
 * @author xsx
 * @date 2024/3/13
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
@Builder
public class SignOptions {
    /**
     * 页面索引
     */
    private Integer pageIndex;
    /**
     * 证书
     */
    private CertificateInfo certificate;
    /**
     * 算法
     */
    private String algorithm;
    /**
     * 签名权限
     */
    private SignPermission permission;
    /**
     * 可视化选项
     */
    private VisualOptions visualOptions;

    /**
     * 初始化
     */
    protected void init() {
        Objects.requireNonNull(this.pageIndex, "the page index can not be null");
        Objects.requireNonNull(this.certificate, "the certificate can not be null");
        Objects.requireNonNull(this.algorithm, "the algorithm can not be null");
        if (Objects.isNull(this.permission)) {
            this.permission = SignPermission.NONE_LIMIT;
        }
    }

    /**
     * 初始化签名选项
     *
     * @param document  文档
     * @param signature 签名
     * @return 返回签名选项
     */
    @SneakyThrows
    protected SignatureOptions initOptions(PDDocument document, PDSignature signature) {
        // 创建签名选项
        SignatureOptions signatureOptions = new SignatureOptions();
        // 设置页码
        signatureOptions.setPage(this.pageIndex);
        // 初始化可视化签名属性
        if (Objects.nonNull(this.visualOptions)) {
            signatureOptions.setVisualSignature(this.visualOptions.initVisualSignature(document, signature, this.pageIndex));
        }
        // 返回签名选项
        return signatureOptions;
    }
}
