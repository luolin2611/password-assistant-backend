package cn.rollin.passwordassistant.controller;

import cn.rollin.passwordassistant.common.res.Response;
import cn.rollin.passwordassistant.common.utils.MinioUtil;
import cn.rollin.passwordassistant.common.utils.UserContext;
import cn.rollin.passwordassistant.pojo.dto.LoginUser;
import cn.rollin.passwordassistant.pojo.dto.PasswordAddReq;
import cn.rollin.passwordassistant.pojo.dto.PasswordUpdateReq;
import cn.rollin.passwordassistant.pojo.vo.FileUploadVO;
import cn.rollin.passwordassistant.pojo.vo.PasswordRecordVO;
import cn.rollin.passwordassistant.service.IPasswordRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private MinioUtil minioUtil;

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
    public Response<List<PasswordRecordVO>> queryPasswordRecords(@RequestParam(name = "id", required = false) Integer id) {
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

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 响应结果
     */
    @PostMapping("/upload")
    public Response<FileUploadVO> uploadFile(@RequestParam("file") MultipartFile file) {
        LoginUser loginUser = UserContext.getUser();
        log.info("上传文件，用户：{}，文件名：{}", loginUser.getUsername(), file.getOriginalFilename());
        String url = minioUtil.uploadFile(file);
        return Response.buildSuccess(new FileUploadVO(url, file.getOriginalFilename()));
    }
}
