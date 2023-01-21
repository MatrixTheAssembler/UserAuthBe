package de.frauas.userauth.exceptions;

public class BadLoginException extends Exception {
    public BadLoginException(){
        super("Wrong username or password");
    }
}
