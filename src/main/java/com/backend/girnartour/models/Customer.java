package com.backend.girnartour.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @Column(name = "customer_code")
    private String id;

    @Column(name = "customer_name")
    private String customerName;

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

    @OneToMany(mappedBy = "customer",cascade= CascadeType.ALL)
    private List<SalesHeader> salesHeaderList;

}
