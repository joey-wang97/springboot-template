package wang.joye.springboottemplate.global;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import wang.joye.springboottemplate.constant.TConstants;
import wang.joye.springboottemplate.util.InterceptorUtil;
import wang.joye.springboottemplate.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * jwt拦截器
 */
@Slf4j
@Component
public class GlobalInterceptor implements HandlerInterceptor {

    @Autowired
    private HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws IOException {
        InterceptorUtil.allowCORS(request, response);

        if (request.getMethod().equals(RequestMethod.OPTIONS.toString())) {
            return true;
        }

        String header = request.getHeader(TConstants.AUTHORIZATION);
        if (StringUtils.isBlank(header) || header.length() <= TConstants.JWT_PREFIX.length()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "please specify the token");
            return false;
        }

        String jwt = header.substring(TConstants.JWT_PREFIX.length());

        try {
            long adminId = JwtUtil.parse(jwt).get(TConstants.ADMIN_ID, Long.class);
            session.setAttribute(TConstants.ADMIN_ID, adminId);
            return true;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "token invalid");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }
}