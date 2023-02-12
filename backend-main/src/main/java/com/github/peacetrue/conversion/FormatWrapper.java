package com.github.peacetrue.conversion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

/**
 * 数据格式封装类。
 *
 * @author peace
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormatWrapper {

    /** 数据格式 */
    private String format;
    /** 转换选项 */
    @Nullable
    private String options;

}
