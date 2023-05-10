package za.co.vodacom.cvm.config.batch;

import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import za.co.vodacom.cvm.domain.VPFileLoad;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class VoucherFieldSetMapper implements FieldSetMapper<VPFileLoad> {

        private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
    @Override
    public VPFileLoad mapFieldSet(FieldSet fieldSet) throws BindException {
        VPFileLoad vpFileLoad = new VPFileLoad();

        try {
            vpFileLoad.setFileName(fieldSet.readString(0));
            vpFileLoad.setBatchId(fieldSet.readInt(1));
            vpFileLoad.setCreateDate(ZonedDateTime.parse(fieldSet.readString(2), DATE_TIME_FORMATTER.withZone(ZoneOffset.UTC)));
            vpFileLoad.setNumLoaded(fieldSet.readInt(3));
            vpFileLoad.setNumFailed(fieldSet.readInt(4));
        }catch (ParseException e) {
                  e.printStackTrace();
           }

//        String date = fieldSet.readString(3);
////        try {
////            vpFileLoad.setCreateDate(ZonedDateTime.from(ZonedDateTime.parse(fieldSet.readString("create_date")).toLocalDate()));
////           // vpFileLoad.setCompletedDate(ZonedDateTime.parse(fieldSet.readString("completed_date")));
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
        System.out.println("VPFile load returned : , "+ vpFileLoad);

        return vpFileLoad;
    }
}
