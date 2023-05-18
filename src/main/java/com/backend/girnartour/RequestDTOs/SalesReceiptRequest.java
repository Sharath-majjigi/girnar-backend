package com.backend.girnartour.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesReceiptRequest {

    private String date;

    private Double amountReceived;

    private String receiptType;

    private String description;

}
