package com.backend.girnartour.controllers;


import com.backend.girnartour.RequestDTOs.POPRequestDTO;
import com.backend.girnartour.RequestDTOs.POPaymentRequest;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdatePOPayment;
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
    public ResponseEntity<?> createPurchaseOrderPayment(@PathVariable String userId, @PathVariable Integer pohId, @RequestBody POPRequestDTO request){
        return poPaymentService.createPayment(userId, pohId, request);
    }

    @GetMapping("/{popId}")
    public ResponseEntity<?> getPurchaseOrderPaymentsById(@PathVariable Integer popId){
        return poPaymentService.getPurchaseOrderPaymentById(popId);
    }
    @GetMapping
    public ResponseEntity<?> getPurchaseOrderPayments(){
        return poPaymentService.getAllPurchaseOrderPayments();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePurchaseOrderPayment(@RequestBody List<UpdatePOPayment> updatePOPayment){
        return poPaymentService.updatePOP(updatePOPayment);
    }
    @DeleteMapping("/{popId}")
    public ResponseEntity<?> deletePurchaseOrderPayment(@PathVariable Integer popId){
        return poPaymentService.deletePurchaseOrderPaymentsById(popId);
    }

}
