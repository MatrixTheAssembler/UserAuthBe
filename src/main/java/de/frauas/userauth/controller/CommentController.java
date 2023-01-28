package de.frauas.userauth.controller;

import de.frauas.userauth.dto.CommentDto;
import de.frauas.userauth.entity.Comment;
import de.frauas.userauth.enums.RoleType;
import de.frauas.userauth.mapper.CommentMapper;
import de.frauas.userauth.service.CommentService;
import de.frauas.userauth.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommentDto> addComment(@PathVariable Long articleId, @RequestBody String comment,
                                                 @RequestHeader("Authorization") String header) {
        Comment newComment = commentService.addComment(articleId, comment, header);
        return new ResponseEntity<>(CommentMapper.toCommentDto(newComment), HttpStatus.CREATED);
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
