package de.frauas.userauth.controller;

import de.frauas.userauth.enums.RoleType;
import de.frauas.userauth.service.CommentService;
import de.frauas.userauth.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    private final JwtTokenUtil jwtTokenUtil;


    @PostMapping("/{articleId}")
    public void addComment(@PathVariable Long articleId, @RequestBody String comment,
                           @RequestHeader("Authorization") String header) {
        commentService.addComment(articleId, comment, header);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, @RequestHeader("Authorization") String header) {
        List<RoleType> roles = jwtTokenUtil.getRolesFromHeader(header);
        commentService.deleteComment(id, header, roles);
    }

    @PutMapping("/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody String content,
                              @RequestHeader("Authorization") String header) {
        commentService.updateComment(id, content, header);
    }
}
