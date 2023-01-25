package de.frauas.userauth.service;

import de.frauas.userauth.dto.ArticleDto;
import de.frauas.userauth.entity.Article;
import de.frauas.userauth.entity.Comment;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.exceptions.ArticleNotFoundException;
import de.frauas.userauth.exceptions.CommentNotFoundException;
import de.frauas.userauth.exceptions.UnauthorizedException;
import de.frauas.userauth.repository.ArticleRepository;
import de.frauas.userauth.repository.CommentRepository;
import de.frauas.userauth.util.JwtTokenUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    public Optional<Article> findArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    public void createArticle(ArticleDto articleDto, String header) {
        User author = userService.findUserByUserName(jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.getTokenFromAuthorizationHeader(header)));
        Article article = Article.builder()
                .headline(articleDto.getHeadline())
                .content(articleDto.getContent())
                .author(author)
                .comments(Collections.emptyList())
                .build();

        articleRepository.save(article);
    }

    public void deleteArticle(Long id, String header) {
        String userName = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.getTokenFromAuthorizationHeader(header));
        Article existingArticle = findArticleById(id).orElseThrow(ArticleNotFoundException::new);

        if (!existingArticle.getAuthor().getUsername().equals(userName)) {
            throw new UnauthorizedException();
        }

        articleRepository.deleteById(id);
    }

    public void updateArticle(Long id, ArticleDto articleDto, String header) {
        String userName = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.getTokenFromAuthorizationHeader(header));
        Article existingArticle = findArticleById(id).orElseThrow(ArticleNotFoundException::new);

        if (!existingArticle.getAuthor().getUsername().equals(userName)) {
            throw new UnauthorizedException();
        }

        existingArticle.setHeadline(articleDto.getHeadline());
        existingArticle.setContent(articleDto.getContent());
        articleRepository.save(existingArticle);
    }

    public void addComment(Long articleId, String comment, String header) {
        User author = userService.findUserByUserName(jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.getTokenFromAuthorizationHeader(header)));
        Article existingArticle = findArticleById(articleId).orElseThrow(ArticleNotFoundException::new);

        commentRepository.save(Comment.builder().content(comment).author(author).article(existingArticle).build());
    }

    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public void deleteComment(Long id, String header) {
        String userName = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.getTokenFromAuthorizationHeader(header));
        Comment existingComment = findCommentById(id).orElseThrow(CommentNotFoundException::new);

        if (!existingComment.getAuthor().getUsername().equals(userName)) {
            throw new UnauthorizedException();
        }

        commentRepository.deleteById(id);
    }

    public void updateComment(Long id, String content, String header) {
        String userName = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.getTokenFromAuthorizationHeader(header));
        Comment existingComment = findCommentById(id).orElseThrow(CommentNotFoundException::new);

        if (!existingComment.getAuthor().getUsername().equals(userName)) {
            throw new UnauthorizedException();
        }

        existingComment.setContent(content);

        commentRepository.save(existingComment);
    }
}
