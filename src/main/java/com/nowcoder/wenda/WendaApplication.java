package com.nowcoder.wenda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@MapperScan(basePackages = {"com.nowcoder.wenda.dao"})
//@EntityScan(basePackages = "com.nowcoder.wenda.model")
public class WendaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WendaApplication.class, args);
    }

}
