package com.backend.girnartour.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales_receipts")
public class SalesReceipts {

    @Id
    @Column(name = "receipt_id")
    private String id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "invoice_number")
    private SalesHeader salesHeader;

    @Column(name = "date_received")
    private String date;

    @Column(name = "amount_received")
    private Double amountReceived;

    @Column(name = "receipt_type")
    private String receiptType;

    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

//    @ManyToOne
//    private Customer customer;

    // private Double totalPaid;

//    @OneToMany(mappedBy = "salesReceipts",cascade = CascadeType.ALL)
//    private List<SalesReceiptDetail> salesDetails;


//    @Transient
//    public Double totalPaidAmount(){
//        Double sum=0D;
//        for(SalesReceiptDetail sa:salesDetails){
//            sum+=sa.getAmountReceived();
//        }
//        return sum;
//    }

}
