package com.backend.girnartour.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesReceiptRequest {

    private String date;

    private BigDecimal amountReceived;

    private String receiptType;

    private String description;

}
