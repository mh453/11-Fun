
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.transform.Templates;

//import questionModel;
//import readQuestion;
//import MainFrame;

public class QuestionPanel extends JPanel implements ActionListener, Serializable {
	private ArrayList<questionModel> questionCollection;
	private questionModel question;
	private JLabel questionType;
	private JLabel questionTitle;
	private ArrayList<JButton> buttonsForQuestion;
	private JButton submit;
	private int correctSolutionAmount;
	private ArrayList<Integer> answers;
	private QuestionPanelListener listener;
	private int numberOfCorrectSolution;
	private String[] buttons;
	private ArrayList<Integer> selectedButtonIndex;

	Random rand;

	public QuestionPanel() {
		setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		setLayout(new GridBagLayout());
		questionType = new JLabel();
		questionTitle = new JLabel();
		buttonsForQuestion = new ArrayList<JButton>();
		Dimension dim = getPreferredSize();
		dim.width = 500;
		setPreferredSize(dim);
		rand = new Random();
		selectedButtonIndex = new ArrayList<Integer>();
	}

	public void getCollectionOfQuestion(
			ArrayList<questionModel> questionCollection) {
		this.questionCollection = questionCollection;
	}

	public void generateQuestion() {
		question = questionCollection.get(rand.nextInt(questionCollection
				.size()));
	}

	public void setLabelsAndButtons() {
		numberOfCorrectSolution = question.getCorrectSolutionNumber().size();
		questionType.setText(question.getQuestionType() + " : "
				+ numberOfCorrectSolution + " Answer - Worth " + question.getAdvanceBy() + " Points");
		questionTitle.setText(question.getTitle());
		buttons = question.getPossibleSolution();
		correctSolutionAmount = question.getCorrectSolutionNumber().size();
	}

	public void setButtons() {
		for (int i = 0; i < buttons.length; i++) {
			// Fill Fresh ArrayList With JButtons
			buttonsForQuestion.add(new JButton(buttons[i]));
			buttonsForQuestion.get(i).setSize(150, 100);
			// buttonsForQuestion.get(i).addActionListener(this);
		}
	}

	public void positionComponents() {
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.weighty = 0.2;
		add(questionType, gc);
		gc.gridy++;
		add(questionTitle, gc);

		for (int i = 0; i < buttonsForQuestion.size(); i++) {
			gc.gridy++;
			buttonsForQuestion.get(i).addActionListener(this);
			add(buttonsForQuestion.get(i), gc);
		}

		gc.gridy++;
		submit = new JButton("Submit");
		submit.addActionListener(this);
		add(submit, gc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton temp = (JButton) e.getSource();
		// Validate To See If the Selected Amount Of Buttons Are Pressed

		for (int i = 0; i < buttonsForQuestion.size(); i++) {
			if (temp == buttonsForQuestion.get(i)) {
				// If Button is White Change Back To Grey
				if (buttonsForQuestion.get(i).getBackground() == Color.YELLOW) {
					buttonsForQuestion.get(i).setBackground(
							new JButton().getBackground());
				} else {
					// Else Set Button To White
					buttonsForQuestion.get(i).setBackground(Color.YELLOW);
				}
			}
		}

		if (temp == submit) {
			int buttonSelected = 0;
			for (int i = 0; i < buttonsForQuestion.size(); i++) {
				if (buttonsForQuestion.get(i).getBackground() == Color.YELLOW) {
					buttonSelected++;
				}
			}

			if (buttonSelected != correctSolutionAmount) {
				// Checking if correct amount of answers are selected!
				listener.selectMoreAnswers("You Need To Select ONLY "
						+ correctSolutionAmount + " Answers");
			} else {
				if (listener != null) {
					for (int i = 0; i < buttonsForQuestion.size(); i++) {
						if (buttonsForQuestion.get(i).getBackground() == Color.YELLOW) {
							//To check answer we use check the index!
							selectedButtonIndex.add(i+1);
						}
					}
					//Set Status Bar to ""
					listener.correctAmountOfAnswersSelected();
					
					if(validateAnswer(selectedButtonIndex)){
						listener.answeredCorretly(question.getAdvanceBy());
					}else{
						listener.answeredIncorrectly(question.getGoBackBy());
					}	
					listener.loadNextQuestion();

				}
			}
		}
	}

	public void refresh() {

		for (JButton button : buttonsForQuestion) {
			this.remove(button);
		}
		this.remove(questionType);
		this.remove(questionTitle);
		this.remove(submit);
		selectedButtonIndex.clear();
		buttonsForQuestion.clear();
		
		correctSolutionAmount = 0;
	}

	public void setListener(QuestionPanelListener listener) {
		this.listener = listener;
	}
	
	public boolean validateAnswer(ArrayList<Integer> clickedButtonIndex){
		answers = question.getCorrectSolutionNumber();
		if(answers.containsAll(clickedButtonIndex)){
			return true;
		}else{
			return false;
		}
	}
}
