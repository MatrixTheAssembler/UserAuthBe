package de.frauas.userauth.service;

import de.frauas.userauth.dto.ArticleDto;
import de.frauas.userauth.entity.Article;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.exceptions.ArticleNotFoundException;
import de.frauas.userauth.repository.ArticleRepository;
import de.frauas.userauth.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final SecurityUtil securityUtil;

    public ArticleService(ArticleRepository articleRepository, SecurityUtil securityUtil) {
        this.articleRepository = articleRepository;
        this.securityUtil = securityUtil;
    }

    public Optional<Article> findArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    public void createArticle(ArticleDto articleDto, User author) {
        Article article = Article.builder()
                .headline(articleDto.getHeadline())
                .content(articleDto.getContent())
                .author(author)
                .comments(Collections.emptyList())
                .build();

        articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public void updateArticle(Long id, String headline, String content) {
        Article existingArticle = findArticleById(id).orElseThrow(ArticleNotFoundException::new);
        existingArticle.setHeadline(headline);
        existingArticle.setContent(content);
        articleRepository.save(existingArticle);

    }
}
