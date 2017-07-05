package graphRepresentation;

public class Assignment {
	private String def, ref;
	
	public Assignment(String x, String y) {
		def = x;
		ref = y;
	}
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String x) {
		ref = x;
	}
	
	public String getDef() {
		return def;
	}
	
	public void setY(String y) {
		def = y;
	}
}
