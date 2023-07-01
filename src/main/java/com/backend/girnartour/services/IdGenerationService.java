//package com.backend.girnartour.services;
//import com.backend.girnartour.exception.ResourceNotFoundException;
//import com.backend.girnartour.models.IdSequence;
//import com.backend.girnartour.repository.IdSequenceRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//@Service
//public class IdGenerationService {
//
//    @Autowired
//    private final IdSequenceRepository idSequenceRepository;
//
//    private static final int START_VALUE = 100000;
//
//    @Autowired
//    public IdGenerationService(IdSequenceRepository idSequenceRepository) {
//        this.idSequenceRepository = idSequenceRepository;
//    }
//
//    public String generateUniqueId(int startValue,String name) {
//        IdSequence idSequence;
//        try{
//            idSequence=idSequenceRepository.findByEntityNameIgnoreCase(name);
//        }catch (Exception e){
//            throw new ResourceNotFoundException("IdSequence","EntityName",name);
//        }
//        if(idSequence==null){
//            idSequence=new IdSequence();
//        }
//        int nextValue = idSequence.getNextValue();
//        if (nextValue == 0) {
//            nextValue = startValue;
//        } else {
//            nextValue++;
//        }
//        idSequence.setNextValue(nextValue);
//        idSequence.setEntityName(name);
//        idSequenceRepository.save(idSequence);
//        return String.format("%06d", nextValue);
//    }
//}
