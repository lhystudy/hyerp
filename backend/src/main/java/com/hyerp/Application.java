package com.hyerp;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动的时候，使用如下命令来指定是否是生产
 * -Dspring.profiles.active=prod
 * 每次启动的时候需要全清空redis，
 */
@SpringBootApplication
@MapperScan("com.hyerp.dao")
@EnableScheduling
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
