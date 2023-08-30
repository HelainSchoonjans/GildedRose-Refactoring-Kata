package com.gildedrose

import spock.lang.Specification

class GildedRoseSpec extends Specification {

    def "should update quality correctly"() {
        given: "some items"
        Item[] items = [new Item(name, sellIn, quality)]

        and: "the application with these items"
        GildedRose app = new GildedRose(items)

        when: "updating quality"
        app.updateQuality()

        then: "the quality is correct"
        app.items[0].quality == expectedQuality

        where:
        name                                        | sellIn | quality | expectedQuality
        "foo"                                       | 0      | 0       | 0
        "Aged Brie"                                 | 0      | 0       | 2
        "Backstage passes to a TAFKAL80ETC concert" | 0      | 0       | 0
        "Sulfuras, Hand of Ragnaros"                | 0      | 0       | 0

        "foo"                                       | 1      | 0       | 0
        "Aged Brie"                                 | 1      | 0       | 1
        "Backstage passes to a TAFKAL80ETC concert" | 1      | 0       | 3
        "Sulfuras, Hand of Ragnaros"                | 1      | 0       | 0

        "foo"                                       | 0      | 1       | 0
        "Aged Brie"                                 | 0      | 1       | 3
        "Backstage passes to a TAFKAL80ETC concert" | 0      | 1       | 0
        "Sulfuras, Hand of Ragnaros"                | 0      | 1       | 1

        "foo"                                       | -1      | 3       | 1
    }


    def "should update sellIn correctly"() {
        given: "some items"
        Item[] items = [new Item(name, sellIn, quality)]

        and: "the application with these items"
        GildedRose app = new GildedRose(items)

        when: "updating quality"
        app.updateQuality()

        then: "the sellIn is correct"
        app.items[0].name == name
        app.items[0].sellIn == expectedSellIn

        where:
        name                                        | sellIn | quality | expectedSellIn
        "foo"                                       | 0      | 0       | -1
        "Aged Brie"                                 | 0      | 0       | -1
        "Backstage passes to a TAFKAL80ETC concert" | 0      | 0       | -1
        "Sulfuras, Hand of Ragnaros"                | 0      | 0       | 0
    }

}
