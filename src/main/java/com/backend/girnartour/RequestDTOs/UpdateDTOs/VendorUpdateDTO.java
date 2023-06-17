package com.backend.girnartour.RequestDTOs.UpdateDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorUpdateDTO {


    public String vendorName;

    public String addressLine1;

    public String addressLine2;

    public String city;

    @Size(max=10,min=3,message = "Enter a valid postal code")
    public Integer postalCode;

    public String country;

    @Size(min=6,max=15,message = "Enter a Valid contact number")
    public String telephone;

    @NotEmpty
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE,message = "Enter a valid Email !")
    public String email;

    public String notes;

}
