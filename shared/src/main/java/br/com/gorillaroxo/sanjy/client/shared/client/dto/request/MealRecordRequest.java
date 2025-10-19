package br.com.gorillaroxo.sanjy.client.shared.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealRecordRequest {
    private Long mealTypeId;
    private Boolean isFreeMeal;
    private Long standardOptionId;        // required if isFreeMeal=false
    private String freeMealDescription;   // required if isFreeMeal=true
    private Double quantity;              // optional, default 1.0
    private String unit;                  // optional, default "serving"
    private String notes;                 // optional
    private LocalDateTime consumedAt;     // optional
}
