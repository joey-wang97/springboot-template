package cn.tianyu.springboottemplate.shiro;

import cn.tianyu.springboottemplate.global.Constants;
import cn.tianyu.springboottemplate.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * shiro将 AuthenticationToken和AuthenticationInfo进行对比，作为登录动作
     * 所以将user的正确信息传递到AuthenticationInfo里
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String jwt = authenticationToken.getPrincipal().toString();
        // 检查jwt是否能被解析
        try {
            Claims claims = JwtUtil.parse(jwt);
            String userId = claims.get(Constants.USER_ID).toString();
            return new SimpleAuthenticationInfo(userId, jwt, "shiro_realm");
        } catch (JwtException e) {
            throw new AuthenticationException("jwt解析失败: " + e.getLocalizedMessage());
        }

    }

    /**
     * 角色权限验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("principal {}", principalCollection);
        return null;
    }
}
