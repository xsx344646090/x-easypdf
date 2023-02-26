import{_ as a,V as n,W as s,$ as e}from"./framework-e82ec112.js";const t={},p=e(`<h2 id="介绍" tabindex="-1"><a class="header-anchor" href="#介绍" aria-hidden="true">#</a> 介绍</h2><div class="hint-container info"><p class="hint-container-title">说明</p><p>内置的统一对象构建助手 XEasyPdfTemplateHandler ，可帮助开发者快速构建出所需对象。</p></div><h2 id="文档对象" tabindex="-1"><a class="header-anchor" href="#文档对象" aria-hidden="true">#</a> 文档对象</h2><div class="language-java line-numbers-mode" data-ext="java"><pre class="language-java"><code><span class="token class-name">XEasyPdfTemplateDocument</span> document <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Document</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="页面对象" tabindex="-1"><a class="header-anchor" href="#页面对象" aria-hidden="true">#</a> 页面对象</h2><div class="language-java line-numbers-mode" data-ext="java"><pre class="language-java"><code><span class="token class-name">XEasyPdfTemplatePage</span> page <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Page</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="书签对象" tabindex="-1"><a class="header-anchor" href="#书签对象" aria-hidden="true">#</a> 书签对象</h2><div class="language-java line-numbers-mode" data-ext="java"><pre class="language-java"><code><span class="token class-name">XEasyPdfTemplateBookmark</span> bookmark <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Bookmark</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="水印对象" tabindex="-1"><a class="header-anchor" href="#水印对象" aria-hidden="true">#</a> 水印对象</h2><div class="language-java line-numbers-mode" data-ext="java"><pre class="language-java"><code><span class="token class-name">XEasyPdfTemplateWatermark</span> watermark <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Watermark</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="文本对象" tabindex="-1"><a class="header-anchor" href="#文本对象" aria-hidden="true">#</a> 文本对象</h2><div class="language-java line-numbers-mode" data-ext="java"><pre class="language-java"><code><span class="token comment">// 文本</span>
<span class="token class-name">XEasyPdfTemplateText</span> text <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Text</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 扩展文本</span>
<span class="token class-name">XEasyPdfTemplateTextExtend</span> textExtend <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>TextExtend</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="图像对象" tabindex="-1"><a class="header-anchor" href="#图像对象" aria-hidden="true">#</a> 图像对象</h2><div class="language-java line-numbers-mode" data-ext="java"><pre class="language-java"><code><span class="token class-name">XEasyPdfTemplateImage</span> image <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Image</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="表格对象" tabindex="-1"><a class="header-anchor" href="#表格对象" aria-hidden="true">#</a> 表格对象</h2><div class="language-java line-numbers-mode" data-ext="java"><pre class="language-java"><code><span class="token comment">// 表格</span>
<span class="token class-name">XEasyPdfTemplateTable</span> table <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Table</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 表头</span>
<span class="token class-name">XEasyPdfTemplateTableHeader</span> header <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Table<span class="token punctuation">.</span>Header</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 表尾</span>
<span class="token class-name">XEasyPdfTemplateTableFooter</span> footer <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Table<span class="token punctuation">.</span>Footer</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 表格体</span>
<span class="token class-name">XEasyPdfTemplateTableBody</span> body <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Table<span class="token punctuation">.</span>Body</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 表格行</span>
<span class="token class-name">XEasyPdfTemplateTableRow</span> row <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Table<span class="token punctuation">.</span>Row</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 表格单元格</span>
<span class="token class-name">XEasyPdfTemplateTableCell</span> cell <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Table<span class="token punctuation">.</span>Cell</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="条码对象" tabindex="-1"><a class="header-anchor" href="#条码对象" aria-hidden="true">#</a> 条码对象</h2><div class="language-java line-numbers-mode" data-ext="java"><pre class="language-java"><code><span class="token class-name">XEasyPdfTemplateBarcode</span> barcode <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>Barcode</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="块容器对象" tabindex="-1"><a class="header-anchor" href="#块容器对象" aria-hidden="true">#</a> 块容器对象</h2><div class="language-java line-numbers-mode" data-ext="java"><pre class="language-java"><code><span class="token class-name">XEasyPdfTemplateBlockContainer</span> blockContainer <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>BlockContainer</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="分割线对象" tabindex="-1"><a class="header-anchor" href="#分割线对象" aria-hidden="true">#</a> 分割线对象</h2><div class="language-java line-numbers-mode" data-ext="java"><pre class="language-java"><code><span class="token class-name">XEasyPdfTemplateSplitLine</span> splitLine <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>SplitLine</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="页码对象" tabindex="-1"><a class="header-anchor" href="#页码对象" aria-hidden="true">#</a> 页码对象</h2><div class="language-java line-numbers-mode" data-ext="java"><pre class="language-java"><code><span class="token comment">// 当前页码</span>
<span class="token class-name">XEasyPdfTemplateCurrentPageNumber</span> currentPageNumber <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>CurrentPageNumber</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 总页码</span>
<span class="token class-name">XEasyPdfTemplateTotalPageNumber</span> totalPageNumber <span class="token operator">=</span> <span class="token class-name">XEasyPdfTemplateHandler<span class="token punctuation">.</span>TotalPageNumber</span><span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div>`,24),c=[p];function l(o,i){return n(),s("div",null,c)}const d=a(t,[["render",l],["__file","构建助手.html.vue"]]);export{d as default};
