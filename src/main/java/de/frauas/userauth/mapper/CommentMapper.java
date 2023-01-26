package de.frauas.userauth.mapper;

import de.frauas.userauth.dto.CommentDto;
import de.frauas.userauth.entity.Comment;
import de.frauas.userauth.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {

    public static Comment toComment(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .author(User.builder().username(commentDto.getAuthor()).build())
                .content(commentDto.getContent())
                .createdAt(commentDto.getCreatedAt())
                .build();
    }

    public static List<Comment> toCommentList(List<CommentDto> commentDtos) {
        return commentDtos.stream()
                .map(CommentMapper::toComment)
                .collect(Collectors.toList());
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .author(comment.getAuthor().getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static List<CommentDto> toCommentDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
