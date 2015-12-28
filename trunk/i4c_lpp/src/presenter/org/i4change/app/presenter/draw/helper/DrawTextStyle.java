package org.i4change.app.presenter.draw.helper;

/**
 * 
 * @author swagner
 *
 */
public class DrawTextStyle {

	private int height;
	private Boolean isBold;
	private Boolean isItalic;
	private String name;
	
	public DrawTextStyle(int height, Boolean isBold, String name, Boolean isItalic) {
		super();
		this.height = height;
		this.isBold = isBold;
		this.isItalic = isItalic;
		this.name = name;
	}
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Boolean getIsBold() {
		return isBold;
	}
	public void setIsBold(Boolean isBold) {
		this.isBold = isBold;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsItalic() {
		return isItalic;
	}
	public void setIsItalic(Boolean isItalic) {
		this.isItalic = isItalic;
	}
	
}
