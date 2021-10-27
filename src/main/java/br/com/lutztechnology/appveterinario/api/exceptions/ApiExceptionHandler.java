package br.com.lutztechnology.appveterinario.api.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiError apiError = new ApiError(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                LocalDateTime.now(),
                exception.getLocalizedMessage()
        );

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<ApiFieldError> apiFieldErrors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            ApiFieldError apiFieldError = new ApiFieldError(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
            apiFieldErrors.add(apiFieldError);
        });

        ValidateApiError validateApiError = new ValidateApiError(
                status.value(),
                status.getReasonPhrase(),
                LocalDateTime.now(),
                "Houveram erros de validação",
                apiFieldErrors
        );

        return new ResponseEntity<>(validateApiError, status);
    }
}
