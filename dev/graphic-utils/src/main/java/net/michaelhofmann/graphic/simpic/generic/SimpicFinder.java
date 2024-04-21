/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.generic;

import net.michaelhofmann.graphic.simpic.util.SimpicFileUtils;
import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import net.michaelhofmann.graphic.simpic.spi.GreyChecker;
import net.michaelhofmann.graphic.simpic.spi.BasicLoader;

/**
 *
 * @author email@MichaelHofmann.net
 */
public class SimpicFinder {
    
    public List<ImageCard> listClusterCards(Path rootPath, boolean recursive)
            throws IOException {
        
        BasicLoader loadBasicsProc = new BasicLoader();
        GreyChecker greyCheckerProc = new GreyChecker();
        
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
                if (greyCheckerProc.isSimilar(card, runner)) {
                    card.addSimilar(GreyChecker.IDENT, runner);
                }
            }
        }
        return correctCards;
    }
}
