package de.frauas.userauth.mapper;

import de.frauas.userauth.dto.ArticleDto;
import de.frauas.userauth.dto.CommentDto;
import de.frauas.userauth.entity.Article;
import de.frauas.userauth.entity.Comment;
import de.frauas.userauth.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleMapper {

    public static Article toArticle(ArticleDto articleDto) {
        return Article.builder()
                .id(articleDto.getId())
                .author(User.builder().username(articleDto.getAuthor()).build())
                .headline(articleDto.getHeadline())
                .content(articleDto.getContent())
                .comments(toCommentList(articleDto.getComments()))
                .createdAt(articleDto.getCreatedAt())
                .build();
    }

    public static List<Article> toArticleList(List<ArticleDto> articleDtos) {
        return articleDtos.stream()
                .map(articleDto -> toArticle(articleDto))
                .collect(Collectors.toList());
    }

    public static ArticleDto toArticleDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .author(article.getAuthor().getUsername())
                .headline(article.getHeadline())
                .content(article.getContent())
                .comments(toCommentDtoList(article.getComments()))
                .createdAt(article.getCreatedAt())
                .build();
    }

    public static List<ArticleDto> toArticleDtoList(List<Article> articles) {
        return articles.stream()
                .map(article -> toArticleDto(article))
                .collect(Collectors.toList());
    }

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
                .map(commentDto -> toComment(commentDto))
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
                .map(comment -> toCommentDto(comment))
                .collect(Collectors.toList());
    }

}
