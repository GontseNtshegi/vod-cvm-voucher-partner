package za.co.vodacom.cvm.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import za.co.vodacom.cvm.domain.VPVouchers;

public class StepSkipListener implements SkipListener<VPVouchers,Number> {

    Logger log = LoggerFactory.getLogger(StepSkipListener.class);
    @Override
    public void onSkipInRead(Throwable throwable) {
        log.debug("Error occured on  read with error {}",throwable.getMessage());
    }

    @Override
    public void onSkipInWrite(Number number, Throwable throwable) {

        log.debug("Error occured on write in line {} :  with error {}", number,throwable.getMessage());

    }

    @Override
    public void onSkipInProcess(VPVouchers vpVouchers, Throwable throwable) {

        log.debug("Error occured in process on {} :with exception {}",vpVouchers, throwable.getMessage());

    }
}
