package de.frauas.userauth.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String headline;

    @Column
    private String content;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    private User author;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void creationDate() {
        this.createdAt = LocalDateTime.now();
    }
}
