package org.erau.icarus.detect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("org.erau.icarus.detect.*")
public class DetectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DetectApplication.class, args);
    }

}

