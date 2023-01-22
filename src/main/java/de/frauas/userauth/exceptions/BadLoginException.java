package de.frauas.userauth.exceptions;

public class BadLoginException extends RuntimeException {
    public BadLoginException(){
        super("Wrong username or password");
    }
}
