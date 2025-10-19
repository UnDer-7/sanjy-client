package br.com.gorillaroxo.sanjy.client.shared.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
public record DietPlanRequestDTO(
    String name,
    LocalDate startDate,
    LocalDate endDate,
    Integer dailyCalories,
    Integer dailyProteinInG,
    Integer dailyCarbsInG,
    Integer dailyFatInG,
    String goal,
    String nutritionistNotes,
    List<MealTypeRequestDTO> mealTypes
) {
}
