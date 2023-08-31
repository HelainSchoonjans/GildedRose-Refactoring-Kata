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
        if (!item.name.equals("Aged Brie")
                && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                decrementQuality(item);
        } else {
            if (item.quality < 50) {
                item.quality = item.quality + 1;

                if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (item.sellIn < 11) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            }
        }

        decrementSellIn(item);

        if (hasSellDatePassed(item)) {
            if (!item.name.equals("Aged Brie")) {
                if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        decrementQuality(item);
                } else {
                    item.quality = item.quality - item.quality;
                }
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
            }
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
