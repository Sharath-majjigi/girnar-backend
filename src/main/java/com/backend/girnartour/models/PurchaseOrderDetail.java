package com.backend.girnartour.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_order_detail")
public class PurchaseOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pax_name")
    private String paxName;

    @Column(name = "description_1")
    private String description1;

    @Column(name = "description_2")
    private String description2;

    @Column(name = "purchase_amount")
    private BigDecimal purchaseCost;

    @Column(name = "sell_amount")
    private BigDecimal sellPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "po_number")
    private PurchaseOrderHeader purchaseOrderHeader;
}
