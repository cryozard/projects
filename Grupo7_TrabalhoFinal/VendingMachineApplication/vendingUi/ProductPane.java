package vendingUi;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import factory.VendingUtils;

public class ProductPane extends JScrollPane{
	
	private static final long serialVersionUID = 1L;

	public ProductPane() {
		this(new JPanel());
	}
	private ProductPane(JPanel panel) {
		super(panel);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBounds(10, 11, 152, 239);

		panel.setLayout(new GridLayout());
		
		String[] products = VendingUtils.getNameProducts();
		panel.setLayout(new GridLayout(products.length,1));
		for(String s: products)
			panel.add(new ProductButton(s));
	}
	
	private class ProductButton extends JButton{
		
		private static final long serialVersionUID = 1L;
		private String description;
		public ProductButton(String description) {
			super(description);
			
			this.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					VendingMachine.machine.screen.addText(VendingUtils.chooseProduct(ProductButton.this.description));
				}
			});
			
			this.description = description;
		}
		
	}
}
