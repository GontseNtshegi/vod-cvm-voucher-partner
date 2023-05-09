package za.co.vodacom.cvm.config.batch;

import org.springframework.batch.item.ItemProcessor;
import za.co.vodacom.cvm.domain.VPFileLoad;

public class VoucherProcessor implements ItemProcessor<VPFileLoad, VPFileLoad> {
    @Override
    public VPFileLoad process(VPFileLoad vpFileLoad) throws Exception {
        System.out.println("Processed vpfileLoad ," + vpFileLoad);
        return vpFileLoad;
    }
}
