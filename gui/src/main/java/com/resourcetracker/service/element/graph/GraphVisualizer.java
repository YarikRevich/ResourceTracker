package com.resourcetracker.service.element.graph;

import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import com.resourcetracker.service.element.IElementResizable;
import org.springframework.stereotype.Service;

@Service
public class GraphVisualizer implements IElementResizable<SmartGraphPanel<String, String>> {
  private SmartGraphPanel<String, String> panel;

  public GraphVisualizer() {
    Graph<String, String> g = new GraphEdgeList<>();
    g.insertVertex("A");
    g.insertVertex("B");
    g.insertVertex("C");
    g.insertVertex("D");
    g.insertVertex("E");
    g.insertVertex("F");
    g.insertVertex("G");

    g.insertEdge("A", "B", "1");
    g.insertEdge("A", "C", "2");
    g.insertEdge("A", "D", "3");
    g.insertEdge("A", "E", "4");
    g.insertEdge("A", "F", "5");
    g.insertEdge("A", "G", "6");

    g.insertVertex("H");
    g.insertVertex("I");
    g.insertVertex("J");
    g.insertVertex("K");
    g.insertVertex("L");
    g.insertVertex("M");
    g.insertVertex("N");

    g.insertEdge("H", "I", "7");
    g.insertEdge("H", "J", "8");
    g.insertEdge("H", "K", "9");
    g.insertEdge("H", "L", "10");
    g.insertEdge("H", "M", "11");
    g.insertEdge("H", "N", "12");

    g.insertEdge("A", "H", "0");

    SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();

    this.panel = new SmartGraphPanel<>(g, strategy);
  }

  /**
   * @return
   */
  @Override
  public SmartGraphPanel<String, String> getContent() {
    return panel;
  }

  /**
   * @param value window width value.
   */
  @Override
  public void prefWidth(Double value) {}

  /**
   * @param value window height value.
   */
  @Override
  public void prefHeight(Double value) {}
}
