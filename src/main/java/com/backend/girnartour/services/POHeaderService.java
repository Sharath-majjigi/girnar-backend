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

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
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



    @Transactional
    public ResponseEntity<?> createPurchaseOrderHeader(String userId, Integer vendorId, POHeaderRequest poHeaderRequest){
        User user=userDAO.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        Vendor vendor=vendorDAO.findById(vendorId).orElseThrow(() -> new ResourceNotFoundException("Vendor","Id",String.valueOf(vendorId)));
//        PurchaseOrderHeader poh=modelMapper.map(poHeaderRequest, PurchaseOrderHeader.class);
        PurchaseOrderHeader poh=new PurchaseOrderHeader();
        poh.setDescription(poHeaderRequest.getDescription());
        poh.setRemarks(poHeaderRequest.getRemarks());
        poh.setPod(poHeaderRequest.getPod());

        List<PurchaseOrderDetail> orderDetails=poHeaderRequest.getPod();

//        List<PurchaseOrderHeader> orderHeaders=user.getPoh();
//        orderHeaders.add(poh);
//        userDAO.save(user);

//        List<PurchaseOrderHeader> vendorOrderHeaders=vendor.getPoh();
//        vendorOrderHeaders.add(poh);
//        vendorDAO.save(vendor);

        List<PurchaseOrderDetail> details=new ArrayList<>();
        for(PurchaseOrderDetail od:orderDetails){
            od.setPurchaseOrderHeader(poh);
            details.add(od);
        }


        poh.setPod(orderDetails);
        poh.setUser(user);
        poh.setVendor(vendor);
        poh.setAmount(poh.getTotalAmt());
        poh.setSellAmount(poh.getSellAmt());

        Timestamp timestamp = getTimestamp(poHeaderRequest);
        poh.setPoDate(timestamp);

        PurchaseOrderHeader saved=poHeaderDAO.save(poh);
        pohDetailDAO.saveAllAndFlush(details);
        return new ResponseEntity<>("POH saved with ID: "+saved.getId(), HttpStatus.OK);

    }

    private static Timestamp getTimestamp(POHeaderRequest poHeaderRequest) {
        String dateString = poHeaderRequest.getPoDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, dateFormatter);

        LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Jakarta")); // Get the current time
        LocalDateTime localDateTime = LocalDateTime.of(localDate, currentTime);

        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return timestamp;
    }

    public ResponseEntity<?> getPurchaseOrderById(Integer pId){
        PurchaseOrderHeader header=poHeaderDAO.findById(pId).orElseThrow(()-> new ResourceNotFoundException("PurchaseOrderHeader","Id",String.valueOf(pId)));
        POHeaderResponse response=modelMapper.map(header,POHeaderResponse.class);
//        String dateString = getDateString(header);
//        response.setPoDate(dateString);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private static String getDateString(PurchaseOrderHeader header) {
        LocalDateTime localDateTime = header.getPoDate().toLocalDateTime();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = localDateTime.format(dateFormatter);
        return dateString;
    }

    public ResponseEntity<?> getAllPurchaseOrders(){
        List<PurchaseOrderHeader> orderHeaders=poHeaderDAO.findAll();
        List<POHeaderResponse> responses=orderHeaders.stream().map(orders -> modelMapper.map(orders,POHeaderResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }

    public ResponseEntity<?> getAllPurchaseOrdersNotInSalesHeader(){
        List<SalesHeader> salesHeaders=salesHeaderDAO.findAll();
        List<PurchaseOrderHeader> purchaseOrderHeaders;

        List<Integer> excludedIds=new ArrayList<>();
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

    public ResponseEntity<?> updatePOH(UpdatePOH updatePOH, Integer id){
        PurchaseOrderHeader poh = poHeaderDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("POH","ID",String.valueOf(id)));

        List<UpdatePOHDetail> updatePOHDetails=updatePOH.getPod();
        if(updatePOH.getPod()!=null){

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
        }

        if(updatePOH.getPoDate()!=null){
            String dateString = updatePOH.getPoDate();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, dateFormatter);

            LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Jakarta")); // Get the current time
            LocalDateTime localDateTime = LocalDateTime.of(localDate, currentTime);

            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            poh.setPoDate(timestamp);
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

    public ResponseEntity deletePurchaseOrderHeader(Integer pId){
        PurchaseOrderHeader header=poHeaderDAO.findById(pId).orElseThrow(()-> new ResourceNotFoundException("PurchaseOrderHeader","Id",String.valueOf(pId)));
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
