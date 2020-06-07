package com.score.configuration;

import com.score.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfiguration {

  @Value(value = "${app.version}")
  private String appVersion;

  /**
   * Create docket
   *
   * @return SPB Docket
   */
  @Bean
  public Docket v1Api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("v1")
        .apiInfo(apiInfo())
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.score.controller"))
        .build();
  }

  /**
   * Configuration of API informations using properties
   *
   * @return created ApiInfo
   */
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(Constants.APP_NAME)
        .description("")
        .termsOfServiceUrl("")
        .license("")
        .licenseUrl("")
        .version(appVersion)
        .build();
  }
}
