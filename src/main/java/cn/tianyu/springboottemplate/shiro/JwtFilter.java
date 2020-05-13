package cn.tianyu.springboottemplate.shiro;

import cn.tianyu.springboottemplate.global.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 汪继友
 * @since 2020/5/11
 */
@Slf4j
public class JwtFilter extends PathMatchingFilter {

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS"))
            return true;

        String header = httpServletRequest.getHeader(Constants.AUTHORIZATION);
        if (StringUtils.isBlank(header)) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "please specify the authorization");
            return false;
        }

        JwtToken jwtToken = new JwtToken(header);
        Subject subject = SecurityUtils.getSubject();

        subject.login(jwtToken);
        return true;
    }
}

