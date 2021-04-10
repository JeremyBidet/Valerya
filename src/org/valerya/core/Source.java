package org.valerya.core;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.valerya.data.Citizen;
import org.valerya.data.Domain;
import org.valerya.data.Monster;

public class Source {

	public static final int STOCK = 1<<0;
	public static final int CITIZEN = 1<<1;
	public static final int DOMAIN = 1<<2;
	public static final int MONSTER = 1<<3;
	public static final int ANY_PLAYER = 1<<4;
	public static final int TWO_PLAYER = 1<<5;
	public static final int THREE_PLAYER = 1<<6;
	public static final int FOUR_PLAYER = 1<<7;
	public static final int ALL_PLAYER = 1<<8;

	public static String[] names(final int source) {
		Field[] fields = Source.class.getFields();
		return (String[]) Arrays.stream(fields)
				.filter(f -> {
					try {
						return ((Integer) f.get(null) & source) != 0;
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						return false;
					}
				})
				.map(Field::getName)
				.collect(Collectors.toList())
				.toArray();
	}

	public static int value(final String... names) {
		return Arrays.stream(names)
				.map(name -> {
					try {
						return (Integer) Source.class.getField(name).get(null);
					} catch (IllegalAccessException | NoSuchFieldException e) {
						e.printStackTrace();
						return 0;
					}
				})
				.reduce(Integer::sum)
				.orElse(0);
	}

	public static Object get(final int source) {
		if(source == STOCK) {
			return null;
		}
		switch(source) {
			case CITIZEN: return choose(Citizen.citizens);
			case DOMAIN: return choose(Domain.domains);
			case MONSTER: return choose(Monster.monsters);
			default: break;
		}
		if((source & (ANY_PLAYER | TWO_PLAYER | THREE_PLAYER | FOUR_PLAYER | ALL_PLAYER)) != 0) {
			return choose(Player.players);
		}
		return null;
	}

	private static Object choose(final Map<String, ? extends Object> choices) {
		Map<Integer, String> matches = new HashMap<>();

		int i = 0;
		for(String name : choices.keySet()) {
			matches.put(i++, name);
		}

		for(Map.Entry<Integer, String> item : matches.entrySet()) {
			System.out.println(item.getKey() + ". " + item.getValue());
		}

		int choice;
		System.out.print("Choose one: ");

		Scanner sc = new Scanner(System.in);
		choice = sc.nextInt();

		return choices.get(matches.get(choice));
	}

}
