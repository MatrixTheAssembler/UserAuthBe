package de.frauas.userauth.service;


import de.frauas.userauth.entity.Article;
import de.frauas.userauth.entity.Comment;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.enums.RoleType;
import de.frauas.userauth.exceptions.ArticleNotFoundException;
import de.frauas.userauth.exceptions.CommentNotFoundException;
import de.frauas.userauth.exceptions.UnauthorizedException;
import de.frauas.userauth.repository.ArticleRepository;
import de.frauas.userauth.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final ArticleRepository articleRepository;

    private final UserService userService;

    public Comment addComment(Long articleId, String comment, String username) {
        User author = userService.findUserByUserName(username, false);
        Article existingArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        return commentRepository.save(Comment.builder()
                .content(comment)
                .author(author)
                .article(existingArticle)
                .build());
    }

    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public void deleteComment(Long articleId, Long commentId, String username, List<RoleType> roles) {
        Article existingArticle =
                articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        Comment existingComment =
                existingArticle.getComments().stream().filter(c -> Objects.equals(c.getId(), commentId)).findFirst()
                        .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!existingComment.getAuthor().getUsername().equals(username) &&
                !roles.contains(RoleType.MODERATOR)) {
            throw new UnauthorizedException();
        }

        commentRepository.deleteById(commentId);
    }

    public void updateComment(Long articleId, Long commentId, String content, String username) {
        Article existingArticle =
                articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        Comment existingComment =
                existingArticle.getComments().stream().filter(c -> Objects.equals(c.getId(), commentId)).findFirst()
                        .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!existingComment.getAuthor().getUsername().equals(username)) {
            throw new UnauthorizedException();
        }

        existingComment.setContent(content);

        commentRepository.save(existingComment);
    }
}
