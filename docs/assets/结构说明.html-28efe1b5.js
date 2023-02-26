import{_ as e,V as l,W as p,X as a,Y as n,Z as o,$ as s,F as c}from"./framework-e82ec112.js";const i={},d=s(`<h2 id="xml-声明" tabindex="-1"><a class="header-anchor" href="#xml-声明" aria-hidden="true">#</a> XML 声明</h2><div class="language-xml line-numbers-mode" data-ext="xml"><pre class="language-xml"><code><span class="token prolog">&lt;?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?&gt;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="根元素" tabindex="-1"><a class="header-anchor" href="#根元素" aria-hidden="true">#</a> 根元素</h2><div class="language-xml line-numbers-mode" data-ext="xml"><pre class="language-xml"><code><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span><span class="token namespace">fo:</span>root</span> <span class="token attr-name"><span class="token namespace">xmlns:</span>fo</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">&quot;</span>http://www.w3.org/1999/XSL/Format<span class="token punctuation">&quot;</span></span><span class="token punctuation">&gt;</span></span>
	<span class="token comment">&lt;!--此处为文档内容--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span><span class="token namespace">fo:</span>root</span><span class="token punctuation">&gt;</span></span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div>`,4),r={class:"hint-container tip"},u=a("p",{class:"hint-container-title"},"提示",-1),m={href:"http://www.w3.org/1999/XSL/Format",target:"_blank",rel:"noopener noreferrer"},k=s(`<h2 id="页面布局" tabindex="-1"><a class="header-anchor" href="#页面布局" aria-hidden="true">#</a> 页面布局</h2><div class="language-xml line-numbers-mode" data-ext="xml"><pre class="language-xml"><code><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span><span class="token namespace">fo:</span>layout-master-set</span><span class="token punctuation">&gt;</span></span>
	<span class="token comment">&lt;!--此处为页面模板--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span><span class="token namespace">fo:</span>layout-master-set</span><span class="token punctuation">&gt;</span></span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><div class="hint-container tip"><p class="hint-container-title">提示</p><p>此元素标签内应包含至少一个页面模板</p></div><h2 id="页面模板" tabindex="-1"><a class="header-anchor" href="#页面模板" aria-hidden="true">#</a> 页面模板</h2><div class="language-xml line-numbers-mode" data-ext="xml"><pre class="language-xml"><code><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span><span class="token namespace">fo:</span>simple-page-master</span> <span class="token attr-name">master-name</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">&quot;</span>A4<span class="token punctuation">&quot;</span></span><span class="token punctuation">&gt;</span></span>
    <span class="token comment">&lt;!--此处为页面区域--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span><span class="token namespace">fo:</span>simple-page-master</span><span class="token punctuation">&gt;</span></span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><div class="hint-container tip"><p class="hint-container-title">提示</p><p>此元素标签内应包含至少一个页面主体区域，且应当设置模板名称 “master-name”</p></div><h2 id="页面区域" tabindex="-1"><a class="header-anchor" href="#页面区域" aria-hidden="true">#</a> 页面区域</h2><div class="language-xml line-numbers-mode" data-ext="xml"><pre class="language-xml"><code><span class="token comment">&lt;!--页面主体--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span><span class="token namespace">fo:</span>region-body</span> <span class="token punctuation">/&gt;</span></span>
<span class="token comment">&lt;!--页眉--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span><span class="token namespace">fo:</span>region-before</span> <span class="token punctuation">/&gt;</span></span>
<span class="token comment">&lt;!--页脚--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span><span class="token namespace">fo:</span>region-after</span> <span class="token punctuation">/&gt;</span></span>
<span class="token comment">&lt;!--左侧栏--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span><span class="token namespace">fo:</span>region-start</span> <span class="token punctuation">/&gt;</span></span>
<span class="token comment">&lt;!--右侧栏--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span><span class="token namespace">fo:</span>region-end</span> <span class="token punctuation">/&gt;</span></span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><div class="hint-container tip"><p class="hint-container-title">提示</p><p>此元素标签中 “&lt;fo:region-body /&gt;” 为必选项，且需写在第一行，其余为可选项，可根据需要添加</p></div><h2 id="页面序列" tabindex="-1"><a class="header-anchor" href="#页面序列" aria-hidden="true">#</a> 页面序列</h2><div class="language-xml line-numbers-mode" data-ext="xml"><pre class="language-xml"><code><span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span><span class="token namespace">fo:</span>page-sequence</span> <span class="token attr-name">master-reference</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">&quot;</span>A4<span class="token punctuation">&quot;</span></span><span class="token punctuation">&gt;</span></span>
    <span class="token comment">&lt;!--此处为页面流--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span><span class="token namespace">fo:</span>page-sequence</span><span class="token punctuation">&gt;</span></span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><div class="hint-container tip"><p class="hint-container-title">提示</p><p>此元素标签内应包含至少一个页面流，且应当设置指向的模板名称 “master-reference”。同时，一个页面序列就表示一个新的页面</p></div><h2 id="页面流" tabindex="-1"><a class="header-anchor" href="#页面流" aria-hidden="true">#</a> 页面流</h2><div class="language-xml line-numbers-mode" data-ext="xml"><pre class="language-xml"><code><span class="token comment">&lt;!--页面流--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span><span class="token namespace">fo:</span>flow</span> <span class="token attr-name">flow-name</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">&quot;</span>xsl-region-body<span class="token punctuation">&quot;</span></span><span class="token punctuation">&gt;</span></span>
    <span class="token comment">&lt;!--此处为页面内容--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span><span class="token namespace">fo:</span>flow</span><span class="token punctuation">&gt;</span></span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><div class="hint-container tip"><p class="hint-container-title">提示</p><p>此元素标签内应包含至少一个页面内容，且应当设置指向的区域名称 “flow-name”。区域名称为固定值，具体内容如下：</p></div><table style="width:auto;display:table;margin-left:auto;margin-right:auto;"><thead><tr><th>区域名称</th><th>指向</th></tr></thead><tbody><tr><td>xsl-region-body</td><td>页面主体</td></tr><tr><td>xsl-region-before</td><td>页眉</td></tr><tr><td>xsl-region-after</td><td>页脚</td></tr><tr><td>xsl-region-start</td><td>左侧栏</td></tr><tr><td>xsl-region-end</td><td>右侧栏</td></tr></tbody></table>`,16);function g(v,h){const t=c("ExternalLinkIcon");return l(),p("div",null,[d,a("div",r,[u,a("p",null,[n('此元素标签中还应包含 fo 的命名空间 “ xmlns:fo=" '),a("a",m,[n("http://www.w3.org/1999/XSL/Format"),o(t)]),n(' " ”')])]),k])}const x=e(i,[["render",g],["__file","结构说明.html.vue"]]);export{x as default};
