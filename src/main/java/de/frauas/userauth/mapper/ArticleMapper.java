package de.frauas.userauth.mapper;

import de.frauas.userauth.dto.ArticleDto;
import de.frauas.userauth.entity.Article;
import de.frauas.userauth.entity.User;

import java.util.List;
import java.util.stream.Collectors;

import static de.frauas.userauth.mapper.CommentMapper.toCommentDtoList;
import static de.frauas.userauth.mapper.CommentMapper.toCommentList;

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
                .map(ArticleMapper::toArticle)
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
                .map(ArticleMapper::toArticleDto)
                .collect(Collectors.toList());
    }
}
