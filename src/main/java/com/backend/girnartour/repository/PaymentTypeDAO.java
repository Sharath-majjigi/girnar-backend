package com.backend.girnartour.repository;

import com.backend.girnartour.models.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeDAO extends JpaRepository<PaymentType,String> {
}
