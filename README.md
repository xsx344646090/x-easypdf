<p align="center">
	<img src="https://images.gitee.com/uploads/images/2021/0621/111332_1f43ae97_1494292.png" width="45%">
</p>
<p align="center">
	<strong>pdf 一站式解决方案</strong>
</p>

#### 更新说明
> master分支将作为稳定版本发布，develop分支将会不定期进行更新，欢迎大家提供宝贵意见，QQ交流群：15018726

---

#### 项目主页
> https://x-easypdf.cn

---

#### 项目概述
> dromara x-easypdf是一个java语言简化处理pdf的框架，包含fop模块与pdfbox模块，fop模块以创建功能为主，基于xsl-fo模板生成pdf文档，以数据源的方式进行模板渲染；pdfbox模块以编辑功能为主，对标准的pdfbox进行扩展，添加了成吨的功能。

---

#### 当前版本
> v3.5.3

---

#### 使用环境
> jdk 1.8+

---

#### 项目特性

##### fop模块

支持创建功能，基于 xsl-fo 模板生成 pdf 文档，以数据源的方式进行模板渲染。
- 基于模板生成
- 内置多种数据源（xml 数据源、thymeleaf 数据源、freemarker 数据源、jte 数据源、document 数据源）
- 提供面向对象的方式生成模板（document 数据源）
- 灵活的扩展性

##### pdfbox模块

支持创建与编辑功能，对标准的 pdfbox 进行扩展，添加了成吨的功能。
- 换行与分页：超出页面宽度自动换行，超出页面高度自动分页
- 中文支持：内置华为鸿蒙字体，天然支持中文文本
- 页眉页脚：内置页眉页脚，简单快捷
- 内置组件：内置多个组件，包含文本、图像、条形码、表格、矩形、圆形、线条
- 内置水印：支持文本水印与图像水印
- 文档分析：支持文本分析、图像分析、书签分析、评论分析、表单分析
- 文档比较：支持文本比较与图像比较
- 文档提取：支持文本提取、图像提取、书签提取、评论提取、表单提取
- 文档解析：支持 AI 解析文档内容,适配智谱（glm）、腾讯（hunyuan）、阿里（qwen）、字节跳动（doubao）、月之暗面（kimi）、深度求索（deepseek）、昆仑万维（tiangong）、科大讯飞（spark）、开源中国（gitee）等大模型
- 文档处理：支持合并处理、拆分处理、渲染处理、页面处理、元数据处理、打印处理、替换处理、书签处理、评论处理、附件处理、线性化处理、表单处理、签名处理
- 文件转换：支持 office 文件转 pdf 、 html 转 pdf 、图像转 pdf
- 模板引擎：支持 html 模板渲染为 pdf , 适配 freemarker 、thymeleaf 、jte 、 beetl 、 enjoy 、 velocity 等模板引擎
- 支持自定义组件与扩展

---

#### maven坐标
- ##### 全功能模块（pdfbox + fop）
```maven
<dependency>
    <groupId>org.dromara</groupId>
    <artifactId>x-easypdf</artifactId>
    <version>3.5.3</version>
</dependency>
```

- ##### pdfbox模块
```maven
<dependency>
    <groupId>org.dromara</groupId>
    <artifactId>x-easypdf-pdfbox</artifactId>
    <version>3.5.3</version>
</dependency>
```

- ##### fop模块
```maven
<dependency>
    <groupId>org.dromara</groupId>
    <artifactId>x-easypdf-fop</artifactId>
    <version>3.5.3</version>
</dependency>
```

---

#### 安装教程
```cmd
mvn clean install
```

---

#### 快速体验
- ##### pdfbox模块
```
// 创建文档
Document document = PdfHandler.getDocumentHandler().create();
// 设置使用字体
document.setFontName("微软雅黑");
// 创建页面
Page page = new Page(document);
// 创建文本域
Textarea textarea = new Textarea(page);
// 设置文本
textarea.setText("Hello World!");
// 渲染文本
textarea.render();
// 添加页面
document.appendPage(page);
// 保存文档
document.save("E:\\PDF\\pdfbox\\document\\hello-world.pdf");
// 关闭文档
document.close();
```
- ##### fop模块（使用document数据源）
```
// 创建文档
Document document = TemplateHandler.Document.build();
// 创建页面
Page page = TemplateHandler.Page.build();
// 创建文本
Text text = TemplateHandler.Text.build().setText("hello world");
// 添加文本
page.addBodyComponent(text);
// 添加页面
document.addPage(page);
// 转换
document.transform("E:\\PDF\\fop\\document\\hello-world.pdf");
```

更多教程，请查看[文档](https://x-easypdf.cn)

---

#### Dromara 成员项目

<p align="center">
<a href="https://gitee.com/dromara/TLog" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/tlog.png" alt="一个轻量级的分布式日志标记追踪神器，10分钟即可接入，自动对日志打标签完成微服务的链路追踪" src="https://oss.dev33.cn/sa-token/link/tlog.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/liteFlow" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/liteflow.png" alt="轻量，快速，稳定，可编排的组件式流程引擎" src="https://oss.dev33.cn/sa-token/link/liteflow.png" width="100px">
    </a>
    <a href="https://hutool.cn/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/hutool.jpg" alt="小而全的Java工具类库，使Java拥有函数式语言般的优雅，让Java语言也可以“甜甜的”。" src="https://oss.dev33.cn/sa-token/link/hutool.jpg" width="100px">
    </a>
    <a href="https://sa-token.cc/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/sa-token.png" alt="一个轻量级 java 权限认证框架，让鉴权变得简单、优雅！" src="https://oss.dev33.cn/sa-token/link/sa-token.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/hmily" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/hmily.png" alt="高性能一站式分布式事务解决方案。" src="https://oss.dev33.cn/sa-token/link/hmily.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/Raincat" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/raincat.png" alt="强一致性分布式事务解决方案。" src="https://oss.dev33.cn/sa-token/link/raincat.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/myth" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/myth.png" alt="可靠消息分布式事务解决方案。" src="https://oss.dev33.cn/sa-token/link/myth.png" width="100px">
    </a>
    <a href="https://cubic.jiagoujishu.com/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/cubic.png" alt="一站式问题定位平台，以agent的方式无侵入接入应用，完整集成arthas功能模块，致力于应用级监控，帮助开发人员快速定位问题" src="https://oss.dev33.cn/sa-token/link/cubic.png" width="100px">
    </a>
    <a href="https://maxkey.top/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/maxkey.png" alt="业界领先的身份管理和认证产品" src="https://oss.dev33.cn/sa-token/link/maxkey.png" width="100px">
    </a>
    <a href="http://forest.dtflyx.com/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/forest-logo.png" alt="Forest能够帮助您使用更简单的方式编写Java的HTTP客户端" nf="" src="https://oss.dev33.cn/sa-token/link/forest-logo.png" width="100px">
    </a>
    <a href="https://jpom.top/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/jpom.png" alt="一款简而轻的低侵入式在线构建、自动部署、日常运维、项目监控软件" src="https://oss.dev33.cn/sa-token/link/jpom.png" width="100px">
    </a>
    <a href="https://su.usthe.com/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/sureness.png" alt="面向 REST API 的高性能认证鉴权框架" src="https://oss.dev33.cn/sa-token/link/sureness.png" width="100px">
    </a>
    <a href="https://easy-es.cn/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/easy-es2.png" alt="傻瓜级ElasticSearch搜索引擎ORM框架" src="https://oss.dev33.cn/sa-token/link/easy-es2.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/northstar" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/northstar_logo.png" alt="Northstar盈富量化交易平台" src="https://oss.dev33.cn/sa-token/link/northstar_logo.png" width="100px">
    </a>
    <a href="https://dromara.gitee.io/fast-request/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/fast-request.gif" alt="Idea 版 Postman，为简化调试API而生" src="https://oss.dev33.cn/sa-token/link/fast-request.gif" width="100px">
    </a>
    <a href="https://www.jeesuite.com/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/mendmix.png" alt="开源分布式云原生架构一站式解决方案" src="https://oss.dev33.cn/sa-token/link/mendmix.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/koalas-rpc" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/koalas-rpc2.png" alt="企业生产级百亿日PV高可用可拓展的RPC框架。" src="https://oss.dev33.cn/sa-token/link/koalas-rpc2.png" width="100px">
    </a>
    <a href="https://async.sizegang.cn/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/gobrs-async.png" alt="配置极简功能强大的异步任务动态编排框架" src="https://oss.dev33.cn/sa-token/link/gobrs-async.png" width="100px">
    </a>
    <a href="https://dynamictp.cn/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/dynamic-tp.png" alt="基于配置中心的轻量级动态可监控线程池" src="https://oss.dev33.cn/sa-token/link/dynamic-tp.png" width="100px">
    </a>
    <a href="https://www.x-easypdf.cn" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/x-easypdf.png" alt="一个用搭积木的方式构建pdf的框架（基于pdfbox）" src="https://oss.dev33.cn/sa-token/link/x-easypdf.png" width="100px">
    </a>
    <a href="http://dromara.gitee.io/image-combiner" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/image-combiner.png" alt="一个专门用于图片合成的工具，没有很复杂的功能，简单实用，却不失强大" src="https://oss.dev33.cn/sa-token/link/image-combiner.png" width="100px">
    </a>
    <a href="https://www.herodotus.cn/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/dante-cloud2.png" alt="Dante-Cloud 是一款企业级微服务架构和服务能力开发平台。" src="https://oss.dev33.cn/sa-token/link/dante-cloud2.png" width="100px">
    </a>
    <a href="http://www.mtruning.club" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/go-view.png" alt="低代码数据可视化开发平台" src="https://oss.dev33.cn/sa-token/link/go-view.png" width="100px">
    </a>
    <a href="https://tangyh.top/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/lamp-cloud.png" alt="微服务中后台快速开发平台，支持租户(SaaS)模式、非租户模式" src="https://oss.dev33.cn/sa-token/link/lamp-cloud.png" width="100px">
    </a>
    <a href="https://www.redisfront.com/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/redis-front.png" alt="RedisFront 是一款开源免费的跨平台 Redis 桌面客户端工具, 支持单机模式, 集群模式, 哨兵模式以及 SSH 隧道连接, 可轻松管理Redis缓存数据." src="https://oss.dev33.cn/sa-token/link/redis-front.png" width="100px">
    </a>
    <a href="https://www.yuque.com/u34495/mivcfg" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/electron-egg.png" alt="一个入门简单、跨平台、企业级桌面软件开发框架" src="https://oss.dev33.cn/sa-token/link/electron-egg.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/open-capacity-platform" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/open-capacity-platform.jpg" alt="简称ocp是基于Spring Cloud的企业级微服务框架(用户权限管理，配置中心管理，应用管理，....)" src="https://oss.dev33.cn/sa-token/link/open-capacity-platform.jpg" width="100px">
    </a>
    <a href="http://easy-trans.fhs-opensource.top/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/easy_trans.png" alt="Easy-Trans 一个注解搞定数据翻译,减少30%SQL代码量" src="https://oss.dev33.cn/sa-token/link/easy_trans.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/neutrino-proxy" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/neutrino-proxy.svg" alt="一款基于 Netty 的、开源的内网穿透神器。" src="https://oss.dev33.cn/sa-token/link/neutrino-proxy.svg" width="100px">
    </a>
    <a href="https://chatgpt.cn.obiscr.com/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/chatgpt.png" alt="一个支持在 JetBrains 系列 IDE 上运行的 ChatGPT 的插件。" src="https://oss.dev33.cn/sa-token/link/chatgpt.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/zyplayer-doc" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/zyplayer-doc.png" alt="zyplayer-doc是一款适合团队和个人使用的WIKI文档管理工具，同时还包含数据库文档、Api接口文档。" src="https://oss.dev33.cn/sa-token/link/zyplayer-doc.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/payment-spring-boot" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/payment-spring-boot.png" alt="最全最好用的微信支付V3 Spring Boot 组件。" src="https://oss.dev33.cn/sa-token/link/payment-spring-boot.png" width="100px">
    </a>
    <a href="https://www.j2eefast.com/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/j2eefast.png" alt="J2eeFAST 是一个致力于中小企业 Java EE 企业级快速开发平台,我们永久开源!" src="https://oss.dev33.cn/sa-token/link/j2eefast.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/data-compare" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/dataCompare.png" alt="数据库比对工具：hive 表数据比对，mysql、Doris 数据比对，实现自动化配置进行数据比对，避免频繁写sql 进行处理，低代码(Low-Code) 平台" src="https://oss.dev33.cn/sa-token/link/dataCompare.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/open-giteye-api" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/open-giteye-api.svg" alt="giteye.net 是专为开源作者设计的数据图表服务工具类站点，提供了包括 Star 趋势图、贡献者列表、Gitee指数等数据图表服务。" src="https://oss.dev33.cn/sa-token/link/open-giteye-api.svg" width="100px">
    </a>
    <a href="https://gitee.com/dromara/RuoYi-Vue-Plus" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/RuoYi-Vue-Plus.png" alt="后台管理系统 重写 RuoYi-Vue 所有功能 集成 Sa-Token + Mybatis-Plus + Jackson + Xxl-Job + SpringDoc + Hutool + OSS 定期同步" src="https://oss.dev33.cn/sa-token/link/RuoYi-Vue-Plus.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/RuoYi-Cloud-Plus" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/RuoYi-Cloud-Plus.png" alt="微服务管理系统 重写RuoYi-Cloud所有功能 整合 SpringCloudAlibaba Dubbo3.0 Sa-Token Mybatis-Plus MQ OSS ES Xxl-Job Docker 全方位升级 定期同步" src="https://oss.dev33.cn/sa-token/link/RuoYi-Cloud-Plus.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/stream-query" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/stream-query.png" alt="允许完全摆脱 Mapper 的 mybatis-plus 体验！封装 stream 和 lambda 操作进行数据返回处理。" src="https://oss.dev33.cn/sa-token/link/stream-query.png" width="100px">
    </a>
    <a href="https://wind.kim/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/sms4j.png" alt="短信聚合工具，让发送短信变的更简单。" src="https://oss.dev33.cn/sa-token/link/sms4j.png" width="100px">
    </a>
    <a href="https://cloudeon.top/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/cloudeon.png" alt="简化kubernetes上大数据集群的运维管理" src="https://oss.dev33.cn/sa-token/link/cloudeon.png" width="100px">
    </a>
    <a href="https://github.com/dromara/hodor" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/hodor.png" alt="Hodor是一个专注于任务编排和高可用性的分布式任务调度系统。" src="https://oss.dev33.cn/sa-token/link/hodor.png" width="100px">
    </a>
    <a href="http://nsrule.com/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/test-hub.png" alt="流程编排，插件驱动，测试无限可能" src="https://oss.dev33.cn/sa-token/link/test-hub.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/disjob" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/disjob-2.png" alt="Disjob是一个分布式的任务调度框架" src="https://oss.dev33.cn/sa-token/link/disjob-2.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/binlog4j" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/Binlog4j.png" alt="轻量级 Mysql Binlog 客户端, 提供宕机续读, 高可用集群等特性" src="https://oss.dev33.cn/sa-token/link/Binlog4j.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/yft-design" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/yft-design.png" alt="基于 Canvas 的开源版 创客贴 支持导出json，svg, image文件。" src="https://oss.dev33.cn/sa-token/link/yft-design.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/x-file-storage" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/x-file-storage.svg" alt="在 SpringBoot 中通过简单的方式将文件存储到 本地、阿里云 OSS、腾讯云 COS、七牛云 Kodo等" src="https://oss.dev33.cn/sa-token/link/x-file-storage.svg" width="100px">
    </a>
    <a href="https://wemq.nicholasld.cn/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/wemq.png" alt="开源、高性能、安全、功能强大的物联网调试和管理解决方案。" src="https://oss.dev33.cn/sa-token/link/wemq.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/mayfly-go" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/mayfly-go.png" alt="web 版 linux(终端[终端回放] 文件 脚本 进程 计划任务)、数据库（mysql postgres）、redis(单机 哨兵 集群)、mongo 统一管理操作平台" src="https://oss.dev33.cn/sa-token/link/mayfly-go.png" width="100px">
    </a>
    <a href="https://akali.yomahub.com/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/akali.png" alt="Akali(阿卡丽)，轻量级本地化热点检测/降级框架，10秒钟即可接入使用！大流量下的神器" src="https://oss.dev33.cn/sa-token/link/akali.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/dbswitch" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/dbswitch.png" alt="异构数据库迁移同步(搬家)工具。" src="https://oss.dev33.cn/sa-token/link/dbswitch.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/easyAi" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/easyAI.png" alt="Java 傻瓜式 AI 框架。" src="https://oss.dev33.cn/sa-token/link/easyAI.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/mybatis-plus-ext" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/mybatis-plus-ext.png" alt="mybatis-plus 框架的增强拓展包。" src="https://oss.dev33.cn/sa-token/link/mybatis-plus-ext.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/dax-pay" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/dax-pay.png" alt="免费开源的支付网关。" src="https://oss.dev33.cn/sa-token/link/dax-pay.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/sayOrder" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/sayorder.png" alt="基于easyAi引擎的JAVA高性能，低成本，轻量级智能客服。" src="https://oss.dev33.cn/sa-token/link/sayorder.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/mybatis-jpa-extra" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/mybatis-jpa-extra.png" alt="扩展MyBatis JPA支持，简化CUID操作，增强SELECT分页查询" src="https://oss.dev33.cn/sa-token/link/mybatis-jpa-extra.png" width="100px">
    </a>
    <a href="https://newcar.js.org/zh/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/newcar.png" alt="现代化的动画引擎" src="https://oss.dev33.cn/sa-token/link/newcar.png" width="100px">
    </a>
    <a href="http://warm-flow.cn" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/warm-flow.png" alt="国产自研工作流，其特点简洁(只有6张表)但又不简单，五脏俱全，组件独立，可扩展，可满足中小项目的组件。" src="https://oss.dev33.cn/sa-token/link/warm-flow.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/dy-java" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/dy-java.png" alt="DyJava是一款功能强大的抖音Java开发工具包" src="https://oss.dev33.cn/sa-token/link/dy-java.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/MilvusPlus" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/MilvusPlus.jpg" alt="MilvusPlus（简称 MP）是一个 Milvus 的操作工具，旨在简化与 Milvus 向量数据库的交互，为开发者提供类似 MyBatis-Plus 注解和方法调用风格的直观 API,提高效率而生。" src="https://oss.dev33.cn/sa-token/link/MilvusPlus.jpg" width="100px">
    </a>
    <a href="http://www.easy-query.com/easy-query-doc/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/easy-query.png" alt="java下唯一一款同时支持强类型对象关系查询和强类型SQL语法查询的ORM,拥有对象模型筛选、隐式子查询、隐式join、显式子查询、显式join,支持Java/Kotlin" src="https://oss.dev33.cn/sa-token/link/easy-query.png" width="100px">
    </a>
    <a href="https://gitee.com/dromara/orion-visor" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/orion-visor.png" alt="一款高颜值、现代化的智能运维&amp;轻量堡垒机平台。" src="https://oss.dev33.cn/sa-token/link/orion-visor.png" width="100px">
    </a>
    <a href="https://www.ujcms.com/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/ujcms.png" alt="Java开源网站内容管理系统(java cms)。使用SpringBoot、MyBatis、Vue3、ElementPlus、Vite、TypeScript等技术开发。" src="https://oss.dev33.cn/sa-token/link/ujcms.png" width="100px">
    </a>
    <a href="https://dromara.org/zh/projects/" target="_blank">
        <img class="lazy" data-original="https://oss.dev33.cn/sa-token/link/dromara.png" alt="让每一位开源爱好者，体会到开源的快乐。" src="https://oss.dev33.cn/sa-token/link/dromara.png" width="100px">
    </a>
</p>
<p align="center">
为往圣继绝学，一个人或许能走的更快，但一群人会走的更远。
</p>
