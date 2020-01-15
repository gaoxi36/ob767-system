package cn.ob767.systemservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "cn.ob767.systemservice.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
