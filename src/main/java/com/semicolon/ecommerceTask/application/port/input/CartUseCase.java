package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.CartItemDomainObject;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartUseCase {
    CartItemDomainObject addOrUpdateCartItem(ManageProductDomainObject product, int quantity, String userId);
    List<CartItemDomainObject> getCartItems(String userId);
    Optional<CartItemDomainObject> updateCartItemQuantity(UUID cartItemId, int newQuantity, String userId);
    void removeCartItem(UUID cartItemId , String sellerId);
}
