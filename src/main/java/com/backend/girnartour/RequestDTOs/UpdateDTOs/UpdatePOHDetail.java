package com.backend.girnartour.RequestDTOs.UpdateDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePOHDetail {


    public String id;

    public String paxName;

    public String description1;

    public String description2;

    public Double purchaseCost;

    public Double sellPrice;

}
