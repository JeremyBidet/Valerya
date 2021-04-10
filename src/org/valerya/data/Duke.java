package org.valerya.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Objects;
import org.valerya.utils.ClassBuilder;
import org.valerya.utils.ClassHelper;

public class Duke extends ClassHelper<String> implements ClassBuilder<Duke> {

	public static Map<String, Duke> dukes;

	@JsonBackReference
	public final Type type;

	public final String id;
	public final String desc;
	public final Runnable formula;

	@JsonCreator
	public Duke(@JsonProperty("type") final Type type,
	            @JsonProperty("id") final String id,
	            @JsonProperty("desc") final String desc,
	            @JsonProperty("formula") final Runnable formula) {
		this.type = type;
		this.id = id;
		this.desc = desc;
		this.formula = formula;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Duke)) {
			return false;
		}

		Duke d = (Duke) o;

		return Objects.equals(d.id, this.id) && Objects.equals(d.type, this.type);
	}

	@Override
	public String getId() {
		return this.id;
	}

}
