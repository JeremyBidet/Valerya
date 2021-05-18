package org.valerya.data;

import org.valerya.core.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class Power {

    private static final Consumer<Player> NO_POWER = (p) -> {};
    private static final Trigger NO_TRIGGER = null;

    public static final Map<String, Power> powers = new HashMap<>();

    /* Define CITIZEN powers */
    static {
        powers.put("clerk", Power.createForCitizen("clerk", true,
                (player) -> Moves.collect.accept(player, Item.create(3, Resource.MANA)),
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.MANA))));
        powers.put("monk", Power.createForCitizen("monk", true,
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.GOLD));
                    Moves.collect.accept(player, Item.create(2, Resource.MANA));
                },
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.MANA));
                    Moves.collect.accept(player, Item.create(2, Resource.GOLD));
                }));
        powers.put("blacksmith", Power.createForCitizen("blacksmith", true,
                (player) -> {
                    final int qty = (int) player.citizens.stream().filter(c -> Objects.equals(c.role, Role.SOLDIER)).count();
                    Moves.collect.accept(player, Item.create(qty, Resource.GOLD));
                },
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.GOLD))));
        powers.put("merchant", Power.createForCitizen("merchant", true,
                (player) -> {
                    Item item = Moves.or.apply(Item.create(2, Resource.GOLD), Item.create(2, Resource.MANA));
                    Moves.collect.accept(player, item);
                },
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.GOLD))));
        powers.put("alchemist", Power.createForCitizen("alchemist", true,
                (player) -> Moves.trade.accept(player, Item.create(1, Resource.GOLD), Item.create(3, Resource.MANA)),
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.MANA));
                    Moves.collect.accept(player, Item.create(2, Resource.GOLD));
                }));
        powers.put("mercenary", Power.createForCitizen("mercenary", true,
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.STRENGTH));
                    Moves.collect.accept(player, Item.create(1, Resource.GOLD));
                },
                (player) -> Moves.trade.accept(player, Item.create(1, Resource.STRENGTH), Item.create(2, Resource.GOLD))));
        powers.put("wizard", Power.createForCitizen("wizard", true,
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.MANA));
                    Moves.collect.accept(player, Item.create(1, Resource.STRENGTH));
                },
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.MANA))));
        powers.put("archer", Power.createForCitizen("archer", true,
                (player) -> Moves.collect.accept(player, Item.create(2, Resource.STRENGTH)),
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.STRENGTH))));
        powers.put("peasant", Power.createForCitizen("peasant", true,
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.GOLD)),
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.GOLD))));
        powers.put("knight", Power.createForCitizen("knight", true,
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.STRENGTH)),
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.STRENGTH))));
        powers.put("scoundrel", Power.createForCitizen("scoundrel", true,
                (player) -> {
                    Moves.collect.accept(player, Item.create(2, Resource.STRENGTH));
                    Moves.collect.accept(player, Item.create(2, Resource.GOLD));
                },
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.STRENGTH));
                    Moves.collect.accept(player, Item.create(1, Resource.GOLD));
                }));
        powers.put("thief", Power.createForCitizen("thief", true,
                (player) -> {
                    Item item = Moves.or.apply(Item.create(3, Resource.GOLD), Item.create(3, Resource.MANA));
                    Moves.steal.accept(player, item);
                },
                (player) -> {
                    Moves.collect.accept(player, Item.create(2, Resource.GOLD));
                    Moves.collect.accept(player, Item.create(2, Resource.MANA));
                }));
        powers.put("champion", Power.createForCitizen("champion", true,
                (player) -> Moves.collect.accept(player, Item.create(4, Resource.STRENGTH)),
                (player) -> Moves.trade.accept(player, Item.create(1, Resource.GOLD), Item.create(4, Resource.STRENGTH))));
        powers.put("warlord", Power.createForCitizen("warlord", true,
                (player) -> {
                    final int qty = (int) player.citizens.stream().filter(c -> Objects.equals(c.role, Role.SOLDIER)).count();
                    Moves.collect.accept(player, Item.create(qty, Resource.STRENGTH));
                },
                (player) -> {
                    final int qty = (int) player.citizens.stream().filter(c -> Objects.equals(c.id, "knight")).count();
                    Moves.collect.accept(player, Item.create(qty, Resource.STRENGTH));
                }));
        powers.put("priestess", Power.createForCitizen("priestess", true,
                (player) -> {
                    Moves.collect.accept(player, Item.create(2, Resource.STRENGTH));
                    Moves.collect.accept(player, Item.create(1, Resource.MANA));
                },
                (player) -> Moves.trade.accept(player, Item.create(1, Resource.MANA), Item.create(3, Resource.STRENGTH))));
        powers.put("paladin", Power.createForCitizen("paladin", true,
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.STRENGTH));
                    Moves.collect.accept(player, Item.create(2, Resource.MANA));
                },
                (player) -> Moves.trade.accept(player, Item.create(1, Resource.STRENGTH), Item.create(3, Resource.MANA))));
        powers.put("miner", Power.createForCitizen("miner", true,
                (player) -> {
                    final int qty = player.domains.size();
                    Moves.collect.accept(player, Item.create(1 + qty, Resource.GOLD));
                },
                (player) -> Moves.collect.accept(player, Item.create(4, Resource.GOLD))));
        powers.put("butcher", Power.createForCitizen("butcher", true,
                (player) -> {
                    final int qty = (int) player.citizens.stream().filter(c -> Objects.equals(c.role, Role.CRAFTMAN)).count();
                    Moves.collect.accept(player, Item.create(2 * qty, Resource.GOLD));
                },
                (player) -> Moves.collect.accept(player, Item.create(4, Resource.GOLD))));
    }

    /* Define DOMAIN powers */
    static {
        powers.put("shadows_dock", Power.createForDomain("shadows_dock", true,
                (player) -> Moves.collect.accept(player, Item.create(3, Resource.MANA)),
                Trigger.NOW));
    }

    /* Define MONSTER powers */
    static {
        powers.put("awful_bear", Power.createForMonster("awful_bear", true,
                (player) -> Moves.collect.accept(player, Item.create(3, Resource.MANA))));
    }

    /* Define DUKE powers */
    static {
        powers.put("waryn", Power.createForDuke("waryn", true,
                (player) -> Moves.collect.accept(player, Item.create(3, Resource.MANA))));
    }

    public final Type type;
    public final String id;
    public final Boolean auto;
    public final Consumer<Player> main;
    public final Consumer<Player> secondary;
    public final Trigger trigger;

    /**
     * Constructor used for {@link Type#CITIZEN} cards.
     *
     * @param type the type of the card owning this power
     * @param id the id of the card owning this power
     * @param auto true if the power is automatically activated, false otherwise
     * @param main the main power for this card
     * @param secondary the secondary power for this card
     * @param trigger the event that trigger the power
     */
    private Power(final Type type,
                  final String id,
                  final Boolean auto,
                  final Consumer<Player> main,
                  final Consumer<Player> secondary,
                  final Trigger trigger) {
        this.type = type;
        this.id = id;
        this.auto = auto;
        this.main = main;
        this.secondary = secondary;
        this.trigger = trigger;
    }

    /**
     * Factory used to create a Power for a {@link Type#CITIZEN} card.<br>
     * There's no trigger for this kind of card.<br>
     *
     * @param id the id of the card owning this power
     * @param auto true if the power is automatically activated, false otherwise
     * @param active the active power for a citizen (for the current player)
     * @param passive the passive power for a citizen (for inactive players)
     * @return the newly defined power
     */
    public static Power createForCitizen(final String id,
                                         final Boolean auto,
                                         final Consumer<Player> active,
                                         final Consumer<Player> passive) {
        return new Power(Type.CITIZEN, id, auto, active, passive, NO_TRIGGER);
    }

    /**
     * Factory used to create a Power for a {@link Type#DOMAIN} card.<br>
     * The main power is the power triggered when the domain is built.<br>
     * There's is no secondary power for domains.<br>
     *
     * @param id the id of the card owning this power
     * @param auto true if the power is automatically activated, false otherwise
     * @param built the power when a domain is built
     * @param trigger the event that trigger the power
     * @return the newly defined power
     */
    public static Power createForDomain(final String id,
                                        final Boolean auto,
                                        final Consumer<Player> built,
                                        final Trigger trigger) {
        return new Power(Type.DOMAIN, id, auto, built, NO_POWER, NO_TRIGGER);
    }

    /**
     * Factory used to create a Power for a {@link Type#MONSTER} card.<br>
     * The main power is the power triggered when the monster is defeated.<br>
     * There's is no secondary power for domains.<br>
     *
     * @param id the id of the card owning this power
     * @param auto true if the power is automatically activated, false otherwise
     * @param hunted the power when a monster is hunted
     * @return the newly defined power
     */
    public static Power createForMonster(final String id,
                                         final Boolean auto,
                                         final Consumer<Player> hunted) {
        return new Power(Type.MONSTER, id, auto, hunted, NO_POWER, NO_TRIGGER);
    }

    /**
     * Factory used to create a Power for a {@link Type#DUKE} card.<br>
     * The main power is the power triggered when the game end.<br>
     * There's is no secondary power for domains.<br>
     *
     * @param id the id of the card owning this power
     * @param auto true if the power is automatically activated, false otherwise
     * @param main the power when the game end
     * @return the newly defined power
     */
    public static Power createForDuke(final String id,
                                      final Boolean auto,
                                      final Consumer<Player> main) {
        return new Power(Type.DUKE, id, auto, main, NO_POWER, NO_TRIGGER);
    }

}
