package com.apex.ecommerce.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apex.ecommerce.model.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long>{

}