package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.SalesHeaderRequest;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdateSalesDetail;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.UpdateSalesHeader;
import com.backend.girnartour.ResponseDTOs.SalesHeaderResponse;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.*;
import com.backend.girnartour.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SalesHeaderService {

    @Autowired
    private SalesHeaderDAO salesHeaderDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CustomerDAO customerDAO;


    @Autowired
    private SalesHeaderDetailDAO salesHeaderDetailDAO;
    @Autowired
    private POHeaderDAO poHeaderDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SalesReceiptDAO salesReceiptDAO;


    public ResponseEntity<?> createSalesHeader(String userId, Integer customerId, SalesHeaderRequest salesHeaderRequest){
        User user=userDAO.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        Customer customer=customerDAO.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer","Id",String.valueOf(customerId)));

        SalesHeader salesHeader=new SalesHeader();
//        String uuid= service.generateUniqueId(500000,"salesheader");
//        salesHeader.setId(uuid);
        salesHeader.setUser(user);
        salesHeader.setCustomer(customer);
        Timestamp timestamp=getTimeStamp(salesHeaderRequest);
        salesHeader.setDate(timestamp);
        salesHeader.setSalesCat(salesHeaderRequest.getSalesCat());
        salesHeader.setDescription(salesHeaderRequest.getDescription());
        salesHeader.setMessage(salesHeaderRequest.getMessage());
        salesHeader.setDiscount(salesHeaderRequest.getDiscount());
        salesHeader.setVatAmt(salesHeaderRequest.getVatAmt());

        BigDecimal invoiceAmt=BigDecimal.ZERO;
        List<SalesDetail> salesDetailList=salesHeaderRequest.getSalesDetailList();
        for(SalesDetail detail: salesDetailList){
//            PurchaseOrderHeader header=poHeaderDAO.findById()
            BigDecimal ia= poHeaderDAO.findByPOHId(detail.getPoNumber());
            invoiceAmt=invoiceAmt.add(ia);
        }
        salesHeader.setInvoiceAmt(invoiceAmt);
        salesHeader.setTotalInvoiceAmt(salesHeader.TotalInvoiceAmount());

        List<SalesDetail> salesDetails=salesHeaderRequest.getSalesDetailList();

//        List<SalesHeader> salesHeaders=user.getSalesEntries();
//        salesHeaders.add(salesHeader);
//        userDAO.save(user);
//
//        List<SalesHeader> salesHeaders1=customer.getSalesHeaderList();
//        salesHeaders1.add(salesHeader);
//        customerDAO.save(customer);

        List<SalesDetail> details=new ArrayList<>();
        for(SalesDetail sd:salesDetails){
            sd.setSalesHeader(salesHeader);
            details.add(sd);
        }

        salesHeader.setSalesDetailList(salesHeaderRequest.getSalesDetailList());
        SalesHeader saved=salesHeaderDAO.save(salesHeader);
        salesHeaderDetailDAO.saveAllAndFlush(details);
        return new ResponseEntity<>("Sales Header created Successfully with ID: "+saved.getId(), HttpStatus.CREATED);
    }

    private Timestamp getTimeStamp(SalesHeaderRequest salesHeaderRequest) {
        String dateString = salesHeaderRequest.getDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, dateFormatter);

        LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Jakarta")); // Get the current time
        LocalDateTime localDateTime = LocalDateTime.of(localDate, currentTime);

        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return timestamp;
    }

    public ResponseEntity<?> getAllSalesHeader() {
        List<SalesHeader> salesHeaders=salesHeaderDAO.findAll();
        List<SalesHeaderResponse> salesHeaderResponses=salesHeaders.stream().map(salesHeader -> modelMapper.map(salesHeader,SalesHeaderResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(salesHeaderResponses,HttpStatus.OK);
    }

    public ResponseEntity<?> getSalesHeaderById(Integer id) {
        SalesHeader salesHeader=salesHeaderDAO.findById(id).orElseThrow(()->new ResourceNotFoundException("SalesHeader","Id",String.valueOf(id)));
        SalesHeaderResponse response=modelMapper.map(salesHeader,SalesHeaderResponse.class);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateSalesHeader(Integer id, UpdateSalesHeader updateSalesHeader){
        SalesHeader salesHeader=salesHeaderDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("SalesHeader","Id",String.valueOf(id)));

        List<UpdateSalesDetail> updateSalesDetails=updateSalesHeader.getSalesDetailList();
        if(updateSalesHeader.getSalesDetailList()!=null){

            for(int i=0; i<updateSalesDetails.size(); i++){
                UpdateSalesDetail updateSalesDetail=updateSalesDetails.get(i);
                SalesDetail salesDetail=salesHeaderDetailDAO.findByIdAndSalesHeaderId(updateSalesDetail.getId(),id);

                if(updateSalesDetail.getPoNumber()!=null){
                    salesDetail.setPoNumber(updateSalesDetail.getPoNumber());
                }
                salesHeaderDetailDAO.save(salesDetail);
            }
        }
        if(updateSalesHeader.getDescription()!=null){
            salesHeader.setDescription(updateSalesHeader.getDescription());
        }
        if(updateSalesHeader.getDate()!=null){
            String dateString = updateSalesHeader.getDate();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString, dateFormatter);

            LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Jakarta")); // Get the current time
            LocalDateTime localDateTime = LocalDateTime.of(localDate, currentTime);

            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            salesHeader.setDate(timestamp);
        }
        if(updateSalesHeader.getSalesCat()!=null){
            salesHeader.setSalesCat(updateSalesHeader.getSalesCat());
        }
        if(updateSalesHeader.getMessage()!=null){
            salesHeader.setMessage(updateSalesHeader.getMessage());
        }
        if(updateSalesHeader.getDiscount()!=null){
            salesHeader.setDiscount(updateSalesHeader.getDiscount());
        }
        if(updateSalesHeader.getInvoiceAmt()!=null){
            salesHeader.setInvoiceAmt(updateSalesHeader.getInvoiceAmt());
        }
        if(updateSalesHeader.getVatAmt()!=null){
            salesHeader.setVatAmt(updateSalesHeader.getVatAmt());
        }
        if(updateSalesHeader.getTotalInvoiceAmt()!=null){
            salesHeader.setTotalInvoiceAmt(updateSalesHeader.getTotalInvoiceAmt());
        }

        SalesHeader updatedSalesHeader=salesHeaderDAO.save(salesHeader);
//        SalesHeaderResponse response=modelMapper.map(updatedSalesHeader,SalesHeaderResponse.class);
        return new ResponseEntity<>("Updated SalesHeader: "+updatedSalesHeader.getId(),HttpStatus.OK);
    }

    public ResponseEntity<?> deleteSalesHeader(Integer id){
        SalesHeader salesHeader=salesHeaderDAO.findById(id).orElseThrow(()->new ResourceNotFoundException("SalesHeader","Id",String.valueOf(id)));
        for(SalesDetail detail:salesHeader.getSalesDetailList()){
            salesHeaderDetailDAO.delete(detail);
        }
        List<SalesReceipts> receipts=salesReceiptDAO.findAllBySalesHeaderId(id);
        for(SalesReceipts receipt: receipts){
            salesReceiptDAO.delete(receipt);
        }

        salesHeaderDAO.delete(salesHeader);
        return new ResponseEntity<>("SalesHeader deleted successfully !",HttpStatus.OK);
    }
}
