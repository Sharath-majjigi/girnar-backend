package com.backend.girnartour.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class POPaymentRequest {

//    public String poDate;
//
//    public String description;
//
//    public String remarks;
//
//    public List<PaymentDetail> paymentDetails;

    public String description;

    public String paymentType;

    public String date;

    public Double amountPaid;

}
