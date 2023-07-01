package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.CustomerRequest;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.CustomerUpdateDTO;
import com.backend.girnartour.ResponseDTOs.CustomerResponse;
import com.backend.girnartour.ResponseDTOs.VendorResponseDTO;
import com.backend.girnartour.exception.ResourceAlreadyExistsException;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.Customer;
import com.backend.girnartour.repository.CustomerDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<?> addNewCustomer(CustomerRequest request){
        //check if previously exists [By name,email,telephone]
        String email=request.getEmail();
        String telephone= request.getTelephone();
        String name= request.getCustomerName();
        if(customerDAO.existsByEmail(email) || customerDAO.existsByCustomerName(name) || customerDAO.existsByTelephone(telephone)){
            throw new ResourceAlreadyExistsException("Customer","Name|Telephone|Email"+name+" "+telephone+" "+email);
        }
//        String random_sequence= service.generateUniqueId(300000,"customer");
        Customer customer=modelMapper.map(request, Customer.class);
//        customer.setId(random_sequence);
        Customer saved=customerDAO.save(customer);
        CustomerResponse responseDTO=modelMapper.map(saved, CustomerResponse.class);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateCustomer(CustomerUpdateDTO updateDTO, Integer id){
        Customer existingCustomer = customerDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer","ID",String.valueOf(id)));
        BeanUtils.copyProperties(updateDTO, existingCustomer, getNullPropertyNames(updateDTO));
        Customer updatedVendor = customerDAO.save(existingCustomer);
        VendorResponseDTO responseDTO=modelMapper.map(updatedVendor, VendorResponseDTO.class);
        return ResponseEntity.ok(responseDTO);
    }
    private static String[] getNullPropertyNames(Object source) {
        BeanWrapperImpl src = new BeanWrapperImpl(source);
        return Stream.of(src.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> src.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }


    public ResponseEntity<?> getAllCustomers(){
        List<Customer> allCustomers=customerDAO.findAll();
        List<CustomerResponse> customerResponses=allCustomers.stream()
                .map(customer -> modelMapper.map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerResponses);
    }

    public ResponseEntity<?> getCustomerById(Integer id){
        Customer customer;
        try{
            customer=customerDAO.findById(id).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("Customer","ID",String.valueOf(id));
        }
        CustomerResponse responseDTO=modelMapper.map(customer,CustomerResponse.class);
        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<?> deleteCustomerById(Integer id){
        Customer customer;
        try{
            customer=customerDAO.findById(id).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("Customer","ID",String.valueOf(id));
        }
        customerDAO.delete(customer);
        return new ResponseEntity<>("Customer deleted Successfully with ID: "+id,HttpStatus.OK);
    }
}
