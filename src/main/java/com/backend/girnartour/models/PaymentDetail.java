//package com.backend.girnartour.models;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class PaymentDetail {
//
//    @Id
//    private String id;
//
//    private String description;
//
//    @Column(name = "payment_type")
//    private String paymentType;
//
//    private String date;
//
//    @Column(name = "amount_paid")
//    private Double amountPaid;
//
//    @JsonIgnore
//    @ManyToOne
//    private PurchaseOrderPayments purchaseOrderPayments;
//}
