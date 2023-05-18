package com.backend.girnartour.ResponseDTOs;

import com.backend.girnartour.models.Customer;
import com.backend.girnartour.models.SalesDetail;
import com.backend.girnartour.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesHeaderResponse {

    public String id;

    public UserResponseDTO user;

    public String date;

    public String salesCat;

    public CustomerResponse customer;


    public String description;

    public String message;

    public Double invoiceAmt;

    public Double discount;

    public Double vatAmt;

    public Double totalInvoiceAmt;

    public List<SalesDetail> salesDetailList;

}
