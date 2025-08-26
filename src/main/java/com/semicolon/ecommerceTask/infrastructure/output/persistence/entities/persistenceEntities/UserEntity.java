package com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.UserRole;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private List<UserRole> roles;
}
