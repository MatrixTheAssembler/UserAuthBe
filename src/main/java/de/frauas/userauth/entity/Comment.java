package de.frauas.userauth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String content;
    @ManyToOne
    private Article article;
    @ManyToOne
    private User author;
    @Column(nullable = false)
    private Date creationDate;

    @PrePersist
    void creationDate() {
        this.creationDate = new Date();
    }
}
