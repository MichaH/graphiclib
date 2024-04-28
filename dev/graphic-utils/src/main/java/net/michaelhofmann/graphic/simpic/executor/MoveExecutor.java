/*
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.executor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import net.michaelhofmann.graphic.simpic.util.Parameter;


public class MoveExecutor extends AbstractExecutor {

    private final static String IDENT = "MoveExecutor";
    private final Path destination;
            
    public MoveExecutor(Parameter para) {
        super(IDENT, para);
        destination = Path.of(para.getProperty(
                "simpic.executor.MoveExecutor.destination", null));
    }

    @Override
    protected void executeFirstCard(ImageCard firstCard) {
        System.out.println(firstCard.getFilename() + " remains unchanged");
    }

    @Override
    protected void executeOtherCard(ImageCard firstCard, ImageCard otherCard) {
        System.out.print("%s will be moved as identical to %s ...".formatted(
                        otherCard.getFilename(), firstCard.getFilename()));
        try {
            if ((destination != null) && Files.isDirectory(destination)
                    && Files.isWritable(destination)) {
                Path newPath = Path.of(destination.toFile().getAbsolutePath(),
                        otherCard.getFilename());
                Files.move(otherCard.getPath(), newPath);
                System.out.println("... moved");
            } else {
                System.err.println("destination is not set correctly");
            }
        } catch (IOException ex) {
            System.err.println("can not move " + otherCard.getFilename() + ": " + ex);
        }
    }
}
