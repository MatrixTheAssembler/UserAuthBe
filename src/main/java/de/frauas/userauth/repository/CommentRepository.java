package de.frauas.userauth.repository;

import de.frauas.userauth.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAll();

    Comment findById(long id);
}
