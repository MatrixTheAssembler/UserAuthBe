package de.frauas.userauth.controller;

import de.frauas.userauth.dto.ArticleDto;
import de.frauas.userauth.entity.Article;
import de.frauas.userauth.exceptions.ArticleNotFoundException;
import de.frauas.userauth.mapper.ArticleMapper;
import de.frauas.userauth.service.ArticleService;
import de.frauas.userauth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/article")
public class ArticleController {

    private final UserService userService;
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findArticleById(id).orElseThrow(ArticleNotFoundException::new));
    }

    @GetMapping("/all")
    public List<ArticleDto> getAllArticles() {
        return ArticleMapper.toArticleDtoList(articleService.findAllArticles());
    }

    @PostMapping
    public void createArticle(@RequestBody ArticleDto articleDto) {
        articleService.createArticle(articleDto, userService.findUserByUserName(articleDto.getAuthor()));
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id) { //Todo nur Autor
        articleService.deleteArticle(id);
    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable Long id, @RequestBody String headline, @RequestBody String content) { //Todo nur Autor
        articleService.updateArticle(id, headline, content);
    }
}
