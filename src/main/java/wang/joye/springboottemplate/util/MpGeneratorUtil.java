package wang.joye.springboottemplate.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 代码生成器
 * <p>
 * 根据配置的作者，包名，数据表
 * 自动生成controller, service, mapper层java文件
 * 以及mybatis xml模板
 */
public class MpGeneratorUtil {

    private static final String AUTHOR = "joye";
    public static final String MODULE = "";
    // 默认使用当前包名
    private static final String CURR_PACKAGE = MpGeneratorUtil.class.getPackage().getName();
    private static final String PACKAGE = CURR_PACKAGE.substring(0, CURR_PACKAGE.lastIndexOf('.'));

    private static String username, password, url, driverClassName;

    private static void initDataSource() {
        //读取application.properties文件，不加.properties后缀，不加路径名
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        username = bundle.getString("spring.datasource.username");
        password = bundle.getString("spring.datasource.password");
        url = bundle.getString("spring.datasource.url");
        driverClassName = bundle.getString("spring.datasource.driver-class-name");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        initDataSource();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String baseOutputDir = System.getProperty("user.dir");
        if (StringUtils.isNotBlank(MODULE)) {
            baseOutputDir += "/" + MODULE;
        }
        gc.setOutputDir(baseOutputDir + "/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        // entity使用swagger2进行注解
        gc.setSwagger2(true);
        gc.setBaseResultMap(true);
        // 取消Service的前缀 I
        gc.setServiceName("%sService");
        generator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driverClassName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        generator.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE);
        pc.setEntity("data.entity");
        generator.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        // 自定义xml生成的位置
        List<FileOutConfig> list = new ArrayList<>();
        String finalBaseOutputDir = baseOutputDir;
        list.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return finalBaseOutputDir + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(list);
        generator.setCfg(cfg);

        // 配置模板
        // 默认使用templates下的对应模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setServiceImpl(null);
        templateConfig.setXml(null);
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        generator.setStrategy(strategy);
        generator.execute();
    }
}