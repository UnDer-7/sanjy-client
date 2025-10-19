package br.com.gorillaroxo.sanjy.client.web.controller;

import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.DietPlanRequest;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.DietPlanResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/diet-plan")
public class DietPlanController {

    @GetMapping("/new")
    public String showNewPlanForm(Model model) {
        // Mock: Adicionar objeto vazio ao model para binding do form
        model.addAttribute("dietPlanRequest", new DietPlanRequest());
        return "diet-plan/new";
    }

    @PostMapping
    public String createPlan(@ModelAttribute DietPlanRequest request) {
        // Mock: Não implementar chamada ao backend
        // Em produção, aqui seria feita a chamada via Feign Client
        return "redirect:/diet-plan/active";
    }

    @GetMapping("/active")
    public String showActivePlan(Model model) {
        // Mock: Retornar dados mockados para exibição
        DietPlanResponseDTO mockPlan = null; // Em produção, buscar via Feign Client
        model.addAttribute("dietPlan", mockPlan);
        return "diet-plan/active";
    }
}
