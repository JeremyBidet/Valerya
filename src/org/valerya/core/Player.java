package org.valerya.core;

import org.valerya.data.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    public static final Map<String, Player> players = new HashMap<>();

    public final String name;
    public final Duke duke;
    public final List<Citizen> citizens;
    public final List<Monster> monsters;
    public final List<Domain> domains;
    private final Map<Integer, Integer> resources;

    public Player(final String name, final Duke duke) {
        this.name = name;

        this.resources = new HashMap<Integer, Integer>() {{
            put(Resource.GOLD, 2);
            put(Resource.MANA, 1);
            put(Resource.STRENGTH, 0);
            put(Resource.VP, 0);
        }};

        this.duke = duke;

        this.citizens = new ArrayList<Citizen>() {{
            add(Citizen.citizens.get("starting_peasant"));
            add(Citizen.citizens.get("starting_knight"));
        }};
        this.monsters = new ArrayList<>();
        this.domains = new ArrayList<>();
    }

    /**
     * Return the remaining amount of the given resource.
     *
     * @param resource the resource to evaluate
     * @return the amount
     */
    public int get(final int resource) {
        return this.resources.getOrDefault(resource, -1);
    }

    public void add(final int resource, final int quantity) {
        this.resources.merge(resource, quantity, (i1, i2) -> Math.max(0, Integer.sum(i1, i2)));
    }

    public int gold() {
        return this.resources.get(Resource.GOLD);
    }

    public int mana() {
        return this.resources.get(Resource.MANA);
    }

    public int strength() {
        return this.resources.get(Resource.STRENGTH);
    }

    public int vp() {
        return this.resources.get(Resource.VP);
    }

    public void hunt(Monster monster) {
        this.monsters.add(monster);
        MonsterSet.monsterSets.remove(monster);
    }

}
