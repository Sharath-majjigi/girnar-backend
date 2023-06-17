package com.backend.girnartour.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_order_payments")
public class PurchaseOrderPayments {

    @Id
    @Column(name = "payment_id")
    private String id;

    @OneToOne
    @JoinColumn(name = "po_number")
    private PurchaseOrderHeader purchaseOrderHeader;

    @Column(name = "date_paid")
    private String date;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @Column(name = "payment_type")
    private String paymentType;

    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;


//    @Transient
//    public Double getTotalPaidAmt() {
//        Double sum=0D;
//        for(PaymentDetail detail:paymentDetails){
//            sum+=detail.getAmountPaid();
//        }
//        return sum;
//    }
}


