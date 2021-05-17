package org.valerya.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.valerya.utils.ClassBuilder;
import org.valerya.utils.ClassHelper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Domain extends ClassHelper<String> implements ClassBuilder<Domain> {

    public static Map<String, Domain> domains;

    @JsonBackReference
    public final Type type;

    public final String id;
    public final int cost;

    @JsonBackReference
    public final List<Role> roles;

    public final int vp;
    public final Runnable trigger;
    public final Runnable power;

    @JsonCreator
    public Domain(@JsonProperty("type") final Type type,
                  @JsonProperty("id") final String id,
                  @JsonProperty("cost") final int cost,
                  @JsonProperty("roles") final List<Role> roles,
                  @JsonProperty("vp") final int vp,
                  @JsonProperty("trigger") final Runnable trigger,
                  @JsonProperty("power") final Runnable power) {
        this.type = type;
        this.id = id;
        this.cost = cost;
        this.roles = roles;
        this.vp = vp;
        this.trigger = trigger;
        this.power = power;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Domain)) {
            return false;
        }

        Domain d = (Domain) o;

        return Objects.equals(d.id, this.id) && Objects.equals(d.type, this.type);
    }

    @Override
    public String getId() {
        return this.id;
    }

}
