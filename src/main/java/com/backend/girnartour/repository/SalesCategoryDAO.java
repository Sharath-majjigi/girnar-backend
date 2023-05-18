package com.backend.girnartour.repository;

import com.backend.girnartour.models.SalesCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesCategoryDAO extends JpaRepository<SalesCategory,String> {
}
