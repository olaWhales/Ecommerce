package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserAdapterMapper {
    public  UserDomainObject mapToUserDomainObject(UserEntity userEntity){
        return UserDomainObject.builder()
//            .id(userEntity.getId())
            .name(userEntity.getName())
            .email(userEntity.getEmail())
            .password(userEntity.getPassword())
            .roles(userEntity.getRoles())
            .build();
    }
    public UserEntity mapToUserEntity(UserDomainObject userDomainObject){
        return UserEntity.builder()
            .name(userDomainObject.getName())
            .email(userDomainObject.getEmail())
            .roles(userDomainObject.getRoles())
            .password(userDomainObject.getPassword())
            .build();
    }
}
