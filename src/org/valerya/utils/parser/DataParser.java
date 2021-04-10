package org.valerya.utils.parser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.Stage;
import org.valerya.data.*;
import org.valerya.utils.ClassHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataParser extends ClassHelper<Integer> {

	private static String fileName = "data.json";
	private static String fileDir = "resources";
	private static String filePath = fileDir + "/" + fileName;
	
	public static void init() throws IOException {
		DataParser dataParser = DataParser.parseData();

		Citizen.citizens = dataParser.citizens.stream().collect(Collectors.toMap(Citizen::getId, Function.identity()));
		Domain.domains = dataParser.domains.stream().collect(Collectors.toMap(Domain::getId, Function.identity()));
		Monster.monsters = dataParser.monsters.stream().collect(Collectors.toMap(Monster::getId, Function.identity()));
		Duke.dukes = dataParser.dukes.stream().collect(Collectors.toMap(Duke::getId, Function.identity()));
		MonsterSet.monsterSets = dataParser.monsterSets.stream().collect(Collectors.toMap(MonsterSet::getId, Function.identity()));
	}

	private static DataParser parseData() throws IOException {
		File file = new File(filePath);

		JsonFactory jsonFactory = new JsonFactory();

		ObjectMapper objectMapper = new ObjectMapper(jsonFactory);

		return objectMapper.readerFor(DataParser.class).readValue(file);
	}

	private final List<Stage> stages;
	private final List<Citizen> citizens;
	private final List<Domain> domains;
	private final List<Monster> monsters;
	private final List<Duke> dukes;
	private final List<MonsterSet> monsterSets;

	@JsonCreator
	private DataParser(@JsonProperty("stages") final List<Stage> stages,
	                   @JsonProperty("citizens") final List<Citizen> citizens,
	                   @JsonProperty("domains") final List<Domain> domains,
	                   @JsonProperty("monsters") final List<Monster> monsters,
	                   @JsonProperty("dukes") final List<Duke> dukes,
	                   @JsonProperty("sets") final List<MonsterSet> monsterSets) {
		this.stages = stages;
		this.citizens = citizens;
		this.domains = domains;
		this.monsters = monsters;
		this.dukes = dukes;
		this.monsterSets = monsterSets;
	}
	
	@Override
	public Integer getId() {
		return this.hashCode();
	}
	
}
