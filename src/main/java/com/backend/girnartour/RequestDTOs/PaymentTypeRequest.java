package com.backend.girnartour.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeRequest {

    @NotEmpty(message = "Payment type is required !")
    public String type;

    @NotEmpty
    public String description;

}
