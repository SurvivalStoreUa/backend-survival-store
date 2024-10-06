package ua.teamchallenge.survivalstore.exceptionHandler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException (MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .collect(Collectors.toMap(error -> ((FieldError) error).getField(), error -> error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({HandlerMethodValidationException.class})
    public ResponseEntity<?> handleHandlerMethodValidationException (HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getAllErrors().forEach((error) ->
                putErrorInMap(errors, (DefaultMessageSourceResolvable) error));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    private void putErrorInMap(Map<String, String> errors, DefaultMessageSourceResolvable error){
        Object[] c = error.getArguments();
        DefaultMessageSourceResolvable d = (DefaultMessageSourceResolvable) c[0];
        String field = d.getDefaultMessage();
        String message = error.getDefaultMessage();
        errors.put(field, message);
    }
}
