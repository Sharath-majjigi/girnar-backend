package com.backend.girnartour.RequestDTOs.UpdateDTOs;

import com.backend.girnartour.models.SalesHeader;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSalesDetail {

    public String id;

    public String poNumber;

}
