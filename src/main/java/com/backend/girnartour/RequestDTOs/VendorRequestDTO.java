package com.backend.girnartour.RequestDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorRequestDTO {

    @NotEmpty
    public String vendorName;
    @NotEmpty
    public String addressLine1;

    public String addressLine2;
    @NotEmpty
    public String city;
    @NotNull
    @Size(max=10,min=3,message = "Enter a valid postal code")
    public String postalCode;

    @NotEmpty
    public String country;

    @NotEmpty
    @Size(min=6,max=15,message = "Enter a Valid contact number")
    public String telephone;

    @NotEmpty
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE,message = "Enter a valid Email !")
    public String email;

    public String notes;

}
