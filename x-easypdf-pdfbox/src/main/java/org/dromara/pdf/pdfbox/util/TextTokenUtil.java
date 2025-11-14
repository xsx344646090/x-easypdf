package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.dromara.pdf.pdfbox.core.info.ReplaceInfo;
import org.dromara.pdf.pdfbox.core.info.TextTokenInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.dromara.pdf.shade.org.apache.pdfbox.contentstream.operator.Operator;
import org.dromara.pdf.shade.org.apache.pdfbox.contentstream.operator.OperatorName;
import org.dromara.pdf.shade.org.apache.pdfbox.cos.*;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDDocument;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.PDResources;
import org.dromara.pdf.shade.org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * 文本标记工具
 *
 * @author xsx
 * @date 2024/8/28
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
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
public class TextTokenUtil {

    /**
     * 替换文本标记
     *
     * @param log             日志
     * @param document        文档
     * @param resources       页面资源
     * @param tokens          标记列表
     * @param replaceList     替换列表
     * @param replaceIndexMap 替换索引字典
     * @return 返回布尔值，true为是，false为否
     */
    @SneakyThrows
    public static boolean replaceTextForToken(
            Log log,
            PDDocument document,
            PDResources resources,
            List<Object> tokens,
            List<ReplaceInfo> replaceList,
            Map<Character, Integer> replaceIndexMap
    ) {
        // 定义替换结果
        boolean result = false;
        // 获取文本标记信息列表
        List<TextTokenInfo> infoList = initTextTokenInfoListForCharacter(log, resources, tokens, replaceList, replaceIndexMap);
        // 遍历标记信息
        for (TextTokenInfo info : infoList) {
            // 已替换
            if (info.getIsReplace()) {
                // 获取字体
                PDFont font = info.getReplaceFont();
                // 遍历标记
                for (TextTokenInfo.TextValue textValue : info.getTokens()) {
                    // 处理替换文本
                    processReplaceText(document, resources, font, textValue.getToken(), textValue.getValue());
                }
                // 设置字体
                tokens.set(info.getFontIndex(), COSName.getPDFName(font.getName()));
                // 重置替换结果
                result = true;
            }
        }
        // 返回替换结果
        return result;
    }

    /**
     * 替换文本
     *
     * @param log        日志
     * @param document   文档
     * @param resources  页面资源
     * @param tokens     标记列表
     * @param font       替换字体
     * @param replaceMap 替换字典
     * @return 返回布尔值，true为是，false为否
     */
    @SneakyThrows
    public static boolean replaceTextForToken(
            Log log,
            PDDocument document,
            PDResources resources,
            List<Object> tokens,
            PDFont font,
            Map<String, String> replaceMap
    ) {
        // 正常替换
        boolean normalResult = tryReplaceTextForNormal(log, document, resources, tokens, font, replaceMap);
        // 特殊替换
        boolean specialResult = tryReplaceTextForSpecial(log, document, resources, tokens, font, replaceMap);
        // 返回替换结果
        return normalResult || specialResult;
    }

    /**
     * 尝试替换正常文本
     *
     * @param log        日志
     * @param document   文档
     * @param resources  页面资源
     * @param tokens     标记列表
     * @param font       替换字体
     * @param replaceMap 替换字典
     * @return 返回布尔值，true为是，false为否
     */
    @SneakyThrows
    public static boolean tryReplaceTextForNormal(
            Log log,
            PDDocument document,
            PDResources resources,
            List<Object> tokens,
            PDFont font,
            Map<String, String> replaceMap
    ) {
        // 定义结果
        boolean result = false;
        boolean processFlag = false;
        // 获取信息列表
        List<TextTokenInfo> infoList = initTextTokenInfoListForString(resources, tokens);
        // 遍历信息列表
        for (TextTokenInfo info : infoList) {
            // 遍历标记
            for (TextTokenInfo.TextValue textValue : info.getTokens()) {
                // 获取原始文本
                String originalText = textValue.getReplaceValue();
                // 跳过空文本
                if (Objects.isNull(originalText)) {
                    continue;
                }
                // 定义待替换文本
                String replaceText = originalText;
                // 遍历替换字典
                for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                    // 替换文本包含待替换文本
                    if (replaceText.contains(entry.getKey())) {
                        // 替换文本
                        replaceText = replaceText.replace(entry.getKey(), entry.getValue());
                        // 处理替换文本
                        processReplaceText(document, resources, font, textValue.getToken(), replaceText);
                        // 设置已嵌入子集
                        textValue.setIsEmbedSubset(true);
                        // 子集非空
                        if (Objects.nonNull(textValue.getChildren())) {
                            // 遍历子集
                            for (TextTokenInfo.TextValue child : textValue.getChildren()) {
                                // 处理替换文本
                                processReplaceText(document, resources, font, child.getToken(), "");
                                // 设置已嵌入子集
                                child.setIsEmbedSubset(true);
                            }
                        }
                        // 重置结果
                        result = true;
                        processFlag = true;
                        // 打印日志
                        if (log.isDebugEnabled()) {
                            log.debug("Replaced normal text: original [\"" + originalText + "\"], now [\"" + replaceText + "\"]");
                        }
                        // 结束
                        break;
                    }
                }
            }
            // 结果为已替换
            if (processFlag) {
                // 遍历文本标记
                for (TextTokenInfo.TextValue textValue : info.getTokens()) {
                    // 嵌入子集标记为空
                    if (Objects.isNull(textValue.getIsEmbedSubset())) {
                        // 处理替换文本
                        processReplaceText(document, resources, font, textValue.getToken(), textValue.getValue());
                    }
                }
                // 设置字体
                tokens.set(info.getFontIndex(), COSName.getPDFName(font.getName()));
            }
        }
        // 返回结果
        return result;
    }

    /**
     * 尝试替换特殊文本
     *
     * @param log        日志
     * @param document   文档
     * @param resources  页面资源
     * @param tokens     标记列表
     * @param font       替换字体
     * @param replaceMap 替换字典
     * @return 返回布尔值，true为是，false为否
     */
    @SneakyThrows
    public static boolean tryReplaceTextForSpecial(
            Log log,
            PDDocument document,
            PDResources resources,
            List<Object> tokens,
            PDFont font,
            Map<String, String> replaceMap
    ) {
        // 定义结果
        boolean result = false;
        // 定义信息列表
        List<TextTokenInfo> infoList = initTextTokenInfoListForString(resources, tokens);
        // 遍历信息列表
        for (TextTokenInfo info : infoList) {
            // 定义替换标记
            boolean isReplace = false;
            // 获取完整文本
            String allText = getTextForTokenInfo(info);
            // 定义替换文本
            String replaceText = allText;
            // 遍历替换字典
            for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                // 替换文本包含待替换文本
                if (replaceText.contains(entry.getKey())) {
                    // 替换文本
                    replaceText = replaceText.replace(entry.getKey(), entry.getValue());
                    // 重置替换标记
                    isReplace = true;
                    // 重置结果
                    result = true;
                    // 打印日志
                    if (log.isDebugEnabled()) {
                        log.debug("Replaced special text: original [\"" + allText + "\"], now [\"" + replaceText + "\"]");
                    }
                }
            }
            // 如果进行了替换
            if (isReplace) {
                // 只有一个token
                if (info.getTokens().size() == 1) {
                    // 处理替换文本
                    processReplaceText(document, resources, font, info.getTokens().get(0).getToken(), replaceText);
                } else {
                    // 处理不同长度文本
                    processReplaceText(document, resources, font, info, replaceText);
                }
                // 设置字体
                tokens.set(info.getFontIndex(), COSName.getPDFName(font.getName()));
            }
        }
        // 返回结果
        return result;
    }

    /**
     * 处理不同长度替换文本
     *
     * @param document    文档
     * @param resources   页面资源
     * @param font        替换字体
     * @param info        文本标记信息
     * @param replaceText 替换文本
     */
    @SneakyThrows
    public static void processReplaceText(
            PDDocument document,
            PDResources resources,
            PDFont font,
            TextTokenInfo info,
            String replaceText
    ) {
        // 定义空白
        final String blank = "";
        // 处理空白
        if (Objects.equals(replaceText, blank)) {
            // 遍历文本
            for (TextTokenInfo.TextValue textValue : info.getTokens()) {
                // 处理替换文本
                processReplaceText(document, resources, font, textValue.getToken(), blank);
            }
        } else {
            // 拆分文本标记信息
            List<List<TextTokenInfo.TextValue>> tokenList = splitTextTokenInfo(info);
            // 定义完成标记
            boolean isFinish = false;
            // 定义剩余长度
            int length = tokenList.size() == 1 ? 1 : replaceText.length();
            // 遍历文本标记
            for (List<TextTokenInfo.TextValue> list : tokenList) {
                // 获取第一个文本标记
                TextTokenInfo.TextValue textValue = list.get(0);
                // 完成
                if (isFinish) {
                    // 处理替换文本
                    processReplaceText(document, resources, font, textValue.getToken(), blank);
                } else {
                    if (length <= list.size()) {
                        // 处理替换文本
                        processReplaceText(document, resources, font, textValue.getToken(), replaceText);
                        // 重置完成标记
                        isFinish = true;
                    } else {
                        // 处理替换文本
                        processReplaceText(document, resources, font, textValue.getToken(), replaceText.substring(0, list.size()));
                        // 重置替换文本
                        replaceText = replaceText.substring(list.size());
                        // 重置剩余长度
                        length = replaceText.length();
                    }
                    // 处理剩余文本
                    for (int i = 1; i < list.size(); i++) {
                        // 处理替换文本
                        processReplaceText(document, resources, font, list.get(i).getToken(), blank);
                    }
                }
            }
        }
    }

    /**
     * 拆分文本标记信息
     *
     * @param info 文本标记信息
     * @return 拆分后的文本标记信息
     */
    public static List<List<TextTokenInfo.TextValue>> splitTextTokenInfo(TextTokenInfo info) {
        // 定义文本标记列表
        List<List<TextTokenInfo.TextValue>> tokenList = new ArrayList<>();
        // 定义子列表
        List<TextTokenInfo.TextValue> subList = new ArrayList<>();
        // 定义上一个y坐标
        Float lastY = null;
        // 遍历文本标记
        for (TextTokenInfo.TextValue token : info.getTokens()) {
            // 相同Y轴坐标
            if (Objects.equals(token.getY(), lastY)) {
                subList.add(token);
            } else {
                // 重置子列表
                subList = new ArrayList<>();
                // 添加标记
                subList.add(token);
                // 添加列表
                tokenList.add(subList);
                // 重置上一个y坐标
                lastY = token.getY();
            }
        }
        // 返回文本标记列表
        return tokenList;
    }

    /**
     * 处理替换文本
     *
     * @param document  文档
     * @param resources 页面资源
     * @param font      替换字体
     * @param token     标记
     * @param value     文本
     */
    @SuppressWarnings("all")
    @SneakyThrows
    public static void processReplaceText(
            PDDocument document,
            PDResources resources,
            PDFont font,
            Object token,
            String value
    ) {
        // 嵌入字体
        PdfHandler.getFontHandler().addToSubset(document, font, value);
        // cos数组
        if (token instanceof COSArray) {
            // 转换为cos数组
            COSArray array = (COSArray) token;
            // 清理数组
            array.clear();
            // 添加新值
            array.add(new COSString(font.encode(value)));
        } else {
            // 转换为cos字符串
            COSString cosString = (COSString) token;
            // 设置新值
            cosString.setValue(font.encode(value));
        }
        // 获取字体名称
        COSName replaceFontName = COSName.getPDFName(font.getName());
        // 如果资源字体未包含该字体，则添加资源字体
        if (Objects.isNull(resources.getFont(replaceFontName))) {
            // 添加资源字体
            resources.put(replaceFontName, font);
        }
    }

    /**
     * 初始化文本标记信息列表
     *
     * @param log             日志
     * @param resources       页面资源
     * @param tokens          标记列表
     * @param replaceList     替换列表
     * @param replaceIndexMap 替换索引字典
     * @return 返回文本信息列表
     */
    @SuppressWarnings("all")
    @SneakyThrows
    public static List<TextTokenInfo> initTextTokenInfoListForCharacter(
            Log log,
            PDResources resources,
            List<Object> tokens,
            List<ReplaceInfo> replaceList,
            Map<Character, Integer> replaceIndexMap
    ) {
        // 获取资源字典
        Map<COSName, PDFont> resourceFontMap = initResourceFontMap(resources);
        // 定义文本矩阵索引
        int matrixIndex = 0;
        // 定义X轴坐标
        float x = 0;
        // 定义Y轴坐标
        float y = 0;
        // 定义基本信息
        TextTokenInfo tokenInfo = null;
        // 定义信息列表
        List<TextTokenInfo> infoList = new ArrayList<>(tokens.size());
        // 遍历标记列表
        for (int i = 0; i < tokens.size(); i++) {
            // 获取标记
            Object token = tokens.get(i);
            // 操作标记
            if (token instanceof Operator) {
                // 转换为操作类型
                Operator operator = (Operator) token;
                // 字体大小标记
                if (operator.getName().equals(OperatorName.SET_FONT_AND_SIZE)) {
                    // 获取字体索引
                    int fontIndex = i - 2;
                    // 获取字体大小
                    float fontSize = ((COSNumber) tokens.get(i - 1)).floatValue();
                    // 获取资源字体
                    PDFont resourceFont = resourceFontMap.get(tokens.get(fontIndex));
                    // 如果资源字体不为空，则重置资源字体索引与名称
                    if (Objects.nonNull(resourceFont)) {
                        // 返回文本标记信息
                        tokenInfo = new TextTokenInfo(fontIndex, resourceFont, resourceFont, fontSize, false, new ArrayList<>(16));
                        // 添加文本标记信息
                        infoList.add(tokenInfo);
                    }
                } else if (operator.getName().equals(OperatorName.SET_MATRIX) || operator.getName().equals(OperatorName.MOVE_TEXT)) {
                    // 重置文本矩阵索引
                    matrixIndex = i;
                    // 重置X轴坐标
                    x = ((COSNumber) tokens.get(i - 2)).floatValue();
                    // 重置Y轴坐标
                    y = ((COSNumber) tokens.get(i - 1)).floatValue();
                }
            } else if (token instanceof COSArray || token instanceof COSString) {
                // 初始化文本标记值
                initTextTokenInfoTextValue(log, tokenInfo, token, i, matrixIndex, x, y, replaceList, replaceIndexMap);
            }
        }
        // 返回信息列表
        return infoList;
    }

    /**
     * 初始化文本标记信息列表
     *
     * @param resources 页面资源
     * @param tokens    标记列表
     * @return 返回文本信息列表
     */
    @SuppressWarnings("all")
    @SneakyThrows
    public static List<TextTokenInfo> initTextTokenInfoListForString(PDResources resources, List<Object> tokens) {
        // 获取资源字体字典
        Map<COSName, PDFont> resourceFontMap = initResourceFontMap(resources);
        // 定义文本矩阵索引
        int matrixIndex = 0;
        // 定义X轴坐标
        float x = 0;
        // 定义Y轴坐标
        float y = 0;
        // 定义文本信息
        TextTokenInfo tokenInfo = null;
        // 定义文本列表
        List<TextTokenInfo> infoList = new ArrayList<>(tokens.size());
        // 遍历标记列表
        for (int i = 0; i < tokens.size(); i++) {
            // 获取标记
            Object token = tokens.get(i);
            // 操作标记
            if (token instanceof Operator) {
                // 转换为操作类型
                Operator operator = (Operator) token;
                // 字体大小标记
                if (operator.getName().equals(OperatorName.SET_FONT_AND_SIZE)) {
                    // 获取字体索引
                    int fontIndex = i - 2;
                    // 获取字体大小
                    float fontSize = ((COSNumber) tokens.get(i - 1)).floatValue() / 20F;
                    // 获取资源字体
                    PDFont resourceFont = resourceFontMap.get(tokens.get(fontIndex));
                    // 如果资源字体不为空，则重置资源字体索引与名称
                    if (Objects.nonNull(resourceFont)) {
                        // 返回文本标记信息
                        tokenInfo = new TextTokenInfo(fontIndex, resourceFont, resourceFont, fontSize, false, new ArrayList<>(16));
                        // 添加文本标记信息
                        infoList.add(tokenInfo);
                    }
                } else if (operator.getName().equals(OperatorName.SET_MATRIX) || operator.getName().equals(OperatorName.MOVE_TEXT)) {
                    // 重置文本矩阵索引
                    matrixIndex = i;
                    // 重置X轴坐标
                    x = ((COSNumber) tokens.get(i - 2)).floatValue();
                    // 重置Y轴坐标
                    y = ((COSNumber) tokens.get(i - 1)).floatValue();
                }
            } else if (token instanceof COSArray || token instanceof COSString) {
                // 获取原字符串
                String source = getTextForToken(token, tokenInfo.getFont());
                // 添加token
                tokenInfo.getTokens().add(new TextTokenInfo.TextValue(i, matrixIndex, x, y, token, source));
            }
        }
        // 返回文本标记信息列表
        return infoList;
    }

    /**
     * 初始化文本标记值
     *
     * @param log             日志
     * @param tokenInfo       标记信息
     * @param token           标记
     * @param tokenIndex      标记索引
     * @param matrixIndex     文本矩阵索引
     * @param x               X轴坐标
     * @param y               Y轴坐标
     * @param replaceList     替换列表
     * @param replaceIndexMap 替换索引字典
     */
    public static void initTextTokenInfoTextValue(
            Log log,
            TextTokenInfo tokenInfo,
            Object token,
            int tokenIndex,
            int matrixIndex,
            float x,
            float y,
            List<ReplaceInfo> replaceList,
            Map<Character, Integer> replaceIndexMap
    ) {
        // 标记信息不为空
        if (Objects.nonNull(tokenInfo)) {
            // 获取原字符串
            String source = getTextForToken(token, tokenInfo.getFont());
            // 创建构建器
            StringBuilder newValue = new StringBuilder();
            // 原字符串不为空
            if (Objects.nonNull(source)) {
                // 定义标记值
                TextTokenInfo.TextValue tokenValue;
                // 定义空字符
                final char blank = '\u0000';
                // 转为字符数组
                char[] characters = source.toCharArray();
                // 遍历字符数组
                for (char character : characters) {
                    // 获取替换标记
                    Integer index = replaceIndexMap.get(character);
                    // 存在替换
                    if (Objects.nonNull(index)) {
                        // 遍历替换列表
                        for (ReplaceInfo info : replaceList) {
                            // 该字符与原始字符一致
                            if (Objects.equals(character, info.getOriginal())) {
                                // 获取替换索引
                                Set<Integer> indexes = info.getIndexes();
                                // 索引不为空
                                if (Objects.nonNull(indexes)) {
                                    // 定义替换标记
                                    boolean replaced = false;
                                    // 遍历替换索引
                                    for (Integer replaceIndex : indexes) {
                                        // 索引一致
                                        if (Objects.equals(index, replaceIndex)) {
                                            // 重置替换标记
                                            replaced = true;
                                            // 设置替换标记
                                            tokenInfo.setIsReplace(replaced);
                                            // 设置替换字体
                                            tokenInfo.setReplaceFont(info.getFont());
                                            // 非空字符
                                            if (!Objects.equals(blank, info.getValue())) {
                                                // 添加替换值
                                                newValue.append(info.getValue());
                                            }
                                            // 打印日志
                                            if (log.isDebugEnabled()) {
                                                log.debug("Replaced character: original ['" + character + "'], now ['" + info.getValue() + "'], index ['" + index + "']");
                                            }
                                            // 结束遍历
                                            break;
                                        }
                                    }
                                    // 未替换
                                    if (!replaced) {
                                        // 添加原有值
                                        newValue.append(character);
                                    }
                                } else {
                                    // 设置替换标记
                                    tokenInfo.setIsReplace(true);
                                    // 设置替换字体
                                    tokenInfo.setReplaceFont(info.getFont());
                                    // 非空字符
                                    if (!Objects.equals(blank, info.getValue())) {
                                        // 添加替换值
                                        newValue.append(info.getValue());
                                    }
                                    // 打印日志
                                    if (log.isDebugEnabled()) {
                                        log.debug("Replaced character: original ['" + character + "'], now ['" + info.getValue() + "'], index ['" + index + "']");
                                    }
                                }
                            }
                        }
                        // 索引+1
                        replaceIndexMap.put(character, index + 1);
                    } else {
                        // 添加原有值
                        newValue.append(character);
                    }
                    // 重置标记值
                    tokenValue = new TextTokenInfo.TextValue(tokenIndex, matrixIndex, x, y, token, newValue.toString());
                    // 添加标记值
                    tokenInfo.getTokens().add(tokenValue);
                }
            }
        }
    }

    /**
     * 获取文本
     *
     * @param token 标记
     * @param font  字体
     * @return 返回文本内容
     */
    public static String getTextForToken(Object token, PDFont font) {
        // 定义字符串构建器
        StringBuilder builder = new StringBuilder();
        // 如果为cos数组
        if (token instanceof COSArray) {
            // 转换为cos数组
            COSArray array = (COSArray) token;
            // 遍历cos数组
            for (COSBase cosBase : array) {
                // 数字类型
                if (cosBase instanceof COSInteger) {
                    // 转换
                    COSInteger cosInteger = (COSInteger) cosBase;
                    // 空格,暂时不知道空格的实际表示值，据观测
                    if (cosInteger.intValue() <= -199) {
                        // 添加空格
                        builder.append(' ');
                    }
                } else if (cosBase instanceof COSArray) {
                    // 获取文本
                    String value = getTextForToken(cosBase, font);
                    // 非空
                    if (Objects.nonNull(value)) {
                        // 添加文本
                        builder.append(value);
                    }
                } else {
                    // 拼接字符串
                    joinString(builder, cosBase, font);
                }
            }
        } else {
            // 拼接字符串
            joinString(builder, token, font);
        }
        // 返回文本内容
        return builder.length() > 0 ? builder.toString() : null;
    }

    /**
     * 获取文本
     *
     * @param info 文本标记信息
     * @return 返回文本
     */
    public static String getTextForTokenInfo(TextTokenInfo info) {
        // 定义字符串构建器
        StringBuilder builder = new StringBuilder();
        // 遍历标记
        for (TextTokenInfo.TextValue textValue : info.getTokens()) {
            // 获取文本
            String value = textValue.getValue();
            // 非空
            if (Objects.nonNull(value)) {
                // 添加文本
                builder.append(value);
            }
        }
        // 返回文本
        return builder.toString();
    }

    /**
     * 初始化资源字体字典
     *
     * @param resources 页面资源
     * @return 返回资源字体字典
     */
    @SneakyThrows
    public static Map<COSName, PDFont> initResourceFontMap(PDResources resources) {
        // 定义资源字体字典
        Map<COSName, PDFont> resourceFontMap = new HashMap<>(16);
        // 添加资源字体
        if (Objects.nonNull(resources)) {
            // 遍历字体名称
            for (COSName cosName : resources.getFontNames()) {
                // 添加资源字体
                resourceFontMap.put(cosName, resources.getFont(cosName));
            }
        }
        // 返回字体字典
        return resourceFontMap;
    }

    /**
     * 拼接字符串
     *
     * @param builder 构建器
     * @param token   标记
     * @param font    字体
     */
    @SneakyThrows
    public static void joinString(StringBuilder builder, Object token, PDFont font) {
        // 字符串标记
        if (token instanceof COSString) {
            // 转换为cos字符串
            COSString cosString = (COSString) token;
            // 获取字符串输入流
            try (InputStream in = new ByteArrayInputStream(cosString.getBytes())) {
                // 读取字符
                while (in.available() > 0) {
                    // 转为unicode
                    String unicode = font.toUnicode(font.readCode(in));
                    // 非空
                    if (Objects.nonNull(unicode)) {
                        // 添加字符
                        builder.append(unicode);
                    }
                }
            }
        }
    }
}
