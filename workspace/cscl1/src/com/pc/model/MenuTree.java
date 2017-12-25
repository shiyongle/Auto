package com.pc.model;

/*     构建菜单树    */
import java.util.List;

public class MenuTree {
	private int id;        			  //树ID
	private String text;			 //节点名称
	private String state;			 //是否展开
	private List<MenuTree> children; //子节点
	private  Attributes attributes;  //其它属性（url等）
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
	
    public class Attributes { 
    	private String url;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
    	public Attributes(String url){
    		this.url = url;
    	}
    } 
}

