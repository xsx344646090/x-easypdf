package org.dromara.pdf.pdfbox.support.image;

import lombok.SneakyThrows;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.dromara.pdf.pdfbox.core.enums.ImageType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * jpeg2000图像助手
 *
 * @author xsx
 * @date 2024/12/11
 * @since 1.8
 * <p>
 * Copyright (c) 2020-2024 xsx All Rights Reserved.
 * x-easypdf-pdfbox is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
public class J2kImageHandler {

    /**
     * 解析
     *
     * @param inputStream 输入流
     * @return 返回图像
     */
    @SuppressWarnings("all")
    @SneakyThrows
    public static BufferedImage parse(InputStream inputStream) {
        // 创建一个随机访问输入流
        jj2000.j2k.io.RandomAccessIO in = new jj2000.j2k.util.ISRandomAccessIO(inputStream);
        // 创建一个文件格式读取器
        jj2000.j2k.fileformat.reader.FileFormatReader ff = new jj2000.j2k.fileformat.reader.FileFormatReader(in);
        // 读取文件格式
        ff.readFileFormat();
        // 如果使用了JP2文件格式
        if (ff.JP2FFUsed) {
            // 定位到第一个码流位置
            in.seek(ff.getFirstCodeStreamPos());
        }
        // 创建一个参数列表
        jj2000.j2k.util.ParameterList dpl = new jj2000.j2k.util.ParameterList();
        // 获取解码器的所有参数
        String[][] param = jj2000.j2k.decoder.Decoder.getAllParameters();
        // 遍历参数
        for (int i = param.length - 1; i >= 0; i--) {
            // 如果参数不为空
            if (param[i][3] != null) {
                // 将参数放入参数列表
                dpl.put(param[i][0], param[i][3]);
            }
        }
        // 创建一个新的参数列表
        jj2000.j2k.util.ParameterList pl = new jj2000.j2k.util.ParameterList(dpl);
        // 创建一个头部信息
        jj2000.j2k.codestream.HeaderInfo hi = new jj2000.j2k.codestream.HeaderInfo();
        // 创建一个头部解码器
        jj2000.j2k.codestream.reader.HeaderDecoder hd = new jj2000.j2k.codestream.reader.HeaderDecoder(in, pl, hi);
        // 获取解码器规格
        jj2000.j2k.decoder.DecoderSpecs decSpec = hd.getDecoderSpecs();
        // 创建一个码流读取代理
        jj2000.j2k.codestream.reader.BitstreamReaderAgent agent = jj2000.j2k.codestream.reader.BitstreamReaderAgent.createInstance(in, hd, pl, decSpec, false, hi);
        // 获取编码的组件数量
        int nCompCod = hd.getNumComps();
        // 创建一个深度数组
        int[] depth = new int[nCompCod];
        // 遍历深度数组
        for (int i = 0; i < nCompCod; i++) {
            // 获取原始位深度
            depth[i] = hd.getOriginalBitDepth(i);
        }
        // 创建一个逆WT
        jj2000.j2k.wavelet.synthesis.InverseWT wt = jj2000.j2k.wavelet.synthesis.InverseWT.createInstance(hd.createDequantizer(hd.createROIDeScaler(hd.createEntropyDecoder(agent, pl), pl, decSpec), depth, decSpec), decSpec);
        // 设置图像分辨率级别
        wt.setImgResLevel(agent.getImgRes());
        // 创建一个图像数据转换器
        jj2000.j2k.image.ImgDataConverter converter = new jj2000.j2k.image.ImgDataConverter(wt, 0);
        // 创建一个解码后的图像
        jj2000.j2k.image.BlkImgDataSrc decodedImage = new jj2000.j2k.image.invcomptransf.InvCompTransf(converter, decSpec, depth, pl);
        // 创建一个图像
        Image j2kimage = jj2000.disp.BlkImgDataSrcImageProducer.createImage(decodedImage);
        // 创建一个BufferedImage
        BufferedImage image = new BufferedImage(j2kimage.getWidth(null), j2kimage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // 获取图像的Graphics
        Graphics graphics = image.getGraphics();
        // 绘制图像
        graphics.drawImage(j2kimage, 0, 0, null);
        // 关闭资源
        graphics.dispose();
        // 返回图像
        return image;
    }

    /**
     * 转码
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param outputType   图像类型
     */
    @SneakyThrows
    public static void transcode(InputStream inputStream, OutputStream outputStream, ImageType outputType) {
        // 参数校验
        Objects.requireNonNull(inputStream, "the input stream can not be null");
        Objects.requireNonNull(outputStream, "the output stream can not be null");
        Objects.requireNonNull(outputType, "the output type can not be null");
        ImageIOUtil.writeImage(parse(inputStream), outputType.getType(), outputStream);
    }
}
