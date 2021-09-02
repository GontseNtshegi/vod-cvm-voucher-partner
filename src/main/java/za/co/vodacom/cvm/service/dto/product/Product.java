package za.co.vodacom.cvm.service.dto.product;

import java.time.ZonedDateTime;

public class Product {

    Long count;
    ZonedDateTime minEndDateitme;

    public Product(Long count, ZonedDateTime minEndDateitme) {
        this.count = count;
        this.minEndDateitme = minEndDateitme;
    }
    public Product(Integer count, ZonedDateTime minEndDateitme) {
        this.count = count.longValue();
        this.minEndDateitme = minEndDateitme;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public ZonedDateTime getMinEndDitme() {
        return minEndDateitme;
    }

    public void setMinEndDitme(ZonedDateTime minEndDitme) {
        this.minEndDateitme = minEndDitme;
    }

    @Override
    public String toString() {
        return "Product{" + "count=" + count + ", minEndDitme=" + minEndDateitme + '}';
    }
}
