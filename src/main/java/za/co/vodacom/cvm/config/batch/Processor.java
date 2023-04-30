package za.co.vodacom.cvm.config.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import za.co.vodacom.cvm.domain.VPVouchers;

@Component
public class Processor  implements ItemProcessor<VPVouchers,VPVouchers> {
    @Override
    public VPVouchers process(VPVouchers vpVouchers) throws Exception {
        System.out.println("Reached processor");
        return vpVouchers;
    }
}
