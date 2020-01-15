package cn.ob767.systemservice.interceptor;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final Interceptor interceptor;

    public MvcConfig(Interceptor authInterceptor) {
        this.interceptor = authInterceptor;
    }

    /**
     * 配置静态资源放行
     *
     * @param registry 配置静态资源放行
     * @author 高希 2019/12/06
     * TODO: 2019-12-06
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * 添加拦截器
     *
     * @param registry 添加拦截器
     * @author 高希 2019/12/06
     * TODO: 2019-12-06
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加登录处理拦截器，拦截所有请求，登录请求除外
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(interceptor);

        interceptorRegistration
                .excludePathPatterns("/static/*")
                .excludePathPatterns("/login/*")
                .addPathPatterns("/**");
    }

    /**
     * json视图消息转换器
     *
     * @param converters 转换器
     * @author 高希 2019/12/08
     * TODO: 2019-12-08
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建json消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);

        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializeFilters(new NullValuePropertyFilter());

        //修改配置返回内容的过滤
        //WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
        //WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
        //WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
        //WriteMapNullValue：是否输出值为null的字段,默认为false
        //DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //将json添加到视图消息转换器列表内
        converters.add(fastConverter);
    }

    public class NullValuePropertyFilter implements PropertyFilter {
        /**
         * json过滤指定对象中空的字段值
         *
         * @param object 对象
         * @param name   名称
         * @param value  值
         * @author 高希 2019/12/08
         * TODO: 2019-12-08
         */
        @Override
        public boolean apply(Object object, String name, Object value) {
            if (object instanceof ResponseInterceptor) {
                return value != null;
            }
            return true;
        }
    }
}
