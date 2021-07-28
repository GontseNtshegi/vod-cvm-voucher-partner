package za.co.vodacom.cvm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MSISDNConverter {
    private static final Logger log = LoggerFactory.getLogger(MSISDNConverter.class);

    public String convertToInternal(String msisdn) {
        String first = msisdn.substring(2, 6);
        String second = msisdn.substring(6, msisdn.length());
        String converted = "270" + first + "000" + second;
        log.debug(converted);
        return converted;
    }
    public String convertToExternal(String msisdn) {
        String converted = msisdn;
        if(msisdn.length() == 15){
            String first = msisdn.substring(0, 2);
            String second = msisdn.substring(3, 7);
            String third = msisdn.substring(10, msisdn.length());
            converted =  first  + second + third;
            log.debug(converted);
        }

        return converted;
    }
}
