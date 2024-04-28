/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.spi;

import net.michaelhofmann.graphic.simpic.api.SimpicProcess;
import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.michaelhofmann.graphic.simpic.util.Parameter;

/**
 *
 * @author email@MichaelHofmann.net
 */
public class BasicLoader extends SimpicProcess {
    
    private final static String IDENT = "LoadBasics";
    private final static String ERROR_FLAG = IDENT + ".error";
    private final static String ERROR_FLAG_ACCEPT = ERROR_FLAG + ".accept";

    public BasicLoader(Parameter para) {
        super(IDENT, para);
    }

    @Override
    public void accept(ImageCard card) {
        try {
            BufferedImage loadedImage = ImageIO.read(card.getFile());
            card.setBufferedImage(loadedImage);
        } catch (IOException ex) {
            System.out.println("error while processing with " + IDENT + ": " + ex);
            card.getExceptions().put(ERROR_FLAG, ex);
            card.getExceptions().put(ERROR_FLAG_ACCEPT, ex);
        }
    }

    @Override
    public boolean test(ImageCard card) {
        return ! card.getExceptions().containsKey(ERROR_FLAG);
    }
}
