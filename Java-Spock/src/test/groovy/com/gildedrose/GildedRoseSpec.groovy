package com.gildedrose

import spock.lang.Specification
import spock.lang.Unroll

class GildedRoseSpec extends Specification {

    @Unroll
    def "should #message for backstage"() {
        given: "some items"
        Item[] items = [new Item("Backstage passes to a TAFKAL80ETC concert", sellIn, quality)]

        and: "the application with these items"
        GildedRose app = new GildedRose(items)

        when: "updating quality"
        app.updateQuality()

        then: "the quality is correct"
        app.items[0].quality == expectedQuality

        where:
        sellIn | quality | expectedQuality | message
        11     | 0       | 1               | "increase quality by one if there is more than ten days"
        10     | 0       | 2               | "increase quality by two if there is ten days of less"
        2      | 0       | 3               | "increase quality by three if there is five days of less"
        0      | 10      | 0               | "drop quality to zero after the concert"
    }

    def "should update conjured item quality twice as fast"() {
        given: "some items"
        Item[] items = [new Item("Conjured Mana Cake", sellIn, quality)]

        and: "the application with these items"
        GildedRose app = new GildedRose(items)

        when: "updating quality"
        app.updateQuality()

        then: "the quality is correct"
        app.items[0].quality == expectedQuality

        where:
        sellIn | quality | expectedQuality
        10     | 10      | 8
        10     | 5       | 3
        0      | 10      | 6
        0      | 3       | 0
    }

    def "should increase quality for aged brie"() {
        given: "some items"
        Item[] items = [new Item(name, sellIn, quality)]

        and: "the application with these items"
        GildedRose app = new GildedRose(items)

        when: "updating quality"
        app.updateQuality()

        then: "the quality increased"
        app.items[0].quality > quality

        where:
        name        | sellIn | quality
        "Aged Brie" | 0      | 0
        "Aged Brie" | 1      | 0
        "Aged Brie" | 0      | 1
        "Aged Brie" | -1     | 1
    }

    def "should keep Sulfuras quality unchanged"() {
        given: "some items"
        Item[] items = [new Item(name, sellIn, quality)]

        and: "the application with these items"
        GildedRose app = new GildedRose(items)

        when: "updating quality"
        app.updateQuality()

        then: "the quality increased"
        app.items[0].quality == quality

        where:
        name                         | sellIn | quality
        "Sulfuras, Hand of Ragnaros" | 0      | 80
        "Sulfuras, Hand of Ragnaros" | 1      | 80
        "Sulfuras, Hand of Ragnaros" | -1     | 80
    }

    def "should never update a quality below zero"() {
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

        "foo"                                       | -1     | 3       | 1
        // this item never decreases in quality
        "Sulfuras, Hand of Ragnaros"                | 0      | 80      | 80
    }

    def "should update quality twice as fast when sell date has passed"() {
        given: "some items"
        Item[] items = [new Item(name, 0, quality), new Item(name, 1, quality)]

        and: "the application with these items"
        GildedRose app = new GildedRose(items)

        when: "updating quality"
        app.updateQuality()

        then: "the quality is correct"
        int qualityChangeForExpiredItem = app.items[0].quality - quality
        int qualityChangeForNonExpiredItem = app.items[1].quality - quality
        qualityChangeForExpiredItem == 2 * qualityChangeForNonExpiredItem

        where:
        name                         | quality
        "foo"                        | 0
        "Aged Brie"                  | 0

        "foo"                        | 4
        "Aged Brie"                  | 4

        // this item never decreases in quality
        "Sulfuras, Hand of Ragnaros" | 80
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
        // this item never has to be sold
        "Sulfuras, Hand of Ragnaros"                | 0      | 0       | 0
    }

}
