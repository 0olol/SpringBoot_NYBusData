package com.Oolol.SpringBoot_NYBusData.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Oolol.SpringBoot_NYBusData.Entity.rawData;
import com.Oolol.SpringBoot_NYBusData.Helper.CSVhelp;

import com.Oolol.SpringBoot_NYBusData.Repository.rawDataRepository;

@Service
public class rawDataService {
    @Autowired
    rawDataRepository repository;

    public void save(MultipartFile file) {
        try {
          // List<example3> example3s = CSVhelp.csvtoh2(file.getInputStream().distinct());
          List<rawData> rawDatas = CSVhelp.csvtoh2(file.getInputStream());
          // Set<rawData> rawDatas =  new HashSet<>(CSVhelp.csvtoh2(file.getInputStream()));
          repository.saveAll(rawDatas);
        } catch (IOException e) {
          throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
      }

      //Loading the uniqueId to csv, Using writeExampleToCsv to place it inside
      public ByteArrayInputStream load() {
        List<rawData> rawDatas = repository.findUniqueIds();
    
        ByteArrayInputStream in = CSVhelp.writeExampleToCsv(rawDatas);
        return in;
      }
      
    
      public List<rawData> getAllExamples() {
        return repository.findAll();
      }
      
      public Optional<rawData> findById(Long id) {
        try{
          return repository.findById(id);
        } catch (Exception e) {
          throw new RuntimeException("fail to get ID: " + e.getMessage());
        }
      }


      // public void update1(String column1,String column2) {
      //   try{
      //     System.out.println("hello1 "+ column1+ column2);
      //     repository.update1(column1,column2);
      //   }catch(Exception e){
      //     throw new RuntimeException("fail update1: " + e.getMessage());
      //   }
      // }
      
      // public void update2(String column1,String column2) {
      //   try{
      //     System.out.println("hello2 "+ column1+ column2);
      //     repository.update2(column1,column2);
      //   }catch(Exception e){
      //     throw new RuntimeException("fail update2: " + e.getMessage());
      //   }
      // }

      
      public List<Long> update3(String column1,String column2) {
        try{
          System.out.println("hello4 "+ column1+ column2);
          return repository.findMinId(column1,column2);
        }catch(Exception e){
          throw new RuntimeException("fail update3: " + e.getMessage());
        }
      }

      // public List<Long> findUniqueIds() {
      //   try{
      //     System.out.println("hello5 ");
      //     return repository.findUniqueIds();
      //   }catch(Exception e){
      //     throw new RuntimeException("fail update6: " + e.getMessage());
      //   }
      // }

      
      public List<rawData> findUniqueIds() {
        try{
          System.out.println("hello5 ");
          return repository.findUniqueIds();
        }catch(Exception e){
          throw new RuntimeException("fail update6: " + e.getMessage());
        }
      }
    
}
