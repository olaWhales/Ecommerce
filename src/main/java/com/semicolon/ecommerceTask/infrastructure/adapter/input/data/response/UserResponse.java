package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String name ;
    private String email ;
    private String keycloakId ;
    private String message ;
}
