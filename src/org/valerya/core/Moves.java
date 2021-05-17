package org.valerya.core;

import org.valerya.utils.StringHelper;
import org.valerya.utils.TriConsumer;

import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Moves {

    /**
     * Perform a prompted <code>or</code> between two items.<br>
     * The user has to choose one of them.<br>
     * <br>
     * <b><code>item1</code></b> the first item<br>
     * <b><code>item2</code></b> the second item<br>
     * <b><code>return</code></b> the selected item
     */
    public static final BiFunction<Item, Item, Item> or = (item1, item2) -> {
        int choice = 0;
        System.out.println("Which one ?\n\t1. " + item1 + "\n\t2. " + item2);

        Scanner sc = new Scanner(System.in);
        while (choice != 1 && choice != 2) {
            System.out.print(": ");
            choice = sc.nextInt();
        }
        sc.close();

        return choice == 1 ? item1 : item2;
    };

    /**
     * Collect resource for a player.<br>
     * <br>
     * <b><code>player</code></b> the player to feed<br>
     * <b><code>item</code></b> the resource item<br>
     */
    public static final BiConsumer<Player, Item> collect = (player, item) -> {
        player.add(item.resource, item.quantity);
    };

    /**
     * Perform a prompted <code>trade</code> between two items.<br>
     * The user has to choose if he wants to trade them.<br>
     * <br>
     * <b><code>player</code></b> the player to feed<br>
     * <b><code>item1</code></b> the first item<br>
     * <b><code>item2</code></b> the second item<br>
     * <b><code>return</code></b> the selected item
     */
    public static final TriConsumer<Player, Item, Item> trade = (player, item1, item2) -> {
        String choice = "";
        System.out.println("Trade " + item1 + " for " + item2 + " ?");

        Scanner sc = new Scanner(System.in);
        while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("yes") && !choice.equalsIgnoreCase("n") && !choice.equalsIgnoreCase("no")) {
            System.out.print(": ");
            choice = sc.next();
        }
        sc.close();

        collect.accept(player, item1);
        collect.accept(player, item2);
    };

    /**
     * Steal resource from a unique player.<br>
     * <br>
     * <b><code>player</code></b> the player to feed<br>
     * <b><code>item</code></b> the resource item and its source<br>
     */
    public static final BiConsumer<Player, Item> steal = (player, item) -> {
        String choice = "";
        System.out.println("Steal " + item + " from ?");
        String players = Player.players.values().stream()
                .map(p -> "\t- " + p.name + " (" + p.get(item.resource) + ")")
                .reduce(StringHelper::joinLN)
                .orElse("");
        System.out.println(players);

        Scanner sc = new Scanner(System.in);
        while (!Player.players.containsKey(choice)) {
            System.out.print(": ");
            choice = sc.next();
        }
        sc.close();

        final Player target = Player.players.get(choice);
        final int quantity = Math.min(target.get(item.resource), item.quantity);
        target.add(item.resource, -quantity);
        player.add(item.resource, quantity);
    };

}
