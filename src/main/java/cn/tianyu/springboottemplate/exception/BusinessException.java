package cn.tianyu.springboottemplate.exception;

import cn.tianyu.springboottemplate.util.TResult;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(TResult.TResultCode code) {
        super(code.getMessage());
    }

}
