package com.backend.girnartour.services;


import com.backend.girnartour.RequestDTOs.PaymentTypeRequest;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.PaymentType;
import com.backend.girnartour.repository.PaymentTypeDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentTypeService {


    @Autowired
    private PaymentTypeDAO paymentTypeDAO;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<?> createNewPaymentType(PaymentTypeRequest paymentTypeRequest){
        PaymentType toSave=modelMapper.map(paymentTypeRequest, PaymentType.class);
       PaymentType saved=paymentTypeDAO.save(toSave);
       return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllPaymentType(){
        return new ResponseEntity<>(paymentTypeDAO.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<?> deletePaymentType(String type){
        PaymentType paymentType;
        try{
            paymentType=paymentTypeDAO.findById(type).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("PaymentType","Type",type);
        }
        paymentTypeDAO.delete(paymentType);
        return new ResponseEntity<>("Deleted SuccessFully",HttpStatus.OK);
    }
}
