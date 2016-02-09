
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Majid on 22/12/2015.
 */
public class readQuestion implements Serializable{
	private File file;
	private Scanner inputFromTextFile;
	private ArrayList<questionModel> collectionOfQuestions;

	private String questionType;
	private String title;
	private int noOfPossibleSolution;
	private String possibleSolution[];
	private ArrayList<Integer> correctAnswerNumber;
	private int advance;
	private int goBack;
	private questionModel question;
	private String delims = "[,]";
	Random rand;

	public readQuestion() {
		question = new questionModel();
		collectionOfQuestions = new ArrayList<questionModel>();
		 rand = new Random();
		file = new File("questions.txt");
		loadFile();
	}

	public void loadFile() {
		try {
			inputFromTextFile = new Scanner(file);

			while (inputFromTextFile.hasNextLine()) {

				questionType = inputFromTextFile.nextLine();
				//System.out.println(questionType);
				title = inputFromTextFile.nextLine();
				//System.out.println(title);
				noOfPossibleSolution = Integer.parseInt(inputFromTextFile
						.nextLine());
				//System.out.println(noOfPossibleSolution);
				possibleSolution = new String[noOfPossibleSolution];

				for (int i = 0; i < possibleSolution.length; i++) {
					possibleSolution[i] = inputFromTextFile.nextLine();
					//System.out.println(i + ": " + possibleSolution[i]);
				}
				correctAnswerNumber = new ArrayList<Integer>();
								//Might be Here!//
				for(String questionNumber : inputFromTextFile.nextLine().split(delims)){
					correctAnswerNumber.add(Integer.parseInt(questionNumber));
				}
				//System.out.println(correctAnswerNumber);
				advance = Integer.parseInt(inputFromTextFile.nextLine());
				//System.out.println(advance);
				goBack = Integer.parseInt(inputFromTextFile.nextLine());
				//System.out.println(goBack);

				question = new questionModel();
				question.setQuestionType(questionType);
				question.setTitle(title);
				question.setNumberOfPossibleSolution(noOfPossibleSolution);
				question.setPossibleSolution(possibleSolution);
				question.setCorrectSolutionNumber(correctAnswerNumber);
				question.setAdvanceBy(advance);
				question.setGoBackBy(goBack);
				collectionOfQuestions.add(question);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<questionModel> getQuestion(){
		return collectionOfQuestions;	
	}
	
	public questionModel getRandomQuestion(){
		return collectionOfQuestions.get(rand.nextInt(collectionOfQuestions.size()));
		
	}

	public void showQuestion() {
		for (questionModel model : collectionOfQuestions) {
			System.out.println(model.toString());
			System.out.println("-------------------------------");
		}
	}

}
