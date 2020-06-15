package cn.tianyu.springboottemplate.global;

import cn.tianyu.springboottemplate.util.TResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author 天宇
 * 全局controller advice
 * 包装请求成功的返回结果
 */
@RestControllerAdvice("cn.tianyu.blogapi.controller")
public class GlobalControllerAdvice implements ResponseBodyAdvice {

    /*
     * 此组件是否支持给定的控制器方法返回类型
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return !methodParameter.getGenericParameterType().getTypeName().equals(TResult.class.getTypeName());
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return TResult.success(o);
    }
}
