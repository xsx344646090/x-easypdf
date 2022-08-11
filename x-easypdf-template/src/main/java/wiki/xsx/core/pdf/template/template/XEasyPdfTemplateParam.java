package wiki.xsx.core.pdf.template.template;

import lombok.Data;
import wiki.xsx.core.pdf.template.XEasyPdfTemplateConstants;
import wiki.xsx.core.pdf.template.template.datasource.XEasyPdfTemplateDataSource;

import java.util.Date;

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
     * 数据源
     */
    private XEasyPdfTemplateDataSource dataSource;
    /**
     * 是否开启辅助功能
     */
    private Boolean isAccessibility = Boolean.FALSE;
    /**
     * 是否开启保留空标签
     */
    private Boolean isKeepEmptyTags = Boolean.TRUE;

    /**
     * 初始化
     */
    void init() {
        if (this.configPath == null) {
            this.configPath = XEasyPdfTemplateConstants.DEFAULT_CONFIG_PATH;
        }
        if (this.dataSource == null) {
            throw new IllegalArgumentException("the data source can not be null");
        }
    }
}
