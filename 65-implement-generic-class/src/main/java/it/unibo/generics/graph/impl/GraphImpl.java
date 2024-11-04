package it.unibo.generics.graph.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibo.generics.graph.api.Graph;

public class GraphImpl<N> implements Graph<N> {

    private final Map<N, List<N>> edges;

    public GraphImpl() {
        this.edges = new HashMap<>();
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
        final Map<N, N> allPaths = ResearchAlgorithm.depthFirstSearch(this.edges, source);
        return getPathRecursive(allPaths, source, target);
    }
    
    public List<N> getPath(final N source, final N target, final Algorithm algorithm) {
        final Map<N, N> allPaths;
        if (algorithm.equals(Algorithm.BFS)) {
            allPaths = ResearchAlgorithm.breadthFirstSearch(this.edges, source);
        } else if (algorithm.equals(Algorithm.DFS)) {
            allPaths = ResearchAlgorithm.depthFirstSearch(this.edges, source);
        } else {
            throw new IllegalStateException("Invalid research algorithm");
        }
        return getPathRecursive(allPaths, source, target);
    }

    private List<N> getPathRecursive(final Map<N, N> allPaths, final N s, final N d) {
        if (s.equals(d)) {
            final List<N> list = new ArrayList<>();
            list.add(s);
            return list;
        } else if (allPaths.get(d) == null) {
            return Collections.emptyList();
        } else {
            final List<N> list = getPathRecursive(allPaths, s, allPaths.get(d));
            list.add(d);
            return list;
        }
    }
}
