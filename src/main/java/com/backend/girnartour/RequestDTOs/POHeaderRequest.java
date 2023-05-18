package com.backend.girnartour.RequestDTOs;

import com.backend.girnartour.models.PurchaseOrderDetail;
import com.backend.girnartour.models.User;
import com.backend.girnartour.models.Vendor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
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
