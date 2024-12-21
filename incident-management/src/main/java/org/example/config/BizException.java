package org.example.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class BizException extends RuntimeException{

    public static final String INC_10001 = "INC_10001";

    private String code;
    private String message;
}
