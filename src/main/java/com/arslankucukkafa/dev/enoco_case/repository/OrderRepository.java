package com.arslankucukkafa.dev.enoco_case.repository;

import com.arslankucukkafa.dev.enoco_case.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
