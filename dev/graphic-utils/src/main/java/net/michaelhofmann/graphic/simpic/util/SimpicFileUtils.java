/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author email@MichaelHofmann.net
 */
public class SimpicFileUtils {
    
    public final static Set<String> allowedExtensions
            = Set.of("bmp", "gif", "jpeg", "jpg",
                     "png", "psd", "pspimage", "thm", "tif");   

    private final static BiPredicate<Path, BasicFileAttributes> imageFilter =
            (p, bfa) -> bfa.isRegularFile() && isImage(p);

    /**
     * 
     * @param p
     * @return 
     */
    public static boolean isImage(Path p) {
        return allowedExtensions.contains(
                FilenameUtils.getExtension(p.toFile().getName()));
    }
    
    /**
     * 
     * @param rootPath
     * @param rekursive
     * @return
     * @throws IOException 
     */
    public static Stream<Path> streamAllImages(Path rootPath, boolean rekursive)
            throws IOException{
        
        int maxDepth = rekursive ? Integer.MAX_VALUE : 1;
        return Files.find(rootPath, maxDepth, imageFilter);
    }
}
