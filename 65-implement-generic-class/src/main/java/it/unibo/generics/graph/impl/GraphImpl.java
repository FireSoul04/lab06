package it.unibo.generics.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import it.unibo.generics.graph.api.Graph;

public class GraphImpl<N> implements Graph<N> {

    private final Map<N, List<N>> edges;

    public GraphImpl() {
        edges = new HashMap<>();
    }

    @Override
    public void addNode(final N node) {
        this.edges.putIfAbsent(node, new ArrayList<>());
    }

    @Override
    public void addEdge(final N source, final N target) {
        this.edges.get(source).add(target);
    }

    @Override
    public Set<N> nodeSet() {
        return Set.copyOf(this.edges.keySet());
    }

    @Override
    public Set<N> linkedNodes(final N node) {
        return Set.copyOf(this.edges.get(node));
    }

    @Override
    public List<N> getPath(final N source, final N target) {
        final List<N> path = new ArrayList<>();
        final Map<N, N> allPaths = ResearchAlgorithm.breadthFirstSearch(edges, source);
        
        for (final var x : allPaths.entrySet()) {
            var y = x;
            while (y != null) {
                System.out.print(y.getKey() + "->");
                y = Map.entry(y.getValue(), allPaths.get(y.getValue()));
            }
            System.out.print("nul");
        }
        
        return path;
    }
}
