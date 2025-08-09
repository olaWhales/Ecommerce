package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.CreateSuperAdminRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.CreateSuperAdminResponse;

public interface CreateSuperAdminUseCase {
    void createSuperAdmin();
}

