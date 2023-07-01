package com.backend.girnartour.controllers;

import com.backend.girnartour.RequestDTOs.POHeaderRequest;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdatePOH;
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
    public ResponseEntity<?> createPurchaseOrder(@PathVariable String userId, @PathVariable Integer vendorId, @RequestBody POHeaderRequest request){
        return poHeaderService.createPurchaseOrderHeader(userId, vendorId, request);
    }

    @GetMapping("/{pId}")
    public ResponseEntity<?> getOrderHeaderById(@PathVariable Integer pId){
        return poHeaderService.getPurchaseOrderById(pId);
    }

    @GetMapping
    public ResponseEntity<?> getOrderHeaders(){
        return poHeaderService.getAllPurchaseOrders();
    }
    @GetMapping("/not-in-sales")
    public ResponseEntity<?> getOrderHeadersNotInSalesHeader(){
        return poHeaderService.getAllPurchaseOrdersNotInSalesHeader();
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updatePOH(@PathVariable Integer id, @RequestBody UpdatePOH updatePOH){
        return poHeaderService.updatePOH(updatePOH, id);
    }

    @DeleteMapping("/{pId}")
    public ResponseEntity<?> deleteOrderHeaderById(@PathVariable Integer pId){
        return poHeaderService.deletePurchaseOrderHeader(pId);
    }

}
