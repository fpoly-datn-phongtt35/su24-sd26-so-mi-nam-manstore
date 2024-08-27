package com.example.manstore.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**");
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//
//        // Thêm module JavaTimeModule để hỗ trợ các kiểu ngày giờ Java 8
//        mapper.registerModule(new JavaTimeModule());
//
//        // Không throw exception khi gặp các thuộc tính không xác định
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        // Bật chế độ hiển thị đẹp
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//        // Cấu hình để không lỗi khi gặp các thuộc tính trống
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//
//        // Cấu hình định dạng ngày giờ nếu cần
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        return mapper;
//    }
}
