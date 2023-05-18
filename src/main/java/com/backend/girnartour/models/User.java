package com.backend.girnartour.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User {

    @Id
    private String userid;

    @Column(name = "username")
    private String userName;

    private String email;

    private String password;

    private String role;

    private boolean active;

    private Timestamp lastLogin;

//    private String resetPasswordToken;

//    @CreationTimestamp
//    private Timestamp createdAt;
//
//    @UpdateTimestamp
//    private Timestamp updatedAt;

//    private String otp;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<PurchaseOrderHeader>poh=new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<PurchaseOrderPayments> purchaseOrderPayments=new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<SalesHeader> salesEntries=new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<SalesReceipts> salesReceipts=new ArrayList<>();


}
