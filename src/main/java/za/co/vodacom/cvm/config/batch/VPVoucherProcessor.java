package za.co.vodacom.cvm.config.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import za.co.vodacom.cvm.domain.VPFileLoad;
import za.co.vodacom.cvm.domain.VPVouchers;

public class VPVoucherProcessor implements ItemProcessor<VPVouchers, VPVouchers> {
    Logger log = LoggerFactory.getLogger(VPVoucherProcessor.class);

    @Override
    public VPVouchers process(VPVouchers vpVouchers) {
        log.debug("Precessed VpVouchers are : {} ", vpVouchers);

        return vpVouchers;
    }
}
