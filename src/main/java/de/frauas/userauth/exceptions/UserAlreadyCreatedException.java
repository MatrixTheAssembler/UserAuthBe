package de.frauas.userauth.exceptions;

public class UserAlreadyCreatedException extends Exception {
    public UserAlreadyCreatedException(){
        super("There is already an account registered with the same username");
    }
}
