package freightNetworks;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.matsim.core.utils.io.IOUtils;

import edu.uci.ics.jung.graph.DirectedGraph;


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
		int currentDegree;
		int currentInDegree;
		int currentOutDegree;
		String degreeFile = path+"unweightedDegree.csv";
		BufferedWriter bdegree = IOUtils.getBufferedWriter(degreeFile);
		try{
			bdegree.write("NodeID,Lon,Lat,X,Y,C_Dtot,C_Din,C_Dout");
			bdegree.newLine();
			Iterator<FreightNode> nodeIterator = nodeList.iterator();
			while (nodeIterator.hasNext()) { //Iterate through the nodeList calculating the degrees for each node
				FreightNode current =nodeIterator.next();
				currentDegree=freightNetwork.degree(current);
				currentInDegree=freightNetwork.inDegree(current);
				currentOutDegree=freightNetwork.outDegree(current);
				bdegree.write(String.format("%s,%s,%s,%s,%s,%d,%d,%d\n", 
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
				bdegree.close();
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error("Oops, couldn't close");
			}
		}
		LOG.info("Node degree written to file");	
	}
	public static void main(String[] args) throws FileNotFoundException{
		String path = args [0]; //path to folder with linklist
		String name = args[1]; //network identifier
		String linkType = args[2]; //specifies what a link represents in the linkList

		CalculateMetrics cMet = new CalculateMetrics(path,name,linkType);
		String degreePath=path+name+"_"+linkType+"_";
		//create nodeList here for use in other methods
		Collection<FreightNode> nodeList = cMet.freightNetwork.getVertices();
		cMet.calculateDegree(degreePath,nodeList);
	}

}
