package wang.joye.springboottemplate.exception;

import lombok.Data;
import wang.joye.springboottemplate.util.TResult;

@Data
public class BusinessException extends RuntimeException {

    private int code;
    private Object data;

    public BusinessException(String message) {
        super(message);
        this.code = TResult.TResultCode.FAILURE.getCode();
    }

    public BusinessException(Object data) {
        this.data = data;
    }

    public BusinessException(TResult.TResultCode code) {
        super(code.getMessage());
        this.code = code.getCode();
    }

}
