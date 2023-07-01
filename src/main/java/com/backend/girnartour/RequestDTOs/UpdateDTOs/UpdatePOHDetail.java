package com.backend.girnartour.RequestDTOs.UpdateDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePOHDetail {


    public Integer id;

    public String paxName;

    public String description1;

    public String description2;

    public BigDecimal purchaseCost;

    public BigDecimal sellPrice;

}
