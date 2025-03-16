package cn.rollin.passwordassistant.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.rollin.passwordassistant.common.exception.BizException;
import cn.rollin.passwordassistant.common.utils.JWTUtil;
import cn.rollin.passwordassistant.common.utils.UserContext;
import cn.rollin.passwordassistant.pojo.dto.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static cn.rollin.passwordassistant.common.enums.ResStatusEnum.TOKEN_EMPTY;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        // 验证token是否为空
        if (StrUtil.isBlank(token)) {
            throw new BizException(TOKEN_EMPTY);
        }

        // 验证token并获取用户信息
        LoginUser loginUser = jwtUtil.parseJwt(token, LoginUser.class);
        // 将用户信息存入ThreadLocal
        UserContext.setUser(loginUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清理ThreadLocal
        UserContext.removeUser();
    }
}
