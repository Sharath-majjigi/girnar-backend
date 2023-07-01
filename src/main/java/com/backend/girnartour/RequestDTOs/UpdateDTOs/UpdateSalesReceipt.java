package com.backend.girnartour.RequestDTOs.UpdateDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSalesReceipt {

    public Integer id;

    public String date;

    public BigDecimal amountReceived;

    public String receiptType;

    public String description;

}
