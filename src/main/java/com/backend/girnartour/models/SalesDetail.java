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
@Table(name = "sales_detail")
public class SalesDetail {

    @Id
    private String id;
//
//    private String purchaseNumber;
//
//    private String date;
//
//    private String description;
//
//    private Double totalPurchaseCost;
//
//    private Double totalSellPrice;
//
//    @ManyToOne
//    private SalesHeader salesHeader;


    @Column(name = "po_number")
    private String poNumber;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "invoice_number")
    private SalesHeader salesHeader;

}
