package org.dromara.pdf.pdfbox.util;

import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.dromara.pdf.pdfbox.core.info.ReplaceInfo;
import org.dromara.pdf.pdfbox.core.info.TextTokenInfo;
import org.dromara.pdf.pdfbox.handler.PdfHandler;

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
        // 尝试替换
        boolean normalFlag = tryReplaceTextForNormal(log, document, resources, tokens, font, replaceMap);
        boolean specialFlag = tryReplaceTextForSpecial(log, document, resources, tokens, font, replaceMap);
        // 返回替换结果
        return normalFlag || specialFlag;
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
        // 定义空白字符串
        final String blank = " ";
        // 获取信息列表
        List<TextTokenInfo> infoList = initTextTokenInfoListForString(resources, tokens);
        // 遍历信息列表
        for (TextTokenInfo info : infoList) {
            // 定义子集
            List<TextTokenInfo.TextValue> children = new ArrayList<>(info.getTokens().size());
            // 定义字符串构建器
            StringBuilder builder = new StringBuilder();
            // 定义起始文本
            TextTokenInfo.TextValue beginValue = null;
            // 遍历文本标记
            for (TextTokenInfo.TextValue textValue : info.getTokens()) {
                // 构建器为空
                if (builder.length() == 0) {
                    // 重置起始文本
                    beginValue = textValue;
                } else {
                    // 设置未被替换
                    textValue.setIsReplaced(false);
                    // 添加子集
                    children.add(textValue);
                }
                // 获取文本
                String value = textValue.getValue();
                // 非空文本
                if (Objects.nonNull(value)) {
                    // 起始文本非空且非空白字符串
                    if (Objects.nonNull(beginValue) && Objects.equals(value, blank)) {
                        // 设置替换值
                        beginValue.setReplaceValue(builder.toString());
                        // 设置子集
                        beginValue.setChildren(children);
                        // 设置替换标记
                        beginValue.setIsReplaced(true);
                        // 重置构建器
                        builder = new StringBuilder();
                        // 重置子集
                        children = new ArrayList<>();
                        // 重置结果
                        result = true;
                    } else {
                        // 添加文本
                        builder.append(value);
                    }
                }
            }
            // 结果为单文本
            if (!result) {
                // 遍历标记
                for (TextTokenInfo.TextValue textValue : info.getTokens()) {
                    // 设置替换标记
                    textValue.setIsReplaced(true);
                }
            }
            // 重置结果
            result = false;
            // 遍历标记
            for (TextTokenInfo.TextValue textValue : info.getTokens()) {
                // 替换标记为true
                if (textValue.isReplaced()) {
                    // 获取完整文本
                    String allText = textValue.getReplaceValue();
                    // 定义待替换文本
                    String replaceText = allText;
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
                            // 打印日志
                            if (log.isDebugEnabled()) {
                                log.debug("Replaced normal text: original [\"" + allText + "\"], now [\"" + replaceText + "\"]");
                            }
                            // 结束
                            break;
                        }
                    }
                }
            }
            // 结果为已替换
            if (result) {
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
                } else if (allText.length() == replaceText.length()) {
                    // 处理相同长度文本
                    processSameLengthReplaceText(document, resources, font, info, replaceText);
                } else {
                    // 处理不同长度文本
                    processNotSameLengthReplaceText(document, resources, font, info, replaceText);
                }
                // 设置字体
                tokens.set(info.getFontIndex(), COSName.getPDFName(font.getName()));
            }
        }
        // 返回结果
        return result;
    }
    
    /**
     * 处理相同长度替换文本
     *
     * @param document    文档
     * @param resources   页面资源
     * @param font        替换字体
     * @param info        文本标记信息
     * @param replaceText 替换文本
     */
    @SneakyThrows
    public static void processSameLengthReplaceText(
            PDDocument document,
            PDResources resources,
            PDFont font,
            TextTokenInfo info,
            String replaceText
    ) {
        // 定义起始索引
        int begin = 0;
        // 定义文本
        String value;
        // 遍历标记
        for (TextTokenInfo.TextValue token : info.getTokens()) {
            // 获取结束索引
            int end = begin + token.getValue().length();
            // 截取文本
            value = replaceText.substring(begin, end);
            // 替换文本
            processReplaceText(document, resources, font, token.getToken(), value);
            // 重置起始索引
            begin = end;
        }
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
    public static void processNotSameLengthReplaceText(
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
                processReplaceText(document, resources, font, textValue.getToken(), "");
            }
        } else {
            // 定义替换文本
            String text;
            // 定义起始索引
            int beginIndex = 0;
            // 定义结束索引
            int endIndex;
            // 定义总索引
            int totalIndex = replaceText.length();
            // 获取迭代器
            Iterator<TextTokenInfo.TextValue> iterator = info.getTokens().iterator();
            // 获取文本
            TextTokenInfo.TextValue textValue = iterator.next();
            // 初始化结束索引
            endIndex = textValue.getValue().length();
            // 初始化替换文本
            text = replaceText.substring(beginIndex, endIndex);
            // 重置起始索引
            beginIndex = endIndex;
            // 处理替换文本
            processReplaceText(document, resources, font, textValue.getToken(), text);
            // 遍历文本
            while (iterator.hasNext()) {
                // 重置文本
                textValue = iterator.next();
                // 最大索引
                if (beginIndex == totalIndex) {
                    // 处理替换空白
                    processReplaceText(document, resources, font, textValue.getToken(), "");
                } else {
                    // 重置结束索引
                    endIndex = endIndex + textValue.getValue().length();
                    // 重置结束索引为最大索引
                    if (endIndex > totalIndex) {
                        endIndex = totalIndex;
                    }
                    // 重置替换文本
                    text = replaceText.substring(beginIndex, endIndex);
                    // 重置起始索引
                    beginIndex = endIndex;
                    // 处理替换文本
                    processReplaceText(document, resources, font, textValue.getToken(), text);
                }
            }
        }
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
        // 定义基本信息
        TextTokenInfo tokenInfo = null;
        // 定义信息列表
        List<TextTokenInfo> infoList = new ArrayList<>(tokens.size());
        // 遍历标记列表
        for (int i = 0; i < tokens.size(); i++) {
            // 获取标记
            Object token = tokens.get(i);
            // 如果标记为cosName，则重置资源字体
            if (token instanceof COSName) {
                // 获取资源字体
                PDFont resourceFont = resourceFontMap.get(token);
                // 如果资源字体不为空，则重置资源字体索引与名称
                if (Objects.nonNull(resourceFont)) {
                    // 返回文本标记信息
                    tokenInfo = new TextTokenInfo(i, resourceFont, resourceFont, false, new ArrayList<>(16));
                    // 添加文本标记信息
                    infoList.add(tokenInfo);
                }
            } else if (token instanceof COSArray || token instanceof COSString) {
                // 初始化文本标记值
                initTextTokenInfoTextValue(log, tokenInfo, token, i, replaceList, replaceIndexMap);
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
    @SneakyThrows
    public static List<TextTokenInfo> initTextTokenInfoListForString(PDResources resources, List<Object> tokens) {
        // 获取资源字体字典
        Map<COSName, PDFont> resourceFontMap = initResourceFontMap(resources);
        // 定义文本信息
        TextTokenInfo tokenInfo = null;
        // 定义文本列表
        List<TextTokenInfo> infoList = new ArrayList<>(tokens.size());
        // 遍历标记列表
        for (int i = 0; i < tokens.size(); i++) {
            // 获取标记
            Object token = tokens.get(i);
            // 如果标记为cosName，则重置资源字体
            if (token instanceof COSName) {
                // 获取资源字体
                PDFont resourceFont = resourceFontMap.get(token);
                // 如果资源字体不为空，则重置资源字体索引与名称
                if (Objects.nonNull(resourceFont)) {
                    // 返回文本标记信息
                    tokenInfo = new TextTokenInfo(i, resourceFont, resourceFont, false, new ArrayList<>(16));
                    // 添加文本标记信息
                    infoList.add(tokenInfo);
                }
            } else if (token instanceof COSArray || token instanceof COSString) {
                // 标记信息不为空
                if (Objects.nonNull(tokenInfo)) {
                    // 获取原字符串
                    String source = getTextForToken(token, tokenInfo.getFont());
                    // 添加token
                    tokenInfo.getTokens().add(new TextTokenInfo.TextValue(i, token, source));
                }
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
     * @param replaceList     替换列表
     * @param replaceIndexMap 替换索引字典
     */
    public static void initTextTokenInfoTextValue(
            Log log,
            TextTokenInfo tokenInfo,
            Object token,
            int tokenIndex,
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
                    tokenValue = new TextTokenInfo.TextValue(tokenIndex, token, newValue.toString());
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
                        builder.append(" ");
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
