package cn.rollin.passwordassistant.common.utils;

import cn.hutool.core.util.IdUtil;
import cn.rollin.passwordassistant.common.exception.BizException;
import io.minio.*;
import io.minio.http.Method;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static cn.rollin.passwordassistant.common.enums.ResStatusEnum.FILE_DOWNLOAD_ERROR;
import static cn.rollin.passwordassistant.common.enums.ResStatusEnum.FILE_UPLOAD_ERROR;

/**
 * minio 工具类
 *
 * @author rollin
 * @date 2025-03-22 09:36:37
 */
@Slf4j
@Component
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.retry.max-attempts:3}")
    private int maxRetryAttempts;

    @Value("${minio.retry.delay:1000}")
    private long retryDelay;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件访问URL
     */
    public String uploadFile(MultipartFile file) {
        int attempts = 0;
        Exception lastException = null;

        while (attempts < maxRetryAttempts) {
            try {
                log.info("开始上传文件到MinIO，bucket: {}", bucket);

                // 检查bucket是否存在
                boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
                log.info("Bucket {} {}", bucket, found ? "已存在" : "不存在");

                if (!found) {
                    log.info("创建Bucket: {}", bucket);
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
                }

                // 生成文件名
                String fileName = IdUtil.fastSimpleUUID() + "_" + file.getOriginalFilename();
                log.info("准备上传文件: {}", fileName);

                // 上传文件
                minioClient.putObject(
                    PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
                );

                log.info("文件上传成功: {}", fileName);

                // 获取文件访问URL
                String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .method(Method.GET)
                        .build()
                );

                log.info("生成文件访问URL: {}", url);
                return url;
            } catch (Exception e) {
                lastException = e;
                attempts++;
                log.warn("文件上传失败，尝试第{}次重试", attempts, e);

                if (attempts < maxRetryAttempts) {
                    try {
                        Thread.sleep(retryDelay);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }

        log.error("文件上传失败，已重试{}次", attempts, lastException);
        throw new BizException(FILE_UPLOAD_ERROR);
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param response HTTP响应
     * @throws IOException IO异常
     */
    public void downloadFile(String fileName, HttpServletResponse response) throws IOException {
        try {
            // 获取文件输入流
            InputStream file = minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .build()
            );

            // 设置响应头
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(fileName, StandardCharsets.UTF_8));

            // 写入响应
            byte[] buf = new byte[1024];
            int len;
            try (InputStream inputStream = file) {
                while ((len = inputStream.read(buf)) != -1) {
                    response.getOutputStream().write(buf, 0, len);
                }
            }
        } catch (Exception e) {
            log.error("文件下载失败", e);
            throw new BizException(FILE_DOWNLOAD_ERROR);
        }
    }

    /**
     * 获取文件访问URL
     *
     * @param fileName 文件名
     * @return 文件访问URL
     */
    public String getFileUrl(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .method(Method.GET)
                    .build()
            );
        } catch (Exception e) {
            log.error("获取文件URL失败", e);
            throw new BizException(FILE_DOWNLOAD_ERROR);
        }
    }
}
