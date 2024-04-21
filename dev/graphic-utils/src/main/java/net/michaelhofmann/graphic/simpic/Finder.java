/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic;

import net.michaelhofmann.graphic.simpic.spi.GreyChecker;
import net.michaelhofmann.graphic.simpic.generic.SimpicFinder;
import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.SortedSet;

/**
 *
 * @author michael
 */
public class Finder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        Path rootPath = Path.of("path to your directory");
        SimpicFinder finder = new SimpicFinder();
        List<ImageCard> allCards = finder.listClusterCards(rootPath, true);
        
//        allCards.stream()
//                .forEach(System.out::println);
        
        allCards.stream()
                .map(c -> c.getSortedSuperCluster(GreyChecker.IDENT))
                .forEach(Finder::dimSortString);
    }
    
    private final static void dimSortString(SortedSet<ImageCard> set) {
        for (ImageCard card : set) {
            String line = "%5d, %8d, %s".formatted(
                    card.getPicId(), 
                    card.getDimension(), 
                    card.getFile().getAbsolutePath());
            System.out.println(line);
        }
        System.out.println("");
    }
    
}
