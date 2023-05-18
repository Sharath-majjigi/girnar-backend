package com.backend.girnartour.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vendor{

    @Id
    @Column(name = "vendor_code")
    private String id;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    private String city;

    @Column(name = "postal_code")
    private Integer postalCode;

    private String country;

    @Column(name = "tel")
    private String telephone;

    private String email;

    private String notes;


    @OneToMany(mappedBy = "vendor",cascade = CascadeType.ALL)
    private List<PurchaseOrderHeader> poh=new ArrayList<>();
}
