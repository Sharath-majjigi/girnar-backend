package com.backend.girnartour.RequestDTOs;

import com.backend.girnartour.models.Customer;
import com.backend.girnartour.models.SalesDetail;
import com.backend.girnartour.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesHeaderRequest {


    public String date;

    public String salesCat;

    public String description;

    public String message;

    public BigDecimal discount;

    public BigDecimal vatAmt;

    public BigDecimal invoiceAmt;

    public BigDecimal totalInvoiceAmt;

    public List<SalesDetail> salesDetailList;

}
