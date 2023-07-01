package com.backend.girnartour.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales_header")
public class SalesHeader {

    @Id
    @Column(name = "invoice_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private BigDecimal invoiceAmt;

    private BigDecimal discount;

    @Column(name = "invoice_vat")
    private BigDecimal vatAmt;

    @Column(name = "invoice_total")
    private BigDecimal totalInvoiceAmt;

    @Column(name = "total_amount_paid")
    public BigDecimal totalAmountPaid;

    @OneToMany(mappedBy = "salesHeader",cascade = CascadeType.ALL)
    private List<SalesDetail> salesDetailList;

    public BigDecimal TotalInvoiceAmount(){
        BigDecimal ia=getInvoiceAmt();
        BigDecimal va=getVatAmt();
        BigDecimal da=getDiscount();
        BigDecimal ans= ia.add(va);
        return ans.subtract(da);
    }



}
