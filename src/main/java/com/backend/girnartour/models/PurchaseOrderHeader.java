package com.backend.girnartour.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_order_header")
public class PurchaseOrderHeader {

    @Id
    @Column(name = "po_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "po_date",columnDefinition = "TIMESTAMP")
    private Timestamp poDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vendor_code")
    private Vendor vendor;

    private String description;

    private String remarks;

    @Column(name = "po_amount")
    private BigDecimal amount;

    @Column(name = "sell_amount")
    private BigDecimal sellAmount;


    @OneToMany(mappedBy = "purchaseOrderHeader",cascade = CascadeType.ALL)
    private List<PurchaseOrderDetail> pod=new ArrayList<>();

    @Column(name = "total_amount_paid")
    public BigDecimal totalAmountPaid;

    @Transient
    public BigDecimal getTotalAmt(){
        BigDecimal sum = BigDecimal.ZERO;
        List<PurchaseOrderDetail> purchaseOrderDetails=getPod();
        for(PurchaseOrderDetail od:purchaseOrderDetails) {
            sum = sum.add(od.getPurchaseCost());
        }
        return sum;
    }

    @Transient
    public BigDecimal getSellAmt(){
        BigDecimal sum= BigDecimal.ZERO;
        List<PurchaseOrderDetail> purchaseOrderDetails=getPod();
        for(PurchaseOrderDetail od:purchaseOrderDetails){
            sum= sum.add(od.getSellPrice());
        }
        return sum;
    }

}
