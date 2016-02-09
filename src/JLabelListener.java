
import java.io.Serializable;

public interface JLabelListener extends Serializable{
	public void setName(String name);
	public void setCharectarChosenURL(String url);
	public void addPlayerPressed(boolean b);
	public void exitButtonPressed(boolean b);
}
