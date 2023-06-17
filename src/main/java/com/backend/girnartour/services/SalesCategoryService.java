package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.SalesCategoryRequest;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdateSalesCategory;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.SalesCategory;
import com.backend.girnartour.repository.SalesCategoryDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.math.BigInteger;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class SalesCategoryService {

    @Autowired
    private SalesCategoryDAO salesCategoryDAO;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<?> createNewSalesCategory(SalesCategoryRequest salesCategoryRequest){

        SalesCategory toSave=modelMapper.map(salesCategoryRequest, SalesCategory.class);
        SalesCategory saved=salesCategoryDAO.save(toSave);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllSalesCategory(){
        return new ResponseEntity<>(salesCategoryDAO.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<?> deleteSalesCategory(Integer type){
        SalesCategory salesCategory;
        try{
            salesCategory=salesCategoryDAO.findById(type).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("SalesCategory","Type",Integer.toString(type));
        }
        salesCategoryDAO.delete(salesCategory);
        return new ResponseEntity<>("Deleted SuccessFully",HttpStatus.OK);
    }

    public ResponseEntity<?> updateSalesCategory( Integer id,UpdateSalesCategory updateSalesCategory){
        SalesCategory salesCategory=salesCategoryDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("SalesCategory","Id",Integer.toString(id)));
        BeanUtils.copyProperties(updateSalesCategory, salesCategory, getNullPropertyNames(updateSalesCategory));
        SalesCategory updatedSalesCategory = salesCategoryDAO.save(salesCategory);
        return ResponseEntity.ok(updatedSalesCategory);
    }
    private static String[] getNullPropertyNames(Object source) {
        BeanWrapperImpl src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> src.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
