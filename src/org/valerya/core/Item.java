package org.valerya.core;

public class Item {

    public int quantity;
    public int resource;

    /**
     * @param quantity the quantity of the {@link Resource}
     * @param resource the {@link Resource}
     */
    private Item(final int quantity, final int resource) {
        this.quantity = quantity;
        this.resource = resource;
    }

    /**
     * Return a human-friendly representation of the item quantity and {@linkplain Resource resource}.<br>
     */
    @Override
    public String toString() {
        return quantity + " " + resource;
    }

    public static Item create(final int quantity, final int resource) {
        return new Item(quantity, resource);
    }

}
