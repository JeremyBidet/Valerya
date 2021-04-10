package org.valerya.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Objects;
import org.valerya.utils.ClassBuilder;
import org.valerya.utils.ClassHelper;

public class MonsterSet extends ClassHelper<String> implements ClassBuilder<MonsterSet> {

	public static Map<String, MonsterSet> monsterSets;

	@JsonBackReference
	public final Settlement settlement;

	@JsonBackReference
	public final Map<Monster, Integer> items;

	@JsonCreator
	public MonsterSet(@JsonProperty("settlement") final Settlement settlement,
	                  @JsonProperty("items") final Map<Monster, Integer> items) {
		this.settlement = settlement;
		this.items = items;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MonsterSet)) {
			return false;
		}

		MonsterSet ms = (MonsterSet) o;

		return Objects.equals(ms.settlement, this.settlement);
	}

	@Override
	public String getId() {
		return "MONSTER_SET_" + this.settlement;
	}

}
