
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SingleBoard extends JPanel implements Serializable{
	private Tile[] tile;

	private String urlIcon;

	private int position;

	private boolean gameOver = false;

	public SingleBoard() {
		super();
		tile = new Tile[12];
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout();

	}

	public void setLayout() {
		GridBagConstraints gc = new GridBagConstraints();
		for (int i = 0; i < tile.length; i++) {
			tile[i] = new Tile();
			gc.gridx = 0;
			gc.gridy = i;
			add(tile[i], gc);
		}
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition(){
		return position;
	}

	public void updatePosition(int updatedPosition) {
		// updates the position so we can go down
		position = position + updatedPosition;

		if (position < 0) {
			position = 0;
		} else if (position >= 11) {
			position = 11;
		}
	}

	public void deleteOldPosition() {
		for (int i = 0; i < tile.length; i++) {
			if (i == position) {
				tile[i].setIcon(null);
				revalidate();
				repaint();
			}
		}
	}

	public void addIcon() {
		for (int i = 0; i < tile.length; i++) {
			if (i == position) {
				tile[i].setIcon(loadImage("/resource/" + urlIcon, urlIcon, 57,
						45));
				tile[i].setHorizontalTextPosition(JLabel.CENTER);

			}
		}
	}

	public void checkIfGameIsOver() {
			if (position == tile.length-1) {
				gameOver = true;
			}
	}

	public void setIcon(String url) {
		this.urlIcon = url;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public static ImageIcon loadImage(String path, String desc, int width,
			int height) {

		ImageIcon icon = createImageIcon(path, desc);

		Image resized = icon.getImage().getScaledInstance(width, height,
				java.awt.Image.SCALE_SMOOTH);

		ImageIcon resizedIcon = new ImageIcon(resized, desc);

		return resizedIcon;

	}

	// Check URL Path
	public static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = ChoosePlayer.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

}
