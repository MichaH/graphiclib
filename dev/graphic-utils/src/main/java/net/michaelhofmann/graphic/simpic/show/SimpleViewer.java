/*
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.show;

import java.util.List;
import net.michaelhofmann.graphic.simpic.generic.AbstractThing;
import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import net.michaelhofmann.graphic.simpic.util.Parameter;


public class SimpleViewer extends AbstractThing {

    private final static String IDENT = "SimpleViewer";
    
    public SimpleViewer(Parameter para) {
        super(IDENT, para);
    }
    
    public void show(List<ImageCard> allCards) {
        allCards.stream()
                .forEach(System.out::println);
    }
}
