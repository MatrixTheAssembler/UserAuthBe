package de.frauas.userauth.exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super("No Comment with his ID found");
    }
}
