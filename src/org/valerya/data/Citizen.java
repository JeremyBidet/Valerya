package org.valerya.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.valerya.utils.ClassBuilder;
import org.valerya.utils.ClassHelper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Citizen extends ClassHelper<String> implements ClassBuilder<Citizen> {

    public static Map<String, Citizen> citizens;

    @JsonBackReference
    public final Type type;

    public final String id;

    @JsonBackReference
    public final Role role;

    public final List<Integer> ranks;
    public final int baseCost;
    public final Runnable powerActive;
    public final Runnable powerPassive;

    @JsonCreator
    public Citizen(@JsonProperty("type") final Type type,
                   @JsonProperty("id") final String id,
                   @JsonProperty("role") final Role role,
                   @JsonProperty("ranks") final List<Integer> ranks,
                   @JsonProperty("baseCost") final int baseCost,
                   @JsonProperty("powerActive") final Runnable powerActive,
                   @JsonProperty("powerPassive") final Runnable powerPassive) {
        this.type = type;
        this.id = id;
        this.role = role;
        this.ranks = ranks;
        this.baseCost = baseCost;
        this.powerActive = powerActive;
        this.powerPassive = powerPassive;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Citizen)) {
            return false;
        }

        Citizen c = (Citizen) o;

        return Objects.equals(c.id, this.id)
                && Objects.equals(c.type, this.type)
                && Objects.equals(c.role, this.role);
    }

    @Override
    public String getId() {
        return this.id;
    }

}
