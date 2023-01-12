package de.frauas.userauth.entity;

import de.frauas.userauth.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String salt;

    @ElementCollection
    private List<RoleType> roles = new ArrayList<>();
}
