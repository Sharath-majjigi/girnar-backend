package com.backend.girnartour.controllers;

import com.backend.girnartour.RequestDTOs.SalesHeaderRequest;
import com.backend.girnartour.services.SalesHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales-header")
public class SalesHeaderController {

    @Autowired
    private SalesHeaderService salesHeaderService;

    @PostMapping("/{userid}/{customerId}")
    public ResponseEntity<?> createSalesHeader(@PathVariable String userid, @PathVariable String customerId, @RequestBody SalesHeaderRequest salesHeaderRequest){
        return salesHeaderService.createSalesHeader(userid,customerId,salesHeaderRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSalesHeader(){
        return salesHeaderService.getAllSalesHeader();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalesHeaderById(@PathVariable String id){
        return salesHeaderService.getSalesHeaderById(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalesHeader(@PathVariable String id){
        return salesHeaderService.deleteSalesHeader(id);
    }
}
