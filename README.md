# localLab

This repository holds all the Java classes I use to create networks and measure their metrics for the purposes of my PhD research.

## freightNetworks

This is the Java package currently being developed and under code review. This package contains classes that create a directed network from an edgelist/lnklist file and then calculates metrics related to the network. 

The following checklist shows the basic metrics that can be calculated and those that I still have to code:
* [X] Degree centrality (In, Out and Total)
* [ ] Betweenness centrality (Link, Node, Weighted, Unweighted)
* [ ] Link salience
* [ ] Diameter and average shortest path
* [ ] Closeness centrality (Weighted, Unweighted)
* [ ] Enumerate shortest path sets

For now I have loaded a small sample of the real data in *./data/freightNetworksTest*. I have tested it locally and it executes correctly. I have also verified the output.

## gridExperiments

This is not currently up for code review.


