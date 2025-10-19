package br.com.gorillaroxo.sanjy.client.shared.client;

import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.MealRecordRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.SearchMealRecordParamRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.MealRecordResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.PagedResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
    value = "br.com.gorillaroxo.sanjy.client.shared.client.MealRecordFeignClient",
    url = "${sanjy-client.external-apis.sanjy-server.url}",
    path = "/v1/meal-record"
)
public interface MealRecordFeignClient {

    @PostMapping
    MealRecordResponseDTO newMealRecord(@RequestBody MealRecordRequestDTO requestDTO);

    @GetMapping("/today")
    List<MealRecordResponseDTO> getTodayMealRecords();

    @GetMapping
    PagedResponseDTO<MealRecordResponseDTO> searchMealRecords(@SpringQueryMap SearchMealRecordParamRequestDTO paramRequestDTO);

}
