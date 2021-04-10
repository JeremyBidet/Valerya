package org.valerya.core;

import org.valerya.data.Monster;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Move {

	/**
	 * Collect resource(s) for a player.<br>
	 * <code>player</code> the player to feed<br>
	 * <code>params</code> the resource items<br>
	 * <br>
	 * <b>Format expected:</b> collect:{x,{@link Resource}},{},...
	 */
	private static final BiConsumer<Player, Item[]> collect = (player, params) -> {
		for(Item item : params) {
			player.add(item.resource, item.quantity);
		}
	};

	/**
	 * Trade resource(s) for a player.<br>
	 * <code>player</code> the player who will trade<br>
	 * <code>params</code> the resource items, work by group of 2 {@link Item}<br>
	 * <br>
	 * <b>Format expected:</b> trade:{-x,{@link Resource}},{y,{@link Resource}},{},{}...
	 */
	private static final BiConsumer<Player, Item[]> trade = (player, params) -> {
		for(int i=0; i+1<params.length; i+=2) {
			collect.accept(player, Item.For(params[i], params[i+1]));
		}
	};

	/**
	 * Steal resource(s) from player(s).<br>
	 * <code>player</code> the player to feed<br>
	 * <code>params</code> the resource items and their sources<br>
	 * <br>
	 * <b>Format expected:</b> collect:{x,{@link Resource},{@link Source}},{},...
	 */
	private static final BiConsumer<Player, Item[]> steal = (player, params) -> {
		for(Item item : params) {
			Player target = (Player) Source.get(item.source);
			target.add(item.resource, -item.quantity);
			player.add(item.resource, item.quantity);
		}
	};

	private static final BiConsumer<Player, Item[]> hunt = (player, params) -> {
		Item param1 = params[0];
		Monster monster = Monster.monsters.get(null);
		player.hunt(monster);
	};

	private static final BiConsumer<Player, Item[]> recruit = (player, params) -> {
	};

	private static final BiConsumer<Player, Item[]> build = (player, params) -> {
	};

	public static final Map<String, BiConsumer<Player, Item[]>> moves = new HashMap<String, BiConsumer<Player, Item[]>>() {{
		put("collect", collect);
		put("trade", trade);
		put("steal", steal);
		put("hunt", hunt);
		put("recruit", recruit);
		put("build", build);
	}};

	private static final BiConsumer DEFAULT_MOVE = (arg1, params) -> {};
	private static final Runnable DEFAULT_ACTION = () -> {};

	private static final String moveRegex = "(?<move>recruit|spend|hunt|collect|steal|build|trade)(?<params>:.*)?";
	private static final Pattern movePattern = Pattern.compile(moveRegex);

	private static final String paramsRegex = "(?<param1>\\w+)?(?<nexts>,\\w+)*";
	private static final Pattern paramsPattern = Pattern.compile(paramsRegex);

	private static final String paramRegex = "(?<param>\\w+)";
	private static final Pattern paramPattern = Pattern.compile(paramRegex);

	public static Runnable parseMove(final String moveStr) {
		final Matcher moveMatcher = movePattern.matcher(moveStr);

		if (!moveMatcher.matches()) {
			return DEFAULT_ACTION;
		}

		final String move = moveMatcher.group("move");
		final String paramsStr = moveMatcher.group("params");
		final Matcher paramsMatcher = paramsPattern.matcher(paramsStr);

		if (!paramsMatcher.matches()) {
			return DEFAULT_ACTION;
		}

		String param1 = paramsMatcher.group("param1");
		String nexts = paramsMatcher.group("nexts");

		final Matcher paramMatcher = paramPattern.matcher(nexts);

		Item[] params = { new Item(Integer.valueOf(param1), Integer.valueOf(nexts)) };

		final BiConsumer<Player, Item[]> vbc = moves.getOrDefault(move, DEFAULT_MOVE);

		final Runnable r = () -> {
			vbc.accept(null, params);
		};

		return r;
	}

}
