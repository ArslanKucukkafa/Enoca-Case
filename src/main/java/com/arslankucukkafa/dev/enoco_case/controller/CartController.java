package com.arslankucukkafa.dev.enoco_case.controller;

import com.arslankucukkafa.dev.enoco_case.model.Cart;
import com.arslankucukkafa.dev.enoco_case.model.dto.ItemDto;
import com.arslankucukkafa.dev.enoco_case.service.impl.CartServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartServiceImpl cartService;

    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    // TODO: GetCart UpdateCart EmptyCart

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCart(@RequestParam Long customerId){
        try {
            Cart cart = cartService.getCart(customerId);
            return new ResponseEntity<>(cart, null, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), null, HttpStatus.NOT_FOUND);
        }
    }

/*
     TODO: burda body olarak List<Item> almak çok fonksiyonel olmayabilir. Daha iyi bir çözüm bulunabilir.
     Fixme: Çünkü Item içersinde product model var ve bu model var mı diye kontrol etmek gerekebilir.
*/
/*@Operation(summary = "Get a product by id", description = "Returns a product as per the id")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
})
    @PutMapping("/update-cart/{customerId}")
    public ResponseEntity<?> updateCart(@RequestBody List<ItemDto> items, @RequestParam Long customerId){
        return new ResponseEntity<>(cartService.updateCart(customerId, items), null, HttpStatus.OK);
    }





    @DeleteMapping("/remove-product-from-cart/{customerId}")
    public ResponseEntity<?> removeProductFromCart(@RequestBody ItemDto itemDto, @RequestParam Long customerId){
        return new ResponseEntity<>(cartService.removeItemFromCart(customerId, itemDto), null, HttpStatus.OK);
    }*/


    @PostMapping("/empty-cart")
    public ResponseEntity<?> emptyCart(@RequestParam Long customerId){
        return new ResponseEntity<>(cartService.emptyCart(customerId), null, HttpStatus.OK);
    }

    @PostMapping("/add-product-to-cart/{customerId}")
    public ResponseEntity<?> addProductToCart(@RequestParam Long customerId, @RequestBody ItemDto itemDto){
        return new ResponseEntity<>(cartService.addItemToCart(customerId, itemDto), null, HttpStatus.OK);
    }

    @DeleteMapping("/remove-product-from-cart/{customerId}")
    public ResponseEntity<?> removeProductFromCart(@RequestParam Long customerId, @RequestBody ItemDto itemDto){
        return new ResponseEntity<>(cartService.removeItemFromCart(customerId, itemDto), null, HttpStatus.OK);
    }

    @GetMapping("/all-carts")
    public ResponseEntity<?> getAllCarts(){
        return new ResponseEntity<>(cartService.getAllCarts(), null, HttpStatus.OK);
    }
}
