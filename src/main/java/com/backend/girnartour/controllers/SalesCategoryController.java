package com.backend.girnartour.controllers;

import com.backend.girnartour.RequestDTOs.SalesCategoryRequest;
import com.backend.girnartour.services.SalesCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/sales-category")
public class SalesCategoryController {

    @Autowired
    private SalesCategoryService salesCategoryService;

    @PostMapping("/")
    public ResponseEntity<?> createSalesCategory(@Valid @RequestBody SalesCategoryRequest salesCategoryRequest){
        return salesCategoryService.createNewSalesCategory(salesCategoryRequest);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllSalesCategory(){
        return salesCategoryService.getAllSalesCategory();
    }

    @DeleteMapping("/{type}")
    public ResponseEntity<?> deleteSalesCategory(@PathVariable String type){
        return salesCategoryService.deleteSalesCategory(type);
    }


}
