package freightNetworks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;


public class GenerateNetwork {
	static DirectedGraph<FreightNode, FreightLink> freightNetwork;
	public GenerateNetwork(){

	}

	public static DirectedGraph<FreightNode, FreightLink> constructFreightNetwork(String path, String name, String linkType){
		//define the filename
		String filename = null;
		if((name.equals("gauteng"))||(name.equals("ethekwini"))||(name.equals("capetown"))){
			filename = path+name;
		}else{System.out.println("There isn't a linklist for a graph named "+name);}
		if((linkType.equals("trip"))||(linkType.equals("veh"))){
			filename=filename+"_"+linkType+".csv";
		}else{System.out.println("There isn't a linklist for a link type "+linkType);}


		//read in the linklist into an ArrayList
		BufferedReader br1 = null;
		String lineNode = null;
		ArrayList<String> readList = new ArrayList<String>();
		try {
			br1 = new BufferedReader(new FileReader(filename));
			lineNode=br1.readLine(); //Just to discard the heading
			while ((lineNode = br1.readLine())!=null) { //while you are not at the end of the file
				readList.add(lineNode);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br1 != null) {
				try {
					br1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		//Create nodeList and linkList from readList
		int dim = readList.size();
		ArrayList<FreightNode> nodeList = new ArrayList<FreightNode>();
		ArrayList <String> key = new ArrayList<String>(); //this is an additional ArrayList that stores the index and node id of the nodes in nodeList. I had to add this because I don't know how to search for the int id alone without adding all the other fields
		String [] currentString = new String[11];
		int [][] linklist = new int [dim][3];
		for (int i =0; i<dim;i++){ //should it be <= or <? test
			currentString = readList.get(i).split(";");
			linklist[i][0]=Integer.parseInt(currentString[0]);
			linklist[i][1]=Integer.parseInt(currentString[5]);
			linklist[i][2]=Integer.parseInt(currentString[10]);
			for(int y =0;y<=5;y=y+5){
				if(!nodeList.contains(currentString[y])){//if the node is not already in the nodeList
					FreightNode currentNode =new FreightNode(currentString[y], Double.parseDouble(currentString[y+1]), Double.parseDouble(currentString[y+2]), Double.parseDouble(currentString[y+3]), Double.parseDouble(currentString[y+4]));
					nodeList.add(currentNode);
					key.add(currentString[y]);
				}
			}
		}

		//Create the graph from the nodeList and linklist
		freightNetwork = new DirectedSparseMultigraph<FreightNode, FreightLink>();
		String idFrom;
		String idTo;
		for (int k =0; k<dim;k++){
			idFrom = Integer.toString(linklist[k][0]);
			idTo = Integer.toString(linklist[k][1]);
			freightNetwork.addEdge(new FreightLink(Integer.toString(k),linklist[k][2]),nodeList.get(key.indexOf(idFrom)),nodeList.get(key.indexOf(idTo)),EdgeType.DIRECTED);

		}
		return freightNetwork;
	}

}
