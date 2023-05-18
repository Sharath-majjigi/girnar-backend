package com.backend.girnartour.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_order_detail")
public class PurchaseOrderDetail {

    @Id
    private String id;

    @Column(name = "pax_name")
    private String paxName;

    @Column(name = "description_1")
    private String description1;

    @Column(name = "description_2")
    private String description2;

    @Column(name = "purchase_amount")
    private Double purchaseCost;

    @Column(name = "sell_amount")
    private Double sellPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "po_number")
    private PurchaseOrderHeader purchaseOrderHeader;
}
