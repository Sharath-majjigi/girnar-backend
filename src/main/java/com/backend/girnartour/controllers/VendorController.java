package com.backend.girnartour.controllers;

import com.backend.girnartour.RequestDTOs.UserRequestDTO;
import com.backend.girnartour.RequestDTOs.UserUpdateDTO;
import com.backend.girnartour.RequestDTOs.VendorRequestDTO;
import com.backend.girnartour.RequestDTOs.VendorUpdateDTO;
import com.backend.girnartour.ResponseDTOs.UserResponseDTO;
import com.backend.girnartour.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/vendor")
@CrossOrigin
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> saveNewVendor(@Valid @RequestBody VendorRequestDTO vendorRequestDTO){
        return vendorService.addNewVendor(vendorRequestDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public ResponseEntity<?> getAllVendors(){
        return vendorService.getAllVendors();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable String id){
        return vendorService.getVendorById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVendor(@RequestBody VendorUpdateDTO updateDTO, @PathVariable String id){
        return vendorService.updateVendor(updateDTO,id);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVendorById(@PathVariable String id){
        return vendorService.deleteVendorById(id);
    }
}
