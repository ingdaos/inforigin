package co.com.ml.inforigin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class InfoOriginApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfoOriginApplication.class, args);
    }

}
