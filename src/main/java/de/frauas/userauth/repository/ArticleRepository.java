package de.frauas.userauth.repository;

import de.frauas.userauth.entity.Article;
import de.frauas.userauth.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    List<Article> findAll();

    Article findById(long id);

    Article findByAuthor(User author);

}
