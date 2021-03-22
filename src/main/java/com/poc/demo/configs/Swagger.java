package com.poc.demo.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.PathProvider;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;
import static springfox.documentation.spring.web.paths.RelativePathProvider.ROOT;

@Configuration
@EnableSwagger2
public class Swagger {

    @Value("${swagger.basePath:/}")
    private String basePath;

    @Bean
    public Docket docket() {
        return new Docket(SWAGGER_2)
                .pathProvider(this.pathProvider())
                .select()
                .apis(basePackage("com.poc.demo"))
                .paths(any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("POC")
                        .version("1.0.0")
                        .build());
    }

    private PathProvider pathProvider() {
        return new AbstractPathProvider() {
            @Override
            protected String applicationPath() {
                return basePath;
            }

            @Override
            protected String getDocumentationPath() {
                return ROOT;
            }
        };
    }
}