package cn.tianyu.springboottemplate.global;

import cn.tianyu.springboottemplate.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartRunner implements ApplicationRunner {

    private final ApplicationContext applicationContext;

    public StartRunner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) {
        String serverPort = applicationContext.getEnvironment().getProperty("server.port", "8080");
        String serverPath = applicationContext.getEnvironment().getProperty("server.servlet.context-path");

        String apiUrl = String.format("http://%s:%s%s", IpUtil.getMachineIP(), serverPort, serverPath);

        log.info("application started at                {}", apiUrl);
        log.info("application swagger started at        {}/swagger-ui.html", apiUrl);
    }
}
