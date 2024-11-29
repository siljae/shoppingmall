package com.lys.shoppingmall.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("동시성 테스트를 위한 쇼핑몰")
                .version("v0.0.2")
                .description("이 프로젝트는 다양한 사용자 요청을 동시에 처리할 수 있는 쇼핑몰 시스템의 성능과 안정성을 테스트하기 위해 개발되었습니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
