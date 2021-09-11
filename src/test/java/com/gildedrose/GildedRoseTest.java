package com.gildedrose;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    /**
     * Requirement: Once the sell by date has passed, Quality degrades twice as fast
     * Action: Test quality decrease twice as fast after sellIn
     * Logic: If item sellIn = 0 then quality = quality - 2 with exception of Sulfuras as its quality is immutable.
     * */
    @Test
    void testQualityDecreaseByTwiceAfterExpiry() {
        Item[] items = new Item[2];
        items[0] = new Item("Normal milk", 0, 32);
        items[1] = new Item("Normal oil", 0, 17);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(30, app.items[0].quality);
        assertEquals(-1, app.items[1].sellIn);
        assertEquals(15, app.items[1].quality);
    }

    /**
     * Requirement: The Quality of an item is never negative
     * Action: Test quality of item is never negative
     * Logic: If item quality = 0 then quality = 0
     * */
    @Test
    void testQualityOfItemIsNeverNegative() {
        Item[] items = new Item[] { new Item("Wine", 3, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    /**
     * Requirement: The Quality of an item is never more than 50
     * Action: Test quality does not increase beyond 50
     * Logic: Item quality <= 50 with exception of Sulfuras quality = 80
     * */
    @Test
    void testQualityMustNotIncreaseBeyondFifty() {
        Item[] items = new Item[] { new Item("Aged Brie", 4, 50)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(3, app.items[0].sellIn);
        assertEquals(50, app.items[0].quality);
    }

    /**
     * Requirement: "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
     * Action: Test Sulfuras quality doesn't change
     * Logic: Item name = Sulfuras, Hand of Ragnaros then sellIn = sellIn and quality = quality
     * */
    @Test
    void testQualityOfSulfurasDoesnotChange() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
    }

    /**
     * Requirement: At the end of each day our system lowers both values for every item
     * Action: Test items sellIn and quality decrease by 1
     * Logic: If normal item then quality = quality - 1
     * */
    @Test
    void testQualityDecreaseByOne() {
        Item[] items = new Item[] { new Item("+5 Dexterity Vest", 14, 40)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(39, app.items[0].quality);
    }

    /**
     * Requirement: 1. "Aged Brie" actually increases in Quality the older it gets
     *              2. Backstage passes", like aged brie, increases in Quality as its SellIn value approaches
     * Action: Test quality increase by 1 for improving items
     * Logic: If item name in Aged Brie or Backstage passes then quality = quality + 1 with exception of sellIn > 10
     * */
    @Test
    void testQualityIncreasesForImprovingItems(){
        Item[] items = new Item[2];
        items[0] = new Item("Backstage passes to a TAFKAL80ETC concert", 20, 20);
        items[1] = new Item("Aged Brie", 20, 20);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(19, app.items[0].sellIn);
        assertEquals(21, app.items[0].quality);
        assertEquals(19, app.items[1].sellIn);
        assertEquals(21, app.items[1].quality);
    }

    /**
     * Requirement: Backstage passes quality increases by 2 when there are 10 days or less
     * Action: Test quality increase by 2 for Backstage passes having sellIn between 10 and 6 days
     *         (including boundaries) i.e. close to expire
     * Logic: If item name  = "Backstage passes to a TAFKAL80ETC concert" and sellIn <= 10 and
     *        sellIn > 5 then quality = quality + 2
     * */
    @Test
    void testQualityIncreasesByTwoForCloseToExpiry() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn);
        assertEquals(22, app.items[0].quality);
    }

    /**
     * Requirement: Backstage passes quality increases by 3 when there are 5 days or less
     * Action: Test quality increase by 3 for Backstage passes having sellIn between 5 and 1 days
     *         i.e. too close to expire
     * Logic: If item name  = "Backstage passes to a TAFKAL80ETC concert" and sellIn <= 5 and
     *        sellIn > 0 then quality = quality + 3
     * */
    @Test
    void testQualityIncreasesByThreeForTooCloseToExpiry() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].sellIn);
        assertEquals(23, app.items[0].quality);
    }

    /**
     * Requirement: Backstage passes quality drops to 0 after the concert
     * Action: Test Backstage passes quality decreases to 0 after sellIn passes 0
     * Logic: If item name = "Backstage passes to a TAFKAL80ETC concert" and sellIn = 0 then
     *          quality = 0
     * */
    @Test
    void testQualityGoesToZeroForBackstagePassesAndBrieAfterExpiry() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    /**
     * Requirement: "Conjured" items degrade in Quality twice as fast as normal items
     * Action: Test conjured items quality decrease twice as fast
     * Logic: If item name = Conjured then quality = quality - 2
     * */
    @Test
    void testQualityDecreaseTwiceForConjuredItem() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 10, 6)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn);
        assertEquals(4, app.items[0].quality);
    }

}
