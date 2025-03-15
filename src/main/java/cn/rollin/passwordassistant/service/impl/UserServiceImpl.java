package cn.rollin.passwordassistant.service.impl;

import cn.rollin.passwordassistant.common.constant.Constant;
import cn.rollin.passwordassistant.common.exception.BizException;
import cn.rollin.passwordassistant.common.res.Response;
import cn.rollin.passwordassistant.common.utils.JWTUtil;
import cn.rollin.passwordassistant.common.utils.Util;
import cn.rollin.passwordassistant.mapper.UserMapper;
import cn.rollin.passwordassistant.pojo.dto.LoginReq;
import cn.rollin.passwordassistant.pojo.dto.LoginRes;
import cn.rollin.passwordassistant.pojo.dto.LoginUser;
import cn.rollin.passwordassistant.pojo.dto.RegisterReq;
import cn.rollin.passwordassistant.pojo.entity.User;
import cn.rollin.passwordassistant.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import static cn.rollin.passwordassistant.common.enums.ResStatusEnum.REGISTER_USER_ERROR;
import static cn.rollin.passwordassistant.common.enums.ResStatusEnum.REGISTER_USER_REPEAT;

/**
 * 用户服务
 *
 * @author rollin
 * @since 2024-12-27 23:53:06
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public Response<Object> register(RegisterReq req) {
        // 查询用户是否存在和插入用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, req.getUsername()));
        if (ObjectUtils.isNotEmpty(user)) {
            throw new BizException(REGISTER_USER_REPEAT);
        }
        String salt = Constant.SALT_PREFIX + Util.getStringNumRandom(8);
        String password = Md5Crypt.md5Crypt(req.getPassword().getBytes(StandardCharsets.UTF_8), salt);
        user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .salt(salt)
                .signature("这家伙很懒，什么都没留下。")
                .password(password)
                .status("0")
                .build();
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            throw new BizException(REGISTER_USER_REPEAT);
        }
        return Response.buildSuccess();
    }

    @Override
    public Response<LoginRes> login(LoginReq loginReq) {
        String username = loginReq.getUsername();
        String password = loginReq.getPassword();
        // 查询用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username).or().eq(User::getEmail, username));
        if (ObjectUtils.isEmpty(user)) {
            throw new BizException(REGISTER_USER_ERROR);
        }
        // 校验密码
        String salt = user.getSalt();
        String md5Password = Md5Crypt.md5Crypt(password.getBytes(StandardCharsets.UTF_8), salt);
        if (!user.getPassword().equals(md5Password)) {
            throw new BizException(REGISTER_USER_ERROR);
        }
        // 生成token
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(user, loginUser);

        return Response.buildSuccess(new LoginRes(loginUser, jwtUtil.generateLoginJwt(loginUser)));
    }
}
