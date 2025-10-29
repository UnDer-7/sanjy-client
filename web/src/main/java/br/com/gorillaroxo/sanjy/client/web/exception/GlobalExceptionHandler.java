package br.com.gorillaroxo.sanjy.client.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Global exception handler for the web application.
 * Catches all exceptions and returns a user-friendly error page.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all exceptions thrown in the application.
     * Logs the full error details but returns a generic message to the user.
     *
     * @param ex the exception that was thrown
     * @return ModelAndView with error page and generic error message
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(Exception ex) {
        // Log the full exception for debugging (server-side only)
        log.error("An error occurred in the application", ex);

        // Create model and view for error page
        ModelAndView modelAndView = new ModelAndView("error");
        
        // Only expose generic message to the client
        modelAndView.addObject("errorMessage", "Something went wrong.");
        modelAndView.addObject("errorTimestamp", System.currentTimeMillis());
        
        return modelAndView;
    }
}
