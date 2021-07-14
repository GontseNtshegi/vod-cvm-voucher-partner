package za.co.vodacom.cvm.service.dto.product;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Product {

    BigDecimal count;
    ZonedDateTime minEndDateitme;

    public Product(BigDecimal count, ZonedDateTime minEndDateitme) {
        this.count = count;
        this.minEndDateitme = minEndDateitme;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
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
