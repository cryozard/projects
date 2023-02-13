package vendingUi;
import java.awt.EventQueue;

import javax.swing.JFrame;

import factory.VendingUtils;

public class VendingMachine extends JFrame{
	private static final long serialVersionUID = 1L;
	static VendingMachine machine;
	VendingScreen screen;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VendingUtils.initMachine();
					if(VendingMachine.machine == null) VendingMachine.machine = new VendingMachine();
					VendingMachine.machine.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VendingMachine() {
		this.setResizable(false);
		this.setBounds(100, 100, 450, 362);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.screen = new VendingScreen();
		
		this.getContentPane().add(new ProductPane());
		this.getContentPane().add(new CoinPanel());
		
		getContentPane().add(screen);
		
		getContentPane().add(new ReportButton());


	}
}
