package org.dromara.pdf.fop.support.event;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.area.AreaTreeHandler;
import org.dromara.pdf.fop.core.base.Constants;
import org.dromara.pdf.fop.support.font.FontInfoHelper;

import java.io.OutputStream;

/**
 *
 * 默认区域树助手
 *
 * @author xsx
 * @date 2025/1/23
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * gitee is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class DefaultAreaTreeHandler extends AreaTreeHandler {

    /**
     * 有参构造
     *
     * @param userAgent 用户代理
     * @param stream    输出流
     * @throws FOPException fop异常
     */
    public DefaultAreaTreeHandler(FOUserAgent userAgent, OutputStream stream) throws FOPException {
        super(userAgent, Constants.MIME_TYPE, stream);
        FontInfoHelper.init(this.fontInfo);
    }
}
