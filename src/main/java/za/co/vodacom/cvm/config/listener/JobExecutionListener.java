package za.co.vodacom.cvm.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;

public class JobExecutionListener implements org.springframework.batch.core.JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobExecutionListener.class);
    @Override
    public void beforeJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() == BatchStatus.STARTING)
            log.info("Start processing Job at {}", jobExecution.getStartTime());

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

            log.info("Completed processing Job at {}", jobExecution.getEndTime());
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.info("Failed processing Job at {}", jobExecution.getEndTime());
        }

    }
}
