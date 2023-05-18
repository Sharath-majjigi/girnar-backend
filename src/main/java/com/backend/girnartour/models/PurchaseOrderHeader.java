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
@Table(name = "purchase_order_header")
public class PurchaseOrderHeader {

    @Id
    @Column(name = "po_number")
    private String id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "po_date")
    private String poDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vendor_code")
    private Vendor vendor;

    private String description;

    private String remarks;

    @Column(name = "po_amount")
    private Double amount;

    @Column(name = "sell_amount")
    private Double sellAmount;


    @OneToMany(mappedBy = "purchaseOrderHeader",cascade = CascadeType.ALL)
    private List<PurchaseOrderDetail> pod=new ArrayList<>();

    @Transient
    public Double getTotalAmt(){
        Double sum= 0D;
        List<PurchaseOrderDetail> purchaseOrderDetails=getPod();
        for(PurchaseOrderDetail od:purchaseOrderDetails){
            sum+=od.getPurchaseCost();
        }
        return sum;
    }

    @Transient
    public Double getSellAmt(){
        Double sum= 0D;
        List<PurchaseOrderDetail> purchaseOrderDetails=getPod();
        for(PurchaseOrderDetail od:purchaseOrderDetails){
            sum+=od.getSellPrice();
        }
        return sum;
    }

}
