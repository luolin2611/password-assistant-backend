package cn.rollin.passwordassistant.controller;

import cn.rollin.passwordassistant.common.res.Response;
import cn.rollin.passwordassistant.common.utils.UserContext;
import cn.rollin.passwordassistant.pojo.dto.LoginUser;
import cn.rollin.passwordassistant.pojo.dto.PasswordAddReq;
import cn.rollin.passwordassistant.pojo.dto.PasswordUpdateReq;
import cn.rollin.passwordassistant.pojo.vo.PasswordRecordVO;
import cn.rollin.passwordassistant.service.IPasswordRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 密码记录 Controller
 *
 * @author rollin
 * @date 2025-03-16 08:26:52
 */
@Slf4j
@RestController
@RequestMapping("/password")
public class PasswordRecordController {

    @Autowired
    private IPasswordRecordService passwordRecordService;

    /**
     * 添加密码记录
     *
     * @param req 请求参数
     * @return 响应结果
     */
    @PostMapping("/add")
    public Response<Integer> addPasswordRecord(@Validated @RequestBody PasswordAddReq req) {
        LoginUser loginUser = UserContext.getUser();
        log.info("新增密码记录，用户：{}，平台：{}", loginUser.getUsername(), req.getPlatformName());
        return passwordRecordService.addPasswordRecord(req, loginUser.getId());
    }

    /**
     * 修改密码记录
     *
     * @param req 请求参数
     * @return 响应结果
     */
    @PostMapping("/update")
    public Response<Boolean> updatePasswordRecord(@Validated @RequestBody PasswordUpdateReq req) {
        LoginUser loginUser = UserContext.getUser();
        log.info("修改密码记录，用户：{}，记录ID：{}", loginUser.getUsername(), req.getId());
        return passwordRecordService.updatePasswordRecord(req, loginUser.getId());
    }

    /**
     * 查询密码记录
     *
     * @param id 记录ID
     * @return 响应结果
     */
    @GetMapping("/query")
    public Response<List<PasswordRecordVO>> queryPasswordRecords(@RequestParam(required = false) Integer id) {
        LoginUser loginUser = UserContext.getUser();
        log.info("查询密码记录，用户：{}，记录ID：{}", loginUser.getUsername(), id);
        return passwordRecordService.queryPasswordRecords(id, loginUser.getId());
    }

    /**
     * 删除密码记录
     *
     * @param id 记录ID
     * @return 响应结果
     */
    @DeleteMapping("/{id}")
    public Response<Boolean> deletePasswordRecord(@PathVariable Integer id) {
        LoginUser loginUser = UserContext.getUser();
        log.info("删除密码记录，用户：{}，记录ID：{}", loginUser.getUsername(), id);
        return passwordRecordService.deletePasswordRecord(id, loginUser.getId());
    }
}
