package de.frauas.userauth.service;

import de.frauas.userauth.dto.ArticleDto;
import de.frauas.userauth.entity.Article;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.enums.RoleType;
import de.frauas.userauth.exceptions.ArticleNotFoundException;
import de.frauas.userauth.exceptions.UnauthorizedException;
import de.frauas.userauth.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final UserService userService;


    public Article findArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    public void createArticle(ArticleDto articleDto, String username) {
        User author = userService.findUserByUserName(username, false);
        Article article = Article.builder()
                .headline(articleDto.getHeadline())
                .content(articleDto.getContent())
                .author(author)
                .comments(Collections.emptyList())
                .build();

        articleRepository.save(article);
    }

    public void deleteArticle(Long id, String username, List<RoleType> roles) {
        Article existingArticle = findArticleById(id);

        if (!existingArticle.getAuthor().getUsername().equals(username) &&
                !roles.contains(RoleType.MODERATOR)) {
            throw new UnauthorizedException();
        }

        articleRepository.deleteById(id);
    }
}
