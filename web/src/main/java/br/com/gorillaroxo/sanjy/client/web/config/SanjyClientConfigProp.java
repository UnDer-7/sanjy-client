package br.com.gorillaroxo.sanjy.client.web.config;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "sanjy-client")
public record SanjyClientConfigProp(
    ExternalApisProp externalApis
) {

    public record ExternalApisProp(
        GenericApiProp sanjyServer
    ) {

    }

    public record GenericApiProp(
        @NotNull @URL String url
    ) {

    }

}
