package cn.rollin.passwordassistant;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.Collections;

public class CodeGeneratorTest {

    @Test
    public void generate() {
        String url = "jdbc:mysql://127.0.0.1:13309/password-assistant?characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "rollin123";

        FastAutoGenerator.create(url, username, password)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("rollin") // 设置作者
                            .outputDir("/Users/rollin/Desktop/temp/code") // 指定输出目录
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                            .fileOverride(); // 覆盖已有文件
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("cn.rollin.passwordassistant") // 设置父包名
//                            .moduleName("system") // 设置模块名
                            .pathInfo(Collections.singletonMap(
                                    OutputFile.xml,
                                    System.getProperty("user.dir") + "/src/main/resources/mapper"
                            )); // 设置mapperXml生成路径
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("t_user") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_") // 设置过滤表前缀
                            // Entity策略配置
                            .entityBuilder()
                            .enableLombok() // 开启lombok
                            .enableTableFieldAnnotation() // 开启生成实体时生成字段注解
                            // Controller策略配置
                            .controllerBuilder()
                            .enableRestStyle() // 开启生成@RestController
                            // Service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            // Mapper策略配置
                            .mapperBuilder()
                            .enableMapperAnnotation() // 开启@Mapper注解
                            .build();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
