package com.backend.girnartour.controllers;


import com.backend.girnartour.RequestDTOs.POPRequestDTO;
import com.backend.girnartour.RequestDTOs.POPaymentRequest;
import com.backend.girnartour.services.POPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pop")
public class POPController {

    @Autowired
    private POPaymentService poPaymentService;

    @PostMapping("/{userId}/{pohId}")
    public ResponseEntity<?> createPurchaseOrderPayment(@PathVariable String userId, @PathVariable String pohId, @RequestBody POPRequestDTO request){
        return poPaymentService.createPayment(userId, pohId, request);
    }

    @GetMapping("/{popId}")
    public ResponseEntity<?> getPurchaseOrderPaymentsById(@PathVariable String popId){
        return poPaymentService.getPurchaseOrderPaymentById(popId);
    }

    @GetMapping
    public ResponseEntity<?> getPurchaseOrderPayments(){
        return poPaymentService.getAllPurchaseOrderPayments();
    }

    @DeleteMapping("/{popId}")
    public ResponseEntity<?> deletePurchaseOrderPayment(@PathVariable String popId){
        return poPaymentService.deletePurchaseOrderPaymentsById(popId);
    }

}
