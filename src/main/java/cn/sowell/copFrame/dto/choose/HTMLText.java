package cn.sowell.copFrame.dto.choose;

public class HTMLText implements HTMLElement{

	private String text;
	private HTMLTag parent;
	public HTMLText(String text){
		this.text = text;
	}
	
	@Override
	public String getOuterHtml() {
		return this.text;
	}

	@Override
	public HTMLTag getParent() {
		return this.parent; 
	}
	
	@Override
	public String text() {
		return text;
	}
	
	void setParent(HTMLTag parent){
		this.parent = parent;
	}

}
