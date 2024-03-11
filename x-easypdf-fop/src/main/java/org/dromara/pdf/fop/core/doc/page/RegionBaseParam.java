package org.dromara.pdf.fop.core.doc.page;

import lombok.Data;
import lombok.experimental.Accessors;
import org.dromara.pdf.fop.core.doc.component.Component;
import org.dromara.pdf.fop.core.doc.watermark.WatermarkComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * pdf模板-区域基础参数
 *
 * @author xsx
 * @date 2022/11/7
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
class RegionBaseParam {

    /**
     * 背景
     */
    protected String background;
    /**
     * 背景图片
     * <p>注：路径须写为”url('xxx.png')“的形式</p>
     * <p>注：当为windows系统绝对路径时，须添加前缀“/”，例如：”url('/E:\test\test.png')“</p>
     */
    protected String backgroundImage;
    /**
     * 背景图片宽度
     */
    protected String backgroundImageWidth;
    /**
     * 背景图片高度
     */
    protected String backgroundImageHeight;
    /**
     * 背景附件
     * <p>scroll：滚动</p>
     * <p>fixed：固定</p>
     */
    protected String backgroundAttachment;
    /**
     * 背景颜色
     * <p>color：颜色（名称或16进制颜色）</p>
     * <p>transparent：透明</p>
     */
    protected String backgroundColor;
    /**
     * 背景图片定位
     * <p>第一个参数为X轴</p>
     * <p>第二个参数为Y轴</p>
     */
    protected String backgroundPosition;
    /**
     * 背景图片水平定位
     */
    protected String backgroundPositionHorizontal;
    /**
     * 背景图片垂直定位
     */
    protected String backgroundPositionVertical;
    /**
     * 背景图片重复
     * <p>repeat：水平垂直重复</p>
     * <p>repeat-x：水平重复</p>
     * <p>repeat-y：垂直重复</p>
     * <p>no-repeat：不重复</p>
     */
    protected String backgroundRepeat;
    /**
     * 组件列表
     */
    protected final List<Component> components = new ArrayList<>(10);
    /**
     * 水印组件
     */
    protected WatermarkComponent watermark;
}
