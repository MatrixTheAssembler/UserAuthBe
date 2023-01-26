package de.frauas.userauth.exceptions;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(long id) {
        super("No Article with ID: " + id + " found");
    }
}
