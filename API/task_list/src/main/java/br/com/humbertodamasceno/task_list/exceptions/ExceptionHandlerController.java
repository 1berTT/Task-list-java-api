package br.com.humbertodamasceno.task_list.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.List;
import java.util.ArrayList;

@ControllerAdvice
public class ExceptionHandlerController {

    private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        List<ErrorMessageDTO> errors = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(message, error.getField());
            errors.add(errorMessageDTO);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageDTO> handlerHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        String message = e.getMessage();

        if (message != null && (message.contains("TaskStatus") || message.contains("TaskPriority") ||
                message.contains("Cannot deserialize value") || message.contains("not one of the values accepted"))) {
            String field = extractFieldFromMessage(message);
            String errorMessage = "Invalid value for " + field + ". Accepted values: " + getAcceptedValues(field);
            ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(errorMessage, field);
            return new ResponseEntity<>(errorMessageDTO, HttpStatus.BAD_REQUEST);
        }

        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO("Invalid request body format", null);
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.BAD_REQUEST);
    }

    private String extractFieldFromMessage(String message) {
        if (message.contains("TaskStatus")) {
            return "status";
        } else if (message.contains("TaskPriority")) {
            return "priority";
        }
        return "unknown";
    }

    private String getAcceptedValues(String field) {
        if ("status".equals(field)) {
            return "PENDING, IN_PROGRESS, DONE";
        } else if ("priority".equals(field)) {
            return "LOW, MEDIUM, HIGH";
        }
        return "";
    }

}
