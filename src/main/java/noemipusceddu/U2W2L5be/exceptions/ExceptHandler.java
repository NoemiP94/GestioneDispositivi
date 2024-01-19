package noemipusceddu.U2W2L5be.exceptions;

import noemipusceddu.U2W2L5be.payloads.ExceptionsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // --> 404
    public ExceptionsDTO notFoundHandle(NotFoundException e){
        return new ExceptionsDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // --> 400
    public ExceptionsDTO badRequestHandle(BadRequestException e){
        return new ExceptionsDTO(e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // --> 500
    public ExceptionsDTO genericExHandle(Exception e){
        e.printStackTrace();
        return new ExceptionsDTO("Errore nel server! Contattare il backend!", LocalDateTime.now());
    }


}
