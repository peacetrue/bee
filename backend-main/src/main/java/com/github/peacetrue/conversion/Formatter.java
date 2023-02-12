package com.github.peacetrue.conversion;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * 格式化器。在文本字符串和对象之间相互转换。
 *
 * @param <T> 对象类型
 * @author peace
 * @see org.springframework.format.Formatter
 */
public interface Formatter<T> {

    /**
     * 解析文本字符串生成对象。
     *
     * @param text 文本字符串
     * @return 对象
     * @throws IOException 解析过程中发生异常
     */
    default T parse(String text) throws IOException {
        return parse(text, null);
    }

    /**
     * 解析文本字符串生成对象。
     *
     * @param text    文本字符串
     * @param options 解析选项
     * @return 对象
     * @throws IOException 解析过程中发生异常
     */
    T parse(String text, @Nullable String options) throws IOException;

    /**
     * 打印对象生成文本字符串。
     *
     * @param object 对象
     * @return 文本字符串
     * @throws IOException 解析过程中发生异常
     */
    default String print(T object) throws IOException {
        return print(object, null);
    }

    /**
     * 打印对象生成文本字符串。
     *
     * @param object  对象
     * @param options 打印选项
     * @return 文本字符串
     * @throws IOException 解析过程中发生异常
     */
    String print(T object, @Nullable String options) throws IOException;

}
