//package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository;
//
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.SellerEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Repository
//public interface SellerRepository extends JpaRepository<SellerEntity, UUID> {
//    Optional<SellerEntity> findByEmail(String email);
//}