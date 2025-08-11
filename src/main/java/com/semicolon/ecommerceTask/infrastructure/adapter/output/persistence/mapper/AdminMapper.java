package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto.AdminInitiationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto.AdminRegistrationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto.AdminUpdateDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.AdminResponseDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.AdminEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.PendingRegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    @Mapping(target = "adminEmail", source = "adminEmail")
    AdminInitiationDto toInitiationDto(String adminEmail);

    @Mapping(target = "token", source = "token")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "password", source = "password")
    AdminRegistrationDto toRegistrationDto(String token, String email, String firstName, String lastName, String password);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    AdminUpdateDto toUpdateDto(String email, String firstName, String lastName);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "roles", source = "roles")
    AdminResponseDto toResponseDto(AdminDomainObject admin);

    List<AdminResponseDto> toResponseDtoList(List<AdminDomainObject> admins);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "keycloakId", source = "keycloakId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    // @Mapping(target = "password", source = "password") <-- Removed this line
    @Mapping(target = "roles", source = "roles")
    AdminDomainObject toDomainObject(AdminEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "keycloakId", source = "keycloakId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    // @Mapping(target = "password", source = "password") <-- Removed this line
    @Mapping(target = "roles", source = "roles")
    AdminEntity toEntity(AdminDomainObject admin);

    List<AdminDomainObject> toDomainObjectList(List<AdminEntity> entities);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "expiration", source = "expiration")
    PendingRegistrationEntity toPendingEntity(String email, String token, LocalDateTime expiration);
}