package cn.tianyu.springboottemplate.shiro;

import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author 汪继友
 * @since 2020/5/11
 */
@AllArgsConstructor
public class JwtToken implements AuthenticationToken {

    private String jwtToken;

    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }
}
