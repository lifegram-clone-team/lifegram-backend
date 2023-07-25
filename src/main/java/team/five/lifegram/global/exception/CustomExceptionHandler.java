package team.five.lifegram.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//TODO 어노테이션은 위에서 아래로 짧은 것 부터 작성하기
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity DataValidException(MethodArgumentNotValidException ex){
        log.error("Data Not Valid", ex);
        return ResponseEntity.status(400).build();
    }
}
