package de.frauas.userauth.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content;

    @JsonBackReference
    @ManyToOne
    @NotNull
    private Article article;

    @ManyToOne
    private User author;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @PrePersist
    void creationDate() {
        this.creationDate = LocalDateTime.now();
    }
}
