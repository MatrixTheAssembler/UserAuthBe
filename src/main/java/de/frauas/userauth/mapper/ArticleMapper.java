package de.frauas.userauth.mapper;

import de.frauas.userauth.dto.ArticleDto;
import de.frauas.userauth.entity.Article;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleMapper {

    public static Article toArticle(ArticleDto articleDto) { //TODO author und comments?
        return Article.builder()
                .headline(articleDto.getHeadline())
                .content(articleDto.getContent())
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
                .comments(article.getComments())
                .build();
    }

    public static List<ArticleDto> toArticleDtoList(List<Article> articles) {
        return articles.stream()
                .map(article -> toArticleDto(article))
                .collect(Collectors.toList());
    }
}
