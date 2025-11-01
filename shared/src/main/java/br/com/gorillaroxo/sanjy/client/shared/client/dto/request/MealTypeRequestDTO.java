package br.com.gorillaroxo.sanjy.client.shared.client.dto.request;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record MealTypeRequestDTO(
    String name,
    LocalTime scheduledTime,
    String observation,
    List<StandardOptionRequestDTO> standardOptions
) {

    public MealTypeRequestDTO {
        standardOptions = Objects.requireNonNullElseGet(standardOptions, Collections::emptyList);
    }
}
