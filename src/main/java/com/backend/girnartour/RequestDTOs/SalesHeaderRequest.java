package com.backend.girnartour.RequestDTOs;

import com.backend.girnartour.models.Customer;
import com.backend.girnartour.models.SalesDetail;
import com.backend.girnartour.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesHeaderRequest {


    public String date;

    public String salesCat;

    public String description;

    public String message;

    public Double discount;

    public Double vatAmt;

    public Double invoiceAmt;

    public Double totalInvoiceAmt;

    public List<SalesDetail> salesDetailList;

}
