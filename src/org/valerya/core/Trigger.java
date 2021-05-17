package org.valerya.core;

public class Trigger {

    public final Hook hook;
    public final Event event;

    public Trigger(final Hook hook, final Event event) {
        this.hook = hook;
        this.event = event;
    }

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
