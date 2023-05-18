package com.backend.girnartour.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales_category")
public class SalesCategory {


    @Id
    @Column(name = "sales_category")
    private String category;

    private String description;

}
