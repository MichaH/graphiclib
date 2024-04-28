/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.generic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author email@MichaelHofmann.net
 */
public class ImageCard {
    
    private final int picId;
    private final Path path;
    
    // generic attributes
    private BufferedImage bufferedImage;
    private Map<String, Exception> exceptions = new HashMap<>();

    // specifig attributes
    private Map<String, Object> attributes = new HashMap<>();
    
    // Contains all similar images, but does not contain itself.
    private final MultiCluster subCluster = new MultiCluster();

    // In case the images are collected together in parallel.
    private static final AtomicInteger picIdGenerator = new AtomicInteger(0);
    
    public static final Comparator<ImageCard> SIZE_COMPERATOR = (c1, c2) -> 
            c2.getDimension() - c1.getDimension();
    
    public ImageCard(Path path) {
        this.path = path;
        this.picId = picIdGenerator.incrementAndGet();
    }

    @Override
    public String toString() {
        return "%d) %s, %dx%d\n%s".formatted(
                picId,
                getFile().getAbsolutePath(), getWidth(), getHeight(),
                subCluster.toString());
    }
    
    public int processClusterSize(String processIdent) {
        return subCluster.similarSize(processIdent);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.path);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImageCard other = (ImageCard) obj;
        return Objects.equals(this.path, other.path);
    }

    public int getPicId() {
        return picId;
    }
    
    public boolean isUnique() {
        return subCluster.isEmpty();
    }
    
    public int getDimension() {
        return getWidth() * getHeight();
    }

    public Path getPath() {
        return path;
    }
    
    public File getFile() {
        return path.toFile();
    }

    public String getFilename() {
        return getFile().getName();
    }

    public String getParentFilename() {
        return getFile().getParent();
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Integer getWidth() {
        return getBufferedImage() != null ? getBufferedImage().getWidth() : null;
    }

    public Integer getHeight() {
        return getBufferedImage() != null ? getBufferedImage().getHeight() : null;
    }

    public Map<String, Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Map<String, Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public MultiCluster getSubCluster() {
        return subCluster;
    }

    public void addSimilar(String processIdent, ImageCard card) {
        subCluster.addSimilar(processIdent, card);
    }
    
    public List<ImageCard> getSortedSuperCluster(String processIdent) {
        final List<ImageCard> col = new ArrayList<>();
        // We first build a sorted Set with all similar images in the
        // subcluster.
        subCluster.getSimilarByProcess(processIdent)
                .stream()
                .forEach(col::add);
        // Then we must not forget to add this card itself to this set.
        col.add(this);
        Collections.sort(col, ImageCard.SIZE_COMPERATOR);
        return col;
    }
}