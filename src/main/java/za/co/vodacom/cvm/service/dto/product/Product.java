package za.co.vodacom.cvm.service.dto.product;

import java.time.ZonedDateTime;

public class Product {

    Long count;
    ZonedDateTime minEndDateTime;

    public Product(){
        this.count = 0L;
        minEndDateTime = null;
    }

    public Product(Long count, ZonedDateTime minEndDateTime) {
        this.count = count;
        this.minEndDateTime = minEndDateTime;
    }
    public Product(Integer count, ZonedDateTime minEndDateitme) {
        this.count = count.longValue();
        this.minEndDateTime = minEndDateitme;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public ZonedDateTime getMinEndDateTime() {
        return minEndDateTime;
    }

    public void setMinEndDateTime(ZonedDateTime minEndDitme) {
        this.minEndDateTime = minEndDitme;
    }

    @Override
    public String toString() {
        return "Product{" + "count=" + count + ", minEndDitme=" + minEndDateTime + '}';
    }
}
