package Client;

import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;

public class Verification extends JDialog {

	public static void main(String[] args) {
		try {
			Verification dialog = new Verification();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Verification() {
		setBackground(Color.WHITE);
		setBounds(100, 100, 500, 200);
		getContentPane().setLayout(null);
		
		JLabel lblInvaildCredentials = new JLabel("Invaild Credentials");
		lblInvaildCredentials.setFont(new Font("Sitka Text", Font.BOLD, 15));
		lblInvaildCredentials.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvaildCredentials.setBounds(111, 36, 259, 67);
		getContentPane().add(lblInvaildCredentials);
		{
			JLabel label = new JLabel("");
			label.setIcon(new ImageIcon("C:\\Users\\Owner\\Desktop\\th9CSI2N35.jpg"));
			label.setBounds(61, 13, 125, 118);
			getContentPane().add(label);
		}
		//TrialUI t=new TrialUI();
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			//	t.setVisible(true);
			}
		});
		btnOk.setBounds(373, 111, 97, 25);
		getContentPane().add(btnOk);
	}
}
