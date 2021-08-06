package kata.supermarket.offers;

import kata.supermarket.Item;

import java.math.BigDecimal;
import java.util.List;

public interface Offer {
    BigDecimal getDiscountedPriceForItems(List<Item> items);
}
