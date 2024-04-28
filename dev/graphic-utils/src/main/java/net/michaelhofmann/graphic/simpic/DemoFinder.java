/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic;

import net.michaelhofmann.graphic.simpic.generic.SimpicFinder;
import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import net.michaelhofmann.graphic.simpic.executor.DeleteExecutor;
import net.michaelhofmann.graphic.simpic.executor.MoveExecutor;
import net.michaelhofmann.graphic.simpic.show.SimilarClusterViewer;
import net.michaelhofmann.graphic.simpic.show.SimpleViewer;
import net.michaelhofmann.graphic.simpic.spi.GreyChecker;
import net.michaelhofmann.graphic.simpic.util.Parameter;

/**
 *
 * @author michael
 */
public class DemoFinder {
    
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
        Parameter para = loadParameter(args);

        // F I N D
        SimpicFinder finder = new SimpicFinder();
        List<ImageCard> allCards = finder.listClusterCards(para);

        // S H O W
        Set<String> showSet = para.showSet();
        if (showSet.contains("SimpleViewer")) {
            new SimpleViewer(para).show(allCards);
        }
        if (showSet.contains("SimilarClusterViewer")) {
            new SimilarClusterViewer(para).show(allCards, GreyChecker.IDENT);
        }
        
        // E X E C U T E
        String executorName = para.getProperty("simpic.execute", "NULL");
        switch (executorName) {
            case "NULL" -> {
                // NOP
            }
            case "DELETE" -> {
                DeleteExecutor exe = new DeleteExecutor(para);
                exe.execute(allCards, GreyChecker.IDENT);
            }
            case "MOVE" -> {
                MoveExecutor exe = new MoveExecutor(para);
                exe.execute(allCards, GreyChecker.IDENT);
            }
            case "RENAME" -> {
                // NOP
            }
            default -> {
                // NOP
            }
        }
    }

    private static Parameter loadParameter(String[] args)
            throws IllegalArgumentException {
        Parameter p = null;
        try {
            p = Parameter.loadConfig(args);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "Error loading configuration file: " + e.getMessage());
        }
        return p;
    }
}
