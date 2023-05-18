package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.SalesReceiptDTO;
import com.backend.girnartour.RequestDTOs.SalesReceiptRequest;
import com.backend.girnartour.ResponseDTOs.SalesHeaderResponse;
import com.backend.girnartour.ResponseDTOs.SalesReceiptResponse;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.PurchaseOrderHeader;
import com.backend.girnartour.models.SalesHeader;
import com.backend.girnartour.models.SalesReceipts;
import com.backend.girnartour.models.User;
import com.backend.girnartour.repository.SalesHeaderDAO;
import com.backend.girnartour.repository.SalesReceiptDAO;
import com.backend.girnartour.repository.UserDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public ResponseEntity<?> createSalesReceipt(String userid, String salesHeaderId, SalesReceiptDTO salesReceiptDTO){
        User user=userDAO.findById(userid).orElseThrow(() ->new ResourceNotFoundException("User","Id",userid));
        SalesHeader sh=salesHeaderDAO.findById(salesHeaderId).orElseThrow(() ->new ResourceNotFoundException("SalesHeader","Id",salesHeaderId));

        List<SalesReceiptRequest> salesReceiptRequestList=salesReceiptDTO.getRequests();
        for(SalesReceiptRequest request:salesReceiptRequestList){
            SalesReceipts salesReceipts=new SalesReceipts();
            String uuid= String.format("%040d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));
            salesReceipts.setId(uuid.substring(1,6));
            salesReceipts.setSalesHeader(sh);
            salesReceipts.setDate(request.getDate());
            salesReceipts.setAmountReceived(request.getAmountReceived());
            salesReceipts.setReceiptType(request.getReceiptType());
            salesReceipts.setDescription(request.getDescription());
            salesReceipts.setUser(user);
            SalesReceipts saved=salesReceiptDAO.save(salesReceipts);
            SalesReceiptResponse response=modelMapper.map(saved,SalesReceiptResponse.class);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
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

    public ResponseEntity<?> deleteSalesReceipt(String id) {
        SalesReceipts salesReceipt=salesReceiptDAO.findById(id).orElseThrow(()->new ResourceNotFoundException("SalesReceipt","Id",id));
        salesReceiptDAO.delete(salesReceipt);
        return new ResponseEntity<>("SalesReceipt deleted successfully !",HttpStatus.OK);
    }
}
