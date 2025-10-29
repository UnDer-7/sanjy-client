package br.com.gorillaroxo.sanjy.client.shared.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    UNEXPECTED_ERROR("001", "An unexpected internal error occurred.");

    /**
     * Error code shown to the user
     */
    private final String userCode;

    /**
     * Error message shown to the user
     */
    private final String userMessage;
}
