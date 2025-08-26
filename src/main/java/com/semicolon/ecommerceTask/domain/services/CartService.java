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
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.enumPackages.CartStatus;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.entities.persistenceEntities.CartItemEntity;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import lombok.AllArgsConstructor;
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
    public CartItemDomainObject addOrUpdateCartItem (ManageProductDomainObject productDomain, int quantity, String userId) {
        CartDomainObject cart = findOrCreateActiveCart(userId);
        Optional<CartItemDomainObject> existingCartItem = cartItemPersistenceOutPort.findByProductIdAndUserId(productDomain.getId(), userId);
        Optional<ManageProductDomainObject> productOptional = productPersistenceOutPort.findById(productDomain.getId());
        if (productOptional.isEmpty()) {throw new IllegalArgumentException(MessageUtil.PRODUCT_NOT_FOUND + productDomain.getId());}
        ManageProductDomainObject product = productOptional.get();
        int newTotalQuantity = existingCartItem.map(cartItem -> cartItem.getQuantity() + quantity).orElse(quantity);
        if (newTotalQuantity > product.getInStockQuantity()) {throw new IllegalArgumentException(MessageUtil.NOT_ENOUGH_STOCK_AVAILABLE_FOR_PRODUCT_WITH_ID + product.getId());}
        CartItemDomainObject cartItem;
        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            cartItem.setQuantity(newTotalQuantity);
        } else {
            cartItem = new CartItemDomainObject();
            cartItem.setProductId(productDomain.getId());
            cartItem.setQuantity(quantity);
            cartItem.setCartEntityId(cart.getId());
        }
        return cartItemPersistenceOutPort.save(cartItem);
    }

    @Override
    public List<CartItemDomainObject> getCartItems(String userId) {
        Optional<CartDomainObject> cart = cartPersistenceOutPort.findByUserIdAndStatus(userId, CartStatus.ACTIVE);
        if (cart.isEmpty()) {return Collections.emptyList();}
        return cartItemPersistenceOutPort.findByCartId(cart.get().getId());
}

    @Override
    public Optional<CartItemDomainObject> updateCartItemQuantity(UUID cartItemId, int newQuantity, String userId) {
        Optional<CartItemDomainObject> cartItemDomainObject = cartItemPersistenceOutPort.findById(cartItemId);
        if(cartItemDomainObject.isEmpty()){throw new IllegalArgumentException(MessageUtil.CART_ITEM_NOT_FOUND);}
        CartItemDomainObject cartItem = cartItemDomainObject.get();
        Optional<ManageProductDomainObject> productOptional = productPersistenceOutPort.findById(cartItem.getProductId());
        if(productOptional.isEmpty()){throw new IllegalArgumentException(MessageUtil.PRODUCT_ASSOCIATED_WITH_CART_ITEM_NOT_FOUND);};
        ManageProductDomainObject product = productOptional.get();
        if (newQuantity <= 0) {
            cartItemPersistenceOutPort.deleteById(cartItemId);
            return Optional.empty();
        }
        if(newQuantity > product.getInStockQuantity()){throw new IllegalArgumentException(MessageUtil.NOT_ENOUGH_STOCK_AVAILABLE_FOR_PRODUCT_WITH_ID + product.getId());}
        cartItem.setQuantity(newQuantity);
        return Optional.of(cartItemPersistenceOutPort.save(cartItem));
    }

    @Override
    public void removeCartItem(UUID cartItemId , String userId) {
        Optional<CartItemEntity> cartItem = cartItemPersistenceOutPort.findByIdAndCartEntityUserId(cartItemId, userId);
        if (cartItem.isEmpty()) {throw new IllegalArgumentException(MessageUtil.CART_ITEM_NOT_FOUND);}
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
