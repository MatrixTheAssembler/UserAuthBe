package de.frauas.userauth.controller;

import de.frauas.userauth.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/comment")
public class CommentController {

    private final ArticleService articleService;

    public CommentController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/{articleId}")
    public void addComment(@PathVariable Long articleId, @RequestBody String comment,
                           @RequestHeader("Authorization") String header) {
        articleService.addComment(articleId, comment, header);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, @RequestHeader("Authorization") String header) {
        articleService.deleteComment(id, header);
    }

    @PutMapping("/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody String content,
                              @RequestHeader("Authorization") String header) {
        articleService.updateComment(id, content, header);
    }
}
