package com.backend.girnartour.ResponseDTOs;

import com.backend.girnartour.models.SalesHeader;
import com.backend.girnartour.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesReceiptResponse {

    private Integer id;

    private SalesHeaderResponse salesHeader;

    private String date;

    private BigDecimal amountReceived;

    private String receiptType;

    private String description;

    private UserResponseDTO user;

}
