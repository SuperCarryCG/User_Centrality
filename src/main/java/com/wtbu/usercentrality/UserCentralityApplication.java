package com.wtbu.usercentrality;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//全局启动类
@SpringBootApplication
//将mapper里面的文件注入到这个启动类项目中 实现增删改查
@MapperScan("com.wtbu.usercentrality.mapper")
public class UserCentralityApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCentralityApplication.class, args);
    }

}
