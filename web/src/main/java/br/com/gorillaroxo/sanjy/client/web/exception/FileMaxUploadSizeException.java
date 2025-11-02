package br.com.gorillaroxo.sanjy.client.web.exception;

import br.com.gorillaroxo.sanjy.client.shared.exception.BusinessException;
import br.com.gorillaroxo.sanjy.client.shared.util.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

@Slf4j
public class FileMaxUploadSizeException extends BusinessException {

    private static final ExceptionCode CODE = ExceptionCode.FILE_MAX_UPLOAD_SIZE;
    private static final HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public FileMaxUploadSizeException(final String customMessage, final Throwable originalCause) {
        super(CODE, STATUS, customMessage, originalCause);
    }

    public FileMaxUploadSizeException() {
        super(CODE, STATUS);
    }

    public FileMaxUploadSizeException(final Throwable originalCause) {
        super(CODE, STATUS, originalCause);
    }

    public FileMaxUploadSizeException(final String customMessage) {
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
