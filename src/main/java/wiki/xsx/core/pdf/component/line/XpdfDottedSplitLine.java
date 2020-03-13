package wiki.xsx.core.pdf.component.line;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import wiki.xsx.core.pdf.component.XpdfComponent;
import wiki.xsx.core.pdf.doc.XpdfDocument;
import wiki.xsx.core.pdf.doc.XpdfPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 虚线分割线组件
 * @author xsx
 * @date 2020/3/4
 * @since 1.8
 */
public class XpdfDottedSplitLine implements XpdfComponent {

    /**
     * 分割线参数
     */
    private XpdfLineParam param = new XpdfLineParam();
    /**
     * 点线长度
     */
    private Float lineLength = 10F;
    /**
     * 点线间隔
     */
    private Float lineSpace = 10F;

    /**
     * 有参构造
     * @param fontPath 字体路径
     */
    public XpdfDottedSplitLine(String fontPath) {
        this.param.setFontPath(fontPath);
    }

    /**
     * 设置边距（上下左右）
     * @param margin 边距
     * @return 返回虚线分割线组件
     */
    public XpdfDottedSplitLine setMargin(float margin) {
        this.param.setMarginLeft(margin).setMarginRight(margin).setMarginTop(margin).setMarginBottom(margin);
        return this;
    }

    /**
     * 设置左边距
     * @param margin 边距
     * @return 返回虚线分割线组件
     */
    public XpdfDottedSplitLine setMarginLeft(float margin) {
        this.param.setMarginLeft(margin);
        return this;
    }

    /**
     * 设置右边距
     * @param margin 边距
     * @return 返回虚线分割线组件
     */
    public XpdfDottedSplitLine setMarginRight(float margin) {
        this.param.setMarginRight(margin);
        return this;
    }

    /**
     * 设置上边距
     * @param margin 边距
     * @return 返回虚线分割线组件
     */
    public XpdfDottedSplitLine setMarginTop(float margin) {
        this.param.setMarginTop(margin);
        return this;
    }

    /**
     * 设置下边距
     * @param margin 边距
     * @return 返回虚线分割线组件
     */
    public XpdfDottedSplitLine setMarginBottom(float margin) {
        this.param.setMarginBottom(margin);
        return this;
    }

    /**
     * 设置分割线宽度
     * @param lineWidth 分割线宽度
     * @return 返回虚线分割线组件
     */
    public XpdfDottedSplitLine setLineWidth(float lineWidth) {
        this.param.setLineWidth(lineWidth);
        return this;
    }

    /**
     * 设置分割线线型
     * @param lineCapStyle 分割线线型
     * @return 返回虚线分割线组件
     */
    public XpdfDottedSplitLine setLineCapStyle(LineCapStyle lineCapStyle) {
        this.param.setLineCapStyle(lineCapStyle);
        return this;
    }

    /**
     * 设置点线长度
     * @param lineLength 点线长度
     * @return 返回虚线分割线组件
     */
    public XpdfDottedSplitLine setLineLength(float lineLength) {
        this.lineLength = lineLength;
        return this;
    }

    /**
     * 设置点线间隔
     * @param lineSpace 点线间隔
     * @return 返回虚线分割线组件
     */
    public XpdfDottedSplitLine setLineSpace(float lineSpace) {
        this.lineSpace = lineSpace;
        return this;
    }

    /**
     * 画图
     *
     * @param document pdf文档
     * @param page     pdf页面
     * @throws IOException IO异常
     */
    @Override
    public void draw(XpdfDocument document, XpdfPage page) throws IOException {
        // 初始化虚线分割线参数
        this.init(document, page);
        // 定义线条组件
        XpdfLine xpdfLine;
        // 初始化线条组件
        xpdfLine = new XpdfLine(this.param);
        // 执行画图
        xpdfLine.draw(document, page);
        // 计算点线数量，点线数量 = (pdfBox最新页面宽度 - 左边距 - 右边距) / (点线长度 + 点线间隔)
        int count = (int) Math.floor(
                (page.getLastPage().getMediaBox().getWidth() - this.param.getMarginLeft() - this.param.getMarginRight())
                /
                (this.lineLength + this.lineSpace)
        );
        // 循环点线数量进行画图
        for (int j = 1; j <= count; j++) {
            // 设置页面X轴起始坐标，起始坐标 = 结束坐标 + 点线间隔
            this.param.setBeginX(
                    this.param.getEndX() + this.lineSpace
            // 设置页面X轴结束坐标，结束坐标 = 起始坐标 + 点线长度
            ).setEndX(
                    this.param.getBeginX() + this.lineLength
            );
            // 重新初始化线条组件
            xpdfLine = new XpdfLine(this.param);
            // 执行画图
            xpdfLine.draw(document, page);
        }
        // 设置pdf页面Y轴起始坐标，起始坐标 = 起始坐标 - 线宽 / 2
        page.setPageY(this.param.getBeginY() - this.param.getLineWidth() / 2);
    }

    /**
     * 初始化参数
     * @param page pdf页面
     */
    private void init(XpdfDocument document, XpdfPage page) throws IOException {
        // 定义线宽
        float lineWidth = this.param.getLineWidth() / 2;
        // 如果当前页面Y轴坐标不为空，则进行分页判断
        if (page.getPageY()!=null) {
            // 分页判断，如果（当前Y轴坐标-上边距-线宽）小于下边距，则进行分页
            if (page.getPageY() - this.param.getMarginTop() - lineWidth <= this.param.getMarginBottom()) {
                // 添加新的pdfBox页面
                page.getPageList().add(new PDPage(page.getLastPage().getMediaBox()));
                // 设置当前Y轴坐标为空，表示新页面
                page.setPageY(null);
            }
        }
        // 设置X轴Y轴起始结束坐标
        this.param.setBeginX(
                // 左边距
                this.param.getMarginLeft()
        ).setBeginY(
                // 如果当前页面Y轴坐标为空，则起始坐标 = pdfBox最新页面高度 - 上边距 - 线宽，否则起始坐标 = 当前页面Y轴坐标 - 上边距 - 线宽
                page.getPageY()==null?
                // pdfBox最新页面高度 - 上边距 - 线宽
                page.getLastPage().getMediaBox().getHeight() - this.param.getMarginTop() - lineWidth:
                // 当前页面Y轴坐标 - 上边距 - 线宽
                page.getPageY() - this.param.getMarginTop() - lineWidth
        ).setEndX(
                // X轴起始坐标 + 点线长度
                this.param.getBeginX() + this.lineLength
        ).setEndY(
                // Y轴起始坐标
                this.param.getBeginY()
        );
        // 设置字体
        this.param.setFont(
                PDType0Font.load(
                        document.getDocument(),
                        Files.newInputStream(Paths.get(this.param.getFontPath()))
                )
        );
    }
}