package com.backend.girnartour.repository;

import com.backend.girnartour.models.PurchaseOrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface POHeaderDAO extends JpaRepository<PurchaseOrderHeader,String> {

    @Query(value = "select sell_amount from purchase_order_header where po_number = ?1",nativeQuery = true)
    Double findByPOHId(String id);

}
