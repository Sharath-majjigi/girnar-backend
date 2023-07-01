package com.backend.girnartour.controllers;

import com.backend.girnartour.RequestDTOs.PaymentTypeRequest;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdatePaymentType;
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
    public ResponseEntity<?> deletePaymentType(@PathVariable Integer type){
        return paymentTypeService.deletePaymentType(type);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentType(@PathVariable Integer id, @RequestBody UpdatePaymentType updatePaymentType){
        return paymentTypeService.updatePaymentType(id, updatePaymentType);
    }

}
