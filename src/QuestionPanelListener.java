
import java.io.Serializable;

public interface QuestionPanelListener extends Serializable{
	public void selectMoreAnswers(String message);
	public void correctAmountOfAnswersSelected();
	public void loadNextQuestion();
	public void answeredCorretly(int advanceMove);
	public void answeredIncorrectly(int moveBack);
}
