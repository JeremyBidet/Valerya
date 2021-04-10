package org.valerya.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Objects;
import org.valerya.utils.ClassBuilder;
import org.valerya.utils.ClassHelper;

public class Monster extends ClassHelper<String> implements ClassBuilder<Monster> {

	public static Map<String, Monster> monsters;

	@JsonBackReference
	public final Type type;

	public final String id;

	@JsonBackReference
	public final Settlement settlement;

	@JsonBackReference
	public final Role role;

	public final int defenseStrength;
	public final int defenseMana;
	public final int vp;
	public final Runnable reward;

	@JsonCreator
	public Monster(@JsonProperty("type") final Type type,
	               @JsonProperty("id") final String id,
	               @JsonProperty("settlement") final Settlement settlement,
	               @JsonProperty("role") final Role role,
	               @JsonProperty("defenseStrength") final int defenseStrength,
	               @JsonProperty("defenseMana") final int defenseMana,
	               @JsonProperty("vp") final int vp,
	               @JsonProperty("reward") final Runnable reward) {
		this.type = type;
		this.id = id;
		this.settlement = settlement;
		this.role = role;
		this.defenseStrength = defenseStrength;
		this.defenseMana = defenseMana;
		this.vp = vp;
		this.reward = reward;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Monster)) {
			return false;
		}

		Monster m = (Monster) o;

		return Objects.equals(m.id, this.id)
				&& Objects.equals(m.type, this.type)
				&& Objects.equals(m.settlement, this.settlement)
				&& Objects.equals(m.role, this.role);
	}

	@Override
	public String getId() {
		return this.id;
	}

}
