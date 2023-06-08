package za.co.vodacom.cvm.config.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.web.rest.errors.BadRequestAlertException;

public class VoucherProcessor implements ItemProcessor<VPVouchers, VPVouchers> {

    public static final Logger log = LoggerFactory.getLogger(VoucherProcessor.class);

    @Override
    public VPVouchers process(VPVouchers vpVouchers) throws Exception {

        if (vpVouchers.getQuantity() == null || vpVouchers.getQuantity() <= 0) {

            throw new BadRequestAlertException(
                "Quantity must have a value: " + vpVouchers.getQuantity(),
                "VPVouchers",
                "Quantity must not be null"
            );
        }

        if (vpVouchers.getStartDate() == null || vpVouchers.getStartDate().toLocalDateTime() == null) {

            throw new BadRequestAlertException(
                "Start date should not be null: " + vpVouchers.getQuantity(),
                "VPVouchers",
                "Start date should not be null"
            );
        }

        return vpVouchers;
    }
}
