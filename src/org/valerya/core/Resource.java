package org.valerya.core;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Resource {

    public static final int GOLD = 1 << 0;
    public static final int MANA = 1 << 1;
    public static final int STRENGTH = 1 << 2;
    public static final int VP = 1 << 3;

    public static final int CITIZEN = 1 << 4;
    public static final int MONSTER = 1 << 5;
    public static final int DOMAIN = 1 << 6;

    public static final int SOLDIER = 1 << 7;
    public static final int CRAFTMAN = 1 << 8;
    public static final int SHADOW = 1 << 9;
    public static final int SAINT = 1 << 10;

    public static final int MINION = 1 << 11;
    public static final int BEAST = 1 << 12;
    public static final int TITAN = 1 << 13;
    public static final int BOSS = 1 << 14;

    public static final int FOREST = 1 << 15;
    public static final int VALLEY = 1 << 16;
    public static final int RUINS = 1 << 17;
    public static final int MOUNTAINS = 1 << 18;
    public static final int HILLS = 1 << 19;
    public static final int SWAMP = 1 << 20;
    public static final int CAVE = 1 << 21;
    public static final int MOORS = 1 << 22;

    public static final int PEASANT = 1 << 23;
    public static final int KNIGHT = 1 << 24;

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
        return "GOLD" + sep + "MANA" + sep + "STRENGTH" + sep + "VP"
                + sep + "CITIZEN" + sep + "MONSTER" + sep + "DOMAIN"
                + sep + "SOLDIER" + sep + "CRAFTMAN" + sep + "SHADOW" + sep + "SAINT"
                + sep + "MINION" + sep + "BEAST" + sep + "TITAN" + sep + "BOSS"
                + sep + "FOREST" + sep + "VALLEY" + sep + "RUINS" + sep + "MOUNTAINS" + sep + "HILLS" + sep + "SWAMP" + sep + "CAVE" + sep + "MOORS"
                + sep + "PEASANT" + sep + "KNIGHT";
    }

}
