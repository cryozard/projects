package vendingUi;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import factory.VendingUtils;

public class CoinPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public CoinPanel() {
		String[] coins = VendingUtils.getMoney();
		this.setLayout(new GridLayout(coins.length/2,2));
		this.setBackground(new Color(175, 211, 228));
		this.setBounds(163, 11, 261, 95);
		
		for(String s: coins)
			this.add(new CoinButton(s));
	}
	private class CoinButton extends JButton{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String description;
		public CoinButton(String description) {
			super(description);
			this.description = description;
			
			this.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					VendingMachine.machine.screen.addText(VendingUtils.insertCoin(CoinButton.this.description));;
				}
			});
		}
	}
}
