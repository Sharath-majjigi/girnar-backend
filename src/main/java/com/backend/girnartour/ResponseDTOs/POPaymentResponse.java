package com.backend.girnartour.ResponseDTOs;


import com.backend.girnartour.models.PurchaseOrderDetail;
import com.backend.girnartour.models.PurchaseOrderHeader;
import com.backend.girnartour.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class POPaymentResponse {

//    public String id;

//    public String poDate;
//
//    public String description;
//
//    public String remarks;
//
//    public Double amount;
//
//    public Double totalPaid;
//
//    public String vendorName;
//
//    public UserResponseDTO user;
//
//    public VendorResponseDTO vendor;
//
//    public List<PaymentDetail> paymentDetails;


    public String id;

    public POHeaderResponse purchaseOrderHeader;

    public String date;

    public Double amountPaid;

    public String paymentType;

    public String description;

    public UserResponseDTO user;
}
