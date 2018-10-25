package se.kth.jpf_visual;

import java.util.ArrayList;
import java.util.List;

public class TextLineList {
	List<TextLine> list = new ArrayList<>();
	boolean noSrc = false;

	public TextLineList(List<TextLine> l) {
		this.list = l;
	}

	public void setNoSrc(boolean b) {
		this.noSrc = b;
	}

	public boolean isNoSrc() {
		return noSrc;
	}

	public List<TextLine> getList() {
		return new ArrayList<>(list);
	}

	public int getHeight() {
		return list.size();
	}
	
	public TextLine getTextLine(int idx){
		return list.get(idx);
	}
}
