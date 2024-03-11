package org.dromara.pdf.fop.support.freemarker;

import freemarker.cache.URLTemplateLoader;
import lombok.Setter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * 远程模板加载器
 *
 * @author xsx
 * @date 2024/1/15
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
public class DefaultURLTemplateLoader extends URLTemplateLoader {

    /**
     * 远程路径（目录）
     */
    private final String remotePath;
    /**
     * 模板名称
     */
    @Setter
    private String templateName;

    /**
     * 有参构造
     *
     * @param remotePath 远程路径（目录）
     */
    public DefaultURLTemplateLoader(String remotePath) {
        this.remotePath = remotePath;
    }

    /**
     * 获取路径
     *
     * @param name 模板名称
     * @return 返回路径
     */
    @Override
    protected URL getURL(String name) {
        if (!Objects.equals(this.templateName, name)) {
            return null;
        }
        String fullPath = this.remotePath + name;
        if ((this.remotePath.equals("/")) && this.isInvalid(fullPath)) {
            return null;
        }

        URL url;
        try {
            url = new URL(fullPath);
        } catch (MalformedURLException e) {
            url = null;
        }
        return url;
    }

    /**
     * 是否无效
     *
     * @param fullPath 完整路径
     * @return
     */
    private boolean isInvalid(String fullPath) {
        int i = 0;
        int ln = fullPath.length();
        if ((i < ln) && (fullPath.charAt(i) == '/')) {
            i++;
        }
        while (i < ln) {
            char c = fullPath.charAt(i);
            if (c == '/') {
                return false;
            }
            if (c == ':') {
                return true;
            }
            i++;
        }
        return false;
    }
}
