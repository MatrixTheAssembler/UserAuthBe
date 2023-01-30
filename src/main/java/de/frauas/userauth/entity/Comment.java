package de.frauas.userauth.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 10000)
    private String content;

    @JsonBackReference
    @ManyToOne
    @NotNull
    private Article article;

    @ManyToOne
    private User author;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void creationDate() {
        this.createdAt = LocalDateTime.now();
    }
}
