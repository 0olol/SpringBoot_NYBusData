package com.Oolol.SpringBoot_NYBusData.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.Oolol.SpringBoot_NYBusData.Entity.rawData;
import com.Oolol.SpringBoot_NYBusData.Helper.CSVhelp;
import com.Oolol.SpringBoot_NYBusData.Message.responsemessage;
import com.Oolol.SpringBoot_NYBusData.Service.rawDataService;

@CrossOrigin("http://localhost:8081")
// @Getter
// @Setter
@Controller
@RequestMapping("/api/csv")
@PropertySource("classpath:application.properties")
public class CSVController {
    
  @Value("${csv.download.fileName}")
  private String filename;
    
    @Autowired
    rawDataService fileService;
    // private EntityManager entityManager; // Using EntityManager
    // customImpl customService; // Doing extra custom query

    //uploading the csv file
    @PostMapping("/upload")
    public ResponseEntity<responsemessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
    
        if(CSVhelp.hasCSVFormat(file)){        
          try {
            fileService.save(file);
    
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new responsemessage(message));
          } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new responsemessage(message));
          }
        }
    
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new responsemessage(message));
      }
    
      //Get All as 
      @GetMapping("/examples3")
      public ResponseEntity<List<rawData>> getAllExamples() {
        try {
          List<rawData> examples = fileService.getAllExamples();
    
          if (examples.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }
    
          return new ResponseEntity<>(examples, HttpStatus.OK);
        } catch (Exception e) {
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
      }


      // Find Duplicates and returning 
      @GetMapping("/updates")
      public ResponseEntity<List<rawData>> updateFile() {
      // public ResponseEntity<responsemessage> updateFile(@PathParam("column1") String column1, @PathVariable String column2) {
        try {
          System.out.println("hello 5");
          List<rawData> examples = fileService.findUniqueIds();
          if (examples.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          }

          return new ResponseEntity<>(examples,HttpStatus.OK);
        } catch (Exception e) {
          System.out.println(e);
          
          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
          // return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new responsemessage(message));

        }
      }
      // @GetMapping("/updates/{column1}/{column2}")
      // public ResponseEntity<responsemessage> updateFile(@PathVariable String column1, @PathVariable String column2) {
      // // public ResponseEntity<responsemessage> updateFile(@PathParam("column1") String column1, @PathVariable String column2) {
      //   try {
      //     System.out.println("hello "+ column1+ column2);
      //     // entityManager.createNativeQuery("UPDATE rawData SET CORRECT = 'true' where id in (Select MIN(id) from rawData group by " + column1 + ","+column2+" having count(*) = 1)");
      //     // Using EntityManager
      //     // customService.update4(column1, column2); // Doing extra custom query
      //     fileService.update1(column1, column2);
      //     fileService.update2( column1, column2);

      //     return new ResponseEntity<>(HttpStatus.OK);
      //   } catch (Exception e) {
      //     System.out.println(e);
          
      //     return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      //     // return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new responsemessage(message));

      //   }
      // }

      // Just taking alook whether the hello api works
      @GetMapping("/Hello")
      public ResponseEntity<responsemessage> hello(){
        // public ResponseEntity<responsemessage> updateFile(@PathParam("column1") String column1, @PathVariable String column2) {
          String message = "";
          try {
            System.out.println("hello hello");
            message = "hello";
            return ResponseEntity.status(HttpStatus.OK).body(new responsemessage(message));
          } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
          }
        }


        // Getting back information on one ID
        @GetMapping("/getOneID/{id}")
        public ResponseEntity<Optional<rawData>> getOneId(@PathVariable Long id) {
          try {
            System.out.println(id);
            Optional<rawData> examples = fileService.findById(id);
      
            if (examples.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            // return ResponseEntity.status(HttpStatus.OK).body(examples.get());
            return new ResponseEntity<>(examples, HttpStatus.OK);
          } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
          }
        }


        //Testing Getting back as ID, This is really useless so .....
        @GetMapping("/geting/{column1}/{column2}")
        public ResponseEntity<List<Long>> getIdGroupBy(@PathVariable String column1, @PathVariable String column2) {
        // public ResponseEntity<responsemessage> updateFile(@PathParam("column1") String column1, @PathVariable String column2) {
          try {
            System.out.println("hello3 "+ column1+ column2);

            List<Long> examples = fileService.update3(column1, column2);

            if (examples.isEmpty()) {
              return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(examples, HttpStatus.OK);
          } catch (Exception e) {
            System.out.println(e);
            
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            // return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new responsemessage(message));
  
          }
        }


  @GetMapping("/downloadCsv")
  public ResponseEntity<Resource> exportIntoCSV() {


    // placing a string from pom.xml to change the name from time to time
    InputStreamResource file = new InputStreamResource(fileService.load());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(file);
  }
}
