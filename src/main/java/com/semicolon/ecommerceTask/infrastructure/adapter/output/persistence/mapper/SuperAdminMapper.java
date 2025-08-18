package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.enumPackage.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import java.util.Collections;

@Mapper(componentModel = "spring", imports = {Collections.class, UserRole.class})
public interface SuperAdminMapper {

    @Mapping(target = "roles", expression = "java(Collections.singletonList(UserRole.SUPERADMIN))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password")
    UserDomainObject toDomain(String firstName, String lastName, String email, String password);

    @Mapping(target = "password", source = "rawPassword")
    @Mapping(target = "id", source = "keycloakId")
    UserDomainObject updateWithKeycloak(UserDomainObject domain, String rawPassword, String keycloakId);

    @ObjectFactory
    default UserDomainObject cloneWithUpdate(UserDomainObject domain) {
        return domain.toBuilder().build();
    }
}
