
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Tile extends JLabel implements Serializable{
	Random random = new Random();
	Color colour;

	public Tile() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		Dimension dim = getPreferredSize();
		dim.width = 60;
		dim.height = 50;
		setPreferredSize(dim);
		niceColourForBackground();
	}

//	public void startTile() {
//		setText("START");
//		setHorizontalAlignment(JLabel.CENTER);
//	}

	public void niceColourForBackground() {
		final float hue = random.nextFloat();
		final float saturation = 0.9f;
		final float luminance = 1.0f;
		colour = Color.getHSBColor(hue, saturation, luminance);
		setOpaque(true);
		setBackground(colour);
	}
}
