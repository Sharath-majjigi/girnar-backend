package com.backend.girnartour.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales_receipts")
public class SalesReceipts {

    @Id
    @Column(name = "receipt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "invoice_number")
    private SalesHeader salesHeader;

    @Column(name = "date_received",columnDefinition = "TIMESTAMP")
    private Timestamp date;

    @Column(name = "amount_received")
    private BigDecimal amountReceived;

    @Column(name = "receipt_type")
    private String receiptType;

    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

}
