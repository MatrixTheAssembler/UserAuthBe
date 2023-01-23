package de.frauas.userauth.exceptions;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException() {
        super("No Article with his ID found");
    }
}
