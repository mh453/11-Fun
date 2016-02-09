
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Majid on 22/12/2015.
 */
public class questionModel implements Serializable{
  private String questionType;
  private String title;
  private int numberOfPossibleSolution;
  private String possibleSolution[];
  private ArrayList<Integer> correctSolutionNumber;
  private int advanceBy;
  private int goBackBy;

  public questionModel()
  {
	  correctSolutionNumber = new ArrayList<Integer>();
  }

public String getQuestionType() {
	return questionType;
}

public void setQuestionType(String questionType) {
	this.questionType = questionType;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public int getNumberOfPossibleSolution() {
	return numberOfPossibleSolution;
}

public void setNumberOfPossibleSolution(int numberOfPossibleSolution) {
	this.numberOfPossibleSolution = numberOfPossibleSolution;
}

public String[] getPossibleSolution() {
	return possibleSolution;
}

public void setPossibleSolution(String[] possibleSolution) {
	this.possibleSolution = possibleSolution;
}

public ArrayList<Integer> getCorrectSolutionNumber() {
	return correctSolutionNumber;
}

public void setCorrectSolutionNumber(ArrayList<Integer> correctSolutionNumberS) {
	this.correctSolutionNumber = correctSolutionNumberS;
}

public int getAdvanceBy() {
	return advanceBy;
}

public void setAdvanceBy(int advanceBy) {
	this.advanceBy = advanceBy;
}

public int getGoBackBy() {
	return goBackBy;
}

public void setGoBackBy(int goBackBy) {
	this.goBackBy = goBackBy;
}

@Override
public String toString() {
	System.out.println("Amount of Correct Solution: " + correctSolutionNumber.size());
	return "questionModel [questionType=" + questionType + ", title=" + title
			+ ", numberOfPossibleSolution=" + numberOfPossibleSolution
			+ ", possibleSolution=" + Arrays.toString(possibleSolution)
			+ ", correctSolutionNumber=" + correctSolutionNumber
			+ ", advanceBy=" + advanceBy + ", goBackBy=" + goBackBy + "]";
}
}
