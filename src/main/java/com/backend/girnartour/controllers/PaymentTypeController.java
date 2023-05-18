package com.backend.girnartour.controllers;

import com.backend.girnartour.RequestDTOs.PaymentTypeRequest;
import com.backend.girnartour.services.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/payment-type")
public class PaymentTypeController {

    @Autowired
    private PaymentTypeService paymentTypeService;

    @PostMapping("/")
    public ResponseEntity<?> createPaymentType(@Valid @RequestBody PaymentTypeRequest paymentTypeRequest){
        return paymentTypeService.createNewPaymentType(paymentTypeRequest);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllPaymentType(){
        return paymentTypeService.getAllPaymentType();
    }


    @DeleteMapping("/{type}")
    public ResponseEntity<?> deletePaymentType(@PathVariable String type){
        return paymentTypeService.deletePaymentType(type);
    }


}
