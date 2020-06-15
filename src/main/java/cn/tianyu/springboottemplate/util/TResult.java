package cn.tianyu.springboottemplate.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TResult {

    private Integer code;
    private String message;
    private Object data;

    public TResult() {
    }

    public TResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static TResult success() {
        TResult result = new TResult();
        result.setResultCode(TResultCode.SUCCESS);
        return result;
    }

    public static TResult success(Object data) {
        TResult result = new TResult();
        result.setResultCode(TResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static TResult failure(String message) {
        TResult result = new TResult();
        result.setCode(TResultCode.FAILURE.getCode());
        result.message = message;
        return result;
    }

    public static TResult failure(TResultCode resultCode) {
        TResult result = new TResult();
        result.setResultCode(resultCode);
        return result;
    }

    public static TResult failure(TResultCode resultCode, Object data) {
        TResult result = new TResult();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    /**
     * 将TResultCode中的返回码和返回信息复制到TResult中
     */
    private void setResultCode(TResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public enum TResultCode {
        /* 成功状态码 */
        SUCCESS(1, "成功"),

        FAILURE(2, "失败"),
        KICK_OUT(3, "您已在别处登录"),

        /* 参数错误：10001-19999 */
        PARAM_IS_INVALID(10001, "参数无效"),
        PARAM_IS_BLANK(10002, "参数为空"),
        PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
        PARAM_NOT_COMPLETE(10004, "参数缺失"),

        /* 用户错误：20001-29999*/
        USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
        USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
        USER_NOT_EXIST(20004, "用户不存在"),
        USER_HAS_EXISTED(20005, "用户已存在"),

        /* 业务错误：30001-39999 */
        BUSINESS_ERROR(30001, "某业务出现问题"),

        /* 系统错误：40001-49999 */
        SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),

        /* 数据错误：50001-599999 */
        DATA_NONE(50001, "数据不存在"),
        DATA_IS_DELETED(50001, "数据已删除，请先恢复"),
        DATA_IS_WRONG(50002, "数据有误"),
        DATA_ALREADY_EXISTED(50003, "数据已存在"),

        /* 接口错误：60001-69999 */
        INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
        INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
        INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
        INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
        INTERFACE_EXCEED_LOAD(60006, "接口负载过高");

        private Integer code;

        private String message;

        TResultCode(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        /**
         * 校验重复的code值
         */
        public static void main(String[] args) {
            TResultCode[] apiTResultCodes = TResultCode.values();
            System.out.println(apiTResultCodes[0].name());
            List<Integer> codeList = new ArrayList<>();
            for (TResultCode apiTResultCode : apiTResultCodes) {
                if (codeList.contains(apiTResultCode.code)) {
                    System.out.println("warning, find duplicate code:" + apiTResultCode.code);
                } else {
                    codeList.add(apiTResultCode.getCode());
                }
            }
        }

        public Integer getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }
}
