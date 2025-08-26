package com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.adminRequestDto.AdminInitiationRequest;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.adminRequestDto.AdminRegistrationRequest;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.adminRequestDto.AdminUpdateDto;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.AdminResponse;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.UserEntity;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.UserRole;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.temporaryPendingRegistrationEntity.PendingAdminRegistrationEntity;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    @Mapping(target = "adminEmail", source = "adminEmail")
    AdminInitiationRequest toInitiationDto(String adminEmail);

    @Mapping(target = "roles", constant = "ADMIN", qualifiedByName = "mapRolesFromDto")
    UserDomainObject toUserDomainObject(AdminRegistrationRequest dto);

    @Mapping(target = "roles", constant = "ADMIN", qualifiedByName = "mapRolesFromDto")
    AdminDomainObject toAdminDomainObject(AdminRegistrationRequest dto);

    @Named("mapRolesFromDto")
    default List<UserRole> mapRolesFromDto(String role) {
        return List.of(UserRole.valueOf(role));
    }

    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    AdminUpdateDto toUpdateDto(String email, String firstName, String lastName);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    AdminResponse toResponseDto(AdminDomainObject admin);

    List<AdminResponse> toResponseDtoList(List<AdminDomainObject> admins);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "roles", source = "roles")
    AdminDomainObject toDomainObject(UserEntity entity);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "roles", source = "roles")
    UserEntity toEntity(AdminDomainObject admin);

    List<AdminDomainObject> toDomainObjectList(List<UserEntity> entities);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "expiration", source = "expiration")
    PendingAdminRegistrationEntity toPendingEntity(String email, String token, LocalDateTime expiration);

    UserDomainObject mapToUserRepresentation(UserRepresentation userRepresentation);

    @Mapping(target = "message", source = "message")
    @Mapping(target = "fullName", expression = "java(admin.getFirstName() + ' ' + admin.getLastName())")
    AdminResponse toAdminResponseWithFullNameAndMessage(AdminDomainObject admin, String message);
}