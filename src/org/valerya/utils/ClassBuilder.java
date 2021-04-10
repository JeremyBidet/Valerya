package org.valerya.utils;

import java.lang.reflect.Field;

public interface ClassBuilder<T> {
	
	default T with(final String fieldName, final Object fieldValue) {
		final Field field;
		try {
			field = ((T) this).getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return (T) this;
		}
		
		try {
			field.set(this, fieldValue);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
			return (T) this;
		}
		
		return (T) this;
	}
	
}
