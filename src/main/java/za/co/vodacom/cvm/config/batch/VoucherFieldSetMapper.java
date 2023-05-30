package za.co.vodacom.cvm.config.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.service.dto.voucher.VPVoucherDTO;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class VoucherFieldSetMapper implements FieldSetMapper<VPVouchers> {
    private final VPVoucherDTO vpVoucherDTO;

    public VoucherFieldSetMapper(VPVoucherDTO vpVoucherDTO) {
        this.vpVoucherDTO = vpVoucherDTO;
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

    public static final Logger log = LoggerFactory.getLogger(VoucherFieldSetMapper.class);

    @Bean
    public VPVoucherDTO responseDTO() {
        return new VPVoucherDTO();
    }

    @Override
    public VPVouchers mapFieldSet(FieldSet fieldSet) throws BindException {
        VPVouchers vpVouchers = new VPVouchers();


        try {
            vpVouchers.setQuantity(fieldSet.readInt("quantity"));
            vpVouchers.setProductId(fieldSet.readString("product_id"));
            vpVouchers.setDescription(fieldSet.readString("description"));
            vpVouchers.setVoucherCode(fieldSet.readString("voucher_code"));
            vpVouchers.setBatchId(vpVoucherDTO.getBatchId());
            vpVouchers.setCollectionPoint(fieldSet.readString("collection_point"));
            vpVouchers.setStartDate(ZonedDateTime.parse(fieldSet.readString("start_date"), DATE_TIME_FORMATTER.withZone(ZoneOffset.UTC)));
            vpVouchers.setEndDate(ZonedDateTime.parse(fieldSet.readString("end_date"), DATE_TIME_FORMATTER.withZone(ZoneOffset.UTC)));
            vpVouchers.setExpiryDate(ZonedDateTime.parse(fieldSet.readString("expiry_date"), DATE_TIME_FORMATTER.withZone(ZoneOffset.UTC)));

        }catch (ParseException e) {
            e.printStackTrace();
        }

        log.info("VPVouchers returned : {}", vpVouchers);

        return vpVouchers;
    }
}
