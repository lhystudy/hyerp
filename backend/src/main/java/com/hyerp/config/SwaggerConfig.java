package com.hyerp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置类
 * 用于自动生成API接口文档
 * 
 * 访问地址：http://localhost:6818/swagger-ui/index.html
 */
@Configuration
//@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("ERP管理系统API文档")
                        .description("电商HY-ERP系统接口文档(by Li HaoYang)，包含用户登录、商品管理、订单管理等功能。")
                        .version("1.0.0"));
    }
}

