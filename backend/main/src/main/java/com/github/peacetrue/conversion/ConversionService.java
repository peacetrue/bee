package com.github.peacetrue.conversion;

import java.io.IOException;

/**
 * 转换服务。
 *
 * @author peace
 **/
public interface ConversionService {

    /**
     * 转换指定格式的原始文本数据为其他格式的目标文本数据。
     *
     * @param sourceData   原始文本数据
     * @param sourceFormat 原始数据格式
     * @param targetFormat 目标数据格式
     * @return 目标文本数据
     * @throws IOException 转换过程中发生异常
     */
    String convert(String sourceData, FormatWrapper sourceFormat, FormatWrapper targetFormat) throws IOException;


}
