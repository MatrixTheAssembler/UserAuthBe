package de.frauas.userauth.exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(long id) {
        super("No Comment with ID: " + id + " found");
    }
}
