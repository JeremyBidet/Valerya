package org.valerya.utils;

import java.util.Objects;

public class Tuple<U, V, W> {

    public U first;
    public V second;
    public W third;

    public Tuple(U first, V second, W third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tuple)) {
            return false;
        }

        Tuple d = (Tuple) o;

        return Objects.equals(d.first, this.first) && Objects.equals(d.second, this.second) && Objects.equals(d.third, this.third);
    }

}
