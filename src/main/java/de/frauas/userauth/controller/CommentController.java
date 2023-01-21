package de.frauas.userauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("comment")
public class CommentController {

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable String id) {
        // TODO
        return "ToDo";
    }

    @PutMapping("/{id}")
    public String updateComment(@PathVariable String id) {
        // TODO
        return "ToDo";
    }
}
