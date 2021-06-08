package org.valerya.data;

/**
 * The type of a card.<br>
 * The enum value is associated with a class type.<br>
 * Can be:
 * <ul>
 *     <li>{@linkplain Type#CITIZEN citizen} a citizen => {@link Citizen}</li>
 *     <li>{@linkplain Type#DOMAIN domain} a domain => {@link Domain}</li>
 *     <li>{@linkplain Type#MONSTER monster} a monster => {@link Monster}</li>
 *     <li>{@linkplain Type#DUKE duke} a resource => {@link Duke}</li>
 * </ul>
 */
public enum Type {

    CITIZEN(Citizen.class),
    DOMAIN(Domain.class),
    MONSTER(Monster.class),
    DUKE(Duke.class),
    ;
    
    public Class clazz;
    
    Type(final Class clazz) {
        this.clazz = clazz;
    }

}
