package wang.joye.springboottemplate.global;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Set;

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
        String serverPath = applicationContext.getEnvironment().getProperty("server.servlet.context-path", "");

        Set<String> set = NetUtil.localIpv4s();
        String apiUrl = String.format("http://%s:%s%s",
                set.iterator().hasNext() ? set.iterator().next() : NetUtil.LOCAL_IP
                , serverPort, serverPath);

        log.info("application swagger started at    {}/doc.html#plus", apiUrl);
        log.info("application start successful!");
    }
}
