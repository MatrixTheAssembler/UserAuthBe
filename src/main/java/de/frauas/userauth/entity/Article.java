package de.frauas.userauth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String headline;
    @Column
    private String content;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "article_comments",
            joinColumns = {@JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "COMMENT_ID", referencedColumnName = "ID")})
    private List<Comment> comments = new ArrayList<>();

}
