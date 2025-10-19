package br.com.gorillaroxo.sanjy.client.shared.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietPlanRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer dailyCalories;
    private Integer dailyProteinInG;
    private Integer dailyCarbsInG;
    private Integer dailyFatInG;
    private String goal;
    private String nutritionistNotes;
    private List<MealTypeRequest> mealTypes;
}
