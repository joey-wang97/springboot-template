package wang.joye.springboottemplate.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 雪花算法
 *
 * @author 汪继友
 * @since 2020/4/16
 */
public class SnowflakeIdUtil {

    private static final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    public static String nextStrId() {
        return snowflake.nextIdStr();
    }

    public static long nextId() {
        return snowflake.nextId();
    }
}
