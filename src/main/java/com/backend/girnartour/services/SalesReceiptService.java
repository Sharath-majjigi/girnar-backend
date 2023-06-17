package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.SalesReceiptDTO;
import com.backend.girnartour.RequestDTOs.SalesReceiptRequest;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdatePOPayment;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdateSalesReceipt;
import com.backend.girnartour.ResponseDTOs.POPaymentResponse;
import com.backend.girnartour.ResponseDTOs.SalesHeaderResponse;
import com.backend.girnartour.ResponseDTOs.SalesReceiptResponse;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.*;
import com.backend.girnartour.repository.SalesHeaderDAO;
import com.backend.girnartour.repository.SalesReceiptDAO;
import com.backend.girnartour.repository.UserDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SalesReceiptService {

    @Autowired
    private SalesReceiptDAO salesReceiptDAO;

    @Autowired
    private SalesHeaderDAO salesHeaderDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IdGenerationService service;

    public ResponseEntity<?> createSalesReceipt(String userid, String salesHeaderId, SalesReceiptDTO salesReceiptDTO){
        User user=userDAO.findById(userid).orElseThrow(() ->new ResourceNotFoundException("User","Id",userid));
        SalesHeader sh=salesHeaderDAO.findById(salesHeaderId).orElseThrow(() ->new ResourceNotFoundException("SalesHeader","Id",salesHeaderId));

        List<SalesReceiptRequest> salesReceiptRequestList=salesReceiptDTO.getRequests();
        for(SalesReceiptRequest request:salesReceiptRequestList){
            SalesReceipts salesReceipts=new SalesReceipts();
            String uuid= service.generateUniqueId(600000,"salesreceipt");
            salesReceipts.setId(uuid);
            salesReceipts.setSalesHeader(sh);
            salesReceipts.setDate(request.getDate());
            salesReceipts.setAmountReceived(request.getAmountReceived());
            salesReceipts.setReceiptType(request.getReceiptType());
            salesReceipts.setDescription(request.getDescription());
            salesReceipts.setUser(user);
            salesReceiptDAO.save(salesReceipts);
        }
        return new ResponseEntity<>("Successfully Created",HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllSalesReceipt() {
        List<SalesReceipts> salesReceipts=salesReceiptDAO.findAll();
        List<SalesReceiptResponse> salesReceiptResponses=salesReceipts.stream()
                .map(receipt -> modelMapper.map(receipt, SalesReceiptResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(salesReceiptResponses,HttpStatus.OK);
    }

    public ResponseEntity<?> getSalesReceiptById(String id) {
        SalesReceipts salesReceipt=salesReceiptDAO.findById(id).orElseThrow(()->new ResourceNotFoundException("SalesReceipt","Id",id));
        SalesReceiptResponse response=modelMapper.map(salesReceipt,SalesReceiptResponse.class);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<?> updateSalesReceipt(List<UpdateSalesReceipt> updateDTOs){
        for(int i=0; i<updateDTOs.size(); i++){
            UpdateSalesReceipt updateSalesReceipt=updateDTOs.get(i);
            SalesReceipts existingSalesReceipt = salesReceiptDAO.findById(updateSalesReceipt.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("SalesReceipt","ID",updateSalesReceipt.getId()));
            BeanUtils.copyProperties(updateSalesReceipt, existingSalesReceipt, getNullPropertyNames(updateSalesReceipt));
            salesReceiptDAO.save(existingSalesReceipt);
        }
       return new ResponseEntity<>("Successfully updated !",HttpStatus.OK);
    }
    private static String[] getNullPropertyNames(Object source) {
        BeanWrapperImpl src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> src.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public ResponseEntity<?> deleteSalesReceipt(String id) {
        SalesReceipts salesReceipt=salesReceiptDAO.findById(id).orElseThrow(()->new ResourceNotFoundException("SalesReceipt","Id",id));
        salesReceiptDAO.delete(salesReceipt);
        return new ResponseEntity<>("SalesReceipt deleted successfully !",HttpStatus.OK);
    }
}
