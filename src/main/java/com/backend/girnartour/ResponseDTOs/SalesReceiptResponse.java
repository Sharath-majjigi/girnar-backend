package com.backend.girnartour.ResponseDTOs;

import com.backend.girnartour.models.SalesHeader;
import com.backend.girnartour.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesReceiptResponse {

    private String id;

    private SalesHeaderResponse salesHeader;

    private String date;

    private Double amountReceived;

    private String receiptType;

    private String description;

    private UserResponseDTO user;

}
