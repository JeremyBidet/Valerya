package org.valerya.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class Toss {
	
	public static int dice1Faces = 6;
	public static int dice2Faces = 6;
	
	public static Map<Integer, Integer> tossesProbability = new HashMap<>(dice1Faces + dice2Faces);
	
	static {
		IntStream.range(1, dice1Faces+1)
				.forEach(i -> IntStream.range(1, dice2Faces+1)
						.forEach(j -> {
							tossesProbability.merge(i, 1, Integer::sum);
							tossesProbability.merge(j, 1, Integer::sum);
							tossesProbability.merge(i+j, 1, Integer::sum);
						}));
	}
	
	public static int getProbability(final int value) {
		return tossesProbability.get(value);
	}
	
	public static int getProbability100(final int value) {
		return 100 * tossesProbability.get(value) / (dice1Faces * dice2Faces);
	}
	
	public static class Dice {
		
		private final int faces;
		private int value;
		private int oldValue;
		
		public Dice(final int faces) {
			this.faces = faces;
			this.value = 0;
			this.oldValue = 0;
		}
		
		public int value() {
			return this.value;
		}
		
		public int faces() {
			return this.faces;
		}
		
		public int oldValue() {
			return this.oldValue;
		}
		
		public Dice change(final int newValue) {
			this.oldValue = value;
			this.value = newValue;
			return this;
		}
		
		public Dice toss() {
			value = new Random().nextInt(6) + 1;
			return this;
		}
		
	}
	
	public static Toss toss() {
		Toss toss = new Toss();
		toss.dice1.toss();
		toss.dice2.toss();
		return toss;
	}
	
	public Dice dice1 = new Dice(dice1Faces);
	public Dice dice2 = new Dice(dice2Faces);
	
	public int sum() {
		return dice1.value() + dice2.value();
	}
	
}
