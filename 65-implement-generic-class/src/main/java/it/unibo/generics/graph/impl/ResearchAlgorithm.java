package it.unibo.generics.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResearchAlgorithm {
    
    private static enum Color {
        WHITE,
        GRAY,
        BLACK
    };

    private ResearchAlgorithm() {
    }

    public static <N> void initializeValues(
        final N source,
        final Set<N> nodes,
        final Map<N, Color> colors,
        final Map<N, N> fathers
    ) {
        for (final N node : nodes) {
            colors.put(node, Color.WHITE);
            fathers.put(node, null);
        }
        colors.put(source, Color.GRAY);
    }
    
    public static <N> Map<N, N> breadthFirstSearch(final Map<N, List<N>> edges, final N source) {
        final int graphNodes = edges.size();
        final Map<N, Color> colors = new HashMap<>(graphNodes);
        final Map<N, N> fathers = new HashMap<>(graphNodes);
        final List<N> nodesQueue = new ArrayList<>();

        initializeValues(source, edges.keySet(), colors, fathers);
        
        nodesQueue.addFirst(source);
        while (!nodesQueue.isEmpty()) {
            final N src = nodesQueue.removeFirst();
            for (final N dst : edges.get(src)) {
                if (colors.get(dst).equals(Color.WHITE)) {
                    colors.put(dst, Color.GRAY);
                    fathers.put(dst, src);
                    nodesQueue.addFirst(dst);
                }
            }
            colors.put(src, Color.BLACK);
        }
        return new HashMap<>(fathers);
    }

    public static <N> Map<N, N> depthFirstSearch(final Map<N, List<N>> edges, final N source) {
        final int graphNodes = edges.size();
        final Map<N, Color> colors = new HashMap<>(graphNodes);
        final Map<N, N> fathers = new HashMap<>(graphNodes);

        initializeValues(source, edges.keySet(), colors, fathers);
        
        for (final N node : edges.keySet()) {
            if (colors.get(node).equals(Color.WHITE)) {
                dfsVisit(colors, fathers, edges, source);
            }
        }
        return new HashMap<>(fathers);
    }

    private static <N> void dfsVisit(
        final Map<N, Color> colors,
        //final Map<N, Double> distances,
        final Map<N, N> fathers,
        final Map<N, List<N>> edges,
        final N src
    ) {
        colors.put(src, Color.GRAY);
        for (final N dst : edges.get(src)) {
            if (colors.get(dst).equals(Color.WHITE)) {
                fathers.put(dst, src);
                dfsVisit(colors, fathers, edges, dst);
            }
        }
        colors.put(src, Color.BLACK);
    }
}
