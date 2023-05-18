package com.backend.girnartour.controllers;

import com.backend.girnartour.RequestDTOs.CustomerRequest;
import com.backend.girnartour.RequestDTOs.CustomerUpdateDTO;
import com.backend.girnartour.services.CustomerService;
import com.backend.girnartour.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> saveNewCustomer(@Valid @RequestBody CustomerRequest customerRequest){
        return customerService.addNewCustomer(customerRequest);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public ResponseEntity<?> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id){
        return customerService.getCustomerById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdateDTO updateDTO, @PathVariable String id){
        return customerService.updateCustomer(updateDTO,id);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable String id){
        return customerService.deleteCustomerById(id);
    }

}
