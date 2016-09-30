package freightNetworks;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;


public class FreightNode implements Comparable<FreightNode>{
	public String id;
	public double Lon;
	public double Lat;
	public double X;
	public double Y;

	public FreightNode(String id, double Lon, double Lat, double X, double Y) {
		this.id = id;
		this.Lon = Lon;
		this.Lat = Lat;
		this.X = X;
		this.Y = Y;
	}
	
	public String toString() {
		return "id: " + id + " (Lon: "+ Lon + ";Lat: "+ Lat + ")"+" (X: "+ X + ";Y: "+ Y + ")";
	}  
	
	
	public String getId(){
		return this.id;
	}
	
	public double getX(){
		return X;
	}
	
	public double getY(){
		return Y;
	}
	
	public double getLon(){
		return Lon;
	}
	
	public double getLat(){
		return Lat;
	}

	@Override // why does this need to be here?
	public int compareTo(FreightNode o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
