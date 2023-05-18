package com.backend.girnartour.repository;

import com.backend.girnartour.models.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PohDetailDAO extends JpaRepository<PurchaseOrderDetail,String> {
}
