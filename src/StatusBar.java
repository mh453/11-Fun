
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel implements Serializable {
	private String text;
	private String ColourOfCounter;
	private JLabel textLabel;

	public StatusBar() {
		super();
		setPreferredSize(new Dimension(100, 30));
		textLabel = new JLabel();
		setLayout(new FlowLayout());
		add(textLabel,FlowLayout.LEFT);
	}

	public void setMessage(String text) {
		this.text = text;
		textLabel.setText(text);
		//System.out.println(text);
	}

	public String whichImage(String imgURL) {
		switch (imgURL) {
		case ("blobFinalEdit0.png"):
			ColourOfCounter = "Pink Bean";
			break;
		case ("blobFinalEdit1.png"):
			ColourOfCounter = "Red Bean";
			break;
		case ("blobFinalEdit2.png"):
			ColourOfCounter =  "Green Bean";
			break;
		case ("blobFinalEdit3.png"):
			ColourOfCounter = "Sky Blue Bean";
			break;
		}
		return ColourOfCounter;
	}
}
