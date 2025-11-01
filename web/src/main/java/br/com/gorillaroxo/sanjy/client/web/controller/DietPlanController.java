package br.com.gorillaroxo.sanjy.client.web.controller;

import br.com.gorillaroxo.sanjy.client.shared.client.DietPlanFeignClient;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.DietPlanRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.client.dto.response.DietPlanResponseDTO;
import br.com.gorillaroxo.sanjy.client.shared.util.LogField;
import br.com.gorillaroxo.sanjy.client.web.config.TemplateConstants;
import br.com.gorillaroxo.sanjy.client.web.service.ProcessDietPlanFileService;
import br.com.gorillaroxo.sanjy.client.web.util.LoggingHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/diet-plan")
public class DietPlanController {

    private static final String ATTRIBUTE_DIET_PLAN = "dietPlan";
    private static final String ATTRIBUTE_DIET_PLAN_REQUEST = "dietPlanRequest";

    private final DietPlanFeignClient dietPlanFeignClient;
    private final ProcessDietPlanFileService processDietPlanFileService;

    @GetMapping("/new")
    public String showNewPlanForm(Model model) {
        // Only add empty object if not already present (from flash attributes)
        if (!model.containsAttribute(ATTRIBUTE_DIET_PLAN_REQUEST)) {
            model.addAttribute(ATTRIBUTE_DIET_PLAN_REQUEST, DietPlanRequestDTO.builder().build());
        }

        return LoggingHelper.loggingAndReturnControllerPagePath(TemplateConstants.PageNames.DIET_PLAN_NEW);
    }

    @PostMapping
    public String createPlan(@ModelAttribute DietPlanRequestDTO request) {
        dietPlanFeignClient.newDietPlan(request);

        return LoggingHelper.loggingAndReturnControllerPagePath("redirect:/" + TemplateConstants.PageNames.DIET_PLAN_ACTIVE);
    }

    @GetMapping("/active")
    public String showActivePlan(Model model) {
        DietPlanResponseDTO mockPlan = dietPlanFeignClient.activeDietPlan();
        model.addAttribute(ATTRIBUTE_DIET_PLAN, mockPlan);

        return LoggingHelper.loggingAndReturnControllerPagePath(TemplateConstants.PageNames.DIET_PLAN_ACTIVE);
    }

    @PostMapping("/upload")
    public String uploadAndFillForm(@RequestParam("file") MultipartFile file, Model model, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            log.warn("Empty file uploaded");
            model.addAttribute("error", "Please select a file to upload");
            model.addAttribute(ATTRIBUTE_DIET_PLAN_REQUEST, DietPlanRequestDTO.builder().build());

            return LoggingHelper.loggingAndReturnControllerPagePath(TemplateConstants.PageNames.DIET_PLAN_NEW);
        }

        // Process file and get data
        DietPlanRequestDTO mockData = processDietPlanFileService.process(file);

        // Add to flash attributes to survive redirect
        redirectAttributes.addFlashAttribute(ATTRIBUTE_DIET_PLAN_REQUEST, mockData);

        return LoggingHelper.loggingAndReturnControllerPagePath("redirect:/" + TemplateConstants.PageNames.DIET_PLAN_NEW + "?uploaded=success");
    }

}
