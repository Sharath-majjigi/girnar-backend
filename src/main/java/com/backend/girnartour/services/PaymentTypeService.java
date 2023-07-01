package com.backend.girnartour.services;


import com.backend.girnartour.RequestDTOs.PaymentTypeRequest;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdatePaymentType;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.PaymentType;
import com.backend.girnartour.repository.PaymentTypeDAO;
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
public class PaymentTypeService {


    @Autowired
    private PaymentTypeDAO paymentTypeDAO;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<?> createNewPaymentType(PaymentTypeRequest paymentTypeRequest){
        String random_sequence= String.format("%040d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));
        PaymentType toSave=modelMapper.map(paymentTypeRequest, PaymentType.class);
       PaymentType saved=paymentTypeDAO.save(toSave);
       return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllPaymentType(){
        return new ResponseEntity<>(paymentTypeDAO.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<?> deletePaymentType(Integer type){
        PaymentType paymentType;
        try{
            paymentType=paymentTypeDAO.findById(type).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("PaymentType","Type",String.valueOf(type));
        }
        paymentTypeDAO.delete(paymentType);
        return new ResponseEntity<>("Deleted SuccessFully",HttpStatus.OK);
    }

    public ResponseEntity<?> updatePaymentType(Integer id,UpdatePaymentType updatePaymentType){
        PaymentType paymentType=paymentTypeDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("PaymentType","Id",String.valueOf(id)));
        if(updatePaymentType.getDescription()!=null || !updatePaymentType.getDescription().isEmpty()){
            paymentType.setDescription(updatePaymentType.getDescription());
        }
        if(updatePaymentType.getType()!=null || !updatePaymentType.getType().isEmpty()){
            paymentType.setType(updatePaymentType.getType());
        }
        PaymentType updated=paymentTypeDAO.save(paymentType);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    public ResponseEntity<?> updatePaymentType(UpdatePaymentType updatePaymentType, Integer id){
        PaymentType paymentType=paymentTypeDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("PaymentType","Id",String.valueOf(id)));
        BeanUtils.copyProperties(updatePaymentType, paymentType, getNullPropertyNames(updatePaymentType));
        PaymentType updatedPaymentType = paymentTypeDAO.save(paymentType);
        return ResponseEntity.ok(updatedPaymentType);
    }
    private static String[] getNullPropertyNames(Object source) {
        BeanWrapperImpl src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> src.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}

