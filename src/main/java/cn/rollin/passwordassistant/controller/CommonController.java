package cn.rollin.passwordassistant.controller;

import cn.rollin.passwordassistant.common.exception.BizException;
import cn.rollin.passwordassistant.common.res.Response;
import cn.rollin.passwordassistant.common.utils.MinioUtil;
import cn.rollin.passwordassistant.common.utils.UserContext;
import cn.rollin.passwordassistant.pojo.dto.LoginUser;
import cn.rollin.passwordassistant.pojo.vo.FileUploadVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static cn.rollin.passwordassistant.common.enums.ResStatusEnum.FILE_DOWNLOAD_ERROR;

/**
 * 公共 Controller
 *
 * @author rollin
 * @date 2025-03-22 09:36:27
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private MinioUtil minioUtil;

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

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param response HTTP响应
     */
    @GetMapping("/download/{fileName}")
    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) {
        LoginUser loginUser = UserContext.getUser();
        log.info("下载文件，用户：{}，文件名：{}", loginUser.getUsername(), fileName);
        try {
            minioUtil.downloadFile(fileName, response);
        } catch (IOException e) {
            log.error("文件下载失败", e);
            throw new BizException(FILE_DOWNLOAD_ERROR);
        }
    }
}
