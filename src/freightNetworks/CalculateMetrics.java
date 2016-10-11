package freightNetworks;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.matsim.core.utils.io.IOUtils;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.algorithms.scoring.PageRank;


public class CalculateMetrics {
	private final static Logger LOG = Logger.getLogger(CalculateMetrics.class);
	private DirectedGraph<FreightNode, FreightLink> freightNetwork;
	//private String path;
	public CalculateMetrics(String path, String name, String linkType){
		//this.path = path;
		this.freightNetwork = GenerateNetwork.constructFreightNetwork(path,name, linkType);	
		LOG.info("Freight graph created");	

	}
	public void calculateDegree(String path, Collection<FreightNode> nodeList){
	//Calculates unweighted node degree
		int currentDegree;
		int currentInDegree;
		int currentOutDegree;
		String degreeFile = path+"unweightedDegree.csv";
		BufferedWriter nodedegree = IOUtils.getBufferedWriter(degreeFile);
		try{
			nodedegree.write("NodeID,Lon,Lat,X,Y,C_Dtot,C_Din,C_Dout");
			nodedegree.newLine();
			Iterator<FreightNode> nodeIterator = nodeList.iterator();
			while (nodeIterator.hasNext()) { //Iterate through the nodeList calculating the degrees for each node
				FreightNode current =nodeIterator.next();
				currentDegree=freightNetwork.degree(current);
				currentInDegree=freightNetwork.inDegree(current);
				currentOutDegree=freightNetwork.outDegree(current);
				nodedegree.write(String.format("%s,%s,%s,%s,%s,%d,%d,%d\n", 
						current.getId(),
						Double.toString(current.getLon()),
						Double.toString(current.getLat()),
						Double.toString(current.getX()),
						Double.toString(current.getY()),
						currentDegree,currentInDegree,currentOutDegree));
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("Oops, couldn't write to file.");
		} finally{
			try {
				nodedegree.close();
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error("Oops, couldn't close");
			}
		}
		LOG.info("Node degree written to file (unweighted)");	
	}
	public void calculatePageRank(String path, Collection<FreightNode> nodeList){
	//Calculates unweighted PageRank Score
		
	//TODO Currently this produces the same score for all vertices regardless of alpha and the heterogeneity of the degree distribution.
	//I must test whether I get the same results with EigenvectorCentrality 
	//and must also test whether the networks are strongly connected.	
		
	//TODO add a weighted component
		
		String pagerankFile = path+"pageRank.csv";
		PageRank<FreightNode,FreightLink> pageRank = new PageRank<FreightNode,FreightLink>(freightNetwork, 1);
		BufferedWriter prank = IOUtils.getBufferedWriter(pagerankFile);
		try{
			prank.write("NodeID,Lon,Lat,X,Y,PageRankScore");
			prank.newLine();
			Iterator<FreightNode> nodeIterator = nodeList.iterator();
			while (nodeIterator.hasNext()) { //Iterate through the nodeList calculating the degrees for each node
				FreightNode current =nodeIterator.next();
				prank.write(String.format("%s,%s,%s,%s,%s,%.5f\n", 
						current.getId(),
						Double.toString(current.getLon()),
						Double.toString(current.getLat()),
						Double.toString(current.getX()),
						Double.toString(current.getY()),
						pageRank.getVertexScore(current)));
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("Oops, couldn't write to file.");
		} finally{
			try {
				prank.close();
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error("Oops, couldn't close");
			}
		}
		LOG.info("PageRank scores written to file (unweighted)");
	}

	public void calculateEigenCentrality(){
		//TODO implement calculation of Eigenvector Centrality - weighted and unweighted 
	}
	
	public void calculateNodeBetweenness(){
		//TODO implement calculation of Betweenness Centrality - weighted and unweighted 
	}
	
	public void calculateLinkBetweenness(){
		//TODO figure out a way to (efficiently) calculate link betweenness - weighted and unweighted 
	}
	
	public void calculateLinkSalience(){
		//TODO figure out a way to (efficiently) calculate link salience 
		//I could use Johan's implementation but then I first have to transform to GraphML format.
	}
	
	public void calculateCloseness(){
		//TODO implement calculation of Closeness Centrality - weighted and unweighted 
	}
	
	public void calculateWeakComponent(){
		//TODO implement calculation of weak component 
		// http://jung.sourceforge.net/doc/api/edu/uci/ics/jung/algorithms/cluster/WeakComponentClusterer.html
	}
	public void clustererEdgeBet(){
		//TODO implement EdgeBetweennessClusterer
		//http://jung.sourceforge.net/doc/api/edu/uci/ics/jung/algorithms/cluster/EdgeBetweennessClusterer.html
	}
	public void shortestPathSet(){
		//TODO enumerate the shortest path sets like I did in GridExperiments
		//TODO calculate the avg shortest path length and diameter from enumerated sets
	}
	public void calculateClust(){
		//TODO implement clustering coefficient 
	}
	public void triadicCensus(){
		//TODO implement Triadic Census
	}
	public static void main(String[] args) throws FileNotFoundException{
		String path = args [0]; //path to folder with linklist
		String name = args[1]; //network identifier
		String linkType = args[2]; //specifies what a link represents in the linkList

		CalculateMetrics cMet = new CalculateMetrics(path,name,linkType);
		String metricPath=path+name+"_"+linkType+"_";
		//create nodeList here for use in other methods
		Collection<FreightNode> nodeList = cMet.freightNetwork.getVertices();
		cMet.calculateDegree(metricPath,nodeList);
		cMet.calculatePageRank(metricPath, nodeList);
	}

}
