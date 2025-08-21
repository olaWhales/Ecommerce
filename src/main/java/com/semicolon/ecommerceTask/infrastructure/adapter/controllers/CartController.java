package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.CartUseCase;
import com.semicolon.ecommerceTask.domain.model.CartItemDomainObject;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests.AddToCartRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests.UpdateCartItemQuantityRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses.CartItemResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartUseCase cartUseCase;
    private final CartMapper cartMapper;


    @PostMapping("/add")
    public ResponseEntity<CartItemResponse> addProductToCart(
                                                            @RequestBody AddToCartRequest request,
                                                            @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("sub");
        ManageProductDomainObject product =  new ManageProductDomainObject();
                product.setId(request.getProductId());
        CartItemDomainObject cartItem = cartUseCase.addProductToCart(product, request.getQuantity(), userId);
        CartItemResponse responseDto = cartMapper.toResponseDto(cartItem);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/get-cart-items")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("sub");
        List<CartItemDomainObject> cartItems = cartUseCase.getCartItems(userId);
        return ResponseEntity.ok(cartItems.stream().map(cartMapper::toResponseDto).toList());
    }

    @PutMapping("/update-quantity/{cartItemId}")
    public ResponseEntity<CartItemDomainObject> updateCartItemQuantity(
            @PathVariable UUID cartItemId,
            @RequestBody UpdateCartItemQuantityRequest request, @AuthenticationPrincipal Jwt jwt) {
        Optional<CartItemDomainObject> updatedItem = cartUseCase.updateCartItemQuantity(cartItemId, request.getQuantity());
        return updatedItem.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/remove-item/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable UUID cartItemId) {
        cartUseCase.removeCartItem(cartItemId);
        return ResponseEntity.ok().build();
    }
}
