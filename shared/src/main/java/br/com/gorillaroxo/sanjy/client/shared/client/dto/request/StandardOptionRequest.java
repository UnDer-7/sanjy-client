package br.com.gorillaroxo.sanjy.client.shared.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardOptionRequest {
    private Integer optionNumber;
    private String description;
}
