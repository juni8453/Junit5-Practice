package site.metacoding.junitproject.web.dto.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import site.metacoding.junitproject.web.dto.response.CMResponseDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> apiException(RuntimeException e) {

        CMResponseDto<?> cmResponseDto = CMResponseDto.builder()
            .code(-1)
            .message(e.getMessage())
            .build();

        return new ResponseEntity<>(cmResponseDto, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        CMResponseDto<?> cmResponseDto = CMResponseDto.builder()
            .code(-1)
            .message("Validation Exception 발생")
            .build();

        List<String> errorFields = ex.getFieldErrors().stream()
            .map(FieldError::getField)
            .collect(Collectors.toList());

        List<String> errorMessages = ex.getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());

        Map<String, Object> body = new HashMap<>();
        body.put("statusCode", HttpStatus.BAD_REQUEST);
        body.put("timestamp", LocalDateTime.now());
        body.put("errorFields", errorFields);
        body.put("errorMessage", errorMessages);
        body.put("cmResponseDto", cmResponseDto);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}