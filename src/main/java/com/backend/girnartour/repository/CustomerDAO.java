package com.backend.girnartour.repository;

import com.backend.girnartour.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDAO extends JpaRepository<Customer,String> {
    boolean existsByEmail(String email);

    boolean existsByCustomerName(String name);

    boolean existsByTelephone(String telephone);
}
