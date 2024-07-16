package site.hoyeonjigi.common;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.hoyeonjigi.common.exception.DuplicateResourceException;
import site.hoyeonjigi.common.exception.IncorretSortTypeException;
import site.hoyeonjigi.common.exception.JwtRuntimeException;

import java.util.NoSuchElementException;


@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> notFoundErrorHandler (UsernameNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, DuplicateResourceException.class})
    public ResponseEntity<String> conflictErrorHandler (Exception e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> forbiddenErrorHandler (BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler(IncorretSortTypeException.class)
    public ResponseEntity<String> badRequestErrorHandler (IncorretSortTypeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validErrorHandler (MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(JwtRuntimeException.class)
    public ResponseEntity<String> jwtErrorHandler (JwtRuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> NoSuchElementExceptionHandler (NoSuchElementException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
