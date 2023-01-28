package de.frauas.userauth.controller;

import de.frauas.userauth.dto.ArticleDto;
import de.frauas.userauth.enums.RoleType;
import de.frauas.userauth.mapper.ArticleMapper;
import de.frauas.userauth.service.ArticleService;
import de.frauas.userauth.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    private final JwtTokenUtil jwtTokenUtil;


    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok(ArticleMapper.toArticleDto(articleService.findArticleById(id)));
    }

    @GetMapping
    public List<ArticleDto> getAllArticles() {
        return ArticleMapper.toArticleDtoList(articleService.findAllArticles());
    }

    @PostMapping
    public void createArticle(@RequestBody ArticleDto articleDto, @RequestHeader("Authorization") String header) {
        String username = jwtTokenUtil.getUsernameFromHeader(header);
        articleService.createArticle(articleDto, username);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id, @RequestHeader("Authorization") String header) {
        String username = jwtTokenUtil.getUsernameFromHeader(header);
        List<RoleType> roles = jwtTokenUtil.getRolesFromHeader(header);
        articleService.deleteArticle(id, username, roles);
    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable Long id, @RequestBody ArticleDto articleDto, @RequestHeader(
            "Authorization") String header) {
        String username = jwtTokenUtil.getUsernameFromHeader(header);
        articleService.updateArticle(id, articleDto, username);
    }
}
