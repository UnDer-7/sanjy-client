package br.com.gorillaroxo.sanjy.client.shared.client.dto.response;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record MealTypeResponseDTO(
    Long id,
    String name,
    LocalTime scheduledTime,
    Long dietPlanId,
    List<StandardOptionResponseDTO> standardOptions
) {

    public MealTypeResponseDTO {
        standardOptions = Objects.requireNonNullElse(standardOptions, Collections.emptyList());
    }
}
