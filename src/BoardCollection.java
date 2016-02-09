
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BoardCollection extends JPanel implements Serializable{
	private ArrayList<SingleBoard> singleBoardCollection;
	private int noOfBoards;

	public BoardCollection() {
		setLayout(new GridBagLayout());
		//setBorder(BorderFactory.createLineBorder(Color.black));
		Dimension dim = getPreferredSize();
		dim.width = 600;
		setPreferredSize(dim);
		singleBoardCollection = new ArrayList<SingleBoard>();
	}

	public void noOfBoards(int numberOfBoard) {
		for(int i=0;i<numberOfBoard;i++){
			singleBoardCollection.add(new SingleBoard());
		}
	}


	public void setLayout() {
		GridBagConstraints gc = new GridBagConstraints();
		for (int i = 0; i <singleBoardCollection.size(); i++) {
			switch (i) {
			case 0:
				gc.gridx = 0;
				gc.weightx = 0.1;
				add(singleBoardCollection.get(i),gc);
				break;
			case 1:
				gc.gridx = 1;
				gc.weightx = 0.1;
				add(singleBoardCollection.get(i),gc);
				break;
			case 2:
				gc.gridx = 2;
				gc.weightx = 0.1;
				add(singleBoardCollection.get(i),gc);
				break;
			case 3:
				gc.gridx = 3;
				gc.weightx = 0.1;
				add(singleBoardCollection.get(i),gc);
				break;
			}
			//add(singleBoardCollection[i],gc);
		}

	}
	public ArrayList<SingleBoard> getBoardArray(){
		return singleBoardCollection;
	}

}
