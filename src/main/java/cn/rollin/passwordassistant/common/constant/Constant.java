package cn.rollin.passwordassistant.common.constant;

/**
 * 常量
 *
 * @author rollin
 * @since 2024-12-28 08:08:53
 */
public class Constant {

    private Constant() {
    }

    /**
     * 用户登录验证码Key，%s 表示 由前端 MD%(User-Agent + Ip）字符串组成
     */
    public static final String LOGIN_VALID_CODE_KEY = "password-assistant:login:%s";

    /**
     * 用户注册验证码Key，%s 表示 由前端 MD%(User-Agent + Ip）字符串组成
     */
    public static final String REGISTER_VALID_CODE_KEY = "password-assistant:register:%s";

    public static final String REGISTER_EMAIL_CONTENT = "恭喜您💐💐，即将拥有一套自己的密码管理器；您的注册密码为：%s。请注意：当前验证码3分钟内有效。【rollin】";

    /**
     * 验证码的有效时间单位(分钟)
     */
    public static final int CHECK_CODE_EFFECTIVE_TIME = 3;

    /**
     * Salt 前缀
     */
    public final static String SALT_PREFIX = "$1$";
}
