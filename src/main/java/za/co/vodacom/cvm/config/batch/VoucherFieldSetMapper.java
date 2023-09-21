package za.co.vodacom.cvm.config.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import za.co.vodacom.cvm.domain.VPVoucherDef;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.service.VPVoucherDefService;
import za.co.vodacom.cvm.service.VPVouchersService;
import za.co.vodacom.cvm.web.rest.BatchServiceImpl;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VoucherFieldSetMapper implements FieldSetMapper<VPVouchers> {

    @Autowired
    BatchServiceImpl batchService;

    @Autowired
    VPVouchersService vpVouchersService;

    @Autowired
    VPVoucherDefService vpVoucherDefService;

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
            vpVouchers.setStartDate(LocalDate.parse(fieldSet.readString("start_date")).atStartOfDay(ZoneId.systemDefault()).plusHours(2));
            vpVouchers.setEndDate(LocalDate.parse(fieldSet.readString("end_date")).atStartOfDay(ZoneId.systemDefault()));
            vpVouchers.setExpiryDate(LocalDate.parse(fieldSet.readString("expiry_date")).atStartOfDay(ZoneId.systemDefault()).plusHours(2));

            Optional<VPVoucherDef> vpVoucherDef = vpVoucherDefService.findById(vpVouchers.getProductId());

            log.debug("Product Id found: {}", vpVoucherDef.get().getId());

            if (vpVouchers.getQuantity() == null || vpVouchers.getQuantity() <= 0) {

                throw new BadRequestAlertException(
                    "quantity must be a number greater than 0: " + vpVouchers.getQuantity(),
                    "VPVouchers",
                    "quantity must be a number greater than 0"
                );
            }
            if (!vpVoucherDef.isPresent()) {

                throw new BadRequestAlertException(
                    "product_id must be a valid product_id in vp_voucher_def",
                    "VPVoucherDef",
                    "product_id must be a valid product_id in vp_voucher_def"
                );

            }
            if (vpVouchers.getDescription().equals("") || vpVouchers.getDescription().isEmpty()) {

                throw new BadRequestAlertException(
                    "description must be a string with at least one char",
                    "VPVouchers",
                    "description must be a string with at least one char"
                );
            }
            if (vpVouchers.getVoucherCode().equals("") || vpVouchers.getVoucherCode().isEmpty()) {

                throw new BadRequestAlertException(
                    "voucher code must be a string with at least one char",
                    "VPVouchers",
                    "voucher code must be a string with at least one char"
                );
            }
            if (vpVouchers.getCollectionPoint().equals("") || vpVouchers.getCollectionPoint().isEmpty()) {

                throw new BadRequestAlertException(
                    "collection point must be a string with at least one char",
                    "VPVouchers",
                    "collection point must be a string with at least one char"
                );

            }

            if(vpVouchers.getExpiryDate().toString().isEmpty() || vpVouchers.getExpiryDate() == null){
                log.debug("Setting Expiry date to Null");
                vpVouchers.setExpiryDate(null);
            }

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
