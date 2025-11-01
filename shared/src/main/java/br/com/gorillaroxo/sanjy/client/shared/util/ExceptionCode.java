package br.com.gorillaroxo.sanjy.client.shared.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    UNEXPECTED_ERROR("001", "An unexpected internal error occurred."),
    UNHANDLED_CLIENT_HTTP("002", "A service integration error has occurred"),
    DIET_PLAN_EXTRACTOR_STRATEGY_NOT_FOUND("003", "Unable to process this file format. Please try a different file."),
    FAIL_TO_EXTRACT_TEXT_FROM_PDF_FILE("004", "There was a problem reading your PDF file. Please check the file and try again."),
    FAIL_TO_EXTRACT_TEXT_FROM_PLAIN_TEXT_FILE("005", "There was a problem reading your Plain Text file. Please check the file and try again.");

    /**
     * Error code shown to the user
     */
    private final String userCode;

    /**
     * Error message shown to the user
     */
    private final String userMessage;
}
