/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.generic;

import net.michaelhofmann.graphic.simpic.util.Parameter;

/**
 *
 * @author email@MichaelHofmann.net
 */
public abstract class AbstractThing {
    
    private final Parameter para;
    public final String ident;
            
    public AbstractThing(String ident, Parameter para) {
        this.ident = ident;
        this.para = para;
    }

    public String getIdent() {
        return ident;
    }
    
    public Parameter getPara() {
        return para;
    }
}
