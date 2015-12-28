package org.i4change.app.svg;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.util.ArrayList;
import javax.swing.JComponent;

public class MultiLineText extends JComponent {

	Font font = new Font("Verdana", Font.PLAIN, 12);
	String multilineText = "";
	int wrapWidth = 110;

	ArrayList<ArrayList<TextLayout>> layouts = new ArrayList<ArrayList<TextLayout>>();
	TextLayout layout;

	public MultiLineText(String multilineText) {
		this.multilineText = multilineText;
		breakLines();
	}

	public void breakLines() {

		layouts.add(new ArrayList<TextLayout>());

		final AttributedString attStr = new AttributedString(multilineText);
		attStr.addAttribute(TextAttribute.FONT, font);
		final LineBreakMeasurer measurer = new LineBreakMeasurer(attStr
				.getIterator(), new FontRenderContext(null, true, true));

	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		FontMetrics metrics = g2.getFontMetrics(font);

		int textHeight = metrics.getHeight();
		int left = textHeight; // only for symmetry, could be different

		for (int i = 0; i < layouts.size(); i++) {

			int top = textHeight;
			for (int j = 0; j < layouts.get(i).size(); j++) {

				layout = (TextLayout) layouts.get(i).get(j);
				layout.draw(g2, left, top);

				top += textHeight;
			}
			left += wrapWidth + textHeight;
		}
	}
	
}
