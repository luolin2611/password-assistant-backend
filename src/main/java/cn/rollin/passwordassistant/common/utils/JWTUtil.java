package cn.rollin.passwordassistant.common.utils;


import cn.hutool.crypto.symmetric.AES;
import cn.rollin.passwordassistant.common.enums.JWTSubjectEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static cn.rollin.passwordassistant.common.enums.JWTSubjectEnum.LOGIN_USER;

/**
 * JWT 工具类
 *
 * @author rollin
 * @since 2025-01-15 22:51:22
 */
@Slf4j
@Component
public class JWTUtil {

    /**
     * jwt 加密密钥
     */
    @Value("${security.jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${security.aes.secretKey}")
    private String aesSecretKey;

    /**
     * jwt 默认过期时间(单位：毫秒)
     */
    @Value("${security.jwt.expiration}")
    private Long defaultExpiration;

    /**
     * 令牌前缀
     */
    @Value("${security.jwt.prefix}")
    private String tokenPrefix;

    /**
     * 生成默认的jwt字符串
     *
     * @param object 载荷部分对象
     * @return jwt 字符串
     */
    public String generateDefaultJwt(Object object) {
        return generateJwt(JWTSubjectEnum.DEFAULT_SUBJECT, System.currentTimeMillis() + defaultExpiration, object);
    }

    /**
     * 生成jwt字符串
     *
     * @param subjectEnum 主题枚举
     * @param expiration  jwt过期时间
     * @param t           载荷部分对象
     * @return jwt 字符串
     */
    public <T> String generateJwt(JWTSubjectEnum subjectEnum, Long expiration, T t) {
        String token = Jwts.builder()
                .subject(subjectEnum.getSubject())
                .claim("object", t)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()), Jwts.SIG.HS256)
                .compact();
        return tokenPrefix + new AES(aesSecretKey.getBytes()).encryptBase64(token);
    }

    public <T> String generateLoginJwt(T t) {
        String token = Jwts.builder()
                .subject(LOGIN_USER.getSubject())
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()), Jwts.SIG.HS256)
                .expiration(new Date(System.currentTimeMillis() + defaultExpiration))
                .claim("object", t)
                .compact();
        return tokenPrefix + new AES(aesSecretKey.getBytes()).encryptBase64(token);
    }

    /**
     * 解析jwt字符串
     *
     * @param jwtStr jwt字符串
     * @return jwt对象
     */
    public <T> T parseJwt(String jwtStr) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtStr);
        return (T) claimsJws.getPayload().get("object");
    }
}
