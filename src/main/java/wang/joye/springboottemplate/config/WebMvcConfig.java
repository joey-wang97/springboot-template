package wang.joye.springboottemplate.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 汪继友
 * @since 2020/5/25
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 添加跨域支持
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    /**
     * 添加fastjson转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonConvert = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat(JSON.DEFFAULT_DATE_FORMAT);

//		config.setFeatures(Feature.OrderedField);
//		config.setFeatures(Feature.SortFeidFastMatch);
        config.setSerializerFeatures(
//				SerializerFeature.SortField,
//				SerializerFeature.MapSortField,
                SerializerFeature.PrettyFormat,
                SerializerFeature.BrowserCompatible,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty
        );

        fastJsonConvert.setFastJsonConfig(config);
        converters.add(0, fastJsonConvert);
    }
}
