package org.valerya.utils;

import java.util.Arrays;
import java.util.Objects;

public class ObjectHelper {
	
	public static boolean equals(Object... objects) {
		final long nonNulls = Arrays.stream(objects).filter(Objects::nonNull).count();
		if (nonNulls > 0 && nonNulls < objects.length) {
			return false;
		}
		return Arrays.stream(objects).allMatch(o -> Objects.equals(o, objects[0]));
	}
	
}
