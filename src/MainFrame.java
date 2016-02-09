
//import BoardCollection;
//import ChoosePlayer;
//import JLabelListener;
//import QuestionPanel;
//import QuestionPanelListener;
//import SingleBoard;
//import StatusBar;
//import ToolBar;
//import ToolBarButtonListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.*;

//import Player;
//import questionModel;
//import readQuestion;

/**
 * Created by Majid on 22/12/2015.
 */
public class MainFrame extends JFrame {
	private ToolBar toolBar;
	private ChoosePlayer choosePlayerScreen;
	private StatusBar statusBar;
	private QuestionPanel questionPanel;
	private BoardCollection gameBoard;
	private questionModel qs;
	private readQuestion readingQuestion;

	// Data Model
	private String nameOfPlayer;
	private String imageURL;
	private Map<String, Player> mapOfPlayers;
	private ArrayList<String> keys;
	boolean isGameOver = false;
	private int turn;

	public MainFrame() {

		super("11+ Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// promptBeforeClose();
		setSize(1080, 700);
		setVisible(true);
		setMinimumSize(new Dimension(1080, 700));

		setLayout(new BorderLayout());

		// mapOfPlayers = new LinkedHashMap<String, String>();
		mapOfPlayers = new LinkedHashMap<String, Player>();
		keys = new ArrayList<String>();

		toolBar = new ToolBar();
		choosePlayerScreen = new ChoosePlayer();
		statusBar = new StatusBar();
		questionPanel = new QuestionPanel();
		gameBoard = new BoardCollection();

		readingQuestion = new readQuestion();
		qs = new questionModel();
		// Question Panel is not Visible after the ChoosePlayer Screen is
		// exited!
		questionPanel.setVisible(false);
		gameBoard.setVisible(false);

		choosePlayerScreen.setJLabelListener(new JLabelListener() {

			@Override
			public void setName(String name) {
				// System.out.println(name);
				nameOfPlayer = name;
			}

			@Override
			public void setCharectarChosenURL(String url) {
				System.out.println(url);
				imageURL = url;

				choosePlayerScreen.deactivatePlayerIcon(url);

			}

			@Override
			public void addPlayerPressed(boolean b) {

				if (b == true) {
					if (mapOfPlayers.containsKey(imageURL)) {
						statusBar
								.setMessage("Player Not Added Cant Have Same Icon As Other Player!");
					} else {
						mapOfPlayers.put(imageURL, new Player(nameOfPlayer));

						mapOfPlayers.get(imageURL).setIcon(imageURL);

						statusBar.setMessage("Added Player - " + nameOfPlayer
								+ " (" + statusBar.whichImage(imageURL) + ")");
						// System.out.println(mapOfPlayers.get(imageURL));
					}

					if (mapOfPlayers.size() >= 4) {
						choosePlayerScreen.deactivateAddPlayerButton();
						System.out.print(mapOfPlayers.values());

					}
				}
			}

			@Override
			public void exitButtonPressed(boolean b) {
				if (b == true) {
					choosePlayerScreen.setVisible(false);
					// When choosenPlayerScreen is NOT visible then
					// QuestionPanel and Board is visible
					questionPanel.setVisible(true);

					// Reset Status Bar!
					statusBar.setMessage("");

					gameBoard.noOfBoards(mapOfPlayers.size());
					gameBoard.setLayout();

					for (String key : mapOfPlayers.keySet()) {
						keys.add(key);
					}
					int indexs = 0;
					for (SingleBoard board : gameBoard.getBoardArray()) {
						board.setIcon(mapOfPlayers.get(keys.get(indexs))
								.getIcon());
						// At start we are at position 0;
						board.setPosition(0);
						board.addIcon();
						indexs++;
					}
					// Setting status to first player!
					int playerIndexInGame = turn % mapOfPlayers.size();
					gameBoard.getBoardArray().get(playerIndexInGame)
							.checkIfGameIsOver();
					if (!gameBoard.getBoardArray().get(playerIndexInGame)
							.isGameOver() != true) {
						gameOver();
					}
					WhoTurn();
					gameBoard.setVisible(true);
				}

			}
		});

		toolBar.setToolBarListener(new ToolBarButtonListener() {

			@Override
			public void openButtonPressed() {
				openFile();
				gameBoard.setVisible(true);
				questionPanel.setVisible(true);
				choosePlayerScreen.setVisible(false);
				gameBoard.setLayout();
				revalidate();
				repaint();
				System.out.println("Opening");

			}

			@Override
			public void saveButtonPressed() {
				savingData();
			}
		});
		questionPanel.getCollectionOfQuestion(readingQuestion.getQuestion());
		questionPanel.generateQuestion();
		questionPanel.setLabelsAndButtons();
		questionPanel.setButtons();
		questionPanel.positionComponents();

		questionPanel.setListener(new QuestionPanelListener() {
			// Message to Status Bar
			@Override
			public void selectMoreAnswers(String message) {
				statusBar.setMessage(message);
			}

			// Refresh Status Bar to ""
			@Override
			public void correctAmountOfAnswersSelected() {
				statusBar.setMessage("");
			}

			@Override
			public void loadNextQuestion() {
				questionPanel.refresh();
				questionPanel.repaint();
				questionPanel.revalidate();
				questionPanel.generateQuestion();
				questionPanel.setLabelsAndButtons();
				questionPanel.setButtons();
				questionPanel.positionComponents();
			}

			@Override
			public void answeredCorretly(int advanceMove) {

				statusBar.setMessage("Correct Moving " + advanceMove
						+ " Spaces");
				int playerIndexInGame = turn % mapOfPlayers.size();

				gameBoard.getBoardArray().get(playerIndexInGame)
						.checkIfGameIsOver();
				if (gameBoard.getBoardArray().get(playerIndexInGame)
						.isGameOver() != true) {
					WhoTurn();
					Advance(advanceMove);
					// Update Next Player Status!
					WhoTurn();
				} else {
					gameOver();
				}
			}

			@Override
			public void answeredIncorrectly(int moveBack) {
				statusBar.setMessage("Incorrect Moving Back " + moveBack
						+ " Spaces");
				int playerIndexInGame = turn % mapOfPlayers.size();

				gameBoard.getBoardArray().get(playerIndexInGame)
						.checkIfGameIsOver();
				if (gameBoard.getBoardArray().get(playerIndexInGame)
						.isGameOver() != true) {
					System.out.println("Working Aswell");
					WhoTurn();
					GoBack(moveBack);
					// Update Next Player Status!
					WhoTurn();
				} else {
					gameOver();
				}
			}
		});

		setJMenuBar(toolBar.createJMenuBar());

		add(choosePlayerScreen, BorderLayout.CENTER);
		add(statusBar, BorderLayout.NORTH);
		add(questionPanel, BorderLayout.EAST);
		add(gameBoard, BorderLayout.WEST);
		promptBeforeClose();
	}
	public void savingData() {
		String fileName = "GameData.bin";
		// file = new File("GameData.bin");
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(mapOfPlayers);
			oos.writeObject(imageURL);
			oos.writeObject(keys);
			oos.writeObject(gameBoard);			
			oos.writeBoolean(isGameOver);
			oos.writeInt(turn);
			//oos.writeObject(choosePlayerScreen);
			oos.writeObject(statusBar);
			//oos.writeObject(questionPanel);
			oos.writeObject(qs);
			//oos.writeObject(readingQuestion);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}
	
	public void openFile(){
		String fileName = "GameData.bin";
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
			try {
				//I have commented mapOfPlayers and key because they throw cast warning
				//And means you have to compile with Xlint-
				//mapOfPlayers = (HashMap<String, Player>) ois.readObject();
				imageURL = (String) ois.readObject();
				//keys = (ArrayList<String>) ois.readObject();
				gameBoard = (BoardCollection) ois.readObject();
				isGameOver = ois.readBoolean();
				turn = ois.readInt();
				statusBar = (StatusBar) ois.readObject();
				qs = (questionModel) ois.readObject();
				ois.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void promptBeforeClose() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// JDialog Before Closing!
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Exit", "Save" };
				int PromptResult = JOptionPane.showOptionDialog(null,
						"Are you sure you want to exit?", "Exit",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, ObjButtons,
						ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				} else if (PromptResult == JOptionPane.NO_OPTION) {
					savingData();
					System.exit(0);
				}
			}
		});
	}

	public void gameOver() {
		int playerIndex = turn % mapOfPlayers.size();
		JOptionPane.showMessageDialog(this, "Congratulation "
				+ mapOfPlayers.get(keys.get(playerIndex)).getName()
				+ " You Won!", "Winner", JOptionPane.INFORMATION_MESSAGE);
	}

	public void Advance(int increment) {
		int playerIndex = turn % mapOfPlayers.size();

		switch (playerIndex) {
		case 0:
			gameBoard.getBoardArray().get(playerIndex).deleteOldPosition();
			gameBoard.getBoardArray().get(playerIndex)
					.updatePosition(increment);
			gameBoard.getBoardArray().get(playerIndex).addIcon();
			break;
		case 1:
			gameBoard.getBoardArray().get(playerIndex).deleteOldPosition();
			gameBoard.getBoardArray().get(playerIndex)
					.updatePosition(increment);
			gameBoard.getBoardArray().get(playerIndex).addIcon();
			break;
		case 2:
			gameBoard.getBoardArray().get(playerIndex).deleteOldPosition();
			gameBoard.getBoardArray().get(playerIndex)
					.updatePosition(increment);
			gameBoard.getBoardArray().get(playerIndex).addIcon();
			break;
		case 3:
			gameBoard.getBoardArray().get(playerIndex).deleteOldPosition();
			gameBoard.getBoardArray().get(playerIndex)
					.updatePosition(increment);
			gameBoard.getBoardArray().get(playerIndex).addIcon();
			break;
		}
		turn++;
	}

	public void GoBack(int goBack) {
		int playerIndex = turn % mapOfPlayers.size();

		switch (playerIndex) {
		case 0:
			// Setting negated value
			gameBoard.getBoardArray().get(playerIndex).deleteOldPosition();
			gameBoard.getBoardArray().get(playerIndex)
					.updatePosition(goBack = -goBack);
			gameBoard.getBoardArray().get(playerIndex).addIcon();
			break;
		case 1:
			gameBoard.getBoardArray().get(playerIndex).deleteOldPosition();
			gameBoard.getBoardArray().get(playerIndex)
					.updatePosition(goBack = -goBack);
			gameBoard.getBoardArray().get(playerIndex).addIcon();
			break;
		case 2:
			gameBoard.getBoardArray().get(playerIndex).deleteOldPosition();
			gameBoard.getBoardArray().get(playerIndex)
					.updatePosition(goBack = -goBack);
			gameBoard.getBoardArray().get(playerIndex).addIcon();
			break;
		case 3:
			gameBoard.getBoardArray().get(playerIndex).deleteOldPosition();
			gameBoard.getBoardArray().get(playerIndex)
					.updatePosition(goBack = -goBack);
			gameBoard.getBoardArray().get(playerIndex).addIcon();
			break;
		}
		turn++;
	}

	// Tell status bar whos go it is
	public void WhoTurn() {
		int playerIndexs = turn % mapOfPlayers.size();

		switch (playerIndexs) {
		case 0:
			statusBar.setMessage("Current Player - "
					+ mapOfPlayers.get(keys.get(playerIndexs)).getName()
					+ " : "
					+ statusBar.whichImage(mapOfPlayers.get(
							keys.get(playerIndexs)).getIcon()));
			break;
		case 1:
			statusBar.setMessage("Current Player - "
					+ mapOfPlayers.get(keys.get(playerIndexs)).getName()
					+ " : "
					+ statusBar.whichImage(mapOfPlayers.get(
							keys.get(playerIndexs)).getIcon()));
			break;
		case 2:
			statusBar.setMessage("Current Player - "
					+ mapOfPlayers.get(keys.get(playerIndexs)).getName()
					+ " : "
					+ statusBar.whichImage(mapOfPlayers.get(
							keys.get(playerIndexs)).getIcon()));
			break;
		case 3:
			statusBar.setMessage("Current Player - "
					+ mapOfPlayers.get(keys.get(playerIndexs)).getName()
					+ " : "
					+ statusBar.whichImage(mapOfPlayers.get(
							keys.get(playerIndexs)).getIcon()));
			break;
		}
	}

}
