package com.backend.girnartour.RequestDTOs.UpdateDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSalesHeader {


    public String date;

    public String salesCat;

    public String description;

    public String message;

    public BigDecimal discount;

    public BigDecimal vatAmt;

    public BigDecimal invoiceAmt;

    public BigDecimal totalInvoiceAmt;

    public List<UpdateSalesDetail> salesDetailList;

}
