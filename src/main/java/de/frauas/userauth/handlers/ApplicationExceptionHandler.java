package de.frauas.userauth.handlers;

import de.frauas.userauth.exceptions.UserAlreadyCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "There is already an account registered with the same username"
    )
    @ExceptionHandler(UserAlreadyCreatedException.class)
    public void handleException(UserAlreadyCreatedException e){}
}
