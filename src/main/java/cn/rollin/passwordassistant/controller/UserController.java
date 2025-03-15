package cn.rollin.passwordassistant.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.rollin.passwordassistant.common.constant.Constant;
import cn.rollin.passwordassistant.common.exception.BizException;
import cn.rollin.passwordassistant.common.res.Response;
import cn.rollin.passwordassistant.common.utils.EmailUtil;
import cn.rollin.passwordassistant.common.utils.Util;
import cn.rollin.passwordassistant.pojo.dto.LoginReq;
import cn.rollin.passwordassistant.pojo.dto.LoginRes;
import cn.rollin.passwordassistant.pojo.dto.RegisterReq;
import cn.rollin.passwordassistant.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static cn.rollin.passwordassistant.common.constant.Constant.CHECK_CODE_EFFECTIVE_TIME;
import static cn.rollin.passwordassistant.common.constant.Constant.REGISTER_VALID_CODE_KEY;
import static cn.rollin.passwordassistant.common.enums.ResStatusEnum.CHECK_CODE_EFFECTIVE;
import static cn.rollin.passwordassistant.common.enums.ResStatusEnum.CHECK_CODE_INVALID;
import static cn.rollin.passwordassistant.common.enums.ResStatusEnum.EMAIL_EMPTY;

/**
 * 用户 Controller
 *
 * @author rollin
 * @since 2024-12-27 23:53:06
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private IUserService userService;


    /**
     * 发送注册验证码
     *
     * @param email   收件邮箱
     * @param request 请求体
     * @return 响应结果
     */
    @GetMapping("/send-code")
    public Response<Object> sendEmailCode(@RequestParam("email") String email, HttpServletRequest request) {
        log.info("发送验证码.");
        if (StrUtil.isBlank(email)) {
            throw new BizException(EMAIL_EMPTY);
        }
        // 检查验证码是否已发送
        String cacheCodeKey = String.format(REGISTER_VALID_CODE_KEY, generateStr(request));
        if (redisTemplate.hasKey(cacheCodeKey)) {
            throw new BizException(CHECK_CODE_EFFECTIVE);
        }
        // 发送验证码
        String checkCode = Util.getStringNumRandom(6);
        String content = String.format(Constant.REGISTER_EMAIL_CONTENT, checkCode);
//        emailUtil.sendSimpleMail(email, "用户注册", content);
        redisTemplate.opsForValue().set(cacheCodeKey, checkCode, CHECK_CODE_EFFECTIVE_TIME, TimeUnit.MINUTES);
        log.debug("验证码发送成功，验证码保存Key为：{}, 验证码为：{}", cacheCodeKey, checkCode);
        return Response.buildSuccess();
    }

    /**
     * 用户注册
     *
     * @param req     请求参数
     * @param request 请求体
     * @return 响应结果
     */
    @PostMapping("register")
    public Response<Object> register(@Validated @RequestBody RegisterReq req, HttpServletRequest request) {
        String cacheCodeKey = String.format(REGISTER_VALID_CODE_KEY, generateStr(request));
        String checkCode = redisTemplate.opsForValue().get(cacheCodeKey);
        // 验证验证码
        if (StrUtil.isBlank(checkCode) || !checkCode.equals(req.getVerifyCode())) {
            throw new BizException(CHECK_CODE_INVALID);
        }
        // 注册用户
        redisTemplate.delete(cacheCodeKey);
        return userService.register(req);
    }

    /**
     * 用户登录
     *
     * @param loginReq 请求体
     * @return 登录结果
     */
    @PostMapping("login")
    public Response<LoginRes> login(@RequestBody @Validated LoginReq loginReq) {
        return userService.login(loginReq);
    }

    /**
     * 通过浏览器生成组成redis Key 一部分
     *
     * @param request request
     * @return 结果
     */
    private String generateStr(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String ipAddr = Util.getIpAddr(request);
        return new MD5().digestHex(userAgent + ipAddr);
    }

}
