package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.SalesCategoryRequest;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.SalesCategory;
import com.backend.girnartour.repository.SalesCategoryDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SalesCategoryService {

    @Autowired
    private SalesCategoryDAO salesCategoryDAO;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<?> createNewSalesCategory(SalesCategoryRequest salesCategoryRequest){

        SalesCategory toSave=modelMapper.map(salesCategoryRequest, SalesCategory.class);
        System.out.println(toSave.getCategory() + " desc = "+toSave.getDescription());
        SalesCategory saved=salesCategoryDAO.save(toSave);
        System.out.println(saved.getDescription() + " desc = "+saved.getDescription());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllSalesCategory(){
        return new ResponseEntity<>(salesCategoryDAO.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<?> deleteSalesCategory(String type){
        SalesCategory salesCategory;
        try{
            salesCategory=salesCategoryDAO.findById(type).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("SalesCategory","Type",type);
        }
        salesCategoryDAO.delete(salesCategory);
        return new ResponseEntity<>("Deleted SuccessFully",HttpStatus.OK);
    }
}
