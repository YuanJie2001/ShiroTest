package com.vector.springbootshiro.Utils;

/**
 * @author jeebase-WANGLEI
 * @ClassName: PublicResultConstant
 * @Description: TODO
 * @date 2018年5月18日 下午11:49:45
 */
public enum ResponseConstant {
    /**
     * 成功 20x
     */
    // 成功 服务器已经成功处理了请求。通常，这表示服务器提供了请求的网页。
    SUCCESS_200(200, "success"),
    // 已创建 请求成功并且服务器创建了新的资源。
    SUCCESS_201(201, "success"),
    //已接受 服务器已接受请求，但尚未处理。
    SUCCESS_202(202, "success"),
    // 重置内容 服务器成功处理了请求，但没有返回任何内容。
    SUCCESS_205(205, "success"),

    /**
     * 异常 40x
     */
    // 错误请求 服务器不理解请求的语法。
    FAILED_400(400, "错误请求"),
    // -未授权 请求要求身份验证。 token过期
    UNAUTHORIZED_401(401, "未授权"),
    // 禁止 服务器拒绝请求。
    FAILED_403(403, "服务器拒绝请求"),
    // 未找到 服务器到不到请求的网页。
    FAILED_404(404, "服务器到不到请求的网页"),
    // 408-请求超时 服务器等候请求时发生超时。
    FAILED_408(408, "请求超时"),
    /**
     * 服务器错误 50x
     */
    // 服务器内部错误
    SERVER_ERROR_500(500,"服务器内部错误"),
    //服务器不可用 服务器目前无法使用（由于超载或者停机维护）。
    SERVER_ERROR_503(503,"服务器不可用"),
    /**
     * 操作失败
     */
    ERROR(90000000, "操作失败"),
    /**
     * 参数错误
     */
    PARAM_ERROR(90000003, "参数错误"),
    /**
     * 验证码错误
     */
    INVALID_RE_VCODE(10000011, "验证码错误"),
    /**
     * 用户名或密码错误
     */
    INVALID_USERNAME_PASSWORD(10000003, "账号或密码错误"),
    /**
     *
     */
    INVALID_RE_PASSWORD(10000010, "两次输入密码不一致"),
    /**
     * 用户名或密码错误
     */
    INVALID_PASSWORD(10000009, "旧密码错误"),
    /**
     * 用户名重复
     */
    USERNAME_ALREADY_IN(10000002, "用户已存在"),
    /**
     * 用户不存在
     */
    INVALID_USER(10000001, "用户不存在"),
    /**
     * 角色不存在
     */
    INVALID_ROLE(10000004, "角色不存在"),
    /**
     * 角色不存在
     */
    ROLE_USER_USED(10000008, "角色使用中，不可删除"),
    /**
     * 参数错误-已存在
     */
    INVALID_PARAM_EXIST(10000005, "请求参数已存在"),
    /**
     * 参数错误
     */
    INVALID_PARAM_EMPTY(10000006, "请求参数为空"),
    /**
     * 没有权限
     */
    USER_NO_PERMITION(10000007, "当前用户无该接口权限");


    public int result;

    public String msg;

    ResponseConstant(int result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
