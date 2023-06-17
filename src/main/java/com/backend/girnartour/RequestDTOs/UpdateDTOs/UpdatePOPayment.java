package com.backend.girnartour.RequestDTOs.UpdateDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePOPayment {

    public String id;

    public String date;

    public Double amountPaid;

    public String paymentType;

    public String description;

}
