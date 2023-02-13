package vendingUi;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class VendingScreen extends JScrollPane{
	private static final long serialVersionUID = 1L;
	private static final Color BACKGROUND = new Color(0, 0, 0);
	private static final Color TEXT_COLOR = new Color(0, 255, 0);
	private JTextPane textPane;
	
	public VendingScreen() {this(new JTextPane());}
	public VendingScreen(JTextPane textPane) {
		super(textPane);
		this.textPane = textPane;
		textPane.setBackground(BACKGROUND);
		textPane.setForeground(TEXT_COLOR);
		textPane.setBounds(172, 117, 252, 195);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBounds(172, 117, 252, 195);
		new SmartScroller(this, SmartScroller.VERTICAL, SmartScroller.END);
	}
	
	public void addText(String text) {
		String currentText = textPane.getText();
		textPane.setText(currentText+'\n'+text);
	}
}
