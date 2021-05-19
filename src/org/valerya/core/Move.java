package org.valerya.core;

import org.valerya.data.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent a move during the game.<br>
 * It also store all previous moves into an historic.<br>
 */
public class Move {
	
	public static int cpt = 0;
	public static List<Move> moves = new ArrayList<>();
	
	public final int index;
	public final Player player;
	public final Toss toss;
	public final Map<Integer, Integer> harvest = new HashMap<Integer, Integer>();
	public final List<Action> actions = new ArrayList<>();
	
	private Move(final Player player, final Toss toss) {
		this.index = Move.cpt;
		this.player = player;
		this.toss = toss;
	}
	
	private Move(final Player player) {
		this(player, null);
	}
	
	public Move harvest(final int resource, int quantity) {
		harvest.merge(resource, quantity, Integer::sum);
		return this;
	}
	
	public Move addAction(final Action action) {
		this.actions.add(action);
		return this;
	}
	
	public static Move createMain(final Player player, final Toss toss) {
		Move move = new Move(player, toss);
		Move.cpt++;
		Move.moves.add(move);
		return move;
	}
	
	public static Move createNext(final Player player) {
		Move move = new Move(player);
		Move.cpt++;
		Move.moves.add(move);
		return move;
	}
	
}
