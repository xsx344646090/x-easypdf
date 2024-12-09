package org.dromara.pdf.fop.core.doc;

import lombok.Data;
import lombok.experimental.Accessors;
import org.dromara.pdf.fop.core.doc.bookmark.BookmarkComponent;
import org.dromara.pdf.fop.core.doc.page.PageComponent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * pdf模板-文档参数
 *
 * @author xsx
 * @date 2022/8/7
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under the Mulan PSL v2.
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
class DocumentParam {
    /**
     * 配置文件路径（fop配置文件路径）
     */
    private String configPath;
    /**
     * 加密长度
     */
    private Integer encryptionLength;
    /**
     * 拥有者密码
     */
    private String ownerPassword;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 是否禁止打印
     */
    private Boolean isNoPrint;
    /**
     * 是否禁止编辑
     */
    private Boolean isNoEdit;
    /**
     * 是否禁止文档组合
     */
    private Boolean isNoAssembleDoc;
    /**
     * 是否禁止复制
     */
    private Boolean isNoCopy;
    /**
     * 是否禁止复制内容用于辅助工具
     */
    private Boolean isNoAccessContent;
    /**
     * 是否禁止页面提取
     */
    private Boolean isNoPrintHQ;
    /**
     * 是否禁止注释
     */
    private Boolean isNoAnnotations;
    /**
     * 是否禁止填写表单
     */
    private Boolean isNoFillForm;
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
     * pdf模板页面列表
     */
    private final List<PageComponent> pageList = new ArrayList<>(10);
    /**
     * pdf模板书签列表
     */
    private List<BookmarkComponent> bookmarkList = new ArrayList<>(10);
}
