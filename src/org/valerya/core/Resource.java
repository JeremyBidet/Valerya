package org.valerya.core;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class represent the resources used in game.<br>
 * It has a resource quantity and a resource type.<br>
 * <br>
 * The resource field is intended to work with {@linkplain Resource static fields}.<br>
 * The value of the integer must match with one of the resources in the class.<br>
 * <br>
 * 4 resources are available. Each resource is defined with bitwise operations:
 * <ul>
 *     <li>{@linkplain Resource#GOLD gold} => 1</li>
 *     <li>{@linkplain Resource#MANA mana} => 2</li>
 *     <li>{@linkplain Resource#STRENGTH strength} => 4</li>
 *     <li>{@linkplain Resource#VP victory point} => 8</li>
 * </ul>
 * The <code>|</code> (or) operator can be used to combine them.<br>
 * <br>
 * Convenient methods:
 * <ul>
 *     <li>{@link Resource#value}: get the integer value of resource(s) according to name(s)</li>
 *     <li>{@link Resource#names}: get the name(s) of resource(s) according to an integer value</li>
 * </ul>
 */
public class Resource {
    
    public static final int GOLD = 1 << 0;
    public static final int MANA = 1 << 1;
    public static final int STRENGTH = 1 << 2;
    public static final int VP = 1 << 3;
    
    /**
     * Get the name of the resource(s) according to the given resource integer value.<br>
     *
     * @param resource the integer value of the resource.
     * @return the name(s)
     */
    public static String[] names(final int resource) {
        Field[] fields = Resource.class.getFields();
        return Arrays.stream(fields)
                .filter(f -> {
                    try {
                        return ((Integer) f.get(null) & resource) != 0;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .map(Field::getName)
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }
    
    /**
     * Get the integer value of resource(s) according to the(ir) name(s).<br>
     *
     * @param names the name(s)
     * @return the integer value
     */
    public static int value(final String... names) {
        return Arrays.stream(names)
                .map(name -> {
                    try {
                        return (Integer) Resource.class.getField(name).get(null);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                        return 0;
                    }
                })
                .reduce(Integer::sum)
                .orElse(0);
    }
    
    public int quantity;
    public int resource;

    /**
     * @param quantity the quantity of the {@link Resource}
     * @param resource the {@link Resource}
     */
    private Resource(final int quantity, final int resource) {
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
     * Create a {@link Resource} with given quantity and resource type.<br>
     *
     * @param quantity the resource quantity
     * @param resource the resource type (as integer value)
     * @return the newly created item
     */
    public static Resource create(final int quantity, final int resource) {
        return new Resource(quantity, resource);
    }

}
