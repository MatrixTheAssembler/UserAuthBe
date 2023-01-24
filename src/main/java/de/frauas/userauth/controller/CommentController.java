package de.frauas.userauth.controller;

import de.frauas.userauth.service.ArticleService;
import de.frauas.userauth.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final UserService userService;
    private final ArticleService articleService;

    public CommentController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @PostMapping("/{articleId}")
    public void addComment(@PathVariable Long articleId, @RequestBody String comment, @RequestHeader("Authorization") String header) { //TODO: Funktioniert noch nicht richtig
        articleService.addComment(articleId, comment, header);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, @RequestHeader("Authorization") String header) { //TODO: Funktioniert noch nicht
        articleService.deleteComment(id, header);
    }

    @PutMapping("/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody String content, @RequestHeader("Authorization") String header) { //TODO: Funktioniert noch nicht richtig
        articleService.updateComment(id, content, header);
    }
}
