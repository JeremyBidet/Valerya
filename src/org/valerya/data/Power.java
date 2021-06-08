package org.valerya.data;

import org.valerya.core.*;
import org.valerya.utils.ListHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class Power {

    private static final Consumer<Player> NO_POWER = p -> {};
    private static final Trigger NO_TRIGGER = null;

    public static final Map<String, Power> powers = new HashMap<>();

    /* Define CITIZEN powers */
    static {
        powers.put("clerk", Power.createForCitizen("clerk", true,
                (player) -> PowerHelper.collect.accept(player, Resource.create(3, Resource.MANA)),
                (player) -> PowerHelper.collect.accept(player, Resource.create(1, Resource.MANA))));
        powers.put("monk", Power.createForCitizen("monk", true,
                (player) -> {
                    PowerHelper.collect.accept(player, Resource.create(1, Resource.GOLD));
                    PowerHelper.collect.accept(player, Resource.create(2, Resource.MANA));
                },
                (player) -> {
                    PowerHelper.collect.accept(player, Resource.create(1, Resource.MANA));
                    PowerHelper.collect.accept(player, Resource.create(2, Resource.GOLD));
                }));
        powers.put("blacksmith", Power.createForCitizen("blacksmith", true,
                (player) -> {
                    final int qty = ListHelper.<Citizen>count(player.citizens, c -> c.role == Role.SOLDIER);
                    PowerHelper.collect.accept(player, Resource.create(qty, Resource.GOLD));
                },
                (player) -> PowerHelper.collect.accept(player, Resource.create(1, Resource.GOLD))));
        powers.put("merchant", Power.createForCitizen("merchant", true,
                (player) -> {
                    Resource resource = PowerHelper.or.apply(Resource.create(2, Resource.GOLD), Resource.create(2, Resource.MANA));
                    PowerHelper.collect.accept(player, resource);
                },
                (player) -> PowerHelper.collect.accept(player, Resource.create(1, Resource.GOLD))));
        powers.put("alchemist", Power.createForCitizen("alchemist", true,
                (player) -> PowerHelper.trade.accept(player, Resource.create(1, Resource.GOLD), Resource.create(3, Resource.MANA)),
                (player) -> {
                    PowerHelper.collect.accept(player, Resource.create(1, Resource.MANA));
                    PowerHelper.collect.accept(player, Resource.create(2, Resource.GOLD));
                }));
        powers.put("mercenary", Power.createForCitizen("mercenary", true,
                (player) -> {
                    PowerHelper.collect.accept(player, Resource.create(1, Resource.STRENGTH));
                    PowerHelper.collect.accept(player, Resource.create(1, Resource.GOLD));
                },
                (player) -> PowerHelper.trade.accept(player, Resource.create(1, Resource.STRENGTH), Resource.create(2, Resource.GOLD))));
        powers.put("wizard", Power.createForCitizen("wizard", true,
                (player) -> {
                    PowerHelper.collect.accept(player, Resource.create(1, Resource.MANA));
                    PowerHelper.collect.accept(player, Resource.create(1, Resource.STRENGTH));
                },
                (player) -> PowerHelper.collect.accept(player, Resource.create(1, Resource.MANA))));
        powers.put("archer", Power.createForCitizen("archer", true,
                (player) -> PowerHelper.collect.accept(player, Resource.create(2, Resource.STRENGTH)),
                (player) -> PowerHelper.collect.accept(player, Resource.create(1, Resource.STRENGTH))));
        powers.put("peasant", Power.createForCitizen("peasant", true,
                (player) -> PowerHelper.collect.accept(player, Resource.create(1, Resource.GOLD)),
                (player) -> PowerHelper.collect.accept(player, Resource.create(1, Resource.GOLD))));
        powers.put("knight", Power.createForCitizen("knight", true,
                (player) -> PowerHelper.collect.accept(player, Resource.create(1, Resource.STRENGTH)),
                (player) -> PowerHelper.collect.accept(player, Resource.create(1, Resource.STRENGTH))));
        powers.put("scoundrel", Power.createForCitizen("scoundrel", true,
                (player) -> {
                    PowerHelper.collect.accept(player, Resource.create(2, Resource.STRENGTH));
                    PowerHelper.collect.accept(player, Resource.create(2, Resource.GOLD));
                },
                (player) -> {
                    PowerHelper.collect.accept(player, Resource.create(1, Resource.STRENGTH));
                    PowerHelper.collect.accept(player, Resource.create(1, Resource.GOLD));
                }));
        powers.put("thief", Power.createForCitizen("thief", true,
                (player) -> {
                    Resource resource = PowerHelper.or.apply(Resource.create(3, Resource.GOLD), Resource.create(3, Resource.MANA));
                    PowerHelper.steal.accept(player, resource);
                },
                (player) -> {
                    PowerHelper.collect.accept(player, Resource.create(2, Resource.GOLD));
                    PowerHelper.collect.accept(player, Resource.create(2, Resource.MANA));
                }));
        powers.put("champion", Power.createForCitizen("champion", true,
                (player) -> PowerHelper.collect.accept(player, Resource.create(4, Resource.STRENGTH)),
                (player) -> PowerHelper.trade.accept(player, Resource.create(1, Resource.GOLD), Resource.create(4, Resource.STRENGTH))));
        powers.put("warlord", Power.createForCitizen("warlord", true,
                (player) -> {
                    final int qty = ListHelper.<Citizen>count(player.citizens, c -> c.role == Role.SOLDIER);
                    PowerHelper.collect.accept(player, Resource.create(qty, Resource.STRENGTH));
                },
                (player) -> {
                    final int qty = ListHelper.<Citizen>count(player.citizens, c -> "knight".equals(c.id));
                    PowerHelper.collect.accept(player, Resource.create(qty, Resource.STRENGTH));
                }));
        powers.put("priestess", Power.createForCitizen("priestess", true,
                (player) -> {
                    PowerHelper.collect.accept(player, Resource.create(2, Resource.STRENGTH));
                    PowerHelper.collect.accept(player, Resource.create(1, Resource.MANA));
                },
                (player) -> PowerHelper.trade.accept(player, Resource.create(1, Resource.MANA), Resource.create(3, Resource.STRENGTH))));
        powers.put("paladin", Power.createForCitizen("paladin", true,
                (player) -> {
                    PowerHelper.collect.accept(player, Resource.create(1, Resource.STRENGTH));
                    PowerHelper.collect.accept(player, Resource.create(2, Resource.MANA));
                },
                (player) -> PowerHelper.trade.accept(player, Resource.create(1, Resource.STRENGTH), Resource.create(3, Resource.MANA))));
        powers.put("miner", Power.createForCitizen("miner", true,
                (player) -> {
                    final int qty = player.domains.size();
                    PowerHelper.collect.accept(player, Resource.create(1 + qty, Resource.GOLD));
                },
                (player) -> PowerHelper.collect.accept(player, Resource.create(4, Resource.GOLD))));
        powers.put("butcher", Power.createForCitizen("butcher", true,
                (player) -> {
                    final int qty = ListHelper.<Citizen>count(player.citizens, c -> c.role == Role.CRAFTMAN);
                    PowerHelper.collect.accept(player, Resource.create(2 * qty, Resource.GOLD));
                },
                (player) -> PowerHelper.collect.accept(player, Resource.create(4, Resource.GOLD))));
    }

    /* Define DOMAIN powers */
    static {
        powers.put("shadows_dock", Power.createForDomain("shadows_dock", true,
                (player) -> PowerHelper.stealRandomMonsters.accept(player, 1),
                Trigger.NOW));
        powers.put("lamentations_source", Power.createForDomain("lamentations_source", true,
                (player) -> {},
                Trigger.AFTER_RECRUIT));
        powers.put("craftmen_village", Power.createForDomain("craftmen_village", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("darks_harbor", Power.createForDomain("darks_harbor", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("blackwidow_den", Power.createForDomain("blackwidow_den", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("graylake_dungeon", Power.createForDomain("graylake_dungeon", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("coupe_gorge", Power.createForDomain("coupe_gorge", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("urdr_orb", Power.createForDomain("urdr_orb", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("asteraton_eye", Power.createForDomain("asteraton_eye", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("dawn_citadel", Power.createForDomain("dawn_citadel", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("st_aquilin_cathedral", Power.createForDomain("st_aquilin_cathedral", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("lakeside_lookout", Power.createForDomain("lakeside_lookout", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("scarlet_battalion", Power.createForDomain("scarlet_battalion", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("purple_thorn", Power.createForDomain("purple_thorn", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("peak_camp", Power.createForDomain("peak_camp", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("emerald_fortress", Power.createForDomain("emerald_fortress", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("nae_golden_obelisk", Power.createForDomain("nae_golden_obelisk", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("missous_hill", Power.createForDomain("missous_hill", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("ostendaar_monolith", Power.createForDomain("ostendaar_monolith", true,
                (player) -> {},
                Trigger.NOW));
        powers.put("desert_orchid", Power.createForDomain("desert_orchid", true,
                (player) -> {
                    final int qty = ListHelper.<Citizen>count(player.citizens, c -> c.role == Role.SAINT);
                    PowerHelper.changeDice.accept(Resource.create(qty, Resource.GOLD), 1);
                },
                Trigger.AFTER_TOSS));
        powers.put("fox_grove", Power.createForDomain("fox_grove", true,
                (player) -> PowerHelper.changeDice.accept(Resource.create(2, Resource.GOLD), 6),
                Trigger.AFTER_TOSS));
        powers.put("broken_hand", Power.createForDomain("broken_hand", true,
                (player) -> PowerHelper.recruit.accept(player, Role.SAINT),
                Trigger.NOW));
        powers.put("pratchett_plateau", Power.createForDomain("pratchett_plateau", true,
                (player) -> { /*TODO: complex, because we modify a domain and not a player*/ },
                Trigger.BEFORE_BUILD));
        powers.put("gargane_embrace", Power.createForDomain("gargane_embrace", true,
                (player) -> {
                    if (Objects.equals(Turn.current.firstMove.toss.dice1, Turn.current.firstMove.toss.dice2)) {
                        PowerHelper.collect.accept(player,Resource.create(1, Resource.VP));
                    }
                },
                Trigger.AFTER_TOSS));
    }

    /* Define MONSTER powers */
    static {
        powers.put("awful_bear", Power.createForMonster("awful_bear", true,
                (player) -> PowerHelper.collect.accept(player, Resource.create(3, Resource.MANA))));
    }

    /* Define DUKE powers */
    static {
        powers.put("waryn", Power.createForDuke("waryn", true,
                (player) -> PowerHelper.collect.accept(player, Resource.create(3, Resource.MANA))));
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
