package com.backend.girnartour.services;

import com.backend.girnartour.RequestDTOs.VendorRequestDTO;
import com.backend.girnartour.RequestDTOs.UpdateDTOs.VendorUpdateDTO;
import com.backend.girnartour.ResponseDTOs.VendorResponseDTO;
import com.backend.girnartour.exception.ResourceAlreadyExistsException;
import com.backend.girnartour.exception.ResourceNotFoundException;
import com.backend.girnartour.models.Vendor;
import com.backend.girnartour.repository.VendorDAO;
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
public class VendorService {

    @Autowired
    private VendorDAO vendorDAO;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<?> addNewVendor(VendorRequestDTO requestDTO){
        //check if previously exists [By name,email,telephone]
        String email=requestDTO.getEmail();
        String telephone= requestDTO.getTelephone();
        String name= requestDTO.getVendorName();
        if(vendorDAO.existsByEmail(email) || vendorDAO.existsByVendorName(name) || vendorDAO.existsByTelephone(telephone)){
            throw new ResourceAlreadyExistsException("Vendor","Name|Telephone|Email"+name+" "+telephone+" "+email);
        }
//        String sequenceId=idGenerationService.generateUniqueId(100000,"vendor");
        Vendor vendor=modelMapper.map(requestDTO, Vendor.class);
//        vendor.setId(sequenceId);
        Vendor saved=vendorDAO.save(vendor);
        VendorResponseDTO responseDTO=modelMapper.map(saved, VendorResponseDTO.class);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateVendor(VendorUpdateDTO vendorUpdateDTO,Integer id){
        Vendor existingVendor = vendorDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor","ID",String.valueOf(id)));
        BeanUtils.copyProperties(vendorUpdateDTO, existingVendor, getNullPropertyNames(vendorUpdateDTO));
        Vendor updatedVendor = vendorDAO.save(existingVendor);
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


    public ResponseEntity<?> getAllVendors(){
        List<Vendor> allVendors=vendorDAO.findAll();
        List<VendorResponseDTO> vendorResponseDTOS=allVendors.stream()
                .map(vendor -> modelMapper.map(vendor, VendorResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(vendorResponseDTOS);
    }

    public ResponseEntity<?> getVendorById(Integer id){
        Vendor vendor;
        try{
            vendor=vendorDAO.findById(id).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("Vendor","ID",String.valueOf(id));
        }
        VendorResponseDTO responseDTO=modelMapper.map(vendor,VendorResponseDTO.class);
        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<?> deleteVendorById(Integer id){
        Vendor vendor;
        try{
            vendor=vendorDAO.findById(id).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("Vendor","ID",String.valueOf(id));
        }
        vendorDAO.delete(vendor);
        return new ResponseEntity<>("Vendor deleted Successfully with ID: "+id,HttpStatus.OK);
    }
}
