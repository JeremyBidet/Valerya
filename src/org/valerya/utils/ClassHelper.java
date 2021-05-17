package org.valerya.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Abstract class inherited to auto override {@link Object#toString()} method.<br>
 * Also provides an abstract method used to get the ID that will represent the inherited class.<br>
 * The type <code>T</code> refers to the type of the ID, not the class itself.
 */
public abstract class ClassHelper<T> {

    private static int indentLevel = 0;

    private static Set<Integer> printedValue = new HashSet<>();

    private static String parseValue(Object value) {
        if (value == null) {
            return "<null>";
        }

        if (value instanceof Collection) {
            ClassHelper.indentLevel += 2;
            final int level = ClassHelper.indentLevel;

            Collection<Object> l = (Collection<Object>) value;
            if (l.size() == 0) {
                ClassHelper.indentLevel -= 2;
                return "{" + l.size() + "}" + "[]";
            } else {
                String toStr = l.stream()
                        .filter(Objects::nonNull)
                        .map(o -> StringHelper.indent(level) + o)
                        .reduce((o1, o2) -> o1 + ",\n" + o2)
                        .orElse("");

                ClassHelper.indentLevel -= 2;
                return "{" + l.size() + "}" + "[" + "\n" + toStr + "\n" + StringHelper.indent(level - 1) + "]";
            }
        }

        if (value instanceof Map) {
            ClassHelper.indentLevel += 2;
            final int level = ClassHelper.indentLevel;

            Map<Object, Object> m = (Map<Object, Object>) value;
            if (m.size() == 0) {
                ClassHelper.indentLevel -= 2;
                return "{" + m.size() + "}" + "[]";
            } else {
                String toStr = m.entrySet().stream()
                        .map(e -> StringHelper.indent(level) + "{" + e.getKey() + ": " + e.getValue() + "}")
                        .reduce((o1, o2) -> o1 + ",\n" + o2)
                        .orElse("");

                ClassHelper.indentLevel -= 2;
                return "{" + m.size() + "}" + "[" + "\n" + StringHelper.indent(level) + toStr + "\n" + StringHelper.indent(level - 1) + "]";
            }
        }

        if (!(value instanceof Integer ||
                value instanceof Boolean ||
                value instanceof String ||
                value instanceof Double ||
                value instanceof Long ||
                value instanceof Float ||
                value instanceof Enum)) {
            return value.toString();
        }

        return value.toString();
    }

    public abstract T getId();

    @Override
    public String toString() {
        final int level = ClassHelper.indentLevel;

        if (printedValue.contains(this.hashCode())) {
            return this.getId() + " [" + Integer.toHexString(super.hashCode()) + "]";
        }

        printedValue.add(this.hashCode());

        final Field[] fields = this.getClass().getFields();

        final String fieldsToString = Arrays.stream(fields)
                .map(field -> {
                    try {
                        return StringHelper.indent(level + 1) + field.getName() + " = " + parseValue(field.get(this));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return String.format(StringHelper.indent(level + 1) + "<Cannot access field %>", field.getName());
                })
                .reduce((f1, f2) -> f1 + "\n" + f2)
                .orElse(StringHelper.indent(level + 1) + "<No field founded>");

        return this.getClass().getSimpleName() + " [" + Integer.toHexString(super.hashCode()) + "] {" + "\n" + fieldsToString + "\n" + StringHelper.indent(level) + "}";
    }

}
