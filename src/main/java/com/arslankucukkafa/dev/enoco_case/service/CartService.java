package com.arslankucukkafa.dev.enoco_case.service;

import com.arslankucukkafa.dev.enoco_case.model.Cart;
import com.arslankucukkafa.dev.enoco_case.model.Customer;
import com.arslankucukkafa.dev.enoco_case.model.dto.ItemDto;

import java.util.List;

public interface CartService {
    Cart emptyCart(Long customerId);
    Cart createCart(Customer customerId);
    Cart updateCart(Long id, List<ItemDto> items);
    Cart addItemToCart(Long customerId, ItemDto itemDto);
    Cart removeItemFromCart(Long customerId, ItemDto itemDto);
    Cart getCart(Long customerId);
}
