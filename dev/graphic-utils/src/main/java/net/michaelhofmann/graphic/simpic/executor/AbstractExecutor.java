/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.executor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.michaelhofmann.graphic.simpic.generic.*;
import net.michaelhofmann.graphic.simpic.util.Parameter;

/**
 *
 * @author email@MichaelHofmann.net
 */
public abstract class AbstractExecutor extends AbstractThing {
    
    private final Set<ImageCard> readyCards = new HashSet<>();

    public AbstractExecutor(String ident, Parameter para) {
        super(ident, para);
    }
    
    public void execute(List<ImageCard> allCards, String processIdent) {
        allCards.stream()
                .map(c -> c.getSortedSuperCluster(processIdent))
                .forEach(l -> executeCluster(l));
    }

    public void executeCluster(List<ImageCard> list) {
        if (list.isEmpty()) {
            System.out.println("warning: superCluster is empty");
            return;
        }
        Iterator<ImageCard> iter = list.iterator();
        ImageCard firstCard = iter.next();
        assert firstCard != null;
        if ( ! readyCards.contains(firstCard)) {
            executeFirstCard(firstCard);                 // execute firstCard
            readyCards.add(firstCard);                   // ready
        }
        while (iter.hasNext()) {
            ImageCard otherCard = iter.next();
            if ( ! readyCards.contains(otherCard)) {
                executeOtherCard(firstCard, otherCard);  // executeOtherCard
                readyCards.add(otherCard);               // ready
            }
        }
    }

    protected abstract void executeFirstCard(ImageCard firstCard);
    protected abstract void executeOtherCard(ImageCard firstCard, ImageCard otherCard);
}
