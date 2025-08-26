package com.semicolon.ecommerceTask.infrastructure.controllers;

import com.semicolon.ecommerceTask.application.port.input.CartUseCase;
import com.semicolon.ecommerceTask.domain.model.CartItemDomainObject;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.AddToCartRequest;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.UpdateCartItemQuantityRequest;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.CartItemResponse;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.CartMapper;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
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
            @AuthenticationPrincipal Jwt user) {
        String userId = user.getClaimAsString("sub");
        ManageProductDomainObject product =  new ManageProductDomainObject();
                product.setId(request.getProductId());
        CartItemDomainObject cartItem = cartUseCase.addOrUpdateCartItem(product, request.getQuantity(), userId);
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
            @RequestBody UpdateCartItemQuantityRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("sub");
        Optional<CartItemDomainObject> updatedItem = cartUseCase.updateCartItemQuantity(cartItemId, request.getQuantity(), userId );
        return updatedItem.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/remove-item/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable UUID cartItemId, @AuthenticationPrincipal Jwt userDetails) {
        String userId = userDetails.getClaimAsString("sub");
        cartUseCase.removeCartItem(cartItemId, userId);
        return ResponseEntity.ok( MessageUtil.SUCCESSFULLY_REMOVED_FROM_CART);
    }
}
