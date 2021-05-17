package org.valerya.utils;

import java.util.stream.IntStream;

public class StringHelper {

    public static String indent(int level) {
        return IntStream.range(0, level).mapToObj(i -> "\t").reduce(String::concat).orElse("");
    }

    private static String join(final String s1, final String s2, final String sep) {
        return String.join(sep, s1, s2);
    }

    public static String joinLN(final String s1, final String s2) {
        return join(s1, s2, "\n");
    }

    public static String joinSpace(final String s1, final String s2) {
        return join(s1, s2, " ");
    }

    public static String joinHyphen(final String s1, final String s2) {
        return join(s1, s2, "-");
    }

    public static String joinColon(final String s1, final String s2) {
        return join(s1, s2, ":");
    }

    public static String joinSemicolon(final String s1, final String s2) {
        return join(s1, s2, ";");
    }

    public static String joinComma(final String s1, final String s2) {
        return join(s1, s2, ",");
    }

    public static String joinPipe(final String s1, final String s2) {
        return join(s1, s2, "|");
    }

    public static String joinSimpleRightArrow(final String s1, final String s2) {
        return join(s1, s2, "->");
    }

    public static String joinSimpleLeftArrow(final String s1, final String s2) {
        return join(s1, s2, "<-");
    }

    public static String joinDoubleRightArrow(final String s1, final String s2) {
        return join(s1, s2, "=>");
    }

    public static String joinDoubleLeftArrow(final String s1, final String s2) {
        return join(s1, s2, "<=");
    }

}
