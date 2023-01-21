package com.example.stockmarket.market.core.item;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ItemGenerator {
    private static final List<ItemOpeningDefinition> OPENING_ITEM_DEFINITIONS = List.of(
        ItemOpeningDefinition.builder().name("Gold Ounce").priceLowerBound(1800).priceHigherBound(2200).build(),
        ItemOpeningDefinition.builder().name("Bitcoin").priceLowerBound(25000).priceHigherBound(40000).build(),
        ItemOpeningDefinition.builder().name("Silver Ounce").priceLowerBound(21).priceHigherBound(26).build(),
        ItemOpeningDefinition.builder().name("Iron Ton").priceLowerBound(109).priceHigherBound(120).build(),
        ItemOpeningDefinition.builder().name("Coal Ton").priceLowerBound(320).priceHigherBound(350).build()
    );

    public static List<Item> generate() {
        return OPENING_ITEM_DEFINITIONS.stream().map(ItemGenerator::getItem).collect(Collectors.toList());
    }

    private static Item getItem(ItemOpeningDefinition itemOpeningDefinition) {
        return Item.builder().name(itemOpeningDefinition.getName()).price(generatePrice(itemOpeningDefinition)).quantity(generateQuantity()).build();
    }

    private static int generateQuantity() {
        return new Random().nextInt(100);
    }

    private static double generatePrice(ItemOpeningDefinition itemOpeningDefinition) {
        return new Random().nextDouble() * (itemOpeningDefinition.getPriceHigherBound() - itemOpeningDefinition.getPriceLowerBound()) + itemOpeningDefinition.getPriceLowerBound();
    }
}
