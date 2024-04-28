/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.api;

import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.michaelhofmann.graphic.simpic.generic.AbstractThing;
import net.michaelhofmann.graphic.simpic.generic.Vuzzy;
import net.michaelhofmann.graphic.simpic.util.Parameter;

/**
 *
 * @author email@MichaelHofmann.net
 */
public abstract class SimpicProcess extends AbstractThing
        implements Consumer<ImageCard>, Predicate<ImageCard> {
    
    public SimpicProcess(String ident, Parameter para) {
        super(ident, para);
    }

    @Override
    public void accept(ImageCard card) {
    }

    @Override
    public abstract boolean test(ImageCard card);

    public Vuzzy getSimilarity(ImageCard a, ImageCard b) {
        return null;
    }
}
