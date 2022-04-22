package com.vector.springbootshiro.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Acerola
 */
@Data
@AllArgsConstructor
public class Result<T> {

    private int code;

    private String msg;

    private T data;

    public Result() {

    }

    public Result(T data) {
        this.data = data;
    }

    /**
     * 返回成功
     */
    public Result<T> success() {
        return success("操作成功！");
    }

    /**
     * 返回成功
     */
    public Result<T> success(String message) {
        return success(200, message);
    }

    /**
     * 文件上传成功
     *
     * @param data
     * @param
     * @return
     */
    public Result<T> fileSuccess(T data) {
        Result<T> result = new Result<>(data);
        result.setCode(0);
        result.setMsg("成功");
        return result;
    }

    /**
     * 返回成功
     */
    public Result<T> success(ResponseConstant constant) {
        return success(constant.getResult(), constant.getMsg());
    }
    /**
     * 返回成功
     */
    public Result<T> success(int code, String message) {
        this.setCode(code);
        this.setMsg(message);
        return this;
    }

    /**
     * 返回失败
     */
    public Result<T> error() {
        return error("操作失败！");
    }

    /**
     * 返回失败
     */
    public Result<T> error(String messag) {
        return error(500, messag);
    }

    /**
     * 返回失败
     */
    public Result<T> error(int code, String message) {
        return success(code, message);
    }

    /**
     * 返回信息
     */
    public Result<T> error(ResponseConstant constant) {
        return success(constant.getResult(), constant.getMsg());
    }

    /**
     * 放入object
     */
    public Result<T> put(T object) {
        this.setData(object);
        return this;
    }

}
