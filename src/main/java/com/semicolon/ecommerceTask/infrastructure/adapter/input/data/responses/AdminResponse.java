package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
