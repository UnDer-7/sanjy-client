package br.com.gorillaroxo.sanjy.client.shared.client;

import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.DietPlanRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.DietPlanResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    value = "br.com.gorillaroxo.sanjy.client.shared.client.DietPlanFeignClient",
    url = "${sanjy-client.external-apis.sanjy-server.url}",
    path = "/v1/diet-plan"
)
public interface DietPlanFeignClient {

    @GetMapping("/active")
    DietPlanResponseDTO activeDietPlan();

    @PostMapping
    DietPlanResponseDTO newDietPlan(@RequestBody DietPlanRequestDTO dietPlan);
}
