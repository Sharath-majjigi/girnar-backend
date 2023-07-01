package com.backend.girnartour.repository;

import com.backend.girnartour.models.SalesHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesHeaderDAO extends JpaRepository<SalesHeader,Integer> {



}
