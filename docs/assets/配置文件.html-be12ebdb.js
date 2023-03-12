import{_ as n,V as s,W as a,$ as t}from"./framework-e82ec112.js";const p={},e=t(`<h2 id="配置文件" tabindex="-1"><a class="header-anchor" href="#配置文件" aria-hidden="true">#</a> 配置文件</h2><div class="language-xml line-numbers-mode" data-ext="xml"><pre class="language-xml"><code><span class="token prolog">&lt;?xml version=&quot;1.0&quot;?&gt;</span>

<span class="token comment">&lt;!-- fop版本 --&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>fop</span> <span class="token attr-name">version</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">&quot;</span>1.0<span class="token punctuation">&quot;</span></span><span class="token punctuation">&gt;</span></span>

    <span class="token comment">&lt;!-- 当前路径（项目所在路径） --&gt;</span>
    <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>base</span><span class="token punctuation">&gt;</span></span>.<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>base</span><span class="token punctuation">&gt;</span></span>

    <span class="token comment">&lt;!-- 默认源图像 dpi（每英寸点数像素） --&gt;</span>
    <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>source-resolution</span><span class="token punctuation">&gt;</span></span>72<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>source-resolution</span><span class="token punctuation">&gt;</span></span>
    <span class="token comment">&lt;!-- 默认目标图像 dpi（每英寸点数像素），默认: 72dpi --&gt;</span>
    <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>target-resolution</span><span class="token punctuation">&gt;</span></span>72<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>target-resolution</span><span class="token punctuation">&gt;</span></span>

    <span class="token comment">&lt;!-- 默认页面宽度与高度（A4） --&gt;</span>
    <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>default-page-settings</span> <span class="token attr-name">width</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">&quot;</span>21cm<span class="token punctuation">&quot;</span></span> <span class="token attr-name">height</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">&quot;</span>29.7cm<span class="token punctuation">&quot;</span></span><span class="token punctuation">/&gt;</span></span>

    <span class="token comment">&lt;!-- 渲染器 --&gt;</span>
    <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>renderers</span><span class="token punctuation">&gt;</span></span>
        <span class="token comment">&lt;!-- pdf 渲染器 --&gt;</span>
        <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>renderer</span> <span class="token attr-name">mime</span><span class="token attr-value"><span class="token punctuation attr-equals">=</span><span class="token punctuation">&quot;</span>application/pdf<span class="token punctuation">&quot;</span></span><span class="token punctuation">&gt;</span></span>
            <span class="token comment">&lt;!-- 文档版本 --&gt;</span>
            <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>version</span><span class="token punctuation">&gt;</span></span>1.5<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>version</span><span class="token punctuation">&gt;</span></span>
            <span class="token comment">&lt;!-- 过滤器 --&gt;</span>
            <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>filterList</span><span class="token punctuation">&gt;</span></span>
                <span class="token comment">&lt;!-- 默认使用 flate 依赖压缩 --&gt;</span>
                <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>value</span><span class="token punctuation">&gt;</span></span>flate<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>value</span><span class="token punctuation">&gt;</span></span>
            <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>filterList</span><span class="token punctuation">&gt;</span></span>
            <span class="token comment">&lt;!-- 字体 --&gt;</span>
            <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>fonts</span><span class="token punctuation">&gt;</span></span>
                <span class="token comment">&lt;!-- 自动扫描系统全部字体 --&gt;</span>
                <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>auto-detect</span><span class="token punctuation">/&gt;</span></span>
            <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>fonts</span><span class="token punctuation">&gt;</span></span>
        <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>renderer</span><span class="token punctuation">&gt;</span></span>
    <span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>renderers</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>fop</span><span class="token punctuation">&gt;</span></span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><div class="hint-container tip"><p class="hint-container-title">提示</p><p>可根据实际情况进行修改</p></div>`,3),l=[e];function c(o,i){return s(),a("div",null,l)}const k=n(p,[["render",c],["__file","配置文件.html.vue"]]);export{k as default};
