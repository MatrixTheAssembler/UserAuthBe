package de.frauas.userauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/{id}")
    public String getArticle(@PathVariable String id) {
        // TODO
        return "ToDo";
    }

    @GetMapping("/all")
    public String getAllArticles() {
        // TODO
        return "ToDo";
    }

    @PostMapping
    public String createArticle() {
        // TODO
        return "ToDo";
    }

    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable String id) {
        // TODO
        return "ToDo";
    }

    @PutMapping("/{id}")
    public String updateArticle(@PathVariable String id) {
        // TODO
        return "ToDo";
    }
}
