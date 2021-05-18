package org.valerya.core;

public class Trigger {

    public final Hook hook;
    public final Event event;

    private Trigger(final Hook hook, final Event event) {
        this.hook = hook;
        this.event = event;
    }

    public static Trigger NOW = new Trigger(Hook.NOW, Event.NONE);
    public static Trigger ANY = new Trigger(Hook.ANY, Event.NONE);

    public static Trigger BEFORE_TOSS = new Trigger(Hook.BEFORE, Event.TOSS);
    public static Trigger BEFORE_HARVEST = new Trigger(Hook.BEFORE, Event.HARVEST);
    public static Trigger BEFORE_ACTION = new Trigger(Hook.BEFORE, Event.ACTION);
    public static Trigger BEFORE_RECRUIT = new Trigger(Hook.BEFORE, Event.RECRUIT);
    public static Trigger BEFORE_HUNT = new Trigger(Hook.BEFORE, Event.HUNT);
    public static Trigger BEFORE_BUILD = new Trigger(Hook.BEFORE, Event.BUILD);

    public static Trigger AFTER_TOSS = new Trigger(Hook.AFTER, Event.TOSS);
    public static Trigger AFTER_HARVEST = new Trigger(Hook.AFTER, Event.HARVEST);
    public static Trigger AFTER_ACTION = new Trigger(Hook.AFTER, Event.ACTION);
    public static Trigger AFTER_RECRUIT = new Trigger(Hook.AFTER, Event.RECRUIT);
    public static Trigger AFTER_HUNT = new Trigger(Hook.AFTER, Event.HUNT);
    public static Trigger AFTER_BUILD = new Trigger(Hook.AFTER, Event.BUILD);

    public static Trigger ANY_TOSS = new Trigger(Hook.ANY, Event.TOSS);
    public static Trigger ANY_HARVEST = new Trigger(Hook.ANY, Event.HARVEST);
    public static Trigger ANY_ACTION = new Trigger(Hook.ANY, Event.ACTION);
    public static Trigger ANY_RECRUIT = new Trigger(Hook.ANY, Event.RECRUIT);
    public static Trigger ANY_HUNT = new Trigger(Hook.ANY, Event.HUNT);
    public static Trigger ANY_BUILD = new Trigger(Hook.ANY, Event.BUILD);

    public enum Hook {
        NOW,
        BEFORE,
        AFTER,
        ANY,
        ;

        /**
         * Return a string representing all fields of this class, separated with the <code>sep</code> param.
         *
         * @param sep the separator
         * @return the string representation
         */
        public static String toString(final String sep) {
            return "NOW" + sep + "BEFORE" + sep + "AFTER" + sep + "ANY";
        }
    }

    public enum Event {
        TOSS,
        HARVEST,
        ACTION,

        RECRUIT,
        HUNT,
        BUILD,

        NONE,
        ;

        /**
         * Return a string representing all fields of this class, separated with the <code>sep</code> param.
         *
         * @param sep the separator
         * @return the string representation
         */
        public static String toString(final String sep) {
            return "TOSS" + sep + "HARVEST" + sep + "ACTION"
                    + sep + "RECRUIT" + sep + "HUNT" + sep + "BUILD"
                    + sep + "NONE";
        }
    }

}
