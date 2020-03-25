package cn.tianyu.springboottemplate.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class HttpServletUtil {
    public static void allowCors(HttpServletRequest request, HttpServletResponse response) {
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
    }

    /**
     * spring boot的sendError方法无法返回数据， 此方法弥补这个问题
     *
     * @param response response
     * @param status   http状态码，一般为401或403
     * @param data     要发送的数据
     */
    public static void sendError(HttpServletResponse response, int status, Object data) {
        response.setStatus(status);
        try {
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(data));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
