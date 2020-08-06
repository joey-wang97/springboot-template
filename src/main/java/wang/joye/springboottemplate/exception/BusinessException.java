package wang.joye.springboottemplate.exception;

import wang.joye.springboottemplate.util.TResult;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private int code;
    private Object data;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Object data) {
        this.data = data;
    }

    public BusinessException(TResult.TResultCode code) {
        super(code.getMessage());
        this.code = code.getCode();
    }

}
