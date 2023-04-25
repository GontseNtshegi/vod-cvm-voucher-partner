package za.co.vodacom.cvm.config.batch;

import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import za.co.vodacom.cvm.domain.VPFileLoad;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.repository.VPVouchersRepository;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private VPVouchersRepository vpVouchersRepository;

    @Bean
    public FlatFileItemReader<VPVouchers> reader(){
        FlatFileItemReader<VPVouchers> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/VoucherP.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());


        return itemReader;
    }

    private LineMapper<VPVouchers> lineMapper(){
        DefaultLineMapper<VPVouchers> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","file_name","batch_id",
            "create_date", "completed_date", "start_date","end_date","expiry_date","collection_point","batch_id");

        BeanWrapperFieldSetMapper<VPVouchers> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(VPVouchers.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public VPVoucherProcessor processor(){
        return new VPVoucherProcessor();
    }


    public RepositoryItemWriter<VPVouchers> writer(){
        RepositoryItemWriter<VPVouchers> writer = new RepositoryItemWriter<>();
        writer.setRepository(vpVouchersRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1() throws IOException {
        return stepBuilderFactory.get("step1").<VPVouchers,VPVouchers>chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean
    public Job runJob() throws IOException {
        return jobBuilderFactory.get("importVPVouchers")
            .flow(step1())
            .end().build();
    }

}
