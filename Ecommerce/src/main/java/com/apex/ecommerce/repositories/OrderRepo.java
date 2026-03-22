package com.apex.ecommerce.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apex.ecommerce.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

}