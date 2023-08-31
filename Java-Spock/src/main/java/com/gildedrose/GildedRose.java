package com.gildedrose;

import static java.lang.Math.*;

class GildedRose {
    public static final int MINIMUM_QUALITY = 0;
    public static final int MAXIMUM_QUALITY = 50;
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            update(item);
        }
    }

    private void update(Item item) {
        if (isLegendary(item)) {
            return;
        }
        degradeQuality(item);
        decrementSellIn(item);
        if (hasSellDatePassed(item)) {
            degradeQuality(item);
        }
    }

    private static void degradeQuality(Item item) {
        if (item.name.equals("Aged Brie")) {
            incrementQuality(item);
        } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (item.sellIn < 0) {
                degradeQualityToZero(item);
                return;
            }
            incrementQuality(item);
            if (item.sellIn < 11) {
                incrementQuality(item);
            }

            if (item.sellIn < 6) {
                incrementQuality(item);
            }
        } else if(item.name.equals("Conjured")){
            increaseQuality(item, -2);
        } else {
            decrementQuality(item);
        }
    }

    private static void degradeQualityToZero(Item item) {
        increaseQuality(item, -item.quality);
    }

    private static void decrementQuality(Item item) {
        increaseQuality(item, -1);
    }

    private static void incrementQuality(Item item) {
        increaseQuality(item, 1);
    }

    private static void increaseQuality(Item item, int amount) {
        item.quality = max(MINIMUM_QUALITY, min(item.quality + amount, MAXIMUM_QUALITY));
    }

    private static boolean hasSellDatePassed(Item item) {
        return item.sellIn < 0;
    }

    private static void decrementSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    private static boolean isLegendary(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }
}
