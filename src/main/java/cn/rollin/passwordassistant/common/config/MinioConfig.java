package cn.rollin.passwordassistant.common.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio 配置
 *
 * @author rollin
 * @date 2025-03-22 09:35:02
 */
@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.connect-timeout:5000}")
    private long connectTimeout;

    @Value("${minio.write-timeout:60000}")
    private long writeTimeout;

    @Value("${minio.read-timeout:60000}")
    private long readTimeout;

    @Bean
    public MinioClient minioClient() {
        try {
            MinioClient client = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();

            // 测试连接
            client.listBuckets();
            return client;
        } catch (Exception e) {
            throw new RuntimeException("MinIO客户端初始化失败: " + e.getMessage(), e);
        }
    }
}
