package org.valerya.core;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Resource {

    public static final int GOLD = 1 << 0;
    public static final int MANA = 1 << 1;
    public static final int STRENGTH = 1 << 2;
    public static final int VP = 1 << 3;

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

    /**
     * Return a string representing all fields of this class, separated with the <code>sep</code> param.
     *
     * @param sep the separator
     * @return the string representation
     */
    public static String toString(final String sep) {
        return "GOLD" + sep + "MANA" + sep + "STRENGTH" + sep + "VP";
    }

}
