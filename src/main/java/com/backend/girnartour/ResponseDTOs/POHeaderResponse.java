package com.backend.girnartour.ResponseDTOs;

import com.backend.girnartour.models.PurchaseOrderDetail;
import com.backend.girnartour.models.User;
import com.backend.girnartour.models.Vendor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class POHeaderResponse {


    public String id;

    public String poDate;

    public String description;

    public String remarks;
    public Double amount;


    public Double sellAmount;


    public String vendorName;

    public UserResponseDTO user;

    public VendorResponseDTO vendor;

    public List<PurchaseOrderDetail> pod;

}
