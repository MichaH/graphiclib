/*
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.executor;

import java.io.IOException;
import java.nio.file.Files;
import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import net.michaelhofmann.graphic.simpic.util.Parameter;


public class DeleteExecutor extends AbstractExecutor {

    private final static String IDENT = "DeleteExecutor";
    
    public DeleteExecutor(Parameter para) {
        super(IDENT, para);
    }

    @Override
    protected void executeFirstCard(ImageCard firstCard) {
        System.out.println(firstCard.getFilename() + " remains unchanged");
    }

    @Override
    protected void executeOtherCard(ImageCard firstCard, ImageCard otherCard) {
        System.out.print("%s will be deleted as identical to %s ...".formatted(
                        otherCard.getFilename(), firstCard.getFilename()));
        try {
            Files.delete(otherCard.getPath());
            System.out.println("... deleted");
        } catch (IOException ex) {
            System.err.println("can not delete " + otherCard.getFilename());
        }
    }
}
