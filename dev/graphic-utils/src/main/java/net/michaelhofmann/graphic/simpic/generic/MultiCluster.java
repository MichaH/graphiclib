/*
 * (c) Michael Hofmann
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.generic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author email@MichaelHofmann.net
 */
public class MultiCluster {
    
    // Every Process has it's own ProcessCluster
    private final Map<String, ProcessCluster> processClusterMap = new HashMap<>();

    public MultiCluster() {
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String processIdent : processClusterMap.keySet()) {
            ProcessCluster pc = processClusterMap.get(processIdent);
            sb.append(pc.toString());
        }
        return sb.toString();
    }

    public int size() {
        return processClusterMap.size();
    }

    public boolean isEmpty() {
        return processClusterMap.isEmpty();
    }
    
    public int similarSize(String processIdent) {
        ProcessCluster processCluster
                = processClusterMap.getOrDefault(processIdent, null);
        return processCluster != null ? processCluster.similarSize() : 0;
    }
    
    public void addSimilar(String processIdent, ImageCard card) {
        ProcessCluster cardCluster = processClusterMap.get(processIdent);
        if (cardCluster == null) {
            cardCluster = new ProcessCluster(processIdent);
            processClusterMap.put(processIdent, cardCluster);
        }
        cardCluster.add(card);
    }
    
    public Set<ImageCard> getSimilarByProcess(String processIdent) {
        return processClusterMap.containsKey(processIdent) ?
                new HashSet<>(processClusterMap.get(processIdent).getCards()) :
                new HashSet<>();
    }
}
