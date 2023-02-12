package com.github.peacetrue.conversion;

import java.util.List;

/**
 * 格式化器注册器。
 *
 * @author peace
 **/
public interface FormatterRegistrar {

    /**
     * 注册格式化器。
     *
     * @param format    数据格式
     * @param formatter 格式化器
     */
    void registerFormatter(String format, Formatter<?> formatter);

    /**
     * 获取支持的数据格式集合。
     *
     * @return 数据格式集合
     */
    List<String> getFormats();

}
