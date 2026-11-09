import javax.swing.SwingUtilities;
import gui.GameWindow;

public class Main{
	public static void main (String [] args) {
		SwingUtilities.invokeLater(()-> {
			GameWindow window = new GameWindow();
			window.setVisible(true);
		});
	}
}