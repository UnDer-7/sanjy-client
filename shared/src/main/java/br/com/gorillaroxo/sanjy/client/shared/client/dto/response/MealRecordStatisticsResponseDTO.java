package br.com.gorillaroxo.sanjy.client.shared.client.dto.response;

public record MealRecordStatisticsResponseDTO(
    Long freeMealQuantity,
    Long plannedMealQuantity,
    Long mealQuantity
) {

}
