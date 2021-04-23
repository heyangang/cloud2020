package com.atguigu.springcloud.alibaba.domain;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@Data
@NoArgsConstructor
public class CommonResult<T> {

    private Integer code;

    private String message;
    private T      data;

    public CommonResult(int code, String message){
        this(code,message,null);
    }
}
