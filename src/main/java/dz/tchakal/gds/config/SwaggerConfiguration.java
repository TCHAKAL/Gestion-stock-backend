package dz.tchakal.gds.config;

import dz.tchakal.gds.util.StaticRoot;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfiguration {

    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("GEstion de stock API documentation ")
                        .title("Stock it easy Rest API")
                        .build()
                )
                .groupName("Rest API V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("dz.tchakal.gds"))
                .paths(PathSelectors.ant(StaticRoot.APP_ROOT+"/.*"))
                .build();
    }
}
