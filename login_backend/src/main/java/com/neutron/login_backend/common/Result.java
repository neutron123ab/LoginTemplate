package com.neutron.login_backend.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private Integer code;   //响应状态码 1：成功；0：失败
    private String message; //响应信息
    private T data;         //响应数据

    /**
     * 响应成功
     * @param object 响应数据
     * @param <T>   响应数据的类型
     * @return      数据和成功标志0
     */
    public static <T> Result<T> success(T object){
        Result<T> result = new Result<>();
        result.data = object;
        result.code = 1;
        return result;
    }

    /**
     * 响应失败
     * @param msg   错误信息
     * @param <T>   类型
     * @return      信息和失败状态码
     */
    public static <T> Result<T> error(String msg){
        Result<T> result = new Result<>();
        result.message = msg;
        result.code = 0;
        return result;
    }

}
