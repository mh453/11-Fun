
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ChoosePlayer extends JPanel implements ActionListener, MouseListener, Serializable{

	private JLabel nameLabel;
	private JTextField nameField;
	private JLabel test;
	private JLabel playBtn;
	private JLabel heading;
	private JLabel title;
	private JButton addPlayer;
	private JButton enterGame;
	private String clickedCharectar = "";

	private JPanel panelForImages;

	private JLabel characters[];

	JLabelListener listener;

	public ChoosePlayer() {
		setLayout(new GridBagLayout());
		Dimension dim = getPreferredSize();
		dim.width = 390;
		setPreferredSize(dim);
		setBackground(Color.white);
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border titledBorder = BorderFactory.createTitledBorder("Add Player");
		Border border = BorderFactory.createCompoundBorder(emptyBorder,
				titledBorder);
		setBorder(border);

		nameField = new JTextField(10);
		nameLabel = new JLabel("Name: ");
		characters = new JLabel[4];
		addPlayer = new JButton("Add Player");
		enterGame = new JButton("Enter Game!");

		heading = new JLabel("Welcome to the 11+ Game!");
		title = new JLabel("Pick a character!");

		panelForImages = new JPanel();
		panelForImages.setLayout(new GridBagLayout());
		panelForImages.setBackground(Color.WHITE);

		addPlayer.addActionListener(this);
		enterGame.addActionListener(this);
		setLayout();

	}
	public void setLayout(){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.fill = gbc.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = 2;
		add(heading, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		add(nameLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(nameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		add(title, gbc);

		for (int i = 0; i < characters.length; i++) {
			String url = "/resource/blobFinalEdit" + i + ".png";
			String shortend = "blobFinalEdit" + i + ".png";
			characters[i] = new JLabel(loadImage(url, shortend, 75, 60));
			characters[i].addMouseListener(this);
		}

		for (int i = 0; i < characters.length; i++) {
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = i;
			gc.gridy = 0;
			gc.weightx = 0.5;
			gc.weighty = 0;
			gc.fill = gc.NONE;
			gc.anchor = GridBagConstraints.CENTER;
			panelForImages.add(characters[i], gc);
		}
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(panelForImages, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.CENTER;
		add(addPlayer, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.CENTER;
		add(enterGame, gbc);
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

	// Used to get name and image chosen and sends to Controller
	// Used to separate view from backend
	public void setJLabelListener(JLabelListener listener) {
		this.listener = listener;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedBtn = (JButton) e.getSource();

		if (clickedBtn == addPlayer) {
			if (listener != null) {
				if(!(nameField.getText().isEmpty()) && !(clickedCharectar.isEmpty())){
					listener.setName(nameField.getText());
					listener.setCharectarChosenURL(clickedCharectar);
					listener.addPlayerPressed(true);
					
					//Reset Name Field & Chosen Image
					nameField.setText("");
					clickedCharectar = "";
					
				}	
			}
		} else if (clickedBtn == enterGame) {
			listener.exitButtonPressed(true);
		}
	}
	
	public void deactivateAddPlayerButton(){
		addPlayer.setEnabled(false);
	}
	public void deactivatePlayerIcon(String url){
		for(int i=0;i<characters.length;i++){
			//System.out.print(characters[i].getIcon().toString());
			if(characters[i].getIcon().toString().equals(url)){
				characters[i].setEnabled(false);
				//System.out.println("Working");
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Handles which image is clicked
		for (int i = 0; i < characters.length; i++) {
			if (e.getSource() == characters[i]) {
				clickedCharectar = characters[i].getIcon().toString();

			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
