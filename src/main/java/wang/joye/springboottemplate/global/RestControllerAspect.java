package wang.joye.springboottemplate.global;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentParser;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 汪继友
 * 请求参数、响应体 统一日志打印
 */
@Aspect
@Component
@Slf4j
public class RestControllerAspect {

    /**
     * 环绕通知
     *
     * @param joinPoint 连接点
     * @return 切入点返回值
     * @throws Throwable 异常信息
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController) ")
    public Object apiLog(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String classMethod = joinPoint.getSignature().getName();
        String ip = ServletUtil.getClientIP(request);
        UserAgent agent = UserAgentParser.parse(request.getHeader("user-agent"));

        // 打印日志
        log.info("********* request log *********");
        log.info("http method: {} {}", request.getMethod(), request.getRequestURI());
        log.info("class method: {} {}", className, classMethod);
        // 去除敏感字段后的parameter map
        // log.info("parameter map: " + JSON.toJSONString(deleteSensitiveContent(request.getParameterMap())));
        // 过滤掉HttpServletRequest相关参数，因为序列化时，json会尝试获取request上下文，造成异常
        List<Object> args = Arrays.stream(joinPoint.getArgs()).filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        String jsonArgs = JSON.toJSONString(args);
        log.info("args: " + jsonArgs);
        log.info("remote ip: " + ip);
        log.info("request time: " + LocalDateTime.now());

        return joinPoint.proceed();
    }
}
