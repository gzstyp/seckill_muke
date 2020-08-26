package com.fwtai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 电商秒杀系统
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019年3月31日 20:43:58
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
@SpringBootApplication(scanBasePackages = {"com.fwtai"}, exclude = ErrorMvcAutoConfiguration.class)
@MapperScan("com.fwtai.dao")
@EnableTransactionManagement
public class Launch extends SpringBootServletInitializer{

    public static void main(String[] args){
        SpringApplication.run(Launch.class,args);
        System.out.println("--应用已成功启动--");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(Launch.class);
    }
}