package com.backend.girnartour.ResponseDTOs;

import com.backend.girnartour.models.SalesDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesHeaderResponse {

    public Long id;

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

    public Double totalAmountPaid;

    public List<SalesDetail> salesDetailList;

}
