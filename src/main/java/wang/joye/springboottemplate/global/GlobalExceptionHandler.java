package wang.joye.springboottemplate.global;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import wang.joye.springboottemplate.exception.BusinessException;
import wang.joye.springboottemplate.util.TResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author 汪继友
 * 全局controller advice，捕捉异常，并包装结果
 * 这里必须使用RestControllerAdvice，否则会跳转到template页面
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public TResult handleException(HttpServletRequest request, Exception e) {
        if (e instanceof SQLException) {
            return handleSqlException(request, (SQLException) e);
        }
        log.error("******** 系统发生异常********");
        log.error("request uri: " + request.getMethod() + " " + request.getRequestURI());
        log.error("parameter map: " + JSON.toJSONString(request.getParameterMap()));
        log.error("request time: " + new Date());
        log.error("堆栈信息", e);
        return TResult.failure(e.getMessage());
    }

    @ExceptionHandler(TypeMismatchException.class)
    public TResult handleMethodArgumentTypeMismatchException(HttpServletRequest request, TypeMismatchException e) {
        return TResult.failure(e.getMessage());
    }

    @ExceptionHandler(PersistenceException.class)
    public TResult handlePersistenceException(HttpServletRequest request, PersistenceException e) {
        return TResult.failure("数据库连接超时");
    }

    @ExceptionHandler(SQLException.class)
    public TResult handleSqlException(HttpServletRequest request, SQLException e) {
        log.error("******** sql异常********\n" +
                "request uri: {} {}\n" +
                "parameter map: {}\n" +
                "sql异常信息 ", request.getMethod(), request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), e);
        return TResult.failure(TResult.TResultCode.SYSTEM_DB_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public TResult handleNullException(HttpServletRequest request, NullPointerException e) {
        log.error("******** 空指针异常********\n" +
                "request uri: {} {}\n" +
                "parameter map: {}\n" +
                "空指针异常 ", request.getMethod(), request.getRequestURI(), JSON.toJSONString(request.getParameterMap()), e);
        return TResult.failure(TResult.TResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public TResult handleBusinessException(HttpServletRequest req, BusinessException e) {
        log.warn(e.getMessage());
        return new TResult(e.getCode(), e.getMessage(), e.getData());
    }

    /**
     * jwt异常
     */
    @ExceptionHandler(JwtException.class)
    public TResult handleJwt(HttpServletRequest req, JwtException e) {
        return TResult.failure(TResult.TResultCode.EXPIRED_TOKEN);
    }

    /**
     * jwt签名异常
     */
    @ExceptionHandler(SignatureException.class)
    public TResult handleSig(HttpServletRequest req, SignatureException e) {
        return TResult.failure(TResult.TResultCode.INVALID_TOKEN);
    }

    /**
     * 处理数据绑定异常
     */
    @ExceptionHandler(BindException.class)
    public TResult handleBindException(HttpServletRequest req, BindException e) {
        StringBuilder error = new StringBuilder();
        e.getFieldErrors().forEach(i -> error.append(i.getField()).append(i.getDefaultMessage()).append("\n"));
        return TResult.failure(error.toString());
    }

    /**
     * 处理参数接收异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalStateException.class, MultipartException.class,
            HttpMessageNotReadableException.class, ValidationException.class, HttpRequestMethodNotSupportedException.class})
    public TResult handleParamException(HttpServletRequest req, Exception e) {
        return TResult.failure(e.getMessage());
    }
}
