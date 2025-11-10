package org.dromara.pdf.pdfbox.core.ext.processor.sign;

import org.bouncycastle.cms.CMSTypedData;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;

import java.io.InputStream;

/**
 * 签名器
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
public interface Signer extends SignatureInterface {

    /**
     * 签名
     *
     * @param content 内容
     * @return 返回字节数组
     */
    @Override
    byte[] sign(InputStream content);

    /**
     * 验证签名
     * <p>注：当未封装签名数据时，无法验证签名</p>
     *
     * @param signature 签名
     */
    void verifySignature(PDSignature signature);

    /**
     * 验证证书
     *
     * @param signature 签名
     */
    void verifyCertificate(PDSignature signature);

    /**
     * 获取CMS数据
     *
     * @param content 内容流
     * @return 返回CMS数据
     */
    default CMSTypedData getCMSData(InputStream content) {
        return new DefaultCMSProcessor(content);
    }
}
