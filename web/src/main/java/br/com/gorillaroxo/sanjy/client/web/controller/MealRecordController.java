package br.com.gorillaroxo.sanjy.client.web.controller;

import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.MealRecordRequest;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.MealRecordResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/meal")
public class MealRecordController {

    @GetMapping("/new")
    public String showNewMealForm(Model model) {
        // Mock: Adicionar objeto vazio para binding
        model.addAttribute("mealRecordRequest", new MealRecordRequest());

        // Mock: Em produção, buscar meal types do plano ativo
        model.addAttribute("mealTypes", List.of());

        return "meal/new";
    }

    @PostMapping
    public String recordMeal(@ModelAttribute MealRecordRequest request) {
        // Mock: Não implementar chamada ao backend
        // Em produção, aqui seria feita a chamada via Feign Client
        return "redirect:/meal/today";
    }

    @GetMapping("/today")
    public String showTodayMeals(Model model) {
        // Mock: Retornar lista vazia
        List<MealRecordResponseDTO> todayMeals = List.of(); // Em produção, buscar via Feign Client

        model.addAttribute("todayMeals", todayMeals);
        model.addAttribute("plannedCount", 0);
        model.addAttribute("freeCount", 0);

        return "meal/today";
    }
}
