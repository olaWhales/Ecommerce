//package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;
//
//import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserPersistenceMapper {
//
//    public UserEntity toEntity(UserDomainObject domainObject) {
//        if (domainObject == null) {
//            return null;
//        }
//        return UserEntity.builder()
//                .id(domainObject.getId())
//                .keycloakId(domainObject.getKeycloakId())
//                .firstName(domainObject.getFirstName())
//                .lastName(domainObject.getLastName())
//                .email(domainObject.getEmail())
//                .password(domainObject.getPassword())
//                .roles(domainObject.getRoles()) // Assuming roles is a list
//                .build();
//    }
//
//    public UserDomainObject toDomain(UserEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//        return UserDomainObject.builder()
//                .id(entity.getId())
//                .keycloakId(entity.getKeycloakId())
//                .firstName(entity.getFirstName())
//                .lastName(entity.getLastName())
//                .email(entity.getEmail())
//                .password(entity.getPassword())
//                .roles(entity.getRoles()) // Assuming roles is a list
//                .build();
//    }
//}
package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // This makes it a Spring bean
public interface UserPersistenceMapper {

    // Map a UserDomainObject to a UserEntity
    @Mapping(target = "password", ignore = true) // Password should not be mapped
    UserEntity toEntity(UserDomainObject domainObject);

    // Map a UserEntity to a UserDomainObject
    UserDomainObject toDomain(UserEntity entity);
}