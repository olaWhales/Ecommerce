package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponseDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
}