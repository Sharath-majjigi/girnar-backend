package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.POHeaderRequest;
import com.backend.girnartour.ResponseDTOs.POHeaderResponse;
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
public class POHeaderService {

    @Autowired
    private POHeaderDAO poHeaderDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VendorDAO vendorDAO;

    @Autowired
    private PohDetailDAO pohDetailDAO;

    @Autowired
    private PurchaseOrderPaymentsDAO paymentsDAO;

    public ResponseEntity<?> createPurchaseOrderHeader(String userId, String vendorId, POHeaderRequest poHeaderRequest){
        User user=userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        Vendor vendor=vendorDAO.findById(vendorId).orElseThrow(() -> new ResourceNotFoundException("Vendor","Id",vendorId));

        PurchaseOrderHeader poh=modelMapper.map(poHeaderRequest, PurchaseOrderHeader.class);
        String random_sequence= String.format("%040d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));
        poh.setId(random_sequence.substring(1,6));
        List<PurchaseOrderDetail> orderDetails=poHeaderRequest.getPod();
        for(PurchaseOrderDetail orderDetail:orderDetails){
            String uuid= String.format("%040d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));
            orderDetail.setId(uuid.substring(1,6));
        }

        List<PurchaseOrderHeader> orderHeaders=user.getPoh();
        orderHeaders.add(poh);
        userDAO.save(user);

        List<PurchaseOrderHeader> vendorOrderHeaders=vendor.getPoh();
        vendorOrderHeaders.add(poh);
        vendorDAO.save(vendor);

        List<PurchaseOrderDetail> details=new ArrayList<>();
        for(PurchaseOrderDetail od:orderDetails){
            od.setPurchaseOrderHeader(poh);
            details.add(od);
        }
        pohDetailDAO.saveAllAndFlush(details);

        poh.setPod(orderDetails);
        poh.setUser(user);
        poh.setVendor(vendor);
        poh.setAmount(poh.getTotalAmt());
        poh.setSellAmount(poh.getSellAmt());
//        poh.setVendorName(vendor.getVendorName());
        PurchaseOrderHeader saved=poHeaderDAO.save(poh);
        POHeaderResponse response=modelMapper.map(saved,POHeaderResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public ResponseEntity<?> getPurchaseOrderById(String pId){
        PurchaseOrderHeader header=poHeaderDAO.findById(pId).orElseThrow(()-> new ResourceNotFoundException("PurchaseOrderHeader","Id",pId));
        POHeaderResponse response=modelMapper.map(header,POHeaderResponse.class);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<?> getAllPurchaseOrders(){
        List<PurchaseOrderHeader> orderHeaders=poHeaderDAO.findAll();
        List<POHeaderResponse> responses=orderHeaders.stream().map(orders -> modelMapper.map(orders,POHeaderResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

    public ResponseEntity deletePurchaseOrderHeader(String pId){
        PurchaseOrderHeader header=poHeaderDAO.findById(pId).orElseThrow(()-> new ResourceNotFoundException("PurchaseOrderHeader","Id",pId));
        for(PurchaseOrderDetail detail:header.getPod()){
            pohDetailDAO.delete(detail);
        }
        //Find all payments with header as FK
        List<PurchaseOrderPayments> allPayments=paymentsDAO.findAllByPurchaseOrderHeaderId(pId);

        for(PurchaseOrderPayments payment: allPayments){
            paymentsDAO.delete(payment);
        }
        poHeaderDAO.delete(header);
        return new ResponseEntity<>("PurchaseOrderHeader Deleted Successfully !",HttpStatus.OK);
    }
}
