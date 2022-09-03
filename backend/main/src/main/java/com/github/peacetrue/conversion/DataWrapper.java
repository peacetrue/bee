package com.github.peacetrue.conversion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 数据封装类。
 *
 * @author peace
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataWrapper implements Serializable {

    /** 数据格式 */
    @NotNull
    @Size(max = 255)
    private String format;

    /** 数据内容 */
    @NotNull
    private String content;
}
