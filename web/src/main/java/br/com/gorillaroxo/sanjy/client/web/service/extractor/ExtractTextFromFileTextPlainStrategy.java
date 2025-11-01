package br.com.gorillaroxo.sanjy.client.web.service.extractor;

import br.com.gorillaroxo.sanjy.client.shared.util.LogField;
import br.com.gorillaroxo.sanjy.client.web.exception.FailToExtractTextFromPlainTextFileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArguments;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExtractTextFromFileTextPlainStrategy implements ExtractTextFromFileStrategy {

    @Override
    public String extract(final MultipartFile file) {
        log.info(
            LogField.Placeholders.FOUR.placeholder,
            StructuredArguments.kv(LogField.MSG.label(), "Extracting text String from Plain Text file"),
            StructuredArguments.kv(LogField.DIET_PLAN_FILE_NAME.label(), file.getOriginalFilename()),
            StructuredArguments.kv(LogField.DIET_PLAN_FILE_CONTENT_TYPE.label(), file.getContentType()),
            StructuredArguments.kv(LogField.DIET_PLAN_FILE_SIZE_BYTES.label(), file.getSize()));

        try {
            byte[] bytes = file.getBytes();
            final String text = new String(bytes, StandardCharsets.UTF_8);

            log.info(
                LogField.Placeholders.FOUR.placeholder,
                StructuredArguments.kv(LogField.MSG.label(), "Successfully extract text String from Plain Text file"),
                StructuredArguments.kv(LogField.DIET_PLAN_FILE_NAME.label(), file.getOriginalFilename()),
                StructuredArguments.kv(LogField.DIET_PLAN_FILE_CONTENT_TYPE.label(), file.getContentType()),
                StructuredArguments.kv(LogField.DIET_PLAN_FILE_SIZE_BYTES.label(), file.getSize()));

            return text;
        } catch (final IOException e) {
            log.warn(
                LogField.Placeholders.FIVE.placeholder,
                StructuredArguments.kv(LogField.MSG.label(), "Fail to extract text from Plain Text file"),
                StructuredArguments.kv(LogField.DIET_PLAN_FILE_NAME.label(), file.getOriginalFilename()),
                StructuredArguments.kv(LogField.DIET_PLAN_FILE_CONTENT_TYPE.label(), file.getContentType()),
                StructuredArguments.kv(LogField.DIET_PLAN_FILE_SIZE_BYTES.label(), file.getSize()),
                StructuredArguments.kv(LogField.EXCEPTION_MESSAGE.label(), e.getMessage()),
                e);

            throw new FailToExtractTextFromPlainTextFileException(e);
        }
    }

    @Override
    public boolean accept(final MultipartFile file) {
        return MediaType.TEXT_MARKDOWN_VALUE.equals(file.getContentType()) || MediaType.TEXT_PLAIN_VALUE.equals(file.getContentType());
    }

}
