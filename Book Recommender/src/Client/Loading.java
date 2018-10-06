package Client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Loading extends JDialog {

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		try {
			
			Loading dialog = new Loading();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Loading() {
		getContentPane().setMinimumSize(new Dimension(700, 200));
		setLocationRelativeTo(null);
		setUndecorated(true);
		setResizable(false);
		setMinimumSize(new Dimension(700, 160));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		{
			JLabel lblLoading = new JLabel("Loading...");
			lblLoading.setBounds(266, 182, 172, 29);
			lblLoading.setHorizontalAlignment(SwingConstants.CENTER);
			lblLoading.setHorizontalTextPosition(SwingConstants.CENTER);
			lblLoading.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 16));
			getContentPane().add(lblLoading);
		}
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setBounds(0, 0, 700, 300);
			lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Owner\\Desktop\\380_display-book-shelfsmall.jpg"));
			getContentPane().add(lblNewLabel);
		}
	}

}
