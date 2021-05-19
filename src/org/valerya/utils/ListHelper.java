package org.valerya.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListHelper {
	
	/**
	 * Filter the given list with the given filter.<br>
	 *
	 * @param <T> the type of the list elements
	 * @param list the list to filter
	 * @param predicate the filter
	 * @return the filtered list
	 */
	public static <T> List<T> filter(final Collection<T> list, final Predicate<T> predicate) {
		if (list == null) {
			return new ArrayList<T>();
		}
		return list.stream().filter(predicate).collect(Collectors.toList());
	}
	
	/**
	 * Count the elements matching the predicate into the given list.<br>
	 *
	 * @param list the list to evaluate
	 * @param predicate the filter
	 * @param <T> the type of the list elements
	 * @return the number of elements matching the filter
	 */
	public static <T> int count(final Collection<T> list, final Predicate<T> predicate) {
		if (list == null) {
			return 0;
		}
		return (int) list.stream().filter(predicate).count();
	}
	
}
