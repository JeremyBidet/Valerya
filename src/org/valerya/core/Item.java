package org.valerya.core;

import java.util.Scanner;

public class Item {

	public final int quantity;
	public final int resource;
	public final int source;

	/**
	 * @param quantity the quantity of the {@link Resource}
	 * @param resource the {@link Resource}
	 * @param source the {@link Source} from which the {@link Resource} comes
	 */
	public Item(final int quantity, final int resource, final int source) {
		this.quantity = quantity;
		this.resource = resource;
		this.source = source;
	}

	/**
	 * Init the item with the default {@link Source} {@linkplain Source#STOCK STOCK}.
	 *
	 * @param quantity the quantity of the {@link Resource}
	 * @param resource the {@link Resource}
	 */
	public Item(final int quantity, final int resource) {
		this(quantity, resource, Source.STOCK);
	}

	/**
	 * Return a human-friendly representation of the item quantity and {@linkplain Resource resource}.<br>
	 * Note: {@linkplain Source source} is returned if different from default {@link Source#STOCK}
	 */
	@Override
	public String toString() {
		return quantity + " " + resource + (source != Source.STOCK ? " from " + source : "");
	}

	/**
	 * Perform a prompted <code>or</code> between items.<br>
	 * The user has to choose one of the items.
	 * @param items the items
	 * @return the selected item
	 */
	public static Item Or(Item... items) {
		int i = 1;
		for (Item item : items) {
			System.out.println(i++ + ". " + item);
		}

		int choice;
		System.out.print("Choose one: ");

		Scanner sc = new Scanner(System.in);
		choice = sc.nextInt();

		return items[choice-1];
	}

	/**
	 * Perform a prompted yes/no for a trade action.
	 * @param item1 the item to give
	 * @param item2 the item to receive
	 * @return both items if yes, none if no
	 */
	public static Item[] For(Item item1, Item item2) {
		boolean yes;
		System.out.print("Trade " + item1 + " for " + item2 + " ?");

		Scanner sc = new Scanner(System.in);
		yes = sc.nextBoolean();

		return yes ? new Item[] {item1, item2} : null;
	}

	/**
	 * Calculate the amount of item to collect for each given resource.<br>
	 * Ex: Foreach( Item(1,GOLD), CITIZEN | SOLDIER ), will collect 1 gold for each soldier that are also citizens (i.e. excepted domains).<br>
	 * Note: the bitwise 'or' is not used to indicate multiple source (recruit 1 CITIZEN and 1 SOLDIER) but a 'and' criteria (recruit 1 Resource=CITIZEN & SOLDIER)
	 * <br>
	 * @param item the item to collect for each resource
	 * @param resources the bitwise 'or' of {@linkplain Resource resources}
	 * @return the calculated item to collect
	 */
	public static Item Foreach(Item item, int resources) {
		return null;
	}

}
