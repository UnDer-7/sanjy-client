package br.com.gorillaroxo.sanjy.client.shared.client.dto.request;

import java.time.LocalTime;
import java.util.List;

public record MealTypeRequestDTO(
    String name,
    LocalTime scheduledTime,
    String observation,
    List<StandardOptionRequestDTO> standardOptions
) {

}
