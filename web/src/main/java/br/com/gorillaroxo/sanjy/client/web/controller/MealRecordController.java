package br.com.gorillaroxo.sanjy.client.web.controller;

import br.com.gorillaroxo.sanjy.client.shared.client.MealRecordFeignClient;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.MealRecordRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.SearchMealRecordParamRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.DietPlanResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.MealRecordResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.PagedResponseDTO;
import br.com.gorillaroxo.sanjy.client.web.config.TemplateConstants;
import br.com.gorillaroxo.sanjy.client.web.service.DietPlanActiveService;
import br.com.gorillaroxo.sanjy.client.web.util.LoggingHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/meal")
public class MealRecordController {

    private static final String ATTRIBUTE_MEAL_TYPES = "mealTypes";

    private final MealRecordFeignClient  mealRecordFeignClient;
    private final DietPlanActiveService dietPlanActiveService;

    @GetMapping("/new")
    public String showNewMealForm(Model model) {
        // Adicionar objeto vazio para binding
        model.addAttribute("mealRecordRequest", MealRecordRequestDTO.builder().build());

        dietPlanActiveService.get()
            .map(DietPlanResponseDTO::mealTypes)
            .ifPresent(mealTypes -> model.addAttribute(ATTRIBUTE_MEAL_TYPES, mealTypes));

        return LoggingHelper.loggingAndReturnControllerPagePath(TemplateConstants.PageNames.MEAL_NEW);
    }

    @PostMapping
    public String recordMeal(@ModelAttribute MealRecordRequestDTO request) {
        mealRecordFeignClient.newMealRecord(request);
        return LoggingHelper.loggingAndReturnControllerPagePath("redirect:/" + TemplateConstants.PageNames.MEAL_TODAY);
    }

    @GetMapping("/today")
    public String showTodayMeals(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime consumedAtAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime consumedAtBefore,
            @RequestParam(required = false) Boolean isFreeMeal,
            Model model) {

        final PagedResponseDTO<MealRecordResponseDTO> pagedMeals = mealRecordFeignClient.searchMealRecords(
            SearchMealRecordParamRequestDTO.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .consumedAtAfter(consumedAtAfter)
                .consumedAtBefore(consumedAtBefore)
                .isFreeMeal(isFreeMeal)
                .build());

        // Calcular contadores no controller (iterando sobre content)
        long plannedMealsCount = pagedMeals.content().stream()
                .filter(meal -> meal.isFreeMeal() != null && !meal.isFreeMeal())
                .count();

        long freeMealsCount = pagedMeals.content().stream()
                .filter(meal -> meal.isFreeMeal() != null && meal.isFreeMeal())
                .count();

        // Adicionar atributos ao modelo
        model.addAttribute("pagedMeals", pagedMeals);
        model.addAttribute("totalPlannedMeals", (int) plannedMealsCount);
        model.addAttribute("totalFreeMeals", (int) freeMealsCount);

        // Filtros para manter estado no formulário
        model.addAttribute("consumedAtAfter", consumedAtAfter);
        model.addAttribute("consumedAtBefore", consumedAtBefore);
        model.addAttribute("isFreeMeal", isFreeMeal);

        return LoggingHelper.loggingAndReturnControllerPagePath(TemplateConstants.PageNames.MEAL_TODAY);
    }
}
