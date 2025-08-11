package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUpdateDto {
    private String email;
    private String firstName;
    private String lastName;
}

