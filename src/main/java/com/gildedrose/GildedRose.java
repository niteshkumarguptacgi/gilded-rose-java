package com.gildedrose;

class GildedRose {
    public static final int MIN_QUALITY = 0;
    public static final int MAX_QUALITY = 50;
    public static final int TOO_CLOSE_TO_EXPIRE = 6;
    public static final int FAR_FROM_EXPIRE = 11;
    public static final int SELLIN_EXPIRY_DAY = 0;
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED = "Conjured";
    Item[] items;

    /**
     * GildedRose constructor. Takes array of items and assign it to local item variable.
     * @param items
     */
    public GildedRose(Item[] items) {
        this.items = items;
    }

    /**
     * Core method which updates item sellIn and quality. For each item control passed to two methods specifically,
     * handleQuality and handleSellIn. Both takes Object of Item as a parameter and update quality and sellIn based
     * on numerous criteria.
     */
    public void updateQuality() {
        for (Item item : items) {
            /* Call to quality handler */
            handleQuality(item);
            /* Call to sellIn handler */
            handleSellIn(item);
        }
    }

    /**
     * Quality helper method. Takes Object of Item as a parameter. For Aged brie or Backstage passes control to
     * increaseQualityIncludingBackstagePasses method. For all other items control passed to
     * decreaseQualityIfHasQuality method.
     * @param item
     */
    private void handleQuality(Item item) {
        if (!item.name.equals(AGED_BRIE)
                && !item.name.equals(BACKSTAGE_PASSES)) {
            decreaseQualityIfHasQuality(item);
        } else {
            increaseQualityIncludingBackstagePasses(item);
        }
    }

    /**
     * Method responsible to decrease quality. At first it makes sure that item has quality and its not Sulfuras then
     * passes control to decreaseQuality method. After receiving control back from decreaseQuality, it checks if item
     * is conjured then passes control to decreaseQuality again to make quality degrade as fast as twice
     * @param item
     */
    private void decreaseQualityIfHasQuality(Item item) {
        if (item.quality > MIN_QUALITY && !item.name.equals(SULFURAS)) {
            decreaseQuality(item);
            if (isConjured(item)) {
                decreaseQuality(item);
            }
        }
    }

    /**
     * Takes Item object as a parameter and decreases its quality by 1
     * @param item
     */
    private void decreaseQuality(Item item) {
        item.quality = item.quality - 1;
    }

    /**
     * Takes Item object as a parameter and checks if item name contains Conjured or not.
     * returns True if Conjured found else false
     * @param item
     * @return boolean
     */
    private boolean isConjured(Item item) {
        return item.name.contains(CONJURED);
    }

    /**
     * Takes Item object as a parameter. If item quantity is less than 50 then increases the quality.
     * It also takes care for Backstage passes quality increase by handing control to increaseQualityOfBackstagePasses
     * method.
     * @param item
     */
    private void increaseQualityIncludingBackstagePasses(Item item) {
        if (item.quality < MAX_QUALITY) {
            increaseQuality(item);
            increaseQualityOfBackstagePasses(item);
        }
    }

    /**
     * Takes Item object as a parameter. This method is dedicated to Backstage passes quality increase. It passes
     * control to increaseQualityForFarToExpire (for sellIn is 10 or lesser) and
     * increaseQualityForCloseToExpire (for sellIn is 5 or lesser) methods.
     * @param item
     */
    private void increaseQualityOfBackstagePasses(Item item) {
        if (item.name.equals(BACKSTAGE_PASSES)) {
            increaseQualityForFarToExpire(item);
            increaseQualityForCloseToExpire(item);
        }
    }

    /**
     * Takes Item object as a parameter. If item sellIn is 5 or less then passes control to increaseQualityIfNotMax
     * method.
     * @param item
     */
    private void increaseQualityForCloseToExpire(Item item) {
        if (item.sellIn < TOO_CLOSE_TO_EXPIRE) {
            increaseQualityIfNotMax(item);
        }
    }

    /**
     * Takes Item object as a parameter. If item sellIn is 10 or less then passes control to increaseQualityIfNotMax
     * method.
     * @param item
     */
    private void increaseQualityForFarToExpire(Item item) {
        if (item.sellIn < FAR_FROM_EXPIRE) {
            increaseQualityIfNotMax(item);
        }
    }

    /**
     * Takes Item object as a parameter. Passes control to increaseQuality if quality is less than 50.
     * @param item
     */
    private void increaseQualityIfNotMax(Item item) {
        if (item.quality < MAX_QUALITY) {
            increaseQuality(item);
        }
    }

    /**
     * Takes Item object as a parameter. Increases item's quality by 1
     * @param item
     */
    private void increaseQuality(Item item) {
        item.quality = item.quality + 1;
    }

    /**
     * Takes Item object as a parameter. Decreases sellIn by 1 except for Sulfuras. It also passes control to
     * handleIfExpired method for further quality alteration if required.
     * @param item
     */
    private void handleSellIn(Item item) {
        if (!item.name.equals(SULFURAS)) {
            item.sellIn = item.sellIn - 1;
        }
        handleIfExpired(item);
    }

    /**
     * Takes Item object as a parameter. Passes control to handleExpired method if sellIn is less than 0
     * @param item
     */
    private void handleIfExpired(Item item) {
        if (item.sellIn < SELLIN_EXPIRY_DAY) {
            handleExpired(item);
        }
    }

    /**
     * Takes Item object as a parameter. Passes control to handleQualityIfNotAgedBrie method for
     * non Aged Brie item and increaseQualityIfNotMax method for Aged Brie item.
     * @param item
     */
    private void handleExpired(Item item) {
        if (!item.name.equals(AGED_BRIE)) {
            handleQualityOfExpiredIfNotAgedBrie(item);
        } else {
            increaseQualityIfNotMax(item);
        }
    }

    /**
     * Takes Item object as a parameter. Passes control to decreaseQualityIfHasQuality method for
     * non Backstage passes item else sets quality to 0
     * @param item
     */
    private void handleQualityOfExpiredIfNotAgedBrie(Item item) {
        if (!item.name.equals(BACKSTAGE_PASSES)) {
            decreaseQualityIfHasQuality(item);
        } else {
            item.quality = MIN_QUALITY;
        }
    }

}
