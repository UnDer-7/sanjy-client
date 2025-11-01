package br.com.gorillaroxo.sanjy.client.web.service.converter;

import br.com.gorillaroxo.sanjy.client.shared.client.dto.request.DietPlanRequestDTO;
import br.com.gorillaroxo.sanjy.client.shared.util.LogField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DietPlanConverter {

    @Qualifier("dietPlanConverterChatClient")
    private final ChatClient chatClient;

    public Optional<DietPlanRequestDTO> convert(final String inputMessage) {
        final Class<DietPlanRequestDTO> type = DietPlanRequestDTO.class;
        try {
            final DietPlanRequestDTO entity = chatClient
                .prompt()
                .user(inputMessage)
                .call()
                .entity(type);

            if (entity == null) {
                log.warn(
                    LogField.Placeholders.TWO.placeholder,
                    StructuredArguments.kv(LogField.MSG.label(), "Could not convert inputMessage into %s, A.I. model return null".formatted(type.getSimpleName())),
                    StructuredArguments.kv(LogField.INPUT_MESSAGE.label(), inputMessage));

                return Optional.empty();
            }

            return Optional.of(entity);

        } catch (final Exception e) {
            log.warn(
                LogField.Placeholders.TWO.placeholder,
                StructuredArguments.kv(LogField.MSG.label(), "An error occurred during converting inputMessage into %s".formatted(type.getSimpleName())),
                StructuredArguments.kv(LogField.INPUT_MESSAGE.label(), inputMessage),
                e);

            return Optional.empty();
        }
    }
}
