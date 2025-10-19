package br.com.gorillaroxo.sanjy.client.shared.client.dto.response;

import lombok.Builder;

@Builder
public record StandardOptionResponseDTO(
    Long id,
    Long optionNumber,
    String description,
    Long mealTypeId
) {

}
