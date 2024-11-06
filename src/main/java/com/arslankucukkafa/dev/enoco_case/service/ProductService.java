package com.arslankucukkafa.dev.enoco_case.service;

import com.arslankucukkafa.dev.enoco_case.model.Product;
import com.arslankucukkafa.dev.enoco_case.model.dto.ProductDto;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductDto productDto);

    Product getProduct(Long id);

    List<Product> getProducts();

    Product updateProduct(ProductDto productDto);

    void deleteProduct(Long id);

    void updateStock(int stock, Long productId);


}
