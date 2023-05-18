package com.backend.girnartour.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesCategoryRequest {

    @NotEmpty(message = "Category is required !")
    public String category;

    @NotEmpty
    public String description;
}
