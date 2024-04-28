/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author email@MichaelHofmann.net
 */
public class Parameter extends Properties {
    
    public static Parameter loadConfig(String[] args)
            throws FileNotFoundException, IOException {
        
        // Check if the -c option is provided
        if (args.length > 1 && args[0].equals("-c")) {
            String configFilePath = args[1];
            try (FileInputStream fileInputStream = new FileInputStream(configFilePath)) {
                Properties properties = new Properties();
                properties.load(fileInputStream);
                final Parameter para = new Parameter();
                properties.entrySet().stream()
                        .forEach(e -> para.put(e.getKey(), e.getValue()));
                Objects.requireNonNull(para);
                return para;
            }
        }
        throw new IllegalArgumentException(
                "Please provide the -c option followed by the path to the configuration file.");
    }    
    
    public Path getRootPath() {
        Path rootPath = Path.of(getProperty("simpic.searchdir.root"));
        Objects.requireNonNull(rootPath);        
        return rootPath;
    }
    
    public boolean isFindRecursive() {
        return Boolean.parseBoolean(
                getProperty("simpic.searchdir.recursive", "false"));
    }
    
    public Set<String> showSet() {
        String line = getProperty("simpic.show.types", "");
        return Arrays.stream(line.split(","))
                .filter(s -> ! s.isBlank())
                .map(s -> s.trim())
                .collect(Collectors.toSet());
    }
}
