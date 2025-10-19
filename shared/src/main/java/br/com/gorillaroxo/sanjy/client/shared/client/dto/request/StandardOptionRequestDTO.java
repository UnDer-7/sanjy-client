package br.com.gorillaroxo.sanjy.client.shared.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record StandardOptionRequestDTO(
    Integer optionNumber,
    String description
) {

}
