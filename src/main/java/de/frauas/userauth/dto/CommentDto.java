package de.frauas.userauth.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}
