package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.POPRequestDTO;
import com.backend.girnartour.RequestDTOs.POPaymentRequest;
import com.backend.girnartour.ResponseDTOs.POPaymentResponse;
import com.backend.girnartour.constants.UserConstants;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.*;
import com.backend.girnartour.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

//    @Autowired
//    private PaymentDetailDAO paymentDetailDAO;

    @Autowired
    private VendorDAO vendorDAO;


//    public ResponseEntity<?> createPurchaseOrderPayment(String userId, String pohID, POPaymentRequest paymentRequest){
//        User user=userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
//        PurchaseOrderHeader poh=poHeaderDAO.findById(pohID).orElseThrow(() -> new ResourceNotFoundException("POH","Id",pohID));
//
//        PurchaseOrderPayments orderPayments=modelMapper.map(paymentRequest, PurchaseOrderPayments.class);
//        orderPayments.setId(UserConstants.random_sequence.substring(1,6));
//
//        List<PaymentDetail> paymentDetails=paymentRequest.getPaymentDetails();
//        for(PaymentDetail paymentDetail:paymentDetails){
//            String uuid= String.format("%040d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));
//            paymentDetail.setId(uuid.substring(1,6));
//        }
//
//        List<PurchaseOrderPayments> orderPaymentsList=user.getPurchaseOrderPayments();
//        orderPaymentsList.add(orderPayments);
//        userDAO.save(user);
//
//
//        List<PaymentDetail> details=new ArrayList<>();
//        for(PaymentDetail pd:paymentDetails){
//            pd.setPurchaseOrderPayments(orderPayments);
//            details.add(pd);
//        }
//        paymentDetailDAO.saveAllAndFlush(details);
//        orderPayments.setPurchaseOrderHeader(poh);
//        orderPayments.setUser(user);
//        orderPayments.setPaymentDetails(paymentDetails);
//        orderPayments.setTotalPaid(orderPayments.getTotalPaidAmt());
//        PurchaseOrderPayments saved=paymentsDAO.save(orderPayments);
//        POPaymentResponse response=modelMapper.map(saved,POPaymentResponse.class);
//        response.setVendorName(poh.getVendorName());
//        response.setAmount(poh.getTotalAmt());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    public ResponseEntity<?> createPayment(String userId, String pohId, POPRequestDTO paymentRequest){
        User user=userDAO.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User","Id",userId));
        PurchaseOrderHeader poh=poHeaderDAO.findById(pohId).orElseThrow(() ->new ResourceNotFoundException("POH","Id",pohId));
//        Vendor vendor=vendorDAO.findById(vendorId).orElseThrow(() ->new ResourceNotFoundException("Vendor","Id",vendorId));

        List<POPaymentRequest> paymentRequests=paymentRequest.getRequests();
        for(POPaymentRequest request:paymentRequests){
            PurchaseOrderPayments pop=new PurchaseOrderPayments();
            String uuid= String.format("%040d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));
            pop.setId(uuid.substring(1,6));
            pop.setPurchaseOrderHeader(poh);
            pop.setDate(request.getDate());
            pop.setAmountPaid(request.getAmountPaid());
            pop.setPaymentType(request.getPaymentType());
            pop.setDescription(request.getDescription());
            pop.setUser(user);
            paymentsDAO.save(pop);
        }
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

    public ResponseEntity deletePurchaseOrderPaymentsById(String popId){
        PurchaseOrderPayments payments=paymentsDAO.findById(popId).orElseThrow(()-> new ResourceNotFoundException("PurchaseOrderPayment","Id",popId));
        paymentsDAO.delete(payments);
        return new ResponseEntity<>("PurchaseOrderPayment deleted Successfully !",HttpStatus.OK);
    }


}
