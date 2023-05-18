package com.backend.girnartour.repository;

import com.backend.girnartour.models.SalesReceipts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesReceiptDAO extends JpaRepository<SalesReceipts,String> {

    List<SalesReceipts> findAllBySalesHeaderId(String id);
}
