/*
 * Copyright (C) 2013 - 2020 Oracle and/or its affiliates. All rights reserved.
 */
package oracle.pgx.algorithms;

import oracle.pgx.algorithm.EdgeProperty;
import oracle.pgx.algorithm.annotations.GraphAlgorithm;
import oracle.pgx.algorithm.PgxEdge;
import oracle.pgx.algorithm.PgxGraph;
import oracle.pgx.algorithm.PgxMap;
import oracle.pgx.algorithm.PgxVertex;
import oracle.pgx.algorithm.VertexProperty;
import oracle.pgx.algorithm.annotations.Out;

@GraphAlgorithm
public class Prim {
  public double mst(PgxGraph g, EdgeProperty<Double> weight, @Out EdgeProperty<Boolean> inMst) {
    if (g.getNumVertices() == 0) {
      return 0.0;
    }

    PgxMap<PgxEdge, Double> q = PgxMap.create();
    PgxVertex root = g.getRandomVertex();

    root.getNeighbors().filter(n -> n != root).forSequential(n -> {
      PgxEdge e = n.edge();
      q.set(e, weight.get(e));
    });

    VertexProperty<Boolean> processed = VertexProperty.create();
    processed.setAll(false);
    processed.set(root, true);
    inMst.setAll(false);

    int numNodes = 1;
    double totalWeight = 0.0;

    while (numNodes < g.getNumVertices() && q.size() > 0) {
      PgxEdge newEdge = q.getKeyForMinValue();
      PgxVertex u = newEdge.sourceVertex();
      PgxVertex v = newEdge.destinationVertex();

      if (processed.get(v)) {
        PgxVertex tmp = v;
        v = u;
        u = tmp;
      }

      q.remove(newEdge);

      if (processed.get(v) != processed.get(u)) {
        inMst.set(newEdge, true);
        processed.set(v, true);
        totalWeight += weight.get(newEdge);

        v.getNeighbors().forEach(n -> {
          PgxEdge e = n.edge();
          if (processed.get(n)) {
            q.remove(e);
          } else {
            q.set(e, weight.get(e));
          }
        });
        numNodes++;
      }
    }

    return totalWeight;
  }
}
