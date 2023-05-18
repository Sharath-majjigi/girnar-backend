package com.backend.girnartour.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    public String id;

    public String customerName;

    public String addressLine1;

    public String addressLine2;

    public String city;

    public Integer postalCode;

    public String country;

    public String telephone;

    public String email;

    public String notes;

}
