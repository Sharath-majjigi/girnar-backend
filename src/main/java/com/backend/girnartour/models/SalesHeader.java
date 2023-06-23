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
@Table(name = "sales_header")
public class SalesHeader {

    @Id
    @Column(name = "invoice_number")
    private String id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "invoice_date")
    private String date;


    @Column(name = "sales_category")
    private String salesCat;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_code")
    private Customer customer;


    private String description;

    private String message;

    @Column(name = "invoice_amount")
    private Double invoiceAmt;

    private Double discount;

    @Column(name = "invoice_vat")
    private Double vatAmt;

    @Column(name = "invoice_total")
    private Double totalInvoiceAmt;

    @Column(name = "total_amount_paid")
    public Double totalAmountPaid;

    @OneToMany(mappedBy = "salesHeader",cascade = CascadeType.ALL)
    private List<SalesDetail> salesDetailList;

    public Double TotalInvoiceAmount(){
        Double ia=getInvoiceAmt();
        Double va=getVatAmt();
        Double da=getDiscount();
        return ia-da+va;
    }



}
