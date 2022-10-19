package com.elema;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j // 提供日志的输出
@SpringBootApplication
@ServletComponentScan // 扫描servlet组件加入容器
@EnableTransactionManagement
public class ElemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElemaApplication.class, args);
        log.info("项目启动成功!");
    }

}
