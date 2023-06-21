package za.co.vodacom.cvm.config.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import za.co.vodacom.cvm.domain.VPVouchers;

public class VoucherProcessor implements ItemProcessor<VPVouchers, VPVouchers> {

    public static final Logger log = LoggerFactory.getLogger(VoucherProcessor.class);

    @Override
    public VPVouchers process(VPVouchers vpVouchers) throws Exception {
        return vpVouchers;
    }
}
