/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import net.michaelhofmann.graphic.simpic.spi.GreyChecker;
import net.michaelhofmann.graphic.simpic.generic.SimpicFinder;
import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 *
 * @author michael
 */
public class Finder {
    
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
        Properties p = null;
        try {
            p = loadConfig(args);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "Error loading configuration file: " + e.getMessage());
        }
        Objects.requireNonNull(p);
        Path rootPath = Path.of(
                p.getProperty("simpic.searchdir.root"));
        boolean recursive = Boolean.parseBoolean(
                p.getProperty("simpic.searchdir.recursive", "false"));
        Objects.requireNonNull(rootPath);

        
        SimpicFinder finder = new SimpicFinder();
        List<ImageCard> allCards = finder.listClusterCards(rootPath, recursive);
        
//        allCards.stream()
//                .forEach(System.out::println);
        
        allCards.stream()
                .map(c -> c.getSortedSuperCluster(GreyChecker.IDENT))
                .forEach(Finder::dimSortString);
    }
    
    private final static void dimSortString(List<ImageCard> list) {
        for (ImageCard card : list) {
            String line = "%5d, %8d, %s".formatted(
                    card.getPicId(), 
                    card.getDimension(), 
                    card.getFile().getAbsolutePath());
            System.out.println(line);
        }
        System.out.println("%d im Block\n".formatted(list.size()));
    }
    
    /*
    */
    private static Properties loadConfig(String[] args)
            throws FileNotFoundException, IOException {
        
        // Check if the -c option is provided
        if (args.length > 1 && args[0].equals("-c")) {
            String configFilePath = args[1];
            try (FileInputStream fileInputStream = new FileInputStream(configFilePath)) {
                Properties properties = new Properties();
                properties.load(fileInputStream);
                return properties;
            }
        }
        throw new IllegalArgumentException("Please provide the -c option followed by the path to the configuration file.");
    }    
}
