/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.api;

import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author email@MichaelHofmann.net
 */
public abstract class SimpicProcess
        implements Consumer<ImageCard>, Predicate<ImageCard> {
    
    public final String ident;

    public SimpicProcess(String ident) {
        this.ident = ident;
    }

    @Override
    public void accept(ImageCard card) {
    }

    @Override
    public abstract boolean test(ImageCard card);

    public Boolean isSimilar(ImageCard a, ImageCard b) {
        return null;
    }
}