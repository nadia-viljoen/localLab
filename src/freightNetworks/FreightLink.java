package freightNetworks;

public class FreightLink implements Comparable<FreightLink>{
	String id;
	double weight;//capacity in this case
	
	public FreightLink(String id, double weight) {
		this.id = id;
		this.weight = weight;
	}
	
	public String getId(){
		return id;
	}
	
	public double getWeight(){
		return weight;
	}
	
	public String toString() {
		return "id:" + id + " (weight: " + weight + ")";
	}

	@Override
	public int compareTo(FreightLink o) {
		return this.getId().compareTo(o.getId());
	}
}
