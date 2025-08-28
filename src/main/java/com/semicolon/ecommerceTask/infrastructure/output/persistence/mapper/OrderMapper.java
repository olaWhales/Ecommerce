package com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper;


import com.semicolon.ecommerceTask.domain.model.OrderDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.OrderEntity;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "userEntity", target = "user")
    OrderDomainObject toDomain(OrderEntity entity);

    @Mapping(source = "user", target = "userEntity")
    OrderEntity toEntity(OrderDomainObject domain);

    default UserEntity map(UUID userId) {
        if (userId == null) return null;
        UserEntity user = new UserEntity();
        user.setId(String.valueOf(userId));
        return user;
    }

    default UUID map(UserEntity userEntity) {
        return userEntity != null ? UUID.fromString(userEntity.getId()) : null;
    }
}
