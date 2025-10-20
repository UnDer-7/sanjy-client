package br.com.gorillaroxo.sanjy.client.web.service;

import br.com.gorillaroxo.sanjy.client.web.service.extractor.ExtractTextFromFileStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessDietPlanFileService {

    private final Set<ExtractTextFromFileStrategy> extractors;

    public void process(final MultipartFile file) {
        final String dietPlanTxt = extractors.stream()
            .filter(extractor -> extractor.accept(file))
            .findFirst()
            // todo: Jogar exception
            .orElseThrow(() -> new RuntimeException("Unsupported file type"))
            .extract(file);

        log.info("Diet plan text: {}", dietPlanTxt);
    }
}
