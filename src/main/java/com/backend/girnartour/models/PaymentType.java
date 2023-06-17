package com.backend.girnartour.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_type")
public class PaymentType {


    @Id
    private String id;

    @Column(name = "payment_type")
    private String type;

    private String description;
}
