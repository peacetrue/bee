package com.github.peacetrue.conversion;

import java.io.IOException;

/**
 * 转换服务。
 *
 * @author peace
 **/
public interface ConversionService {

    /**
     * 转换指定格式的原始数据内容为其他格式的目标数据内容。
     *
     * @param sourceContent 原始数据内容
     * @param sourceFormat  原始数据格式
     * @param targetFormat  目标数据格式
     * @return 目标数据内容
     * @throws IOException 转换过程中发生读写异常
     */
    String convert(String sourceContent, FormatWrapper sourceFormat, FormatWrapper targetFormat) throws IOException;

}
