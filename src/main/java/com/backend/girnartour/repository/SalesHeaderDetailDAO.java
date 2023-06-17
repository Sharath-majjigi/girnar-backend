package com.backend.girnartour.repository;

import com.backend.girnartour.models.SalesDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesHeaderDetailDAO extends JpaRepository<SalesDetail,String> {

    SalesDetail findByIdAndSalesHeaderId(String id, String POHId);

}
