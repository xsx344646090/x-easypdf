#### 什么是 XSL-FO?

- XSL-FO 是用于格式化 XML 数据的语言
- XSL-FO 指可扩展样式表语言格式化对象（Extensible Stylesheet Language Formatting Objects）
- XSL-FO 是一个 W3C 推荐标准
- XSL-FO 目前通常被称为 XSL

---

#### XSL-FO 和格式化有关

XSL-FO 是一种基于 XML 的标记语言，用于描述向屏幕、纸或者其他媒介输出 XML 数据的格式化（信息）。

---

#### XSL-FO 目前通常被称为 XSL

为什么会存在这样的混淆呢？XSL-FO 和 XSL 是一回事吗？

可以这么说，不过我们需要向您作一个解释：

样式化（Styling）是关于 **转换信息** 和 **格式化信息** 两方面的信息。在万维网联盟编写他们的首个 XSL 工作草案的时候，这个草案包括了有关转换和格式化 XML 文档的语言语法。

后来，XSL 工作组把这个原始的草案分为独立的标准：

- XSLT，用于转换 XML 文档的语言
- XSL 和 XSL-FO，用于格式化 XML 文档的语言
- XPath，是通过元素和属性在 XML 文档中进行导航的语言

---

#### 什么是 XSLT？

- XSLT 指 XSL 转换（XSL Transformations）。
- XSLT 是 XSL 中最重要的部分。
- XSLT 可将一种 XML 文档转换为另外一种 XML 文档。
- XSLT 使用 XPath 在 XML 文档中进行导航。
- XPath 是一个 W3C 标准。

---

#### XSLT = XSL 转换

XSLT 是 XSL 中最重要的部分。

XSLT 用于将一种 XML 文档转换为另外一种 XML 文档，或者可被浏览器识别的其他类型的文档，比如 HTML 和 XHTML。通常，XSLT 是通过把每个 XML 元素转换为 (X)HTML 元素来完成这项工作的。

通过 XSLT，您可以向或者从输出文件添加或移除元素和属性。您也可重新排列元素，执行测试并决定隐藏或显示哪个元素，等等。

描述转化过程的一种通常的说法是，XSLT 把 XML 源树转换为 XML 结果树。

---

#### XSL-FO 的文档结构

XSL-FO 是一种 XML 文档，其基本结构如下：

```xml
<!--XML声明-->
<?xml version="1.0" encoding="utf-8"?>

<!--根标签-->
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <!--页面布局-->
    <fo:layout-master-set>
        <!--单页面模板-->
        <fo:simple-page-master master-name="A4">
            <!--页面区域主体-->
            <fo:region-body />
        </fo:simple-page-master>
    </fo:layout-master-set>
    <!--页面序列-->
    <fo:page-sequence master-reference="A4">
        <!--页面流-->
        <fo:flow flow-name="xsl-region-body">
            <!--块-->
            <fo:block/>
        </fo:flow>
    </fo:page-sequence>
</fo:root>
```

---

#### 结构说明

- XML 声明：

```xml
<?xml version="1.0" encoding="utf-8"?>
```



- 根元素：

```xml
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<!--此处为文档内容-->
</fo:root>
```

说明：此元素标签中还应包含 fo 的命名空间  “ xmlns:fo=" http://www.w3.org/1999/XSL/Format " ”



- 页面布局：

```xml
<fo:layout-master-set>
	<!--此处为页面模板-->
</fo:layout-master-set>
```

说明：此元素标签内应包含至少一个页面模板



- 页面模板：

```xml
<fo:simple-page-master master-name="A4">
    <!--此处为页面区域-->
</fo:simple-page-master>
```

说明：此元素标签内应包含至少一个页面主体区域，且应当设置模板名称 “master-name”



- 页面区域：

```xml
<!--页面主体-->
<fo:region-body />
<!--页眉-->
<fo:region-before />
<!--页脚-->
<fo:region-after />
<!--左侧栏-->
<fo:region-start />
<!--右侧栏-->
<fo:region-end />
```

说明：此元素标签中 “<fo:region-body />” 为必选项，且需写在第一行，其余为可选项，可根据需要添加



- 页面序列：

```xml
<fo:page-sequence master-reference="A4">
    <!--此处为页面流-->
</fo:page-sequence>
```

说明：此元素标签内应包含至少一个页面流，且应当设置指向的模板名称 “master-reference”。同时，一个页面序列就表示一个新的页面



- 页面流：

```xml
<!--页面流-->
<fo:flow flow-name="xsl-region-body">
    <!--此处为页面内容-->
</fo:flow>
```

说明：此元素标签内应包含至少一个页面内容，且应当设置指向的区域名称 “flow-name”。区域名称为固定值，具体内容如下：

| 区域名称          | 指向     |
| ----------------- | -------- |
| xsl-region-body   | 页面主体 |
| xsl-region-before | 页眉     |
| xsl-region-after  | 页脚     |
| xsl-region-start  | 左侧栏   |
| xsl-region-end    | 右侧栏   |

---

#### XSL-FO 的页面布局

XSL-FO 的页面布局如下图所示：

![页面布局](https://oscimg.oschina.net/oscnet/up-d7519fd01a38ce1830aeffc2a21235b7eac.png)

---

#### 布局说明

- 边距：
    - margin-top：上边距
    - margin-bottom：下边距
    - margin-left：左边距
    - margin-right：右边距
- 区域：
    - region-body：页面主体
    - region-before：页眉
    - region-after：页脚
    - region-start：左侧栏
    - region-end：右侧栏

注：“region-before”、“region-after”、“region-start” 以及 “region-end” 为页面主体的一部分。为了避免主体的内容覆盖到这些区域，主体的边距至少要等于其他区域的尺寸。例如：

```xml
<fo:simple-page-master master-name="A4" margin-top="20px" margin-bottom="20px" margin-left="10px" margin-right="10px">
    <!--页面主体-->
    <fo:region-body margin="30px"/>
    <!--页眉-->
    <fo:region-before extent="10px"/>
    <!--页脚-->
    <fo:region-after extent="10px"/>
    <!--左侧栏-->
    <fo:region-start extent="20px"/>
    <!--右侧栏-->
    <fo:region-end extent="20px"/>
</fo:simple-page-master>
```

说明：属性 “extent” 表示区域的尺寸，属性 “margin” 表示主体的上下左右边距。由于页面 “margin-top” 与 ”margin-bottom“ 为 ”20px“， “margin-left” 与 ”margin-right“ 为 ”10px“，并且 ”region-before“ 与 ”region-after“ 的属性 “extent” 为 ”10px“， ”region-start“ 与 ”region-end“ 的属性 “extent” 为 ”20px“，所以 ”region-body“ 的属性 ”margin“ 至少为 ”30px“ 才不会覆盖到其他区域。其计算公式为：页面主体边距 = 页面边距 + 区域尺寸。

例如页面主体上边距的计算如下：
region-body.margin-top = simple-page-master.margin-top + region-before.extent
”region-body“ 代表 “<fo:region-body />” 标签
”simple-page-master“ 代表 “<fo:simple-page-master />” 标签
”region-before“ 代表 “<fo:region-before />” 标签。

---

#### XSL-FO 中的单位

- cm：厘米
- mm：毫米
- in：英寸
- pt：点（72 点 = 1 英寸）
- pc：派卡（12 点 = 1 派卡，6 派卡 = 1 英寸）
- px：像素（有时随格式化程序或设备的不同而有所不同）
- em：一个大写 M 的宽度

---

#### XSL-FO 中的常用元素节点

- ##### <fo:root />

> 说明：根元素节点，XSL-FO 文档的顶部节点。其他任意元素节点应包含在此元素节点下。

---



- ##### <fo:layout-master-set />

> 说明：页面布局元素节点，用于定义页面模板的集合。

---



- ##### <fo:simple-page-master />

> 说明：页面模板元素节点，用于定义单个页面模板，其页面被细分为多个区域。

---



- ##### <fo:region-body />

> 说明：页面区域主体元素节点，用于展示文档的主体内容，可包含文字、图像与表格等。

---



- ##### <fo:region-before />

> 说明：页面区域页眉元素节点，用于展示文档的页眉内容，可包含文字、图像与表格等。

---



- ##### <fo:region-after />

> 说明：页面区域页脚元素节点，用于展示文档的页脚内容，可包含文字、图像与表格等。

---



- ##### <fo:region-start />

> 说明：页面区域左侧栏元素节点，用于展示文档的左侧栏内容，可与 “<fo:region-body />” 、“<fo:region-end />” 共同组成三栏布局。

---



- ##### <fo:region-end />

> 说明：页面区域右侧栏元素节点，用于展示文档的右侧栏内容，可与 “<fo:region-body />” 、“<fo:region-start />” 共同组成三栏布局。

---



- ##### <fo:page-sequence />

> 说明：页面序列元素节点，用于定义新页面内容，一个 “<fo:page-sequence />” 元素节点即表示一个新页面的开始。

---



- ##### <fo:flow />

> 说明：页面流元素节点，用于定义页面内容的流向，例如页眉或页脚。

---



- ##### <fo:block />

> 说明：块元素节点，用于定义格式化段落、标题、图像和表格等。

---



- ##### <fo:inline />

> 说明：内联元素节点，用于定义格式化文本。

---



- ##### <fo:external-graphic />

> 说明：外部图像元素节点，用于定义外部图像。

---



- ##### <fo:table />

> 说明：表格元素节点，用于定义表格。

---



- ##### <fo:table-column />

> 说明：表格列元素节点，用于定义位于同一列的表格单元格属性。

---



- ##### <fo:table-header />

> 说明：表头元素节点，用于定义表头。

---



- ##### <fo:table-footer />

> 说明：表尾元素节点，用于定义表尾。

---



- ##### <fo:table-body />

> 说明：表格体元素节点，用于定义表格主体。

---



- ##### <fo:table-row />

> 说明：表格行元素节点，用于定义表格单行内容。

---



- ##### <fo:table-cell />

> 说明：表格单元格元素节点，用于定义表格单个单元格内容。

---



- ##### <fo:list-block />

> 说明：列表元素节点，用于定义列表。

---



- ##### <fo:list-item />

> 说明：列表项元素节点，用于定义列表项。

---



- ##### <fo:list-item-label />

> 说明：列表项标签元素节点，用于定义列表项标签内容。

---



- ##### <fo:list-item-body />

> 说明：列表项主体元素节点，用于定义列表项主体内容。

---



- ##### <fo:static-content />

> 说明：静态内容元素节点，用于定义页面静态内容。当文档进行分页时，新页面也将添加所定义的静态内容。通常用于页眉与页脚。

---



- ##### <fo:page-number />

> 说明：页面索引（页码）元素节点，用于获取当前页面的页码。

---



- ##### <fo:page-number-citation-last />

> 说明：总页面索引（总页码）元素节点，用于获取当前文档的总页码。需与 “<fo:page-sequence />” 元素节点配合使用。在 “<fo:page-sequence />” 元素节点中定义一个 “id” 属性，然后在 “<fo:page-number-citation-last />” 元素节点中定义一个 “ref-id” 属性，并且该属性值为 “<fo:page-sequence />” 元素节点中定义的 “id” 属性值，这样就可以获取到文档的总页码了。特别注意的是，在文档中，当只有一个 “<fo:page-sequence />” 元素节点时，获取到的总页码才是正确值，否则该总页码将不准确。
