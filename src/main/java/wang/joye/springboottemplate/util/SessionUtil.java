package wang.joye.springboottemplate.util;


import wang.joye.springboottemplate.exception.BusinessException;
import wang.joye.springboottemplate.global.Constants;

import javax.servlet.http.HttpSession;

/**
 * @author 汪继友
 * @since 2020/4/9
 */
public class SessionUtil {

    private static void check(HttpSession session, String key) {
        if (session.getAttribute(key) == null) {
            throw new BusinessException("session不包含" + key);
        }
    }

    /**
     * 从session中获取用户id
     */
    public static long getUserId(HttpSession session) {
        check(session, Constants.ADMIN_ID);
        return (long) session.getAttribute(Constants.ADMIN_ID);
    }
}
