package de.frauas.userauth.dto;

import de.frauas.userauth.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ArticleDto {
    private Long id;
    private String headline;
    private String content;
    private List<Comment> comments; //TODO CommentDTO?
    private String author;
    private LocalDate creationDate;
}
