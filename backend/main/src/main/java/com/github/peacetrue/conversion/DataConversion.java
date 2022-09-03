package com.github.peacetrue.conversion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据转换封装类。
 *
 * @author peace
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataConversion implements Serializable {

    @Valid
    @NotNull
    private DataWrapper source;

    @Valid
    @NotNull
    private DataWrapper target;

}
