package org.valerya.core;

import org.valerya.data.*;
import org.valerya.utils.StringHelper;
import org.valerya.utils.TriConsumer;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class PowerHelper {
    
    /**
     * Perform a prompted <code>or</code> between two items.<br>
     * The user has to choose one of them.<br>
     * <br>
     * <b><code>item1</code></b> the first item<br>
     * <b><code>item2</code></b> the second item<br>
     * <b><code>return</code></b> the selected item
     */
    public static final BiFunction<Resource, Resource, Resource> or = (resource1, resource2) -> {
        int choice = 0;
        System.out.println("Which one ?\n\t1. " + resource1 + "\n\t2. " + resource2);

        Scanner sc = new Scanner(System.in);
        while (choice != 1 && choice != 2) {
            System.out.print(": ");
            choice = sc.nextInt();
        }
        sc.close();

        return choice == 1 ? resource1 : resource2;
    };

    /**
     * Collect resource for a player.<br>
     * <br>
     * <b><code>player</code></b> the player to feed<br>
     * <b><code>item</code></b> the resource item<br>
     */
    public static final BiConsumer<Player, Resource> collect = (player, resource) -> {
        player.add(resource.resource, resource.quantity);
    };
    
    /**
     * Spend resource for a player.<br>
     * <br>
     * <b><code>player</code></b> the player to feed<br>
     * <b><code>item</code></b> the resource item<br>
     */
    public static final BiConsumer<Player, Resource> spend = (player, resource) -> {
        player.add(resource.resource, -resource.quantity);
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
    public static final TriConsumer<Player, Resource, Resource> trade = (player, resource1, resource2) -> {
        String choice = "";
        System.out.println("Trade " + resource1 + " for " + resource2 + " ?");

        Scanner sc = new Scanner(System.in);
        while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("yes") && !choice.equalsIgnoreCase("n") && !choice.equalsIgnoreCase("no")) {
            System.out.print(": ");
            choice = sc.next();
        }
        sc.close();

        spend.accept(player, resource1);
        collect.accept(player, resource2);
    };

    /**
     * Steal resource from a unique player.<br>
     * <br>
     * <b><code>player</code></b> the player to feed<br>
     * <b><code>item</code></b> the resource item and its source<br>
     */
    public static final BiConsumer<Player, Resource> steal = (player, resource) -> {
        String choice = "";
        System.out.println("Steal " + resource + " from ? (The stolen amount cannot overtake the target stock)");
        String players = Player.players.values().stream()
                .map(p -> "\t- " + p.name + " (" + p.get(resource.resource) + ")")
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
        resource.quantity = Math.min(target.get(resource.resource), resource.quantity);
        spend.accept(target, resource);
        collect.accept(player, resource);
    };
    
    /**
     * Steal randomly a given amount of domains from player(s).<br>
     * <br>
     * <b><code>player</code></b> the player to feed<br>
     * <b><code>quantity</code></b> the number of domains to steal<br>
     */
    public static final BiConsumer<Player, Integer> stealRandomCitizens = (player, quantity) -> PowerHelper.<Citizen>stealRandomCards(player, quantity, Type.CITIZEN);
    
    /**
     * Steal randomly a given amount of domains from player(s).<br>
     * <br>
     * <b><code>player</code></b> the player to feed<br>
     * <b><code>quantity</code></b> the number of domains to steal<br>
     */
    public static final BiConsumer<Player, Integer> stealRandomMonsters = (player, quantity) -> PowerHelper.<Monster>stealRandomCards(player, quantity, Type.MONSTER);
    
    /**
     * Steal randomly a given amount of domains from player(s).<br>
     * <br>
     * <b><code>player</code></b> the player to feed<br>
     * <b><code>quantity</code></b> the number of domains to steal<br>
     */
    public static final BiConsumer<Player, Integer> stealRandomDomains = (player, quantity) -> PowerHelper.<Domain>stealRandomCards(player, quantity, Type.DOMAIN);
    
    /**
     * Generic stealRandomCards method.<br>
     * According to the parameterized type of the card ({@link Domain}, {@link Monster} or {@link Citizen}),
     * this method will prompt the user to choose at least one player to steal from, the given quantity of cards.<br>
     * <br>
     * The {@linkplain Type type} param must match with the <code>&lt;CARD&gt;</code> type.<br>
     * Example: for &lt;{@link Domain}&gt; parameterized type, we must use the {@link Type#DOMAIN} card type.<br>
     * <br>
     * @param player the player who steal
     * @param quantity the cards quantity to steal
     * @param type the card type to steal
     * @param <CARD> type of the card
     */
    private static <CARD> void stealRandomCards(Player player, int quantity, Type type) {
        final Function<Player, List<CARD>> getter;
        if (type == Type.CITIZEN) {
            getter = p -> (List<CARD>) p.citizens;
        } else if(type == Type.MONSTER) {
            getter = p -> (List<CARD>) p.monsters;
        } else if(type == Type.DOMAIN) {
            getter = p -> (List<CARD>) p.domains;
        } else {
            return;
        }
        
        String choice = "";
        int choiceQty = 0;
        final Map<String, Integer> choices = new HashMap<>(quantity);
    
        System.out.println("Steal randomly " + quantity + " " + type.name().toLowerCase() + (quantity > 1 ? "s" : "") + " from ? (enter name->quantity)");
        String players = Player.players.values().stream()
                .map(p -> "\t- " + p.name + " (" + getter.apply(p).size() + ")\n"
                        + getter.apply(p).stream()
                        .map(m -> "\t\t- " + m.toString())
                        .reduce(StringHelper::joinLN)
                        .orElse(""))
                .reduce(StringHelper::joinLN)
                .orElse("");
        System.out.println(players);
    
        Scanner sc = new Scanner(System.in);
        while (choiceQty != quantity) {
            System.out.print(": ");
            choice = sc.next();
            final String[] choiceSplit = choice.split("->");
            if (choiceSplit.length == 2) {
                final String name = choiceSplit[0];
                try {
                    final int qty = Integer.parseInt(choiceSplit[1]);
                    if (Player.players.containsKey(choice)) {
                        choices.put(name, Math.min(qty, quantity - choiceQty));
                        choiceQty = Math.min(quantity, qty + choiceQty);
                    } else {
                        System.out.println("Invalid player name! Please retry.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity! Please retry.");
                }
            }
        }
        sc.close();
    
        choices.entrySet().stream().forEach(e -> {
            final Player target = Player.players.get(e.getKey());
            IntStream.range(0, e.getValue()).forEach(i -> {
                final CARD card = getter.apply(target).get(new Random().nextInt(getter.apply(target).size()));
                getter.apply(target).remove(card);
                getter.apply(player).add(card);
            });
        });
    }
    
    /**
     * Prompt the player to recruit a citizen of the given {@linkplain Role role} among the available citizens.<br>
     * <br>
     * <b><code>player</code></b> the recruiting player<br>
     * <b><code>role</code></b> the role of the citizenl<br>
     */
    public static final BiConsumer<Player, Role> recruit = (player, role) -> {
        String choice = "";
        System.out.println("Choose the " + role + " to recruit ?");
        String players = Game.citizens.entrySet().stream()
                .filter(e -> e.getKey().role == role && e.getValue() > 0)
                .map(e -> "\t- " + e.getValue() + " " + e.getKey().id)
                .reduce(StringHelper::joinLN)
                .orElse("");
        System.out.println(players);
    
        Scanner sc = new Scanner(System.in);
        while (!Citizen.citizens.containsKey(choice)) {
            System.out.print(": ");
            choice = sc.next();
        }
        sc.close();
        
        final Citizen citizen = Citizen.citizens.get(choice);
        Game.citizens.compute(citizen, (c, v) -> v - 1);
        player.citizens.add(citizen);
    };
    
    /**
     * Prompt the player to change a dice for a given amount of resource.<br>
     * <br>
     * <b><code>resource</code></b> the resource to spend<br>
     * <b><code>value</code></b> the new dice value<br>
     */
    public static final BiConsumer<Resource, Integer> changeDice = (resource, value) -> {
        int choice = -1;
        System.out.println("Spend " + resource + " to change a dice to a " + value + " ? Select the dice:");
        System.out.println("\t0. Do not change");
        System.out.println("\t1. " + Turn.current.firstMove.toss.dice1.value());
        System.out.println("\t2. " + Turn.current.firstMove.toss.dice2.value());
        
        Scanner sc = new Scanner(System.in);
        while (choice < 0 || choice > 2) {
            System.out.print(": ");
            choice = sc.nextInt();
        }
        sc.close();
        
        if(choice == 0) {
            return;
        }
        
        final Toss.Dice dice = choice == 1 ? Turn.current.firstMove.toss.dice1 : Turn.current.firstMove.toss.dice2;
        dice.change(value);
        spend.accept(Turn.current.firstMove.player, resource);
    };
    
}
