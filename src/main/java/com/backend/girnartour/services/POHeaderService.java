package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.POHeaderRequest;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdatePOH;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdatePOHDetail;
import com.backend.girnartour.ResponseDTOs.POHeaderResponse;
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

    @Autowired
    private SalesHeaderDAO salesHeaderDAO;

    @Autowired
    private IdGenerationService service;

    public ResponseEntity<?> createPurchaseOrderHeader(String userId, String vendorId, POHeaderRequest poHeaderRequest){
        User user=userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        Vendor vendor=vendorDAO.findById(vendorId).orElseThrow(() -> new ResourceNotFoundException("Vendor","Id",vendorId));

        PurchaseOrderHeader poh=modelMapper.map(poHeaderRequest, PurchaseOrderHeader.class);
        String random_sequence= service.generateUniqueId(400000,"purchaseorderheader");
        poh.setId(random_sequence);
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
//        poh.setTotalAmountPaid(poh.getTotalAmt());
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

    public ResponseEntity<?> getAllPurchaseOrdersNotInSalesHeader(){
        List<SalesHeader> salesHeaders=salesHeaderDAO.findAll();
        List<PurchaseOrderHeader> purchaseOrderHeaders;

        List<String> excludedIds=new ArrayList<>();
        for(SalesHeader salesHeader:salesHeaders){
            List<SalesDetail> salesDetails=salesHeader.getSalesDetailList();
           for(SalesDetail detail:salesDetails){
               excludedIds.add(detail.getPoNumber());
           }
        }
        if(excludedIds!=null && !excludedIds.isEmpty()){
            purchaseOrderHeaders=poHeaderDAO.findByIdNotIn(excludedIds);
        }else{
            purchaseOrderHeaders=poHeaderDAO.findAll();
        }
        List<POHeaderResponse> responses=purchaseOrderHeaders.stream().map(orders -> modelMapper.map(orders,POHeaderResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

    public ResponseEntity<?> updatePOH(UpdatePOH updatePOH, String id){
        PurchaseOrderHeader poh = poHeaderDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("POH","ID",id));

        List<UpdatePOHDetail> updatePOHDetails=updatePOH.getPod();
        for(int i=0; i<updatePOHDetails.size(); i++){
            UpdatePOHDetail updatePOHDetail=updatePOHDetails.get(i);
            PurchaseOrderDetail detail=pohDetailDAO.findByIdAndPurchaseOrderHeaderId(updatePOHDetail.getId(),id);

            if(updatePOHDetail.getPaxName()!=null){
                detail.setPaxName(updatePOHDetail.getPaxName());
            }
            if(updatePOHDetail.getDescription1()!=null){
                detail.setDescription1(updatePOHDetail.getDescription1());
            }
            if(updatePOHDetail.getDescription2()!=null){
                detail.setDescription2(updatePOHDetail.getDescription2());
            }
            if(updatePOHDetail.getSellPrice()!=null){
                detail.setSellPrice(updatePOHDetail.getSellPrice());
            }
            if(updatePOHDetail.getPurchaseCost()!=null){
                detail.setPurchaseCost(updatePOHDetail.getPurchaseCost());
            }

            pohDetailDAO.save(detail);
        }

        if(updatePOH.getPoDate()!=null){
            poh.setPoDate(updatePOH.getPoDate());
        }
        if(updatePOH.getDescription()!=null){
            poh.setDescription(updatePOH.getDescription());
        }
        if(updatePOH.getRemarks()!=null){
            poh.setRemarks(updatePOH.getRemarks());
        }
        poh.setSellAmount(poh.getSellAmt());
        poh.setAmount(poh.getTotalAmt());
        PurchaseOrderHeader updatedPOH = poHeaderDAO.save(poh);
        POHeaderResponse responseDTO=modelMapper.map(updatedPOH, POHeaderResponse.class);
        return ResponseEntity.ok(responseDTO);
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
