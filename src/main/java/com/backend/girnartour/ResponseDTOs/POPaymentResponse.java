package com.backend.girnartour.ResponseDTOs;


import com.backend.girnartour.models.PurchaseOrderDetail;
import com.backend.girnartour.models.PurchaseOrderHeader;
import com.backend.girnartour.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class POPaymentResponse {


    public Integer id;

    public POHeaderResponse purchaseOrderHeader;

    public String date;

    public BigDecimal amountPaid;

    public String paymentType;

    public String description;

    public UserResponseDTO user;
}
