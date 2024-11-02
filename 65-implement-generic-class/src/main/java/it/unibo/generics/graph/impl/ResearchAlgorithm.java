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
        final Map<N, Double> distances,
        final Map<N, N> fathers
    ) {
        for (final N node : nodes) {
            colors.put(node, Color.WHITE);
            distances.put(node, Double.POSITIVE_INFINITY);
            fathers.put(node, null);
        }
        colors.put(source, Color.GRAY);
        distances.put(source, 0.0);
    }
    
    public static <N> Map<N, N> breadthFirstSearch(final Map<N, List<N>> edges, final N source) {
        final int graphNodes = edges.size();
        final Map<N, Color> colors = new HashMap<>(graphNodes);
        final Map<N, Double> distances = new HashMap<>(graphNodes);
        final Map<N, N> fathers = new HashMap<>(graphNodes);
        final List<N> nodesQueue = new ArrayList<>();

        initializeValues(source, edges.keySet(), colors, distances, fathers);
        
        nodesQueue.addFirst(source);
        while (!nodesQueue.isEmpty()) {
            final N src = nodesQueue.removeFirst();
            for (final N dst : edges.get(src)) {
                if (colors.get(dst).equals(Color.WHITE)) {
                    colors.put(dst, Color.GRAY);
                    distances.put(dst, distances.get(src) + 1.0);
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
        final Map<N, Double> distances = new HashMap<>(graphNodes);
        final Map<N, N> fathers = new HashMap<>(graphNodes);
        Double time = 0.0;

        initializeValues(source, edges.keySet(), colors, distances, fathers);
        
        for (final N node : edges.keySet()) {
            if (colors.get(node).equals(Color.WHITE)) {
                dfsVisit(colors, distances, fathers, edges, source, time);
            }
        }
        return new HashMap<>(fathers);
    }

    private static <N> void dfsVisit(
        final Map<N, Color> colors,
        final Map<N, Double> distances,
        final Map<N, N> fathers,
        final Map<N, List<N>> edges,
        final N src,
        Double time
    ) {
        time++;
        distances.put(src, time);
        colors.put(src, Color.GRAY);
        for (final N dst : edges.get(src)) {
            if (colors.get(dst).equals(Color.WHITE)) {
                fathers.put(dst, src);
                dfsVisit(colors, distances, fathers, edges, src, time);
            }
        }
        colors.put(src, Color.BLACK);
        time++;
    }
}
