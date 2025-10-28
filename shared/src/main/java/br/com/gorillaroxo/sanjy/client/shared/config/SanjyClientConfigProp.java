package br.com.gorillaroxo.sanjy.client.shared.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "sanjy-client",  ignoreUnknownFields = false)
public record SanjyClientConfigProp(
    @NotNull @Valid ExternalApisProp externalApis,
    @NotNull @Valid ApplicationProp application,
    @NotNull @Valid LoggingProp logging
) {

    public record ExternalApisProp(
        @Valid GenericApiProp sanjyServer
    ) {

    }

    public record GenericApiProp(
        @NotNull @URL String url
    ) {

    }

    public record ApplicationProp(
        @NotBlank String channel
    ) {

    }

    public record LoggingProp(
        @NotBlank String level,
        @NotBlank String filePath,
        @NotBlank String appender
    ) {
    }
}
