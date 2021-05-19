package org.valerya.core;

/**
 * This class is used in power appliance.<br>
 * It has an item quantity and a resource type.<br>
 * <br>
 * The resource field is intended to work with {@link Resource} class.<br>
 * The value of the integer must match with one of the resources in the class.<br>
 * Convenients methods exist in Resource class to get:
 * <ul>
 *     <li>either the integer value of a resource according to a name</li>
 *     <li>either the name of a resource according to an integer value</li>
 * </ul>
 */
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
    
    /**
     *
     * @param quantity
     * @param resource
     * @return
     */
    public static Item create(final int quantity, final int resource) {
        return new Item(quantity, resource);
    }

}
