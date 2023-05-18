package com.backend.girnartour.RequestDTOs;

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

    @Size(max=6,min=6,message = "Enter a valid 6 digit postal code")
    public Integer postalCode;

    public String country;

    @Size(min=10,max=10,message = "Enter a Valid 10 digit contact number")
    public String telephone;

    @NotEmpty
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE,message = "Enter a valid Email !")
    public String email;

    public String notes;

}
