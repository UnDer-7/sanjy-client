package br.com.gorillaroxo.sanjy.client.web.service;

import br.com.gorillaroxo.sanjy.client.shared.client.MealRecordFeignClient;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.SearchMealRecordParamRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.MealRecordResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.MealRecordStatisticsResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.PagedResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.util.LogField;
import br.com.gorillaroxo.sanjy.client.shared.util.ThreadUtils;
import br.com.gorillaroxo.sanjy.client.web.domain.SearchMealRecordDomain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchMealRecordService {

    private final MealRecordFeignClient mealRecordFeignClient;

    @Qualifier("applicationTaskExecutor")
    private final TaskExecutor taskExecutor;

    public SearchMealRecordDomain search(Integer pageNumber, Integer pageSize, LocalDateTime consumedAtAfter, LocalDateTime consumedAtBefore,
        Boolean isFreeMeal) {
        final CompletableFuture<PagedResponseDTO<MealRecordResponseDTO>> mealRecordFuture = ThreadUtils.supplyAsyncWithMDC(() -> {
            log.info(
                LogField.Placeholders.ONE.placeholder,
                StructuredArguments.kv(LogField.MSG.label(), "Searching meal records asynchronously..."));

            return mealRecordFeignClient.searchMealRecords(
                SearchMealRecordParamRequestDTO.builder()
                    .pageNumber(pageNumber)
                    .pageSize(pageSize)
                    .consumedAtAfter(consumedAtAfter)
                    .consumedAtBefore(consumedAtBefore)
                    .isFreeMeal(isFreeMeal)
                    .build());
        }, taskExecutor);

        final CompletableFuture<MealRecordStatisticsResponseDTO> mealRecordStatisticsFuture = ThreadUtils.supplyAsyncWithMDC(
            () -> {
                log.info(
                    LogField.Placeholders.ONE.placeholder,
                    StructuredArguments.kv(LogField.MSG.label(), "Searching meal records statistics asynchronously..."));

                return mealRecordFeignClient.getMealRecordStatisticsByDateRange(consumedAtAfter, consumedAtBefore);
            }, taskExecutor);

        return new SearchMealRecordDomain(
            mealRecordFuture.join(),
            mealRecordStatisticsFuture.join()
        );
    }

}
