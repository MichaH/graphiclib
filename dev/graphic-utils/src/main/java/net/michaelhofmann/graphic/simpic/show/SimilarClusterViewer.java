/*
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.show;

import java.util.List;
import net.michaelhofmann.graphic.simpic.generic.AbstractThing;
import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import net.michaelhofmann.graphic.simpic.util.Parameter;


public class SimilarClusterViewer extends AbstractThing {

    private final static String IDENT = "SimilarClusterViewer";
    
    public SimilarClusterViewer(Parameter para) {
        super(IDENT, para);
    }
    
    public void show(List<ImageCard> allCards, String processIdent) {
        allCards.stream()
                .map(c -> c.getSortedSuperCluster(processIdent))
                .forEach(SimilarClusterViewer::dimSortString);
    }
    
    private static void dimSortString(List<ImageCard> list) {
        for (ImageCard card : list) {
            String line = "%5d, %8d, %s".formatted(
                    card.getPicId(), 
                    card.getDimension(), 
                    card.getFile().getAbsolutePath());
            System.out.println(line);
        }
        System.out.println("%d within cluster\n".formatted(list.size()));
    }
}
