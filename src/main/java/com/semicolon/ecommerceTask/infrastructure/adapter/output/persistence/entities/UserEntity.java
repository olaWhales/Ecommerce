package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.enumPackage.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private List<UserRole> roles;
}
