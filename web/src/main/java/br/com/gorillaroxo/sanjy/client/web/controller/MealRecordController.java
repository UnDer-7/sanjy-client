package br.com.gorillaroxo.sanjy.client.web.controller;

import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.MealRecordRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.MealRecordResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.PagedResponseDTO;
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
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/meal")
public class MealRecordController {

    @GetMapping("/new")
    public String showNewMealForm(Model model) {
        // Mock: Adicionar objeto vazio para binding
        model.addAttribute("mealRecordRequest", MealRecordRequestDTO.builder().build());

        // Mock: Em produção, buscar meal types do plano ativo
        model.addAttribute("mealTypes", List.of());

        return "meal/new";
    }

    @PostMapping
    public String recordMeal(@ModelAttribute MealRecordRequestDTO request) {
        // Mock: Não implementar chamada ao backend
        // Em produção, aqui seria feita a chamada via Feign Client
        return "redirect:/meal/today";
    }

    @GetMapping("/today")
    public String showTodayMeals(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime consumedAtAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime consumedAtBefore,
            @RequestParam(required = false) Boolean isFreeMeal,
            Model model) {

        // Mock: Em produção, buscar via Feign Client com os parâmetros de paginação e filtros
        // Por enquanto, retornar resposta vazia paginada
        PagedResponseDTO<MealRecordResponseDTO> pagedMeals = PagedResponseDTO.<MealRecordResponseDTO>builder()
                .totalPages(0)
                .currentPage(pageNumber)
                .pageSize(pageSize)
                .totalItems(0L)
                .content(List.of())
                .build();

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

        return "meal/today";
    }
}
