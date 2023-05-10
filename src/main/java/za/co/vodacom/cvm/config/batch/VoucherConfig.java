package za.co.vodacom.cvm.config.batch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import za.co.vodacom.cvm.domain.VPFileLoad;
import za.co.vodacom.cvm.repository.VPFileLoadRepository;
import za.co.vodacom.cvm.service.dto.voucher.VPVoucherDTO;

import java.io.File;

@Configuration
@EnableBatchProcessing
public class VoucherConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Lazy
    public VPFileLoadRepository vpFileLoadRepository;

    @Bean
    public VoucherFieldSetMapper voucherFieldSetMapper(){
        return new VoucherFieldSetMapper();
    }


    @Bean
    @StepScope
    public FlatFileItemReader reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile){

        return new FlatFileItemReaderBuilder<VPFileLoad>()
            .name("File-Reader")
            .resource(new FileSystemResource(new File(pathToFile)))
            .delimited()
            .names("file_name","batch_id","create_date","num_loaded","num_failed")
            .fieldSetMapper(voucherFieldSetMapper()).linesToSkip(1)
            .build();
    }

    @Bean
    public RepositoryItemWriter<VPFileLoad> writer(){
        RepositoryItemWriter<VPFileLoad> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(vpFileLoadRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }
    public VoucherProcessor processor(){
        return new VoucherProcessor();
    }

    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new StepExecutionListenerSupport() {
            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                int readCount = stepExecution.getReadCount();
                int writeCount = stepExecution.getWriteCount();
               // int commitCount = stepExecution.getCommitCount();
                int failed = stepExecution.getSkipCount();



                stepExecution.getJobExecution().getExecutionContext().putInt("processedCount", writeCount);
                stepExecution.getJobExecution().getExecutionContext().putInt("failedCount", failed);

                return super.afterStep(stepExecution);
            }
        };
    }

    @Bean
    public VPVoucherDTO responseDTO() {
        return new VPVoucherDTO();
    }
    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobExecutionListenerSupport() {
            @Override
            public void afterJob(JobExecution jobExecution) {
                int processedCount = jobExecution.getExecutionContext().getInt("processedCount");
                int failedCount = jobExecution.getExecutionContext().getInt("failedCount");

                // Set the processed count as a custom field in your response DTO

                VPVoucherDTO responseDTO =  responseDTO();

                responseDTO.setNumLoaded(processedCount);
                responseDTO.setNumFailed(failedCount);

                System.out.println("Voucher DTO ............." + responseDTO);


                // Set the response DTO as the job execution exit message
                try {
                    jobExecution.setExitStatus(new ExitStatus("COMPLETED", new ObjectMapper().writeValueAsString(responseDTO)));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
    @Bean
    public Step step(ItemReader<VPFileLoad> itemReader , ItemWriter<VPFileLoad> itemWriter) throws Exception {
        return this.stepBuilderFactory.get("step")
            .<VPFileLoad,VPFileLoad>chunk(2)
            .reader(itemReader)
            .processor(processor())
            .writer(itemWriter)
            .faultTolerant()
            .skipLimit(10)
            .skip(FlatFileParseException.class)
            .listener(stepExecutionListener())
            .build();
    }

    @Bean
    public Job vpFileUpdateJob(JobCompletionNotificationListener listener, Step step) throws Exception {
        return this.jobBuilderFactory.get("VPFile-Job")
            .incrementer(new RunIdIncrementer())
            .start(step)
            .listener(jobExecutionListener())
            .build();

    }

}
