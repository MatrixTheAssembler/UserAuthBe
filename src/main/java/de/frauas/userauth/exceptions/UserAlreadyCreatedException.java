package de.frauas.userauth.exceptions;

public class UserAlreadyCreatedException extends RuntimeException {
    public UserAlreadyCreatedException(){
        super("There is already an account registered with the same username");
    }
}
