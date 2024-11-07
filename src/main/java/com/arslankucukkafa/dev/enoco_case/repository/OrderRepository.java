package com.arslankucukkafa.dev.enoco_case.repository;

import com.arslankucukkafa.dev.enoco_case.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long aLong);

    List<Order> findAllByCustomerId(Long customerId);
}
