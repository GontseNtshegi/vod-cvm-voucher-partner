package za.co.vodacom.cvm.config.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import za.co.vodacom.cvm.config.listener.StepSkipListener;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.repository.VPVouchersRepository;
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
    public VPVouchersRepository vpVouchersRepository;

    @Bean
    public VoucherFieldSetMapper voucherFieldSetMapper(){
        return new VoucherFieldSetMapper();
    }

    public static final Logger log = LoggerFactory.getLogger(VoucherConfig.class);


    @Bean
    @StepScope
    public FlatFileItemReader reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {

        return new FlatFileItemReaderBuilder<VPVouchers>()
            .name("File-Reader")
            .resource(new FileSystemResource(new File(pathToFile)))
            .delimited()
            .names("quantity", "product_id", "description", "voucher_code",
                "collection_point", "start_date", "end_date",
                "expiry_date")
            .fieldSetMapper(voucherFieldSetMapper()).linesToSkip(1)
            .build();
    }

    @Bean
    public RepositoryItemWriter<VPVouchers> writer() {
        RepositoryItemWriter<VPVouchers> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(vpVouchersRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    public VoucherProcessor processor() {
        return new VoucherProcessor();
    }

    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new StepExecutionListenerSupport() {
            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                int writeCount = stepExecution.getWriteCount();
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

                VPVoucherDTO responseDTO = responseDTO();

                responseDTO.setNumLoaded(processedCount);
                responseDTO.setNumFailed(failedCount);

                log.debug("VPVoucherDTO {} ", responseDTO);

            }
        };
    }

    @Bean
    public Step step(ItemReader<VPVouchers> itemReader, ItemWriter<VPVouchers> itemWriter) throws Exception {
        return this.stepBuilderFactory.get("step")
            .<VPVouchers,VPVouchers>chunk(1000)
            .reader(itemReader)
            .processor(processor())
            .writer(itemWriter)
            .faultTolerant()
            .skipLimit(10000)
            .skip(FlatFileParseException.class)
            .listener(stepExecutionListener())
            .listener(skipListener())
            .build();
    }

    @Bean
    public Job vpFileUpdateJob(Step step) throws Exception {
        return this.jobBuilderFactory.get("VPFile-Job")
            .incrementer(new RunIdIncrementer())
            .start(step)
            .listener(jobExecutionListener())
            .build();

    }

    public SkipListener skipListener() {
        return new StepSkipListener();
    }

}
