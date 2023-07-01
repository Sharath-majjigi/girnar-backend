package com.backend.girnartour.RequestDTOs.UpdateDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePOPayment {

    public Integer id;

    public String date;

    public BigDecimal amountPaid;

    public String paymentType;

    public String description;

}
