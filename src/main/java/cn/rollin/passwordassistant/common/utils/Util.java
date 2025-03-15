package cn.rollin.passwordassistant.common.utils;

import cn.rollin.passwordassistant.common.constant.Constant;
import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * description
 *
 * @author rollin
 * @since 2024-12-28 09:00:55
 */
public class Util {

    private static final String ALL_CHAR_NUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private Util() {
    }

    /**
     * 获取ip
     *
     * @param request requst
     * @return id
     */
    public static String getIpAddr(HttpServletRequest request) {
        request.getRemoteAddr();
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // "***.***.***.***".length()
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    /**
     * 获取随机字符的字符串(0-9a-z)
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String getStringNumRandom(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR_NUM.charAt(random.nextInt(ALL_CHAR_NUM.length())));
        }
        return sb.toString();
    }
}
