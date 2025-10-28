package br.com.gorillaroxo.sanjy.client.telegram;

import br.com.gorillaroxo.sanjy.client.shared.config.SanjyClientConfigProp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(SanjyClientConfigProp.class)
@EnableFeignClients(basePackages = "br.com.gorillaroxo.sanjy.client.shared.client")
@ComponentScan(basePackages = {"br.com.gorillaroxo.sanjy.client.shared", "br.com.gorillaroxo.sanjy.client.telegram"})
public class SanjyClientTelegramApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanjyClientTelegramApplication.class, args);
    }

}
