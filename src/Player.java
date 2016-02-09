
import java.io.Serializable;

public class Player implements Serializable{
	private String icon;
	private int position;
	private String name;
	
	public Player(String nameOfPlayer) {
		name = nameOfPlayer;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Player [icon=" + icon + ", name=" + name + "]";
	}
}
