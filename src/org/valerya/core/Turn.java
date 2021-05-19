package org.valerya.core;

import java.util.ArrayList;
import java.util.List;

public class Turn {
	
	public static int cpt = 0;
	public static Turn current;
	
	public Move firstMove;
	public List<Move> nextMoves = new ArrayList<>(Player.players.size()-1);
	
	private Turn(final Move firstMove) {
		this.firstMove = firstMove;
	}
	
	public Turn addMove(final Move move) {
		nextMoves.add(move);
		return this;
	}
	
	public static Turn create(final Move firstMove) {
		Turn turn = new Turn(firstMove);
		Turn.cpt++;
		Turn.current = turn;
		return turn;
	}
	
}
