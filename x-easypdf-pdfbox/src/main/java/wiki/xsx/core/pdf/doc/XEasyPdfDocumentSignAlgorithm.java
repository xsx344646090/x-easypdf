package wiki.xsx.core.pdf.doc;

/**
 * pdf文档签名算法
 *
 * @author xsx
 * @date 2022/8/10
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
public enum XEasyPdfDocumentSignAlgorithm {
    /**
     * RSA
     */
    NONEwithRSA("RSA", "NONEwithRSA"),
    MD2withRSA("RSA", "MD2withRSA"),
    MD5withRSA("RSA", "MD5withRSA"),
    SHA1withRSA("RSA", "SHA1withRSA"),
    SHA256withRSA("RSA", "SHA256withRSA"),
    SHA384withRSA("RSA", "SHA384withRSA"),
    SHA512withRSA("RSA", "SHA512withRSA"),

    /**
     * DSA
     */
    NONEwithDSA("DSA", "NONEwithDSA"),
    SHA1withDSA("DSA", "SHA1withDSA"),

    /**
     * ECDSA
     */
    NONEwithECDSA("ECDSA", "NONEwithECDSA"),
    SHA1withECDSA("ECDSA", "SHA1withECDSA"),
    SHA256withECDSA("ECDSA", "SHA256withECDSA"),
    SHA384withECDSA("ECDSA", "SHA384withECDSA"),
    SHA512withECDSA("ECDSA", "SHA512withECDSA");

    /**
     * 类型
     */
    private String type;
    /**
     * 名称
     */
    private String name;

    /**
     * 有参构造
     *
     * @param type 类型
     * @param name 名称
     */
    XEasyPdfDocumentSignAlgorithm(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
