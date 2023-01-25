package de.frauas.userauth.controller;

import de.frauas.userauth.dto.ArticleDto;
import de.frauas.userauth.entity.Article;
import de.frauas.userauth.exceptions.ArticleNotFoundException;
import de.frauas.userauth.mapper.ArticleMapper;
import de.frauas.userauth.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
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
    public void createArticle(@RequestBody ArticleDto articleDto, @RequestHeader("Authorization") String header) {
        articleService.createArticle(articleDto, header);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id, @RequestHeader("Authorization") String header) {
        articleService.deleteArticle(id, header);
    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable Long id, @RequestBody ArticleDto articleDto, @RequestHeader(
            "Authorization") String header) {
        articleService.updateArticle(id, articleDto, header);
    }
}
