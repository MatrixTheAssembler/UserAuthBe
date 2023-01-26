package de.frauas.userauth.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ArticleDto {
    private Long id;
    private String headline;
    private String content;
    private List<CommentDto> comments;
    private String author;
    private LocalDateTime createdAt;
}
