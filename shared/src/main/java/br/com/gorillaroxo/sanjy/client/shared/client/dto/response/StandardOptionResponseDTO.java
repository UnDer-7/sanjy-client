package br.com.gorillaroxo.sanjy.client.shared.client.dto.response;

public record StandardOptionResponseDTO(
    Long id,
    Long optionNumber,
    String description,
    Long mealTypeId
) {

}
