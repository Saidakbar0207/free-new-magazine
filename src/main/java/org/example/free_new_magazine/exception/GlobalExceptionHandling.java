package org.example.free_new_magazine.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandling {

    record ApiError(String msg, int status, String path, Instant timestamp){}

    private ApiError err(HttpStatus status, String msg, HttpServletRequest req){
        return new ApiError(msg,status.value(),req.getRequestURI(), Instant.now());
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> noFound(NotFoundException e, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err(HttpStatus.NOT_FOUND,e.getMessage(),req));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> unauthorized(UnauthorizedException e, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err(HttpStatus.UNAUTHORIZED,e.getMessage(),req));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> forbidden(ForbiddenException e, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err(HttpStatus.FORBIDDEN,e.getMessage(),req));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> conflict(ConflictException e, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err(HttpStatus.CONFLICT,e.getMessage(),req));
    }




    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validation(MethodArgumentNotValidException e,HttpServletRequest req) {
      Map<String, String> fieldErrors = e.getBindingResult().getFieldErrors().stream()
              .collect(Collectors.toMap(
                      fe -> fe.getField(),
                      fe -> fe.getDefaultMessage(),
                      (a,b) -> a
                      ));
        return ResponseEntity.badRequest().body(err(HttpStatus.BAD_REQUEST,fieldErrors.toString(),req));
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> other(Exception e,HttpServletRequest req) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(err(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),req));
    }

}
