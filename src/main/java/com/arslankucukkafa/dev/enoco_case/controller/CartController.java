package com.arslankucukkafa.dev.enoco_case.controller;

import com.arslankucukkafa.dev.enoco_case.model.dto.ItemDto;
import com.arslankucukkafa.dev.enoco_case.service.impl.CartServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartServiceImpl cartService;

    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }


    @Operation(summary = "This endpoint is returning the cart of the customer")
    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCart(@PathVariable Long customerId){
        return new ResponseEntity<>(cartService.getCart(customerId), null, HttpStatus.OK);
    }

    @Operation(summary = "This endpoint is clearing the cart of the customer")
    @PostMapping("/{customerId}/empty")
    public ResponseEntity<?> emptyCart(@PathVariable Long customerId){
        return new ResponseEntity<>(cartService.emptyCart(customerId), null, HttpStatus.OK);
    }

    @Operation(summary = "This endpoint is adding a product to the cart of the customer")
    @PostMapping("/{customerId}/add-item")
    public ResponseEntity<?> addProductToCart(@PathVariable Long customerId, @RequestBody ItemDto itemDto){
        return new ResponseEntity<>(cartService.addItemToCart(customerId, itemDto), null, HttpStatus.OK);
    }

    @Operation(summary = "This endpoint is removing a product from the cart of the customer")
    @DeleteMapping("/{customerId}/remove-item}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long customerId, @RequestBody ItemDto itemDto){
        return new ResponseEntity<>(cartService.removeItemFromCart(customerId, itemDto), null, HttpStatus.OK);
    }

}
