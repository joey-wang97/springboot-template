package wang.joye.springboottemplate.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.joye.springboottemplate.constant.RedisKey;
import wang.joye.springboottemplate.constant.TConstants;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    @Autowired
    RedisUtil redisUtil;

    public String generateAndSaveToken(long userId) {
        // 生成jwt
        Map<String, Object> map = new HashMap<>(1);
        // 存储用户
        map.put(TConstants.ADMIN_ID, userId);
        String jwt = JwtUtil.generateJwt(map);

        // 保存token，过期自动删除
        redisUtil.set(RedisKey.userToken(userId), jwt);

        return jwt;
    }
}
