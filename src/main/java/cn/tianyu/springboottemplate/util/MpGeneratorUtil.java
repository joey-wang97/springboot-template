package cn.tianyu.springboottemplate.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 代码生成器
 * <p>
 * 根据配置的作者，包名，数据表
 * 自动生成controller, service, mapper层java文件
 * 以及mybatis xml模板
 */
public class MpGeneratorUtil {

    private static final String AUTHOR = "汪继友";
    // 默认使用当前包名
    private static final String PACKAGE = MpGeneratorUtil.class.getPackage().getName();
    // 选择要扫描的表
    private static final String[] TABLES = {"user"};

    private static String username, password, url, driverClassName;

    private static void initDataSource() {
        //读取application.properties文件，不加.properties后缀，不加路径名
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        username = bundle.getString("spring.datasource.username");
        password = bundle.getString("spring.datasource.password");
        url = bundle.getString("spring.datasource.url");
        driverClassName = bundle.getString("spring.datasource.driverClassName");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        initDataSource();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        // entity使用swagger2进行注解
        // gc.setSwagger2(true);
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
        generator.setPackageInfo(pc);

        // 自定义配置，这里用来添加自定义文件CreateAction.class和UpdateAction.class
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                String actionDir = getConfig().getPathInfo().get(ConstVal.CONTROLLER_PATH) + "/action/";
                Map<String, Object> map = new HashMap<>();
                map.put("package", getConfig().getPackageInfo().get(ConstVal.CONTROLLER) + ".action");

                // 生成action
                for (String actionName : new String[]{"Update", "Create"}) {
                    map.put("action", actionName);
                    try {
                        // 使用mybatis模板引擎，初始化模板
                        AbstractTemplateEngine templateEngine = new VelocityTemplateEngine().init(null);
                        String outputFile = actionDir + actionName + "Action.java";
                        File file = new File(outputFile);
                        if (!file.exists()) file.getParentFile().mkdirs();
                        templateEngine.writer(map, templateEngine.templateFilePath("templates/action.java"),
                                outputFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        generator.setCfg(cfg);

        // 配置模板
        // 默认使用templates下的对应模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setServiceImpl(null);
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(TABLES);
        strategy.setControllerMappingHyphenStyle(true);
        generator.setStrategy(strategy);
        generator.execute();
    }
}
