package wiki.xsx.core.pdf.template;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * pdf模板参数
 *
 * @author xsx
 * @date 2022/7/31
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2022 xsx All Rights Reserved.
 * x-easypdf is licensed under the Mulan PSL v2.
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
class XEasyPdfTemplateParam {

    /**
     * 配置路径
     */
    private String configPath;
    /**
     * 模板路径
     */
    private String templatePath;
    /**
     * 模板数据
     */
    private Map<String, Object> templateData;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 主题
     */
    private String subject;
    /**
     * 关键词
     */
    private String keywords;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 初始化
     */
    void init() {
        if (this.configPath == null) {
            throw new IllegalArgumentException("the config path can not be null");
        }
        if (this.templatePath == null) {
            throw new IllegalArgumentException("the template path can not be null");
        }
    }

    /**
     * 模板数据是否非空
     * @return 返回布尔值，是为true，否为false
     */
    boolean isNotEmptyTemplateData() {
        return this.templateData != null && !this.templateData.isEmpty();
    }
}
