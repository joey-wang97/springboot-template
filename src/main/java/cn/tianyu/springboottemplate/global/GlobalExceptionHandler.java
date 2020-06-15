package cn.tianyu.springboottemplate.global;

import cn.tianyu.springboottemplate.exception.BusinessException;
import cn.tianyu.springboottemplate.util.TResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
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
        log.error("******** 系统发生异常********");
        log.error("request uri: " + request.getMethod() + " " + request.getRequestURI());
        log.error("parameter map: " + JSON.toJSONString(request.getParameterMap()));
        log.error("request time: " + new Date());
        log.error("堆栈信息", e);
        return TResult.failure(e.getMessage());
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
