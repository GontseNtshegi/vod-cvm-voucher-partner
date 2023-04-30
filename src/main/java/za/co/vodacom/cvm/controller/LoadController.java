package za.co.vodacom.cvm.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.vodacom.cvm.config.batch.VPVoucherProcessor;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/job")
public class LoadController {
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job job;
    Logger log = LoggerFactory.getLogger(LoadController.class);

    @GetMapping
        public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(map);

        JobExecution jobExecution = jobLauncher.run(job,parameters);
        log.debug("JobExecution {}",jobExecution.getStatus());

        return jobExecution.getStatus();

        }
}
