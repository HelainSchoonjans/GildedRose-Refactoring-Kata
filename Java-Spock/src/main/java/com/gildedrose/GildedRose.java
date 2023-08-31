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
            increaseQuality(item);
        } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (item.sellIn < 0) {
                item.quality = 0;
                return;
            }
            increaseQuality(item);
            if (item.sellIn < 11) {
                increaseQuality(item);
            }

            if (item.sellIn < 6) {
                increaseQuality(item);
            }
        } else {
            decrementQuality(item);
        }
    }

    private static void increaseQuality(Item item) {
        item.quality = min(item.quality + 1, MAXIMUM_QUALITY);
    }

    private static boolean hasSellDatePassed(Item item) {
        return item.sellIn < 0;
    }

    private static void decrementSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    private static void decrementQuality(Item item) {
        item.quality = max(MINIMUM_QUALITY, item.quality - 1);
    }

    private static boolean isLegendary(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }
}
