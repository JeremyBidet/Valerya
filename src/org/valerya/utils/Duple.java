package org.valerya.utils;

import java.util.Objects;

public class Duple<U, V> {

    public U first;
    public V second;

    public Duple(U first, V second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Duple)) {
            return false;
        }

        Duple d = (Duple) o;

        return Objects.equals(d.first, this.first) && Objects.equals(d.second, this.second);
    }

}
