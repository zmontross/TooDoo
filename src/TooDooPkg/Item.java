package TooDooPkg;

import java.io.Serializable;

public class Item implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String defaultTitle = "New Item";
	private static final String defaultContent = "New Item's Content";
	
	private String title;
	private String content;
	
	//############### Constructors ###############
	
	public Item(){
		setTitle(defaultTitle);
		setContent(defaultContent);
	}
	
	public Item(String title){
		setTitle(title);
		setContent(defaultContent);
	}
	
	public Item(String title, String content){
		setTitle(title);
		setContent(content);
	}
	
	
	//############### Get ###############
	
	public String getTitle(){
		return this.title;
	}
	
	public String getContent(){
		return this.content;
	}
	
	public String getDefaultTitle(){
		return defaultTitle;
	}
	
	public String getDefaultContent(){
		return defaultContent;
	}
	
	//############### Set ###############
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	
	//############### Overridden ###############
	
	public String toString(){
		return this.title;
	}
}
