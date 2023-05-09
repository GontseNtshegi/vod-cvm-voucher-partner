package za.co.vodacom.cvm.config.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.vodacom.cvm.repository.VPFileLoadRepository;

@Component
public class JobCompletionNotificationListener  extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private VPFileLoadRepository vpFileLoadRepository;

    @Autowired
    public JobCompletionNotificationListener(VPFileLoadRepository vpFileLoadRepository){
        this.vpFileLoadRepository = vpFileLoadRepository;
    }

    @Override
    public void afterJob(JobExecution jobExecution){
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            log.info("JOB COMPLETED ");

            vpFileLoadRepository.findAll()
                .forEach(vpFileLoad -> log.info("Found " + vpFileLoad));
        }
    }
}
