package com.easypan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 异步调用
//@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.easypan"})
// 事务
//@EnableTransactionManagement
// 定时任务
//@EnableScheduling
public class EasypanApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasypanApplication.class, args);
    }
}
