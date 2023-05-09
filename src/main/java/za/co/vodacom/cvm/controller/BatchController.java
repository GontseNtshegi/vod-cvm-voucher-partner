//package za.co.vodacom.cvm.controller;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.JobParametersInvalidException;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//
//@RestController
//@RequestMapping(path = "/batch")// Root path
//public class BatchController {
//    @Autowired
//    private JobLauncher jobLauncher;
//    @Autowired
//    private Job job;
//
//    private final String TEMP_STORAGE = "C:/Users/tsotm001/OneDrive - Vodafone Group/Desktop/temp/";
//
//    // The function below accepts a GET request to invoke the Batch Process and returns a String as response with the message "Batch Process started!!".
//    @PostMapping(path = "/start") // Start batch process path
//    public ResponseEntity<String> startBatch(@RequestParam("file") MultipartFile multipartFile)  {
//        String originalName = multipartFile.getOriginalFilename();
//        File fileToImport = new File(TEMP_STORAGE + originalName);
//        try {
//            multipartFile.transferTo(fileToImport);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        JobParameters Parameters = new JobParametersBuilder()
//            .addString("fullPathFileName", TEMP_STORAGE + originalName)
//            .addLong("startAt", System.currentTimeMillis()).toJobParameters();
//        try {
//            jobLauncher.run(job, Parameters);
//        } catch (JobExecutionAlreadyRunningException | JobRestartException
//                 | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
//
//            e.printStackTrace();
//        }
//
//        return new ResponseEntity<>("Batch Process started!!", HttpStatus.OK);
//    }
//}
