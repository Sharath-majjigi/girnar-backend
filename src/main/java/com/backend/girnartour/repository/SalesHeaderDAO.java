package com.backend.girnartour.repository;

import com.backend.girnartour.models.SalesHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesHeaderDAO extends JpaRepository<SalesHeader,Integer> {

    @Query(value = "SELECT * FROM sales_header ORDER BY invoice_number DESC",nativeQuery = true)
    List<SalesHeader> findAllByDescendingId();

    List<SalesHeader> findBySalesDetailListPoNumber(Integer poNumber);



}
