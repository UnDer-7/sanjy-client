package br.com.gorillaroxo.sanjy.client.web.service.converter;

import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.DietPlanRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DietPlanConverter {

    @Qualifier("dietPlanConverterChatClient")
    private final ChatClient chatClient;

    public DietPlanRequestDTO convert(final String inputMessage) {
        return chatClient
            .prompt()
            .user(inputMessage)
            .call()
            .entity(DietPlanRequestDTO.class);
    }
}
