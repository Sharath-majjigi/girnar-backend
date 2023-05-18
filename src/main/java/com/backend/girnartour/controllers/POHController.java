package com.backend.girnartour.controllers;

import com.backend.girnartour.RequestDTOs.POHeaderRequest;
import com.backend.girnartour.services.POHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/poh")
public class POHController {

    @Autowired
    private POHeaderService poHeaderService;

    @PostMapping("/{userId}/{vendorId}")
    public ResponseEntity<?> createPurchaseOrder(@PathVariable String userId, @PathVariable String vendorId, @RequestBody POHeaderRequest request){
        return poHeaderService.createPurchaseOrderHeader(userId, vendorId, request);
    }

    @GetMapping("/{pId}")
    public ResponseEntity<?> getOrderHeaderById(@PathVariable String pId){
        return poHeaderService.getPurchaseOrderById(pId);
    }

    @GetMapping
    public ResponseEntity<?> getOrderHeaders(){
        return poHeaderService.getAllPurchaseOrders();
    }

    @DeleteMapping("/{pId}")
    public ResponseEntity<?> deleteOrderHeaderById(@PathVariable String pId){
        return poHeaderService.deletePurchaseOrderHeader(pId);
    }

}
