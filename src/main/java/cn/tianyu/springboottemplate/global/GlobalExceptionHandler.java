package cn.tianyu.springboottemplate.global;

import cn.tianyu.springboottemplate.exception.BusinessException;
import cn.tianyu.springboottemplate.util.TResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.Date;

/**
 * @author 天宇
 * 全局controller advice，捕捉异常，并包装结果
 * 这里必须使用RestControllerAdvice，否则会跳转到template页面
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public TResult handleException(HttpServletRequest request, Exception e) {
        log.error(e.getMessage());
        log.error("系统发生异常: {}", e.getMessage());
        log.error("********* request error log *********");
        log.error("request uri: " + request.getMethod() + " " + request.getRequestURI());
        log.error("parameter map: " + JSON.toJSONString(request.getParameterMap()));
        log.error("request time: " + new Date());
        e.printStackTrace();
        return TResult.failure(e.getMessage());
    }

    /**
     * 身份验证出错
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException e) {
        log.warn(e.getMessage());
        return TResult.TResultCode.USER_LOGIN_ERROR.getMessage();
    }

    /**
     * 权限错误
     * 这里直接返回 UNAUTHORIZED和错误信息，不进行TResult包装
     * 因为UNAUTHORIZED时，code和data没有必要进行传递
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    public String handleAuthorizationException(HttpServletRequest req, HttpServletResponse res, AuthorizationException e) {
        log.debug(req.getSession().getId());
        log.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(NullPointerException.class)
    public TResult handleNullPointerException(HttpServletRequest req, NullPointerException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return TResult.failure(TResult.TResultCode.RESULE_DATA_NONE);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public TResult handleBusinessException(HttpServletRequest req, BusinessException e) {
        log.warn(e.getMessage());
        return TResult.failure(e.getMessage());
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
    @ExceptionHandler(ConstraintViolationException.class)
    public TResult handleConstraintViolationException(HttpServletRequest req, ConstraintViolationException e) {
        return TResult.failure(e.getMessage());
    }
}
