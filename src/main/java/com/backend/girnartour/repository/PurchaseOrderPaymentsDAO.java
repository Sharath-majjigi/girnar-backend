package com.backend.girnartour.repository;

import com.backend.girnartour.models.PurchaseOrderPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderPaymentsDAO extends JpaRepository<PurchaseOrderPayments,String> {

    List<PurchaseOrderPayments> findAllByPurchaseOrderHeaderId(String id);

    @Query(value = "select * from purchase_order_payments where po_number=?1",nativeQuery = true)
    List<PurchaseOrderPayments> findAllPurchaseOrderPaymentsByPOHId(String id);
}
