package org.valerya.data;

import org.valerya.core.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class Power {

    // TODO: implement check control before power appliance
    // TODO: implement auto:true|false (passive? active?)

    public static final Map<String, Power> powers = new HashMap<>();

    /* Define CITIZEN powers */
    static {
        powers.put("clerk", new Power(Type.CITIZEN, "clerk",
                (player) -> Moves.collect.accept(player, Item.create(3, Resource.MANA)),
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.MANA)),
                null));
        powers.put("monk", new Power(Type.CITIZEN, "monk",
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.GOLD));
                    Moves.collect.accept(player, Item.create(2, Resource.MANA));
                },
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.MANA));
                    Moves.collect.accept(player, Item.create(2, Resource.GOLD));
                },
                null));
        powers.put("blacksmith", new Power(Type.CITIZEN, "blacksmith",
                (player) -> {
                    final int qty = (int) player.citizens.stream().filter(c -> Objects.equals(c.role, Role.SOLDIER)).count();
                    Moves.collect.accept(player, Item.create(qty, Resource.GOLD));
                },
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.GOLD)),
                null));
        powers.put("merchant", new Power(Type.CITIZEN, "merchant",
                (player) -> {
                    Item item = Moves.or.apply(Item.create(2, Resource.GOLD), Item.create(2, Resource.MANA));
                    Moves.collect.accept(player, item);
                },
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.GOLD)),
                null));
        powers.put("alchemist", new Power(Type.CITIZEN, "alchemist",
                (player) -> Moves.trade.accept(player, Item.create(1, Resource.GOLD), Item.create(3, Resource.MANA)),
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.MANA));
                    Moves.collect.accept(player, Item.create(2, Resource.GOLD));
                },
                null));
        powers.put("mercenary", new Power(Type.CITIZEN, "mercenary",
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.STRENGTH));
                    Moves.collect.accept(player, Item.create(1, Resource.GOLD));
                },
                (player) -> Moves.trade.accept(player, Item.create(1, Resource.STRENGTH), Item.create(2, Resource.GOLD)),
                null));
        powers.put("wizard", new Power(Type.CITIZEN, "wizard",
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.MANA));
                    Moves.collect.accept(player, Item.create(1, Resource.STRENGTH));
                },
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.MANA)),
                null));
        powers.put("archer", new Power(Type.CITIZEN, "archer",
                (player) -> Moves.collect.accept(player, Item.create(2, Resource.STRENGTH)),
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.STRENGTH)),
                null));
        powers.put("peasant", new Power(Type.CITIZEN, "peasant",
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.GOLD)),
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.GOLD)),
                null));
        powers.put("knight", new Power(Type.CITIZEN, "knight",
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.STRENGTH)),
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.STRENGTH)),
                null));
        powers.put("scoundrel", new Power(Type.CITIZEN, "scoundrel",
                (player) -> {
                    Moves.collect.accept(player, Item.create(2, Resource.STRENGTH));
                    Moves.collect.accept(player, Item.create(2, Resource.GOLD));
                },
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.STRENGTH));
                    Moves.collect.accept(player, Item.create(1, Resource.GOLD));
                },
                null));
        powers.put("thief", new Power(Type.CITIZEN, "thief",
                (player) -> {
                    Item item = Moves.or.apply(Item.create(3, Resource.GOLD), Item.create(3, Resource.MANA));
                    Moves.steal.accept(player, item);
                },
                (player) -> {
                    Moves.collect.accept(player, Item.create(2, Resource.GOLD));
                    Moves.collect.accept(player, Item.create(2, Resource.MANA));
                },
                null));
        powers.put("champion", new Power(Type.CITIZEN, "champion",
                (player) -> Moves.collect.accept(player, Item.create(4, Resource.STRENGTH)),
                (player) -> Moves.trade.accept(player, Item.create(1, Resource.GOLD), Item.create(4, Resource.STRENGTH)),
                null));
        powers.put("warlord", new Power(Type.CITIZEN, "warlord",
                (player) -> {
                    final int qty = (int) player.citizens.stream().filter(c -> Objects.equals(c.role, Role.SOLDIER)).count();
                    Moves.collect.accept(player, Item.create(qty, Resource.STRENGTH));
                },
                (player) -> {
                    final int qty = (int) player.citizens.stream().filter(c -> Objects.equals(c.id, "knight")).count();
                    Moves.collect.accept(player, Item.create(qty, Resource.STRENGTH));
                },
                null));
        powers.put("priestess", new Power(Type.CITIZEN, "priestess",
                (player) -> {
                    Moves.collect.accept(player, Item.create(2, Resource.STRENGTH));
                    Moves.collect.accept(player, Item.create(1, Resource.MANA));
                },
                (player) -> Moves.trade.accept(player, Item.create(1, Resource.MANA), Item.create(3, Resource.STRENGTH)),
                null));
        powers.put("paladin", new Power(Type.CITIZEN, "paladin",
                (player) -> {
                    Moves.collect.accept(player, Item.create(1, Resource.STRENGTH));
                    Moves.collect.accept(player, Item.create(2, Resource.MANA));
                },
                (player) -> Moves.trade.accept(player, Item.create(1, Resource.STRENGTH), Item.create(3, Resource.MANA)),
                null));
        powers.put("miner", new Power(Type.CITIZEN, "miner",
                (player) -> {
                    final int qty = player.domains.size();
                    Moves.collect.accept(player, Item.create(1 + qty, Resource.GOLD));
                },
                (player) -> Moves.collect.accept(player, Item.create(4, Resource.GOLD)),
                null));
        powers.put("butcher", new Power(Type.CITIZEN, "butcher",
                (player) -> {
                    final int qty = (int) player.citizens.stream().filter(c -> Objects.equals(c.role, Role.CRAFTMAN)).count();
                    Moves.collect.accept(player, Item.create(2 * qty, Resource.GOLD));
                },
                (player) -> Moves.collect.accept(player, Item.create(4, Resource.GOLD)),
                null));
    }

    /* Define DOMAIN powers */
    static {
        powers.put("shadows_dock", new Power(Type.DOMAIN, "shadows_dock",
                (player) -> Moves.collect.accept(player, Item.create(3, Resource.MANA)),
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.MANA)),
                new Trigger(Trigger.Hook.ANY, Trigger.Event.NONE)));
    }

    /* Define MONSTER powers */
    static {
        powers.put("awful_bear", new Power(Type.MONSTER, "awful_bear",
                (player) -> Moves.collect.accept(player, Item.create(3, Resource.MANA)),
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.MANA)),
                null));
    }

    /* Define DUKE powers */
    static {
        powers.put("waryn", new Power(Type.DUKE, "waryn",
                (player) -> Moves.collect.accept(player, Item.create(3, Resource.MANA)),
                (player) -> Moves.collect.accept(player, Item.create(1, Resource.MANA)),
                null));
    }

    public final Type type;
    public final String id;
    public final Boolean auto;
    public final Consumer<Player> active;
    public final Consumer<Player> passive;
    public final Trigger trigger;

    public Power(final Type type,
                 final String id,
                 final Boolean auto,
                 final Consumer<Player> active,
                 final Consumer<Player> passive,
                 final Trigger trigger) {
        this.type = type;
        this.id = id;
        this.auto = auto;
        this.active = active;
        this.passive = passive;
        this.trigger = trigger;
    }

    public Power(final Type type,
                 final String id,
                 final Consumer<Player> active,
                 final Consumer<Player> passive,
                 final Trigger trigger) {
        this(type, id, true, active, passive, trigger);
    }

}
