package org.spm.spm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class SpmApplication {
//doc:http://localhost:8080/swagger-ui/index.html
    public static void main(String[] args) {
        SpringApplication.run(SpmApplication.class, args);
    }

}
