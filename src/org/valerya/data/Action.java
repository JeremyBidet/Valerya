package org.valerya.data;

import org.valerya.core.Resource;
import org.valerya.utils.ClassBuilder;
import org.valerya.utils.ObjectHelper;

import java.util.Objects;

/**
 * Represent an action with a {@linkplain Kind kind of action}, a {@linkplain Type card type} and an {@linkplain ITEM item}.<br>
 * The item type must be:
 * <ul>
 *     <li>{@link Monster}</li>
 *     <li>{@link Citizen}</li>
 *     <li>{@link Domain}</li>
 *     <li>{@link Resource}</li>
 * </ul>
 * The card type is used only when the {@link ITEM} is of type {@link Monster}, {@link Citizen} or {@link Domain}.<br>
 * <br>
 * {@link Kind}, {@link Type} and {@link ITEM} must all match, according to the following rules:
 * <ul>
 *     <li>{@link Kind#HUNT} => {@link Type#MONSTER} => {@link Monster}</li>
 *     <li>{@link Kind#RECRUIT} => {@link Type#CITIZEN} => {@link Citizen}</li>
 *     <li>{@link Kind#BUILD} => {@link Type#DOMAIN} => {@link Domain}</li>
 *     <li>{@link Kind#HARVEST} => null => {@link Resource}</li>
 * </ul>
 *
 * @param <ITEM> define the item type.
 */
public class Action<ITEM> implements ClassBuilder<Action> {
    
    /**
     * The kind of the action.<br>
     * The enum value is associated with a class type.<br>
     * Can be:
     * <ul>
     *     <li>{@linkplain Kind#HUNT hunt} a monster => {@link Monster}</li>
     *     <li>{@linkplain Kind#RECRUIT recruit} a citizen => {@link Citizen}</li>
     *     <li>{@linkplain Kind#BUILD build} a domain => {@link Domain}</li>
     *     <li>{@linkplain Kind#HARVEST harvest} a resource => {@link Resource}</li>
     * </ul>
     */
    public enum Kind {
        HUNT(Monster.class),
        RECRUIT(Citizen.class),
        BUILD(Domain.class),
        HARVEST(Resource.class),
        ;
        
        public Class clazz;
        
        Kind(final Class clazz) {
            this.clazz = clazz;
        }
    }
    
    public final Kind kind;
    public final Type type;
    public final ITEM item;
    
    private Action(final Kind kind, final Type type, final ITEM item) {
        this.kind = kind;
        this.type = type;
        this.item = item;
    }
    
    @Override
    public String toString() {
        return "Action {"
                + "kind=" + kind + ", "
                + (type != null ? "type=" + type + ", " : "")
                + "item=" + item
                + "}";
    }
    
    /**
     * Initialize an Action with checking consistency between {@linkplain Kind kind}, {@linkplain Type type} and {@linkplain ITEM item}.<br>
     *
     * @param kind the kind of the action
     * @param type the type of the item, relevant only if the item is a card
     * @param item the item
     * @param <ITEM> the class type of the item
     * @return the newly created Action
     */
    public static <ITEM> Action<ITEM> create(final Kind kind, final Type type, final ITEM item) {
        if (kind == Kind.HARVEST) {
            if (!Objects.equals(kind.clazz, item.getClass())) {
                return null;
            }
            return new Action<>(kind, null, item);
        }
        if (!ObjectHelper.equals(kind.clazz, type.clazz, item.getClass())) {
            return null;
        }
        return new Action<>(kind, type, item);
    }

}
