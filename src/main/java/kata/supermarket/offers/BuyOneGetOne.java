package kata.supermarket.offers;

import kata.supermarket.Item;
import kata.supermarket.ItemByUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class BuyOneGetOne implements Offer {

    private final Item item;

    public BuyOneGetOne(Item item) {
        this.item = item;
    }

    @Override
    public BigDecimal getDiscountedPriceForItems(List<Item> items) {
        ItemByUnit itemByUnit = (ItemByUnit) item;
        int numberOfItems = (int) items.stream().filter(o -> o.productName().equals(item.productName())).count() / 2;

        return itemByUnit.price().multiply(new BigDecimal(numberOfItems)).setScale(2, RoundingMode.HALF_UP);
    }

}