package br.com.lutztechnology.appveterinario.api.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ValidateApiError extends ApiError {

    private List<ApiFieldError> errors;

    public ValidateApiError(
            int code,
            String status,
            LocalDateTime timestamp,
            String message,
            List<ApiFieldError> errors) {
        super(code, status, timestamp, message);
        this.errors = errors;
    }
}
