package org.valerya.core;

public class Trigger {

	public enum Hook {
		BEFORE,
		AFTER,
		ANY,
		;
	}

	public enum Event {
		TOSS,
		HARVEST,
		RECRUIT,
		HUNT,
		BUILD,
		;
	}

	public final Hook hook;
	public final Event event;

	public Trigger(final Hook hook, final Event event) {
		this.hook = hook;
		this.event = event;
	}

}
