package cn.tianyu.springboottemplate.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 过期时间为10天，10天后需要重新登录
     */
    private static final int expireDay = 10;

    // public static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static Key key = Keys.hmacShaKeyFor("test123456test123456test123456ab".getBytes());

    public static String generateJwt(Map<String, Object> claimMap) {
        return generateJwt(claimMap, key);
    }

    /**
     * 根据key生成jwt
     *
     * @param claimMap 参数map，这些参数可以在解析jwt的时候获取
     *                 如userId, 权限id等
     * @param key      用来加密的密钥
     * @return jwt
     */
    public static String generateJwt(Map<String, Object> claimMap, Key key) {
        Date now = new Date();
        return Jwts.builder().setClaims(claimMap)
                // 设置过期时间
                .setExpiration(DateUtils.addDays(now, expireDay))
                // 不处理签发之前的jwt
                .setNotBefore(now)
                // 设置签发时间
                .setIssuedAt(now).signWith(key).compact();
    }

    /**
     * 解密jwt
     *
     * @param jwsStr jwt字符串
     * @return Claims，可以取出jwt中的参数
     */
    public static Claims parse(String jwsStr) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwsStr).getBody();
    }

}
