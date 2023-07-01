package com.backend.girnartour.controllers;


import com.backend.girnartour.RequestDTOs.SalesHeaderRequest;
import com.backend.girnartour.RequestDTOs.SalesReceiptDTO;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdateSalesReceipt;
import com.backend.girnartour.services.SalesReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales-receipt")
public class SalesReceiptController {

    @Autowired
    private SalesReceiptService salesReceiptService;

    @PostMapping("/{userid}/{salesHeaderId}")
    public ResponseEntity<?> createSalesReceipt(@PathVariable String userid, @PathVariable Integer salesHeaderId, @RequestBody SalesReceiptDTO salesReceiptDTO){
        return salesReceiptService.createSalesReceipt(userid, salesHeaderId, salesReceiptDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSalesReceipt(){
        return salesReceiptService.getAllSalesReceipt();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getSalesReceiptById(@PathVariable Integer id){
        return salesReceiptService.getSalesReceiptById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSalesReceipt(@RequestBody List<UpdateSalesReceipt> salesReceipt){
        return salesReceiptService.updateSalesReceipt(salesReceipt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalesReceipt(@PathVariable Integer id){
        return salesReceiptService.deleteSalesReceipt(id);
    }
}
