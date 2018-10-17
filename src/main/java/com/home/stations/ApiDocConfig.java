package com.home.stations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
/*
 swagger2/SpringFox https://www.vojtechruzicka.com/documenting-spring-boot-rest-api-swagger-springfox/
 TODO currently using default output it generates by gathering metadata from controller.
 need to host to render (see online swagger editor)
 */
public class ApiDocConfig
{
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}