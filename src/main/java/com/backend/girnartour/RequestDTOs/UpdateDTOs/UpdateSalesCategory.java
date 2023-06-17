package com.backend.girnartour.RequestDTOs.UpdateDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSalesCategory {

    public String category;

    public String description;
}
