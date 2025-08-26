package com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.UserEntity;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface UserPersistenceMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesFromDomain")
    UserEntity toEntity(UserDomainObject domainObject);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserDomainObject toDomain(UserEntity entity);

    @Named("mapRolesFromDomain")
    default List<UserRole> mapRolesFromDomain(List<UserRole> roles) {
        return roles != null ? roles : List.of();
    }
}
