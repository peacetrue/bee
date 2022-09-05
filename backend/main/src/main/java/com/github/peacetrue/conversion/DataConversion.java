package com.github.peacetrue.conversion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据转换参数。
 *
 * @author peace
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataConversion implements Serializable {

    /** 原始数据 */
    @Valid
    @NotNull
    private DataWrapper source;

    /** 目标数据 */
    @Valid
    @NotNull
    private DataWrapper target;

}
