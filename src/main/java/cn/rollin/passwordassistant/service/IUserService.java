package cn.rollin.passwordassistant.service;

import cn.rollin.passwordassistant.common.res.Response;
import cn.rollin.passwordassistant.pojo.dto.LoginReq;
import cn.rollin.passwordassistant.pojo.dto.LoginRes;
import cn.rollin.passwordassistant.pojo.dto.RegisterReq;

/**
 * 用户服务
 *
 * @author rollin
 * @since 2024-12-27 23:53:06
 */
public interface IUserService {

    /**
     * 用户注册
     *
     * @param req
     * @return
     */
    Response<Object> register(RegisterReq req);

    /**
     * 用户登录
     *
     * @param loginReq 请求体
     * @return 登录结果
     */
    Response<LoginRes> login(LoginReq loginReq);
}
