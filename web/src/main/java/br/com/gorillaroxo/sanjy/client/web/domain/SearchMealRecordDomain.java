package br.com.gorillaroxo.sanjy.client.web.domain;

import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.MealRecordResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.MealRecordStatisticsResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.PagedResponseDTO;

public record SearchMealRecordDomain(
    PagedResponseDTO<MealRecordResponseDTO> mealRecord,
    MealRecordStatisticsResponseDTO mealRecordStatistics
) {

}
