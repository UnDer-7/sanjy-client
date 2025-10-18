package br.com.gorillaroxo.sanjy.client.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SanjyClientWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanjyClientWebApplication.class, args);
    }

}
