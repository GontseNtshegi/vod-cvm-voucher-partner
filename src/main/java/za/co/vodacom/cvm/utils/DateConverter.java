package za.co.vodacom.cvm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.vodacom.cvm.config.Constants;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateConverter {
    private static final Logger log = LoggerFactory.getLogger(DateConverter.class);

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);

    public static OffsetDateTime parseStringDateTime(String dateTime){
        return OffsetDateTime.of(LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER), ZoneOffset.ofHours(2));
    }
}
