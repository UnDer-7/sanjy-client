package br.com.gorillaroxo.sanjy.client.web.controller;

import br.com.gorillaroxo.sanjy.client.shared.client.DietPlanFeignClient;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.DietPlanRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.MealTypeRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.StandardOptionRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.DietPlanResponseDTO;
import br.com.gorillaroxo.sanjy.client.web.service.ProcessDietPlanFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/diet-plan")
public class DietPlanController {

    private final DietPlanFeignClient dietPlanFeignClient;
    private final ProcessDietPlanFileService processDietPlanFileService;

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

    @PostMapping("/upload")
    public String uploadAndFillForm(@RequestParam("file") MultipartFile file, Model model) {
        log.info("File uploaded: {} (size: {} bytes)", file.getOriginalFilename(), file.getSize());

        // Validate file
        if (file.isEmpty()) {
            log.warn("Empty file uploaded");
            model.addAttribute("error", "Please select a file to upload");
            model.addAttribute("dietPlanRequest", DietPlanRequestDTO.builder().build());
            return "diet-plan/new";
        }

        // Create mock data
        DietPlanRequestDTO mockData = processDietPlanFileService.process(file);

        // Add to model to fill form
        model.addAttribute("dietPlanRequest", mockData);

        return "diet-plan/new";
    }

    private DietPlanRequestDTO createMockDietPlan() {
        // Create breakfast options
        List<StandardOptionRequestDTO> breakfastOptions = new ArrayList<>();
        breakfastOptions.add(new StandardOptionRequestDTO(1, "Pão francês sem miolo -- 45g | Ovos mexidos -- 3 ovos"));
        breakfastOptions.add(new StandardOptionRequestDTO(2, "Ovos mexidos -- 2 ovos | Pão integral -- 25g"));

        // Create lunch options
        List<StandardOptionRequestDTO> lunchOptions = new ArrayList<>();
        lunchOptions.add(new StandardOptionRequestDTO(1, "Arroz -- 100g | Frango grelhado -- 150g | Salada -- 100g"));
        lunchOptions.add(new StandardOptionRequestDTO(2, "Macarrão integral -- 90g | Carne moída -- 120g | Brócolis -- 80g"));

        // Create dinner options
        List<StandardOptionRequestDTO> dinnerOptions = new ArrayList<>();
        dinnerOptions.add(new StandardOptionRequestDTO(1, "Batata doce -- 150g | Peixe grelhado -- 130g | Legumes -- 100g"));

        // Create meal types
        List<MealTypeRequestDTO> mealTypes = new ArrayList<>();
        mealTypes.add(new MealTypeRequestDTO(
                "Breakfast",
                LocalTime.of(9, 30),
                breakfastOptions
        ));
        mealTypes.add(new MealTypeRequestDTO(
                "Lunch",
                LocalTime.of(13, 0),
                lunchOptions
        ));
        mealTypes.add(new MealTypeRequestDTO(
                "Dinner",
                LocalTime.of(21, 0),
                dinnerOptions
        ));

        // Create complete diet plan
        return DietPlanRequestDTO.builder()
                .name("Plan #02 - Cutting (Imported)")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(2))
                .dailyCalories(2266)
                .dailyProteinInG(186)
                .dailyCarbsInG(288)
                .dailyFatInG(30)
                .goal("Fat reduction with muscle preservation")
                .nutritionistNotes("Imported from file - mock data used for testing")
                .mealTypes(mealTypes)
                .build();
    }
}
