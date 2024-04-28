/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.generic;

import net.michaelhofmann.graphic.simpic.util.SimpicFileUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import net.michaelhofmann.graphic.simpic.spi.GreyChecker;
import net.michaelhofmann.graphic.simpic.spi.BasicLoader;
import net.michaelhofmann.graphic.simpic.util.Parameter;

/**
 *
 * @author email@MichaelHofmann.net
 */
public class SimpicFinder {
    
    public List<ImageCard> listClusterCards(Parameter para)
            throws IOException {
        
        Path rootPath = para.getRootPath();
        boolean recursive = para.isFindRecursive();
        
        BasicLoader loadBasicsProc = new BasicLoader(para);
        GreyChecker greyCheckerProc = new GreyChecker(para);
        
        List<ImageCard> correctCards = SimpicFileUtils.streamAllImages(
                    rootPath, recursive)
                .map(ImageCard::new)
                .peek(loadBasicsProc::accept)
                .filter(loadBasicsProc::test)
                .peek(greyCheckerProc::accept)
                .filter(greyCheckerProc::test)
                .toList();

        for (ImageCard card : correctCards) {
            for (ImageCard runner : correctCards) {
                if (card.equals(runner)) {
                    // Do not compare cards with itself
                    continue;
                }
                if (greyCheckerProc.getSimilarity(card, runner).isTrue()) {
                    card.addSimilar(GreyChecker.IDENT, runner);
                }
            }
        }
        return correctCards;
    }
}
