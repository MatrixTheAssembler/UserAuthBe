package de.frauas.userauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/article")
public class BlogController {

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

    @DeleteMapping
    public String deleteArticle() {
        // TODO
        return "ToDo";
    }

    @PutMapping
    public String updateArticle() {
        // TODO
        return "ToDo";
    }

    @GetMapping("/comment")
    public String getComments() {
        // TODO
        return "ToDo";
    }

    @PostMapping("/comment")
    public String createComment() {
        // TODO
        return "ToDo";
    }

    @DeleteMapping("/comment")
    public String deleteComment() {
        // TODO
        return "ToDo";
    }

    @PutMapping("/comment")
    public String updateComment() {
        // TODO
        return "ToDo";
    }


}
