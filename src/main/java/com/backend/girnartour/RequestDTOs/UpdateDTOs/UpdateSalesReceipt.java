package com.backend.girnartour.RequestDTOs.UpdateDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSalesReceipt {

    public String id;

    public String date;

    public Double amountReceived;

    public String receiptType;

    public String description;

}
