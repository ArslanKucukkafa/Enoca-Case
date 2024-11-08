package com.arslankucukkafa.dev.enoco_case.service.impl;

import com.arslankucukkafa.dev.enoco_case.exception.ResourceNotFoundException;
import com.arslankucukkafa.dev.enoco_case.model.OrderItem;
import com.arslankucukkafa.dev.enoco_case.model.Product;
import com.arslankucukkafa.dev.enoco_case.model.dto.ProductDto;
import com.arslankucukkafa.dev.enoco_case.repository.ProductRepository;
import com.arslankucukkafa.dev.enoco_case.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductDto productDto){
        Product product = productDto.productDtoToProduct(productDto);
       return productRepository.save(product);
    }

    public Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product updateProduct(Long productId,ProductDto productDto) {

        Product product = productDto.productDtoToProduct(productDto);
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setStock(product.getStock());
        existingProduct.setPrice(product.getPrice());

        // Burda ilişkili olan orderItem'lar ve cart'lar güncelleniyor.
        for (OrderItem orderItem : existingProduct.getOrderItems()) {
            orderItem.updateTotalPrice();
            if (orderItem.getCart() != null) {
                orderItem.getCart().updateCartTotalPrice();
            }
        }

        try {
            Product updatedProduct = productRepository.save(product);
            return updatedProduct;
        } catch (Exception e) {
            throw new RuntimeException("Exception while updating product: ", e);
        }
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        for (OrderItem orderItem : product.getOrderItems()) {
            if (orderItem.getCart() != null) {
                orderItem.getCart().getCartItems().remove(orderItem);
                orderItem.getCart().updateCartTotalPrice();
            }
        }
        try {
            productRepository.deleteById(product.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting product: ", e);
        }
    }

    @Override
    public void updateStock(int stock, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setStock(stock);
         try {
             productRepository.save(product);
         } catch (Exception e) {
             throw new RuntimeException("Error while updating stock: ",e);
         }
    }


}
