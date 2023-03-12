import{_ as t,V as e,W as l,$ as n}from"./framework-e82ec112.js";const d={},r=n('<h2 id="常用元素" tabindex="-1"><a class="header-anchor" href="#常用元素" aria-hidden="true">#</a> 常用元素</h2><div class="hint-container info"><p class="hint-container-title">说明</p><p>XSL-FO 文档中的常用元素如下所示：</p></div><table><thead><tr><th style="text-align:center;"><div style="width:240px;">元素节点</div></th><th style="text-align:left;"><div style="text-align:center;">说明</div></th></tr></thead><tbody><tr><td style="text-align:center;">&lt;fo:root /&gt;</td><td style="text-align:left;">根元素节点，XSL-FO 文档的顶部节点。其他任意元素节点应包含在此元素节点下。</td></tr><tr><td style="text-align:center;">&lt;fo:layout-master-set /&gt;</td><td style="text-align:left;">页面布局元素节点，用于定义页面模板的集合。</td></tr><tr><td style="text-align:center;">&lt;fo:simple-page-master /&gt;</td><td style="text-align:left;">页面模板元素节点，用于定义单个页面模板，其页面被细分为多个区域。</td></tr><tr><td style="text-align:center;">&lt;fo:region-body /&gt;</td><td style="text-align:left;">页面区域主体元素节点，用于展示文档的主体内容，可包含文字、图像与表格等。</td></tr><tr><td style="text-align:center;">&lt;fo:region-before /&gt;</td><td style="text-align:left;">页面区域页眉元素节点，用于展示文档的页眉内容，可包含文字、图像与表格等。</td></tr><tr><td style="text-align:center;">&lt;fo:region-after /&gt;</td><td style="text-align:left;">页面区域页脚元素节点，用于展示文档的页脚内容，可包含文字、图像与表格等。</td></tr><tr><td style="text-align:center;">&lt;fo:region-start /&gt;</td><td style="text-align:left;">页面区域左侧栏元素节点，用于展示文档的左侧栏内容，可包含文字、图像与表格等。</td></tr><tr><td style="text-align:center;">&lt;fo:region-end /&gt;</td><td style="text-align:left;">页面区域右侧栏元素节点，用于展示文档的右侧栏内容，可包含文字、图像与表格等。</td></tr><tr><td style="text-align:center;">&lt;fo:page-sequence /&gt;</td><td style="text-align:left;">页面序列元素节点，用于定义新页面内容，一个 “&lt;fo:page-sequence /&gt;” 元素节点即表示一个新页面的开始。</td></tr><tr><td style="text-align:center;">&lt;fo:flow /&gt;</td><td style="text-align:left;">页面流元素节点，用于定义页面内容的流向，例如页眉或页脚。</td></tr><tr><td style="text-align:center;">&lt;fo:block /&gt;</td><td style="text-align:left;">块元素节点，用于定义格式化段落、标题、图像和表格等。</td></tr><tr><td style="text-align:center;">&lt;fo:inline /&gt;</td><td style="text-align:left;">内联元素节点，用于定义格式化文本。</td></tr><tr><td style="text-align:center;">&lt;fo:external-graphic /&gt;</td><td style="text-align:left;">外部图像元素节点，用于定义外部图像。</td></tr><tr><td style="text-align:center;">&lt;fo:table /&gt;</td><td style="text-align:left;">表格元素节点，用于定义表格。</td></tr><tr><td style="text-align:center;">&lt;fo:table-column /&gt;</td><td style="text-align:left;">表格列元素节点，用于定义位于同一列的表格单元格属性。</td></tr><tr><td style="text-align:center;">&lt;fo:table-header /&gt;</td><td style="text-align:left;">表头元素节点，用于定义表头。</td></tr><tr><td style="text-align:center;">&lt;fo:table-footer /&gt;</td><td style="text-align:left;">表尾元素节点，用于定义表尾。</td></tr><tr><td style="text-align:center;">&lt;fo:table-body /&gt;</td><td style="text-align:left;">表格体元素节点，用于定义表格主体。</td></tr><tr><td style="text-align:center;">&lt;fo:table-row /&gt;</td><td style="text-align:left;">表格行元素节点，用于定义表格单行内容。</td></tr><tr><td style="text-align:center;">&lt;fo:table-cell /&gt;</td><td style="text-align:left;">表格单元格元素节点，用于定义表格单个单元格内容。</td></tr><tr><td style="text-align:center;">&lt;fo:list-block /&gt;</td><td style="text-align:left;">列表元素节点，用于定义列表。</td></tr><tr><td style="text-align:center;">&lt;fo:list-item /&gt;</td><td style="text-align:left;">列表项元素节点，用于定义列表项。</td></tr><tr><td style="text-align:center;">&lt;fo:list-item-label /&gt;</td><td style="text-align:left;">列表项标签元素节点，用于定义列表项标签内容。</td></tr><tr><td style="text-align:center;">&lt;fo:list-item-body /&gt;</td><td style="text-align:left;">列表项主体元素节点，用于定义列表项主体内容。</td></tr><tr><td style="text-align:center;">&lt;fo:static-content /&gt;</td><td style="text-align:left;">静态内容元素节点，用于定义页面静态内容。当文档进行分页时，新页面也将添加所定义的静态内容。通常用于页眉与页脚。</td></tr><tr><td style="text-align:center;">&lt;fo:page-number /&gt;</td><td style="text-align:left;">页面索引（页码）元素节点，用于显示当前页面的页码。</td></tr><tr><td style="text-align:center;">&lt;fo:page-number-citation-last /&gt;</td><td style="text-align:left;">总页面索引（总页码）元素节点，用于显示当前文档的总页码。</td></tr></tbody></table><div class="hint-container tip"><p class="hint-container-title">提示</p><p>“&lt;fo:page-number-citation-last /&gt;” 元素节点需与 “&lt;fo:page-sequence /&gt;” 元素节点配合使用。 在 “&lt;fo:page-sequence /&gt;” 元素节点中定义一个 “id” 属性，然后在 “&lt;fo:page-number-citation-last /&gt;” 元素节点中定义一个 “ref-id” 属性， 并且该属性值为 “&lt;fo:page-sequence /&gt;” 元素节点中定义的 “id” 属性值，这样就可以获取到文档的总页码了。</p></div><div class="hint-container warning"><p class="hint-container-title">特别注意</p><p>在文档中，当只有一个 “&lt;fo:page-sequence /&gt;” 元素节点时，获取到的总页码才是正确值，否则该总页码将不准确。</p></div>',5),a=[r];function i(g,s){return e(),l("div",null,a)}const f=t(d,[["render",i],["__file","常用元素.html.vue"]]);export{f as default};
