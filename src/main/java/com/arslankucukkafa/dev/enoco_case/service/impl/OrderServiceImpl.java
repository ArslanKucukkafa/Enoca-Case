package com.arslankucukkafa.dev.enoco_case.service.impl;

import com.arslankucukkafa.dev.enoco_case.exception.InsufficientStockException;
import com.arslankucukkafa.dev.enoco_case.exception.ResourceNotFoundException;
import com.arslankucukkafa.dev.enoco_case.model.Cart;
import com.arslankucukkafa.dev.enoco_case.model.Order;
import com.arslankucukkafa.dev.enoco_case.model.OrderItem;
import com.arslankucukkafa.dev.enoco_case.model.Product;
import com.arslankucukkafa.dev.enoco_case.repository.OrderRepository;
import com.arslankucukkafa.dev.enoco_case.service.CartService;
import com.arslankucukkafa.dev.enoco_case.service.OrderService;
import com.arslankucukkafa.dev.enoco_case.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private CartService cartService;
    private ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    /*PlaceOrder GetOrderForCode GetAllOrdersForCustomer*/

    // TODO: stockda varsa siparişi oluşturur ve stocktan düşer, yoksa hata döner
    @Override
    public Order placeOrder(Long customerId) {
        Cart currentCart = cartService.getCart(customerId);
        Order order = cartToOrder(customerId);

        /* İlk for döngüsü ile stock kontrolü yapılıyor. Eğer stock yetersizse hata döner.
        * İki farklı for kullanmamın sebebi, customer'ın onayladıgı bir order entity'si oluşturmak */
        for (OrderItem item: currentCart.getCartItems()) {
            Product product = productService.getProduct(item.getProduct().getId());
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Not enough stock for product: " + product.getName());
            }
        }
        for (OrderItem item: currentCart.getCartItems()) {
            Product product = productService.getProduct(item.getProduct().getId());
            product.setStock(product.getStock() - item.getQuantity());
            productService.updateStock(product.getStock(), product.getId());
        }
        cartService.emptyCart(customerId);
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving order");
        }
    }

    @Override
    public Order getOrderForCode(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }

    @Override
    public List<Order> getAllOrdersForCustomer(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    private Order cartToOrder(Long customerId) {
        Cart cart = cartService.getCart(customerId);
        Order order = new Order();
        order.setOrderItems(cart.getCartItems());
        order.setCustomer(cart.getCustomer());
        order.setTotalOrderPrice(cart.getCartTotalPrice());
        return order;
    }
}
