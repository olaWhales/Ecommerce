package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.input.CartUseCase;
import com.semicolon.ecommerceTask.application.port.output.persistence.CartItemPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.CartPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.ProductPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.CartDomainObject;
import com.semicolon.ecommerceTask.domain.model.CartItemDomainObject;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CartItemEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.CartStatus;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService implements CartUseCase {
    private final CartPersistenceOutPort cartPersistenceOutPort;
    private final CartItemPersistenceOutPort cartItemPersistenceOutPort;
    private final ProductPersistenceOutPort productPersistenceOutPort;
    private final UserPersistenceOutPort userPersistenceOutPort;

    @Override
    public CartItemDomainObject addProductToCart(ManageProductDomainObject product, int quantity, String userId) {
        CartDomainObject cart = findOrCreateActiveCart(userId);
        CartItemDomainObject newCartItem = new CartItemDomainObject();
        newCartItem.setCartEntityId(cart.getId());
        newCartItem.setProductId(product.getId()) ;
        newCartItem.setQuantity(quantity);
        return cartItemPersistenceOutPort.save(newCartItem);
    }

    @Override
    public List<CartItemDomainObject> getCartItems(String userId) {
        Optional<CartDomainObject> cart = cartPersistenceOutPort.findByUserIdAndStatus(userId, CartStatus.ACTIVE);
        if (cart.isEmpty()) {return Collections.emptyList();}
        return cartItemPersistenceOutPort.findByCartId(cart.get().getId());
}

    @Override
    public Optional<CartItemDomainObject> updateCartItemQuantity(UUID cartItemId, int newQuantity) {
        return cartItemPersistenceOutPort.findById(cartItemId)
            .map(cartItem -> {
                cartItem.setQuantity(newQuantity);
                return cartItemPersistenceOutPort.save(cartItem);
            });
    }

    @Override
    public void removeCartItem(UUID cartItemId) {
        cartItemPersistenceOutPort.deleteById(cartItemId);
    }

    private CartDomainObject findOrCreateActiveCart(String userId) {
        Optional<CartDomainObject> existingCart = cartPersistenceOutPort.findByUserIdAndStatus(userId, CartStatus.ACTIVE);
        if (existingCart.isPresent()) {
            return existingCart.get();
        } else {
            UserDomainObject user = userPersistenceOutPort.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException(MessageUtil.USER_NOT_FOUND));
            CartDomainObject newCart = new CartDomainObject();
            newCart.setUser(user);
            newCart.setCreatedAt(LocalDateTime.now());
            newCart.setUpdatedAt(LocalDateTime.now());
            newCart.setStatus(CartStatus.ACTIVE);
            return cartPersistenceOutPort.save(newCart);
        }
    }
}
