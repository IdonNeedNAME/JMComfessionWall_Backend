package com.github.idonneedname.jmcomfessionwall_backend.handler;



import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.result.AjaxResult;
import com.github.idonneedname.jmcomfessionwall_backend.util.HandlerUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(50)
public class CustomExceptionHandler {
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public AjaxResult<Object> handleApiException(ApiException e) {
        HandlerUtils.logException(e);
        return AjaxResult.fail(e.getErrorCode(),e.getMessage());
    }

}