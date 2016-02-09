
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//import MainFrame;

public class ToolBar extends JPanel implements ActionListener, Serializable {
	private JMenuBar menuBar;
	private JMenu fileItem;
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem exit;
	private JMenuItem whichBtn;
	private JFileChooser fileChooser;
	private int isApproved;
	private int isSave;

	private ToolBarButtonListener listener;

	public ToolBar() {

		menuBar = new JMenuBar();
		fileItem = new JMenu("File");
		open = new JMenuItem("Open");
		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");
		fileChooser = new JFileChooser();

		menuBar.add(fileItem);
		fileItem.add(open);
		fileItem.addSeparator();
		fileItem.add(save);
		fileItem.addSeparator();
		fileItem.add(exit);

		open.addActionListener(this);
		exit.addActionListener(this);
		save.addActionListener(this);
		add(menuBar);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		whichBtn = ((JMenuItem) e.getSource());
		if (whichBtn == open) {
			
			isApproved = fileChooser.showOpenDialog(this);
			if (isApproved == JFileChooser.APPROVE_OPTION) {
				// ReadFile
				listener.openButtonPressed();
			}

			System.out.println("Open Button Pressed");

		}else if(whichBtn == save){
			listener.saveButtonPressed();
		}else if (whichBtn == exit) {
				System.exit(1);
			}
		}

	public JMenuBar createJMenuBar() {
		return menuBar;
	}

	public void setToolBarListener(ToolBarButtonListener listener) {
		this.listener = listener;
	}

}
