package org.i4change.app.presenter.draw.helper;

import java.util.LinkedList;
import java.util.List;

/**
 * This List must be unique for every Document
 * @author swagner
 *
 */
public class DrawTextStyleList {
	
	List<DrawTextStyle> drawTextStyles = new LinkedList<DrawTextStyle>();
	
	private DrawTextStyle drawTextStyle = null;
	private DrawTextStyle drawTextSpanStyle = null;
	private DrawTextStyle drawTextStyleRole = null;
	private DrawTextStyle drawTextStyleItalic = null;
	
	private DrawTextStyle drawTextStyleFooter = null;
	
	private DrawTextStyle drawTextStyleUnScaled = null;
	private DrawTextStyle drawTextStyleRoleUnScaled = null;
	
	private int startIndentNumber = 1;

	private DrawTextStyle lineTextStyle = null;
	
	public DrawTextStyleList(int startIndentNumber,int startIndentSpanNumber){
		this.startIndentNumber = startIndentNumber;
	}
	
	public DrawTextStyle addDrawTextStyle(int height, Boolean isBold, Boolean isItalic) {
		String baseNameShapeText = "P";
		DrawTextStyle drawTextStyle = new DrawTextStyle(height, isBold, baseNameShapeText+startIndentNumber, isItalic);
		this.drawTextStyles.add(drawTextStyle);
		this.startIndentNumber++;
		return drawTextStyle;
	}

	public List<DrawTextStyle> getDrawTextStyles() {
		return drawTextStyles;
	}
	
	public void setDrawTextStyle(Integer height, boolean isBold) {
		this.drawTextStyle = this.addDrawTextStyle(height, isBold, false);
	};
	
	public void setDrawTextSpanStyle(Integer height, boolean isBold) {
		this.drawTextSpanStyle = this.addDrawTextStyle(height, isBold, false);
	}
	
	public void setLineTextStyle(Integer height, boolean isBold) {
		this.lineTextStyle  = this.addDrawTextStyle(height, isBold, false);
	} 

	public DrawTextStyle getDrawTextStyleRole() {
		return drawTextStyleRole;
	}

	public void setDrawTextStyleRole(Integer height, boolean isBold) {
		this.drawTextStyleRole = this.addDrawTextStyle(height, isBold, false);
	}
	
	public DrawTextStyle getDrawTextStyleUnScaled() {
		return drawTextStyleUnScaled;
	}

	public void setDrawTextStyleUnScaled(Integer height, boolean isBold) {
		this.drawTextStyleUnScaled = this.addDrawTextStyle(height, isBold, false);
	}

	public DrawTextStyle getDrawTextStyleRoleUnScaled() {
		return drawTextStyleRoleUnScaled;
	}

	public void setDrawTextStyleRoleUnScaled(Integer height, boolean isBold) {
		this.drawTextStyleRoleUnScaled = this.addDrawTextStyle(height, isBold, false);
	}

	public DrawTextStyle getDrawTextStyleFooter() {
		return drawTextStyleFooter;
	}
	public void setDrawTextStyleFooter(Integer height, boolean isBold) {
		this.drawTextStyleFooter = this.addDrawTextStyle(height, isBold, false);
	}

	public DrawTextStyle getDrawTextStyle() {
		return drawTextStyle;
	}

	public DrawTextStyle getDrawTextSpanStyle() {
		return drawTextSpanStyle;
	};
	
	public DrawTextStyle getLineTextStyle() {
		return lineTextStyle;
	}

	public DrawTextStyle getDrawTextStyleItalic() {
		return drawTextStyleItalic;
	}
	public void setDrawTextStyleItalic(Integer height) {
		this.drawTextStyleItalic = this.addDrawTextStyle(height, false, true);
	}
	
}
