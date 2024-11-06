package com.arslankucukkafa.dev.enoco_case.util;

import com.arslankucukkafa.dev.enoco_case.model.Cart;
import com.arslankucukkafa.dev.enoco_case.model.OrderItem;
import com.arslankucukkafa.dev.enoco_case.model.Product;
import com.arslankucukkafa.dev.enoco_case.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProductUpdateHandler {

    private final CartRepository cartRepository;

    @Autowired
    public ProductUpdateHandler(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // TODO: Mesela kullanıcı stock degerini 0 yaptıgında sepetler için update işlemi yapmalı mıyım ? Yoksa placeOrder yaparken mi kontrol etmeliyim?
    @Transactional
    public void updateCartItemsWithProduct(Product updatedProduct) {
        List<Cart> carts = cartRepository.findAll();
        for (Cart cart : carts) {
            boolean updated = false;
            for (OrderItem item : cart.getCartItems()) {
                if (item.getProduct().getId().equals(updatedProduct.getId())) {
                    item.setProduct(updatedProduct);
                    item.setTotalPrice(item.getQuantity() * updatedProduct.getPrice());
                    updated = true;
                }
            }
            if (updated) {
                cart.setCartTotalPrice(cart.calculateTotalPrice());
                cartRepository.save(cart);
            }
        }
    }

    @EventListener
    public void handleProductUpdate(Product product) {
        updateCartItemsWithProduct(product);
    }
}