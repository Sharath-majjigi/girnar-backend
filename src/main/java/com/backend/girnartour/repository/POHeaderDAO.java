package com.backend.girnartour.repository;

import com.backend.girnartour.models.PurchaseOrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface POHeaderDAO extends JpaRepository<PurchaseOrderHeader,Integer> {

    @Query(value = "select sell_amount from purchase_order_header where po_number = ?1",nativeQuery = true)
    BigDecimal findByPOHId(Integer id);

    List<PurchaseOrderHeader> findByIdNotIn(List<Integer> ids);

    @Query(value = "SELECT * FROM purchase_order_header ORDER BY po_number DESC",nativeQuery = true)
    List<PurchaseOrderHeader> findAllByDescendingID();



}
