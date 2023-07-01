package com.backend.girnartour.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class POPaymentRequest {

    public String description;

    public String paymentType;

    public String date;

    public BigDecimal amountPaid;

}
