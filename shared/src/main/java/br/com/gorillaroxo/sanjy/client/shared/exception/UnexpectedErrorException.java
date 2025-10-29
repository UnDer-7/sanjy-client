package br.com.gorillaroxo.sanjy.client.shared.exception;

import br.com.gorillaroxo.sanjy.client.shared.util.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

@Slf4j
public class UnexpectedErrorException extends BusinessException {

    private static final ExceptionCode CODE = ExceptionCode.UNEXPECTED_ERROR;
    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public UnexpectedErrorException(final String customMessage, final Throwable originalCause) {
        super(CODE, STATUS, customMessage, originalCause);
    }

    public UnexpectedErrorException() {
        super(CODE, STATUS);
    }

    public UnexpectedErrorException(final Throwable originalCause) {
        super(CODE, STATUS, originalCause);
    }

    public UnexpectedErrorException(final String customMessage) {
        super(CODE, STATUS, customMessage);
    }

    @Override
    protected LogLevel getLogLevel() {
        return LogLevel.ERROR;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

}
