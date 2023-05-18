package com.backend.girnartour.repository;

import com.backend.girnartour.models.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorDAO extends JpaRepository<Vendor,String> {

    boolean existsByEmail(String email);

    boolean existsByTelephone(String telephone);

    boolean existsByVendorName(String name);
}
