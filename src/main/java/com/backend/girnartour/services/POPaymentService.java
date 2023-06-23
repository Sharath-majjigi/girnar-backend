package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.POPRequestDTO;
import com.backend.girnartour.RequestDTOs.POPaymentRequest;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.CustomerUpdateDTO;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdatePOPayment;
import com.backend.girnartour.ResponseDTOs.POPaymentResponse;
import com.backend.girnartour.ResponseDTOs.VendorResponseDTO;
import com.backend.girnartour.constants.UserConstants;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.*;
import com.backend.girnartour.repository.*;
import org.hibernate.cfg.annotations.Nullability;
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
public class POPaymentService {

    @Autowired
    private PurchaseOrderPaymentsDAO paymentsDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private POHeaderDAO poHeaderDAO;


    @Autowired
    private VendorDAO vendorDAO;

    @Autowired
    private IdGenerationService service;


    public ResponseEntity<?> createPayment(String userId, String pohId, POPRequestDTO paymentRequest){
        User user=userDAO.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User","Id",userId));
        PurchaseOrderHeader poh=poHeaderDAO.findById(pohId).orElseThrow(() ->new ResourceNotFoundException("POH","Id",pohId));
//        Vendor vendor=vendorDAO.findById(vendorId).orElseThrow(() ->new ResourceNotFoundException("Vendor","Id",vendorId));
        Double totalAmountPaid=0.0;
        List<POPaymentRequest> paymentRequests=paymentRequest.getRequests();
        for(POPaymentRequest request:paymentRequests){
            PurchaseOrderPayments pop=new PurchaseOrderPayments();
            String uuid= service.generateUniqueId(500000,"purchaseorderpayment");
            pop.setId(uuid);
            totalAmountPaid = totalAmountPaid + request.getAmountPaid();
            pop.setPurchaseOrderHeader(poh);
            pop.setDate(request.getDate());
            pop.setAmountPaid(request.getAmountPaid());
            pop.setPaymentType(request.getPaymentType());
            pop.setDescription(request.getDescription());
            pop.setUser(user);
            paymentsDAO.save(pop);
        }

        Double total= (poh.getTotalAmountPaid()!=null ? poh.getTotalAmountPaid() : 0.0);
        poh.setTotalAmountPaid(totalAmountPaid+total);
        poHeaderDAO.save(poh);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    public ResponseEntity<?> getAllPurchaseOrderPayments(){
        List<PurchaseOrderPayments> orderPayments=paymentsDAO.findAll();
        List<POPaymentResponse> responses=orderPayments.stream().map(orders -> modelMapper.map(orders,POPaymentResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

    public ResponseEntity<?> getPurchaseOrderPaymentById(String popId){
        PurchaseOrderPayments payments=paymentsDAO.findById(popId).orElseThrow(()-> new ResourceNotFoundException("PurchaseOrderPayment","Id",popId));
        POPaymentResponse response=modelMapper.map(payments,POPaymentResponse.class);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<?> updatePOP(List<UpdatePOPayment> updateDTOs){
        for(int i=0; i<updateDTOs.size(); i++){
            UpdatePOPayment updatePOPayment=updateDTOs.get(i);
            PurchaseOrderPayments existingPOP = paymentsDAO.findById(updatePOPayment.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("POP","ID",updatePOPayment.getId()));
            BeanUtils.copyProperties(updatePOPayment, existingPOP, getNullPropertyNames(updatePOPayment));
            paymentsDAO.save(existingPOP);
        }
        return new ResponseEntity<>("Updated !",HttpStatus.OK);
    }
    private static String[] getNullPropertyNames(Object source) {
        BeanWrapperImpl src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> src.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public ResponseEntity deletePurchaseOrderPaymentsById(String popId){
        PurchaseOrderPayments payments=paymentsDAO.findById(popId).orElseThrow(()-> new ResourceNotFoundException("PurchaseOrderPayment","Id",popId));
        paymentsDAO.delete(payments);
        return new ResponseEntity<>("PurchaseOrderPayment deleted Successfully !",HttpStatus.OK);
    }


}
