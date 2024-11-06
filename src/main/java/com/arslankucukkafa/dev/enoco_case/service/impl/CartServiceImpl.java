package com.arslankucukkafa.dev.enoco_case.service.impl;

import com.arslankucukkafa.dev.enoco_case.exception.ResourceAlreadyExistException;
import com.arslankucukkafa.dev.enoco_case.exception.ResourceNotFoundException;
import com.arslankucukkafa.dev.enoco_case.model.Cart;
import com.arslankucukkafa.dev.enoco_case.model.Customer;
import com.arslankucukkafa.dev.enoco_case.model.OrderItem;
import com.arslankucukkafa.dev.enoco_case.model.Product;
import com.arslankucukkafa.dev.enoco_case.model.dto.ItemDto;
import com.arslankucukkafa.dev.enoco_case.repository.CartRepository;
import com.arslankucukkafa.dev.enoco_case.service.CartService;
import com.arslankucukkafa.dev.enoco_case.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private ProductService productService;


    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    /*
         Note: normalde customerId üzerinden cartId'ye ulaşılır. Ama mevcut user için bir principal holder oluşturulmadığı için
         CustomerService'i burda çagırmamız Circular dependency'e yol açıyor. Dogrudan Sepet Id'si üzerinden işlem yapmak daha mantıklı geldi.
    */
    @Override
    public Cart emptyCart(Long customerId) {
        Cart cart = cartRepository.findCartByCustomerId(customerId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + customerId));
        try {
            // if cart is already empty, return the cart
            if (cart.getCartItems().isEmpty()) return cart;
            else {
                cart.setCartItems(new ArrayList<>());
                return cartRepository.save(cart);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while saving cart");
        }
    }

    @Override
    public Cart createCart(Customer customer) {
        try {
            Cart cart = new Cart();
            cart.setCustomer(customer);
            cart.setCartItems(Collections.emptyList());
            return cartRepository.save(cart);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving cart: " + e.getMessage());
        }
    }

    @Override
    public Cart addItemToCart(Long customerId, ItemDto itemDto) {
        Cart cart = cartRepository.findCartByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + customerId));

        var items = cart.getCartItems();
        Product product = productService.getProduct(itemDto.getProductId()); // Burda ilgili id ile product yoksa exception fırlatır
        // CartId auto generated olmasına gerek yok gibi geldi. CustomerId ile cartId'yi bulmak daha mantıklı geldi.
        // Aynı üründen varsa miktarı güncelle, yoksa yeni ürün ekle
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
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItemFromCart(Long customerId, ItemDto itemDto) {
        Cart cart = cartRepository.findCartByCustomerId(customerId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + customerId));

        List<OrderItem> items = cart.getCartItems();

        OrderItem existingItem = items.stream()
                .filter(i -> i.getProduct().getId().equals(itemDto.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            int totalQuantity = existingItem.getQuantity() - itemDto.getQuantity();
            // Eğer miktar 0'dan büyükse güncelle, değilse sil
            if (totalQuantity > 0) existingItem.setQuantity(totalQuantity);
            else items.remove(existingItem);
        } else {
            throw new ResourceNotFoundException("Remove item not found in cart");
        }
        cart.setCartItems(items);
        return cartRepository.save(cart);
    }

    /*
        Bu method aslında pek mantıklı degil gibi maliyet açısından.
        Ama Kullanıcının nasıl bir davranış sergileyeceğini bilemediğimiz için bu şekilde bir method yazdım.
    */

    @Override
    public Cart getCart(Long customerId) {
        return cartRepository.findCartByCustomerId(customerId).orElseThrow(() -> new ResourceAlreadyExistException("Cart not found"));
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart updateCart(Long cartId, List<ItemDto> items) {
        Cart cart = cartRepository.findCartById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
        if (cart != null) {
            List<OrderItem> itemList = new ArrayList<>();
            for (ItemDto itemDto : items) {
                Product product = productService.getProduct(itemDto.getProductId());
                OrderItem item = new OrderItem();
                item.setProduct(product);
                item.setQuantity(itemDto.getQuantity());
                itemList.add(item);
            }
            // ItemDto'dan gelen productlar veritabanından çekilip Item listesine eklendi
            // Todo: Stock kontrolünü burda mı yapmalıyız yoksa, Order veritabanına kaydederken mi yapmalıyız?
            cart.setCartItems(itemList);
            return cartRepository.save(cart);
        } else throw new ResourceNotFoundException("Cart not found");
    }

}

        // FIXME: Bakıldıgında sadece güncellenebilr Item field'ı var. Bu yüzden RequestBody olarak Item alıyor.
