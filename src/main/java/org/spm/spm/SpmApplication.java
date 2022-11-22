package org.spm.spm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.io.IOException;

@SpringBootApplication
@EnableOpenApi
public class SpmApplication {
    //doc:http://localhost:8080/swagger-ui/index.html
    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpmApplication.class, args);
        Runtime.getRuntime().exec("cmd /c start http://localhost:8080/swagger-ui/index.html");//自动打开后端文档
    }

}
