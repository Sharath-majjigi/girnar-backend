package com.backend.girnartour.ResponseDTOs;

import com.backend.girnartour.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorResponseDTO {

    public Integer id;

    public String vendorName;

    public String addressLine1;

    public String addressLine2;

    public String city;

    public Integer postalCode;

    public String country;

    public String telephone;

    public String email;

    public String notes;

}
