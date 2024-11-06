package com.arslankucukkafa.dev.enoco_case.service.impl;

import com.arslankucukkafa.dev.enoco_case.exception.ResourceNotFoundException;
import com.arslankucukkafa.dev.enoco_case.model.Product;
import com.arslankucukkafa.dev.enoco_case.model.dto.ProductDto;
import com.arslankucukkafa.dev.enoco_case.repository.ProductRepository;
import com.arslankucukkafa.dev.enoco_case.service.ProductService;
import com.arslankucukkafa.dev.enoco_case.util.ProductUpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductUpdateHandler productUpdateHandler;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductUpdateHandler productUpdateHandler) {
        this.productRepository = productRepository;
        this.productUpdateHandler = productUpdateHandler;
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

    public Product updateProduct(ProductDto productDto) {
        // ProductDto'da id field'ını Nullable olarak tanımladıgım için burda kontrol ettim.
        if(productDto.getId() == null) throw new RuntimeException("Product id is required");

        Product product = productDto.productDtoToProduct(productDto);
        Optional<Product> isExist = productRepository.findById(product.getId());
        if (isExist.isPresent()) {
            try {
                Product updatedProduct = productRepository.save(product);
                // todo: burda stock güncellemesi için cartları güncellemeye gerek yok.
                productUpdateHandler.handleProductUpdate(updatedProduct);
                return updatedProduct;
            } catch (TransactionSystemException e) {
                throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public void deleteProduct(Long id){
        boolean isExist = productRepository.existsById(id);
        if(isExist){
            productRepository.deleteById(id);
        } else throw new RuntimeException("Product not found");
    }

    @Override
    public void updateStock(int stock, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setStock(stock);
        productRepository.save(product);
    }


}
