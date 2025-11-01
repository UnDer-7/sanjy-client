package br.com.gorillaroxo.sanjy.client.shared.client.dto.request;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Builder(toBuilder = true)
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

    public DietPlanRequestDTO {
        mealTypes = Objects.requireNonNullElseGet(mealTypes, Collections::emptyList);
    }

    public boolean isEmpty() {
        return (name == null || name.isBlank()) &&
               startDate == null &&
               endDate == null &&
               dailyCalories == null &&
               dailyProteinInG == null &&
               dailyCarbsInG == null &&
               dailyFatInG == null &&
               (goal == null || goal.isBlank()) &&
               (nutritionistNotes == null || nutritionistNotes.isBlank()) &&
               (mealTypes == null || mealTypes.isEmpty());
    }

}
