package com.arslankucukkafa.dev.enoco_case.model.dto;

public class ItemDto {
    public Long productId;
    public int quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
