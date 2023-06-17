package com.backend.girnartour.RequestDTOs.UpdateDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSalesHeader {


    public String date;

    public String salesCat;

    public String description;

    public String message;

    public Double discount;

    public Double vatAmt;

    public Double invoiceAmt;

    public Double totalInvoiceAmt;

    public List<UpdateSalesDetail> salesDetailList;

}
