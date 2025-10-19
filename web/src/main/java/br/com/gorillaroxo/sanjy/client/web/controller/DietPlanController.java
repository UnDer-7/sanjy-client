package br.com.gorillaroxo.sanjy.client.web.controller;

import br.com.gorillaroxo.sanjy.client.shared.client.DietPlanFeignClient;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.DietPlanRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.DietPlanResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/diet-plan")
public class DietPlanController {

    private final DietPlanFeignClient dietPlanFeignClient;

    @GetMapping("/new")
    public String showNewPlanForm(Model model) {
        model.addAttribute("dietPlanRequest", DietPlanRequestDTO.builder().build());
        return "diet-plan/new";
    }

    @PostMapping
    public String createPlan(@ModelAttribute DietPlanRequestDTO request) {
        dietPlanFeignClient.newDietPlan(request);
        return "redirect:/diet-plan/active";
    }

    @GetMapping("/active")
    public String showActivePlan(Model model) {
        DietPlanResponseDTO mockPlan = dietPlanFeignClient.activeDietPlan();
        model.addAttribute("dietPlan", mockPlan);
        return "diet-plan/active";
    }
}
