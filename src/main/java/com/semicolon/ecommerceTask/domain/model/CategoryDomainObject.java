package com.semicolon.ecommerceTask.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDomainObject {
    private UUID id;
    private String name;
}
