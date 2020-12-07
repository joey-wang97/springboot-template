package wang.joye.springboottemplate.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 记录所有请求内容和响应内容的filter
 *
 * @author 汪继友
 * @since 2020/11/26
 */
@Slf4j
// @WebFilter("/*")
public class RequestAndResponseFilter implements Filter {
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);

        // 取context-path之后的mapping
        String mappingUri = requestWrapper.getRequestURI().substring(contextPath.length());
        // 遇到options请求和swagger，不记录日志
        // TODO 静态文件不记录日志
        if (requestWrapper.getMethod().equalsIgnoreCase("OPTIONS")
                || StrUtil.startWithAny(mappingUri, "/swagger", "/webjars", "/v2/", "/doc.html", "favicon.ico")) {
            chain.doFilter(request, response);
            return;
        }

        log.info("********* request log *********");
        log.info("http method: {} {}", requestWrapper.getMethod(), requestWrapper.getRequestURI());
        log.info("request param: {}", JSON.toJSONString(requestWrapper.getParameterMap()));
        log.info("request body: {}", ServletUtil.getBody(requestWrapper));
        log.info("remote ip: {}" , ServletUtil.getClientIP(requestWrapper));
        log.info("request time: {}", LocalDateTime.now());
        chain.doFilter(requestWrapper, responseWrapper);
        // 打印响应数据必须放在doFilter之后
        log.info("response data: {}", responseWrapper.getResponseData());

    }

}
