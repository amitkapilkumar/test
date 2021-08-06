package kata.supermarket;

import kata.supermarket.offers.BuyOneGetOne;
import kata.supermarket.offers.BuyTwoForOnePound;
import kata.supermarket.offers.Offer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketTest {

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, String expectedTotal, Iterable<Item> items, Iterable<Offer> offers) {
        final Basket basket = new Basket();
        items.forEach(basket::add);
        offers.forEach(basket::add);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                aSingleItemPricedByWeight(),
                multipleItemsPricedByWeight(),
                fiveItemsPricedPerUnit(),
                twoForOnePoundItems()
        );
    }

    private static Arguments twoForOnePoundItems() {
        return Arguments.of("one pound items", "3.55", Arrays.asList(aPintOfMilk("milk", "2.49"),
                aPintOfMilk("milk", "2.49"), aPintOfMilk("milk", "2.49"), aPintOfMilk("milk", "2.49"),
                aPackOfDigestives("digestive", "1.55")), Collections.singleton(buyTwoForOnePoundMilk("2.49")));
    }

    private static Arguments fiveItemsPricedPerUnit() {
        return Arguments.of("5 items, 4 pint of milk along with digestive", "2.43", Arrays.asList(aPintOfMilk("milk", "0.45"),
                aPintOfMilk("milk", "0.45"), aPintOfMilk("milk", "0.45"), aPintOfMilk("milk", "0.45"),
                aPackOfDigestives("digestive", "1.53")), Collections.singleton(buyOneGetOnePintOfMilk("0.45")));
    }

    private static Offer buyOneGetOnePintOfMilk(String price) {
        return new BuyOneGetOne(new Product(new BigDecimal(price), "milk").oneOf());
    }

    private static Offer buyTwoForOnePoundMilk(String price) {
        return new BuyTwoForOnePound(new Product(new BigDecimal(price), "milk").oneOf());
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()), Collections.emptyList());
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", "1.85",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix()),
                Collections.emptyList()
        );
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()), Collections.emptyList());
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.49", Collections.singleton(aPintOfMilk()), Collections.emptyList());
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList(), Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new Product(new BigDecimal("0.49")).oneOf();
    }

    private static Item aPintOfMilk(String name, String price) {
        return new Product(new BigDecimal(price), name).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product(new BigDecimal("1.55")).oneOf();
    }

    private static Item aPackOfDigestives(String name, String price) {
        return new Product(new BigDecimal(price), name).oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        return new WeighedProduct(new BigDecimal("4.99"));
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        return new WeighedProduct(new BigDecimal("2.99"));
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }
}