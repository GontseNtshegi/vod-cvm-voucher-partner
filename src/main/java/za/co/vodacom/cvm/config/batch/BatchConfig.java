package za.co.vodacom.cvm.config.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import za.co.vodacom.cvm.domain.VPVouchers;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<VPVouchers> itemReader,
                   ItemProcessor<VPVouchers, VPVouchers> itemProcessor,
                   ItemWriter<VPVouchers> itemWriter
                   ){
        Step step = stepBuilderFactory.get("step")
            .<VPVouchers,VPVouchers>chunk(10)
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWriter)
            .build();

        return jobBuilderFactory.get("VPVoucher-Load")
            .incrementer(new RunIdIncrementer())
            .start(step)
            .build();
    }

    @Bean
    public FlatFileItemReader<VPVouchers> itemReader(){

        FlatFileItemReader<VPVouchers> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("C:\\Work\\CVM Loyalty\\vod-cvm-voucher-partner\\src\\main\\resources\\VoucherP.csv"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<VPVouchers> lineMapper() {
        DefaultLineMapper<VPVouchers> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id","file_name","batch_id",
            "create_date", "completed_date", "start_date","end_date","expiry_date","collection_point","batch_id"});

        BeanWrapperFieldSetMapper<VPVouchers> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
      //  fieldSetMapper.setTargetType(VPVouchers.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;

    }
}
