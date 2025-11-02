package br.com.gorillaroxo.sanjy.client.shared.client;

import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.MealRecordRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.SearchMealRecordParamRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.MealRecordResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.PagedResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.exception.UnhandledClientHttpException;
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

    /**
     * Records a meal consumption with timestamp, meal type, and quantity. Can register either a standard meal
     * (following the diet plan by referencing a standard option) or a free meal (off-plan with custom description).
     * @throws UnhandledClientHttpException When the request return an error (4xx or 5xx)
     */
    @PostMapping
    MealRecordResponseDTO newMealRecord(@RequestBody MealRecordRequestDTO requestDTO);

    /**
     * Retrieves all meals consumed today, ordered by consumption time. Includes both standard meals (following the diet plan) and free meals (off-plan).
     * Use this to check daily food intake and diet adherence.
     * @throws UnhandledClientHttpException When the request return an error (4xx or 5xx)
     */
    @GetMapping("/today")
    List<MealRecordResponseDTO> getTodayMealRecords();

    /**
     * Searches meal records with pagination and optional filters (date range, meal type). Returns paginated results with total count.
     * Use this to view historical meal data, analyze eating patterns, or generate reports.
     * @throws UnhandledClientHttpException When the request return an error (4xx or 5xx)
     */
    @GetMapping
    PagedResponseDTO<MealRecordResponseDTO> searchMealRecords(@SpringQueryMap SearchMealRecordParamRequestDTO paramRequestDTO);

}
