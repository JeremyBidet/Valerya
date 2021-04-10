package org.valerya.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.valerya.utils.ClassBuilder;
import org.valerya.utils.ClassHelper;

public class Stage extends ClassHelper<String> implements ClassBuilder<Stage> {

	public final String id;
	public final int order;
	public final int repeat;

	@JsonCreator
	public Stage(@JsonProperty("id") final String id,
	             @JsonProperty("order") final int order,
	             @JsonProperty("repeat") final int repeat) {
		this.id = id;
		this.order = order;
		this.repeat = repeat;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Stage)) {
			return false;
		}

		Stage s = (Stage) o;

		return Objects.equals(s.id, this.id) && Objects.equals(s.order, this.order);
	}

	@Override
	public String getId() {
		return this.id;
	}

}
