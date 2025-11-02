package br.com.gorillaroxo.sanjy.client.shared.client.config.handler;

import br.com.gorillaroxo.sanjy.client.shared.exception.BusinessException;
import feign.Response;

public interface FeignErrorHandler {

    BusinessException handle(Response response, String responseBodyJson);

    boolean canHandle(Response response, String responseBodyJson);

}
