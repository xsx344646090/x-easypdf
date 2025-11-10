package org.dromara.pdf.pdfbox.core.ext.processor.sign;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.cms.CMSTypedData;
import org.dromara.pdf.shade.org.apache.pdfbox.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 默认内容处理器
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
public class DefaultCMSProcessor implements CMSTypedData {
    /**
     * 输入流
     */
    private final InputStream in;
    /**
     * 内容类型
     */
    private final ASN1ObjectIdentifier contentType;

    /**
     * 有参构造
     *
     * @param is 输入流
     */
    public DefaultCMSProcessor(InputStream is) {
        this(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()), is);
    }

    /**
     * 有参构造
     *
     * @param type 内容类型
     * @param is   输入流
     */
    public DefaultCMSProcessor(ASN1ObjectIdentifier type, InputStream is) {
        this.contentType = type;
        this.in = is;
    }

    /**
     * 获取内容
     *
     * @return 返回输入流
     */
    @Override
    public Object getContent() {
        return this.in;
    }

    /**
     * 写出
     *
     * @param out 输出流
     * @throws IOException IO异常
     */
    @Override
    public void write(OutputStream out) throws IOException {
        IOUtils.copy(this.in, out);
        this.in.close();
    }

    /**
     * 获取内容类型
     *
     * @return 返回内容类型
     */
    @Override
    public ASN1ObjectIdentifier getContentType() {
        return this.contentType;
    }
}
