package za.co.vodacom.cvm.config.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.web.rest.BatchServiceImpl;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class VoucherFieldSetMapper implements FieldSetMapper<VPVouchers> {

    @Autowired
    BatchServiceImpl batchService;

    @Autowired
    VPVouchersService vpVouchersService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final Logger log = LoggerFactory.getLogger(VoucherFieldSetMapper.class);

    @Override
    public VPVouchers mapFieldSet(FieldSet fieldSet) throws BindException {
        VPVouchers vpVouchers = new VPVouchers();

        try {
            vpVouchers.setFileId(Math.toIntExact(batchService.fieldId()));
            vpVouchers.setBatchId(batchService.batchIdValue());
            vpVouchers.setQuantity(fieldSet.readInt("quantity"));
            vpVouchers.setProductId(fieldSet.readString("product_id"));
            vpVouchers.setDescription(fieldSet.readString("description"));
            vpVouchers.setVoucherCode(fieldSet.readString("voucher_code"));
            vpVouchers.setCollectionPoint(fieldSet.readString("collection_point"));
            vpVouchers.setStartDate(LocalDate.parse(fieldSet.readString("start_date")).atStartOfDay(ZoneId.systemDefault()));
            vpVouchers.setEndDate(LocalDate.parse(fieldSet.readString("end_date")).atStartOfDay(ZoneId.systemDefault()));
            vpVouchers.setExpiryDate(LocalDate.parse(fieldSet.readString("expiry_date")).atStartOfDay(ZoneId.systemDefault()));

            vpVouchersService.save(vpVouchers);

            log.debug("--------------Vouchers saved Successfully-------------------");
        } catch (ParseException e) {
            log.error("--------------Saving Vouchers Failed -------------------");
            e.printStackTrace();
        }

        log.info("VPVouchers returned : {}", vpVouchers);

        return vpVouchers;
    }
}
