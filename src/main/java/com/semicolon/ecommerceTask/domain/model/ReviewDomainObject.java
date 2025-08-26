package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.UserEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDomainObject {
    private UUID id;
    private UserEntity user;
    private ManageProductDomainObject productEntity;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;


}
