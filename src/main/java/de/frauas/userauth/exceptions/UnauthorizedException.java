package de.frauas.userauth.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException() {
        super("Unauthorised");
    }
}
