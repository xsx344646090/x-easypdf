package wiki.xsx.core.pdf.doc;

import java.io.Serializable;

/**
 * pdf文档表单
 *
 * @author xsx
 * @date 2022/4/2
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
public class XEasyPdfDocumentForm implements Serializable {

    private static final long serialVersionUID = 347480015461916142L;

    /**
     * pdf文档表单填写器
     */
    private final XEasyPdfDocumentFormFiller formFiller;

    /**
     * 有参构造
     *
     * @param formFiller pdf文档表单填写器
     */
    XEasyPdfDocumentForm(XEasyPdfDocumentFormFiller formFiller) {
        this.formFiller = formFiller;
    }

    /**
     * 创建文本属性
     *
     * @return 返回pdf表单文本属性
     */
    public XEasyPdfDocumentFormTextField createTextField() {
        return new XEasyPdfDocumentFormTextField(this);
    }

    /**
     * 完成操作
     *
     * @return 返回pdf文档表单填写器
     */
    public XEasyPdfDocumentFormFiller finish() {
        return this.formFiller;
    }

    /**
     * 获取pdf文档表单填写器
     *
     * @return 返回pdf文档表单填写器
     */
    XEasyPdfDocumentFormFiller getFormFiller() {
        return this.formFiller;
    }
}
