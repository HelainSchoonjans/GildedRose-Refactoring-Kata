package com.gildedrose;

class GildedRose {
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
            if(item.sellIn < 0) {
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
        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }
    }

    private static boolean hasSellDatePassed(Item item) {
        return item.sellIn < 0;
    }

    private static void decrementSellIn(Item item) {
        if (isOrdinaryItem(item)) {
            item.sellIn = item.sellIn - 1;
        }
    }

    private static boolean isOrdinaryItem(Item item) {
        return !isLegendary(item);
    }

    private static void decrementQuality(Item item) {
        if (item.quality > 0 && isOrdinaryItem(item)) {
            item.quality = item.quality - 1;
        }
    }

    private static boolean isLegendary(Item item) {
        return item.name.equals("Sulfuras, Hand of Ragnaros");
    }
}
