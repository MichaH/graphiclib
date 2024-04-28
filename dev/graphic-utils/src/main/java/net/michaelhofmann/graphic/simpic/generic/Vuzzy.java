/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.generic;

import java.io.Serializable;

/** Vuzzy means FuzzyValue.
 *
 * @author michael
 */
public class Vuzzy extends Number implements Serializable {
    
    public static Vuzzy TRUE = new Vuzzy(1.0);
    public static Vuzzy FALSE = new Vuzzy(0.0);

    private final double value;

    private Vuzzy(double value) {
        if ((value > 1.0) || (value < 0.0)) {
            throw new IllegalArgumentException("illegal fuzzy value " + value);
        }
        this.value = value;
    }
    
    public static Vuzzy of(double value) {
        if (value > 1.0) {
            return TRUE;
        } else if (value < 0.0) {
            return FALSE;
        } else {
            return new Vuzzy(value);
        }
    }
    
    public boolean isTrue() {
        return value >= 0.5;
    }
    
    public boolean isFalse() {
        return ! isTrue();
    }
    
    @Override
    public int intValue() {
        return isTrue() ? 1 : 0;
    }

    @Override
    public long longValue() {
        return intValue();
    }

    @Override
    public float floatValue() {
        return (float)value;
    }

    @Override
    public double doubleValue() {
        return value;
    }
}
