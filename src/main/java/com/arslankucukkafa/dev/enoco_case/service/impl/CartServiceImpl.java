package com.arslankucukkafa.dev.enoco_case.service.impl;

import com.arslankucukkafa.dev.enoco_case.exception.InsufficientStockException;
import com.arslankucukkafa.dev.enoco_case.exception.ResourceNotFoundException;
import com.arslankucukkafa.dev.enoco_case.model.Cart;
import com.arslankucukkafa.dev.enoco_case.model.Customer;
import com.arslankucukkafa.dev.enoco_case.model.OrderItem;
import com.arslankucukkafa.dev.enoco_case.model.Product;
import com.arslankucukkafa.dev.enoco_case.model.dto.ItemDto;
import com.arslankucukkafa.dev.enoco_case.repository.CartRepository;
import com.arslankucukkafa.dev.enoco_case.service.CartService;
import com.arslankucukkafa.dev.enoco_case.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);


    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public Cart emptyCart(Long customerId) {
        Cart cart = cartRepository.findCartByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + customerId));
        try {
            // if cart is already empty, return the cart
            if (cart.getCartItems().isEmpty()) return cart;
            else {
                cart.setCartItems(new ArrayList<>());
                return cartRepository.save(cart);
            }
        } catch (Exception e) {
            LOGGER.error("Error while emptying cart with id: " + customerId, e);
            throw new IllegalStateException("Error while emptying cart with id: " + customerId, e);
        }
    }

    @Override
    public Cart createCart(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setCartItems(Collections.emptyList());
        try {
            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving cart: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Cart addItemToCart(Long customerId, ItemDto itemDto) {
        Cart cart = cartRepository.findCartByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + customerId));

        var items = cart.getCartItems();
        Product product = productService.getProduct(itemDto.getProductId());

        // Stok kontrolü
        if (product.getStock() < itemDto.getQuantity()) {
            throw new InsufficientStockException("Not enough stock for product: " + product.getName());
        }

        var existingItem = items.stream()
                .filter(i -> i.getProduct().getId().equals(itemDto.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + itemDto.getQuantity());
        } else {
            OrderItem newItem = new OrderItem();
            newItem.setProduct(product);
            newItem.setQuantity(itemDto.getQuantity());
            items.add(newItem);
        }

        cart.setCartItems(items);
        try {
            return cartRepository.save(cart);
        } catch (Exception e) {
            LOGGER.error("Error while saving cart with id: " + customerId, e);
            throw new IllegalStateException("Error while saving cart with id: " + customerId, e);
        }
    }

    @Override
    public Cart removeItemFromCart(Long customerId, ItemDto itemDto) {
        Cart cart = cartRepository.findCartByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + customerId));

        List<OrderItem> items = cart.getCartItems();

        OrderItem existingItem = items.stream()
                .filter(i -> i.getProduct().getId().equals(itemDto.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            int totalQuantity = existingItem.getQuantity() - itemDto.getQuantity();
            if (totalQuantity > 0) {
                existingItem.setQuantity(totalQuantity);
            } else {
                items.remove(existingItem);
            }
        } else {
            throw new ResourceNotFoundException("Item not found in cart");
        }

        cart.setCartItems(items);
        try {
            return cartRepository.save(cart);
        } catch (Exception e) {
            LOGGER.error("Error while saving cart with id: " + customerId, e);
            throw new IllegalStateException("Error while saving cart with id: " + customerId, e);
        }
    }

    /*
        Bu method aslında pek mantıklı degil gibi maliyet açısından.
        Ama Kullanıcının nasıl bir davranış sergileyeceğini bilemediğimiz için bu şekilde bir method yazdım.
    */

    @Override
    public Cart getCart(Long customerId) {
        return cartRepository.findCartByCustomerId(customerId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public Cart updateCart(Long customerId, List<ItemDto> items) {
        Cart cart = cartRepository.findCartByCustomerId(customerId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + customerId));
            List<OrderItem> itemList = new ArrayList<>();
            for (ItemDto itemDto : items) {
                Product product = productService.getProduct(itemDto.getProductId());
                OrderItem item = new OrderItem();
                item.setProduct(product);
                item.setQuantity(itemDto.getQuantity());
                itemList.add(item);
            }
            cart.setCartItems(itemList);
            try {
                return cartRepository.save(cart);
            } catch (Exception e) {
                LOGGER.error("Error while saving cart with id: " + customerId, e);
                throw new IllegalStateException("Error while saving cart with id: " + customerId, e);
            }
    }

}