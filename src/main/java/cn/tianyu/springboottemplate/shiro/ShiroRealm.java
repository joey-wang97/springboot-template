package cn.tianyu.springboottemplate.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    /**
     * shiro将 AuthenticationToken和AuthenticationInfo进行对比，作为登录动作
     * 所以将user的正确信息传递到AuthenticationInfo里
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //TODO 根据token获取username和pwd
        String username = "admin", pwd = "pwd";
        return new SimpleAuthenticationInfo(username, pwd, "my_realm");
    }

    /**
     * 角色权限验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("principal {}", principalCollection);
        SimpleAuthorizationInfo res = new SimpleAuthorizationInfo();
        // TODO 根据authorizationInfo获取角色
        res.addRole("test_role");
        return res;
    }
}
