package za.co.vodacom.cvm.config.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.vodacom.cvm.domain.VPVouchers;
import za.co.vodacom.cvm.repository.VPVouchersRepository;
import za.co.vodacom.cvm.service.VPVouchersService;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<VPVouchers> {

    private static final Logger logger = LoggerFactory.getLogger(DBWriter.class);

    @Autowired
    private VPVouchersService vpVouchersService;
    @Override
    public void write(List<? extends VPVouchers> vpVouchers) throws Exception {
        logger.debug("List of vpVouchers saved {} ",vpVouchers);
        vpVouchersService.saveAll((Iterable<VPVouchers>) vpVouchers);
    }
}
