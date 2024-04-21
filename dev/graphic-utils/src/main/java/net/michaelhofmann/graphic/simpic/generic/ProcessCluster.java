/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.generic;

import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author email@MichaelHofmann.net
 */
public class ProcessCluster {
    
    private final String processIdent;
    private final Set<ImageCard> cards = new HashSet<>();

    public ProcessCluster(String processIdent) {
        this.processIdent = processIdent;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("similar for process ").append(processIdent)
                .append(": ").append(cards.size()).append("\n");
        for (ImageCard card : cards) {
            sb.append("    %d) %s, similar=%d\n".formatted(
                    card.getPicId(),
                    card.getFile().getAbsoluteFile(),
                    card.processClusterSize(processIdent)));
        }
        return sb.toString();
    }

    public boolean add(ImageCard e) {
        return cards.add(e);
    }

    public int similarSize() {
        return cards.size();
    }

    public Set<ImageCard> getCards() {
        return cards;
    }
}
