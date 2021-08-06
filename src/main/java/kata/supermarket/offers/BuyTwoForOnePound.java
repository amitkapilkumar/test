package kata.supermarket.offers;

import kata.supermarket.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BuyTwoForOnePound implements Offer {

    private final Item item;

    public BuyTwoForOnePound(Item item) {
        this.item = item;
    }

    @Override
    public BigDecimal getDiscountedPriceForItems(List<Item> items) {
        List<Item> filteredItems = items.stream().filter(o -> o.productName().equals(item.productName())).collect(Collectors.toList());
        double totalPrice = items.stream().filter(o -> o.productName().equals(item.productName())).mapToDouble(o -> o.price().doubleValue()).sum();

        int batchOf2 = filteredItems.size() / 2;
        int additionalItem = filteredItems.size() % 2;

        double discountPrice = totalPrice - (batchOf2 + additionalItem * item.price().doubleValue());

        return new BigDecimal(discountPrice);
    }
}
