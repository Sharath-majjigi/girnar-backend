package com.backend.girnartour.RequestDTOs;

import com.backend.girnartour.models.PurchaseOrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class POHeaderRequest {

    public String poDate;

    public String description;

    public String remarks;

    public List<PurchaseOrderDetail> pod;

}
