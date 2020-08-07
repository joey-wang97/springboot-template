package wang.joye.springboottemplate;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
@MapperScan("wang.joye.springboottemplate.mapper")
public class SpringbootTemplateApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTemplateApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootTemplateApplication.class);
    }

    @PostConstruct
    private void init() {
        /*
         * 注册shutdown钩子，当应用程序正常关闭时，会调用这里的方法
         * kill -9时，不会触发这段代码
         */
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("stop application successfully");
        }));
    }
}