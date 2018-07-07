package se.kth.jpf_visual;

public class MethodNode {
	public String clsName;
	public String methodName;

	public MethodNode(String clsName, String methodName) {
		this.clsName = clsName;
		this.methodName = methodName;
	}

	public String toString() {
		return this.methodName;
	}

}