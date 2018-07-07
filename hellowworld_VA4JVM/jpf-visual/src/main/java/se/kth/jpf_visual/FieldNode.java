package se.kth.jpf_visual;

public class FieldNode {   
	public String clsName;
	public String fieldName;

	public FieldNode(String clsName, String fieldName) {
		this.clsName = clsName;
		this.fieldName = fieldName;
	}

	public String toString() {
		return this.fieldName;
	}

}