package ua.teamchallenge.survivalstore.exceptionHandler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String parameterName = ex.getName();
        String parameterType = Optional.ofNullable(ex.getRequiredType())
                .map(Class::getSimpleName)
                .orElse("Unknown");
        Map<String, String> errors = Map.of(parameterName, String.format("Invalid value for parameter. Expected type: %s", parameterType));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .collect(Collectors.toMap(error -> ((FieldError) error).getField(), error -> error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HandlerMethodValidationException.class})
    public ResponseEntity<?> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getAllErrors().forEach(error ->
                putErrorInMap(errors, (DefaultMessageSourceResolvable) error));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private void putErrorInMap(Map<String, String> errors, DefaultMessageSourceResolvable error) {
        Object[] c = error.getArguments();
        DefaultMessageSourceResolvable d = (DefaultMessageSourceResolvable) c[0];
        String field = d.getDefaultMessage();
        String message = error.getDefaultMessage();
        errors.put(field, message);
    }
}
