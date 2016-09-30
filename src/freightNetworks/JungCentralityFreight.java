package freightNetworks;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.matsim.core.utils.io.IOUtils;
import edu.uci.ics.jung.graph.DirectedGraph;

public class JungCentralityFreight {
	private final static Logger LOG = Logger.getLogger(JungCentralityFreight.class);
	public static void calculateAndWriteDegreeCentrality(DirectedGraph<FreightNode,FreightLink> freightNetwork, String degreeFile,
			ArrayList<FreightNode> nodeList){
		int currentDegree;
		int currentInDegree;
		int currentOutDegree;
		BufferedWriter bdegree = IOUtils.getBufferedWriter(degreeFile);
		try{
			bdegree.write("NodeID,Lon,Lat,X,Y,C_Dtot,C_Din,C_Dout");
			bdegree.newLine();
			for (int i=0;i<nodeList.size();i++){
				FreightNode current = nodeList.get(i);
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
		
	}
}
