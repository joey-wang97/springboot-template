package cn.tianyu.springboottemplate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.tianyu.springboottemplate.mapper")
public class SpringbootTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTemplateApplication.class, args);
    }

}
