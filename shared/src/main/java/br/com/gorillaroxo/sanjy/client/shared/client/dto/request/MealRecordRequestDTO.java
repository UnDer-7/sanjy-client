package br.com.gorillaroxo.sanjy.client.shared.client.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record MealRecordRequestDTO(
    Long mealTypeId,
    Boolean isFreeMeal,
    Long standardOptionId,
    String freeMealDescription,
    Double quantity,
    String unit,
    String notes,
    LocalDateTime consumedAt
) {

}
