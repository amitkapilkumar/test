package kata.supermarket;

import java.math.BigDecimal;

public class Product {

    private final BigDecimal pricePerUnit;
    private final String name;

    public Product(final BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
        this.name = null;
    }

    public Product(final BigDecimal pricePerUnit, final String name) {
        this.pricePerUnit = pricePerUnit;
        this.name = name;
    }

    public BigDecimal pricePerUnit() {
        return pricePerUnit;
    }

    public String name() {
        return name;
    }

    public Item oneOf() {
        return new ItemByUnit(this);
    }
}
