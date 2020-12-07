package wang.joye.springboottemplate.constant;

/**
 * redis key
 *
 * @author 汪继友
 * @since 2020/6/22
 */
public class RedisKey {

    public static final String ALL_USER_TOKEN = "user:*:token";
    public static final String ALL_USER_ROLE = "user:*:role";
    public static final String ALL_ROLE_MENU = "role:*:menu";
    public static final String ALL_ROLE_PERMISSION = "role:*:permission";

    /**
     * 用户在某平台下的token
     */
    public static String userToken(long userId) {
        return "user:" + userId + ":token";
    }

    public static String userRole(long userId) {
        return "user:" + userId + ":role";
    }

    public static String roleModuleMenu(long roleId, String module) {
        return "role:" + roleId + ":" + module + ":menu";
    }

    public static String userTaskCount(long userId) {
        return "user:" + userId + ":task-count";
    }

    public static String rolePermission(long roleId) {
        return "role:" + roleId + ":permission";
    }
}