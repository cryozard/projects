package vendingUi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import factory.VendingUtils;

public class ReportButton extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReportButton() {
		super("Emitir relatorios");
		this.setBounds(10, 261, 152, 23);	
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VendingUtils.issueReport();
			}
		});
	}
}
