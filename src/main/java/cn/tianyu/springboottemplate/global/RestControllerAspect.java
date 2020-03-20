package cn.tianyu.springboottemplate.global;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 天宇
 * 请求参数、响应体 统一日志打印
 */
@Aspect
@Component
@Slf4j
public class RestControllerAspect {

    // 敏感字段列表
    private String[] sensitiveFieldArray = {"pwd", "password"};

    public static boolean containIgnoreCase(String input, String regex) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        return m.find();
    }

    /**
     * 环绕通知
     *
     * @param joinPoint 连接点
     * @return 切入点返回值
     * @throws Throwable 异常信息
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController) ")
    public Object apiLog(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.info("********* api log *********");
        log.info("url: " + request.getMethod() + " " + request.getRequestURI());
        log.info("method: " + joinPoint.getSignature().toString());
        log.info("sessionid: " + request.getSession().getId());
        // 去除敏感字段后的parameter map
        log.info("parameter map: " + JSON.toJSONString(deleteSensitiveContent(request.getParameterMap())));
        log.info("args: " + JSON.toJSONString(joinPoint.getArgs()));
        log.info("user-agent: " + request.getHeader("user-agent"));
        log.info("remote ip: " + request.getRemoteAddr() + ", port: " + request.getRemotePort());
        log.info("request time: " + DateFormat.getDateTimeInstance().format(new Date()));
        return joinPoint.proceed();
    }

    /**
     * 删除参数中的敏感内容
     *
     * @return 去除敏感内容后的参数对象
     */
    private Map<String, String[]> deleteSensitiveContent(Map<String, String[]> parameterMap) {
        HashMap<String, String[]> resMap = new HashMap<>(16);
        resMap.putAll(parameterMap);
        resMap.forEach((key, value) -> {
            for (String sensitiveField : sensitiveFieldArray) {
                if (containIgnoreCase(key, sensitiveField)) {
                    resMap.put(key, new String[]{"******"});
                    break;
                }
            }
        });
        return resMap;
    }
}
